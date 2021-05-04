package com.serguni.messenger.components;

import com.serguni.messenger.controllers.RootSceneController;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MouseEventHandler {

    enum Side {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public double curWidth;
    public double curHeight;

    private boolean resizeX;
    private boolean resizeY;

    private double xOffset;
    private double yOffset;

    private static final double OFFSET = 5;


    private Side moveSideX;
    private Side moveSideY;


    private final Stage primaryStage;
    private final RootSceneController controller;

    public MouseEventHandler(Stage primaryStage, RootSceneController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

    private boolean isRight(MouseEvent mouseEvent) {
        return mouseEvent.getSceneX() > primaryStage.getWidth() - OFFSET && mouseEvent.getSceneX() < primaryStage.getWidth() + OFFSET;
    }

    private boolean isLeft(MouseEvent mouseEvent) {
        return mouseEvent.getSceneX() > 0 && mouseEvent.getSceneX() < OFFSET;
    }

    private boolean isUp(MouseEvent mouseEvent) {
        return mouseEvent.getSceneY() > 0 && mouseEvent.getSceneY() < OFFSET;
    }

    private boolean isDown(MouseEvent mouseEvent) {
        return mouseEvent.getSceneY() > primaryStage.getHeight() - OFFSET && mouseEvent.getSceneY() < primaryStage.getHeight() + OFFSET;
    }

    public void init() {
        Scene rootScene = primaryStage.getScene();

        rootScene.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
            boolean isDown = isDown(mouseEvent);
            boolean isUp = isUp(mouseEvent);
            boolean isRight = isRight(mouseEvent);
            boolean isLeft = isLeft(mouseEvent);

            if (isDown && isLeft)
                rootScene.setCursor(Cursor.SW_RESIZE);
            else if (isDown && isRight)
                rootScene.setCursor(Cursor.SE_RESIZE);
            else if (isUp && isLeft)
                rootScene.setCursor(Cursor.NW_RESIZE);
            else if (isUp && isRight)
                rootScene.setCursor(Cursor.NE_RESIZE);
            else if (isUp)
                rootScene.setCursor(Cursor.N_RESIZE);
            else if (isDown)
                rootScene.setCursor(Cursor.S_RESIZE);
            else if (isLeft)
                rootScene.setCursor(Cursor.W_RESIZE);
            else if (isRight)
                rootScene.setCursor(Cursor.E_RESIZE);
            else
                rootScene.setCursor(Cursor.DEFAULT);
        });

        rootScene.setOnMousePressed(mouseEventPressed -> {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            resizeX = false;
            resizeY = false;

            if (mouseEventPressed.getSceneX() > primaryStage.getWidth() - OFFSET &&
                    mouseEventPressed.getSceneX() < primaryStage.getWidth() + OFFSET) {
                resizeX = true;
                moveSideX = Side.RIGHT;
            } else {
                xOffset = mouseEventPressed.getSceneX();
            }

            if (mouseEventPressed.getSceneX() > 0 &&
                    mouseEventPressed.getSceneX() < OFFSET) {
                resizeX = true;
                moveSideX = Side.LEFT;
            } else {
                xOffset = mouseEventPressed.getSceneX();
            }

            if (mouseEventPressed.getSceneY() > primaryStage.getHeight() - OFFSET &&
                    mouseEventPressed.getSceneY() < primaryStage.getHeight() + OFFSET) {
                resizeY = true;
                moveSideY = Side.DOWN;
            } else {
                yOffset = mouseEventPressed.getSceneY();
            }

            if (mouseEventPressed.getSceneY() > 0 && mouseEventPressed.getSceneY() < OFFSET) {
                resizeY = true;
                moveSideY = Side.UP;
            } else {
                yOffset = mouseEventPressed.getSceneY();
            }

            rootScene.setOnMouseDragged(mouseEvent -> {

                if (primaryStage.getWidth() == screenBounds.getMaxX() && primaryStage.getHeight() == screenBounds.getMaxY()) {

                    controller.turn();
                    xOffset = primaryStage.getWidth() / 2;
                    yOffset = 0;
                }

                if ( ! resizeX && ! resizeY && !(mouseEvent.getScreenX() > 0 && mouseEvent.getScreenX() < screenBounds.getMaxX() - 2
                        && mouseEvent.getScreenY() > 0 && mouseEvent.getScreenY() < screenBounds.getMaxY() - 40)) {
                    controller.maximize();
                    return;
                }

                if (!resizeX && !resizeY) {

                    primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                    primaryStage.setY(mouseEvent.getScreenY() - yOffset);
                } else {

                    double newWidth = primaryStage.getWidth() + primaryStage.getX() - mouseEvent.getScreenX() + xOffset;
                    double newHeight = primaryStage.getHeight() + primaryStage.getY() - mouseEvent.getScreenY() + yOffset;

                    if (moveSideY == Side.UP) {
                        if (resizeY && newHeight >= primaryStage.getMinHeight() + 20) {
                            primaryStage.setHeight(newHeight);
                            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
                        }
                    }

                    if (moveSideX == Side.LEFT) {
                        if (resizeX && newWidth >= primaryStage.getMinWidth() + 20) {
                            primaryStage.setWidth(newWidth);
                            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                        }
                    }

                    if (moveSideX == Side.RIGHT) {
                        if (resizeX && mouseEvent.getX() > primaryStage.getMinWidth() + 20) {
                            primaryStage.setWidth(mouseEvent.getX());
                        }
                    }

                    if (moveSideY == Side.DOWN) {
                        if (resizeY && mouseEvent.getY() > primaryStage.getMinHeight()) {
                            primaryStage.setHeight(mouseEvent.getY());
                        }
                    }
                }

                curWidth = primaryStage.getWidth();
                curHeight = primaryStage.getHeight();
            });
        });
    }
}
