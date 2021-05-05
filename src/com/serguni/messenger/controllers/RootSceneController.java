package com.serguni.messenger.controllers;

import com.serguni.messenger.Main;
import com.serguni.messenger.components.CustomWindow;
import com.serguni.messenger.components.NativeStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RootSceneController {

    private NativeStage nativeStage;
    @FXML
    public Button turnButton;
    @FXML
    public Button maximizeButton;

    private final List<CustomWindow> resizeDependElements;

    public RootSceneController() {
        resizeDependElements = new ArrayList<>();
    }

    public void changeMenuButton(Button oldButton, Button newButton) {
        newButton.setVisible(true);
        newButton.setDisable(false);

        oldButton.setVisible(false);
        oldButton.setDisable(true);
    }

    public void setTurnButton() {
        changeMenuButton(maximizeButton, turnButton);
    }

    public void setMaximizeButton() {
        changeMenuButton(turnButton, maximizeButton);
    }

    @FXML
    private void close() {
        nativeStage.getStage().close();
//        main.getPrimaryStage().close();
    }

    public void setNativeStage(NativeStage nativeStage) {
        this.nativeStage = nativeStage;
    }

    @FXML
    private void rollUp() {
        nativeStage.getStage().setIconified(true);
    }

    @FXML
    public void maximize() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Stage stage = nativeStage.getStage();

        stage.setX(0);
        stage.setY(0);

        nativeStage.getMouseEvent().curWidth = stage.getWidth();
        nativeStage.getMouseEvent().curHeight = stage.getHeight();

        stage.setWidth(screenBounds.getMaxX());
        stage.setHeight(screenBounds.getMaxY());

        setTurnButton();

        for (CustomWindow window : resizeDependElements) {
            window.cancelWindow();
        }
    }

    @FXML
    public void showAboutAuthorAndRef() throws IOException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:resources/images/messengerfx.png"));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("views/AboutAuthorAndRefWindow.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.showAndWait();
    }

    @FXML
    public void turn() {

        double width = nativeStage.getMouseEvent().curWidth;
        double height = nativeStage.getMouseEvent().curHeight;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        nativeStage.getStage().setX((screenBounds.getWidth() - width) / 2);
        nativeStage.getStage().setY((screenBounds.getHeight() - height) / 2);


        nativeStage.getStage().setHeight(height);
        nativeStage.getStage().setWidth(width);

        setMaximizeButton();
    }

    public void addResizeDependElement(CustomWindow window) {
        resizeDependElements.add(window);
    }
}
