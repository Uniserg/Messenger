package com.serguni.messenger.components;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CustomWindow {
//    private Stack<CustomWindow> windowsStack;

    private Pane window;
    private Runnable cancelSpecFunc;
    private CustomWindow child;
    private CustomWindow parent;
    private double coordinate;


    public CustomWindow(Pane window, double coordinate, CustomWindow parent) {
        this.coordinate = coordinate;
        this.window = window;

        if (parent != null)
            setParent(parent);
    }

    public CustomWindow(Pane window, Runnable cancelSpecFunc) {
        this.window = window;
        this.cancelSpecFunc = cancelSpecFunc;
    }

    private void moveObject(double coordinate) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(250));
        translateTransition.setToX(coordinate);
        translateTransition.setNode(window);
        translateTransition.play();
    }

    public void showWindow() {
        System.out.println("ПОКАЗЫВАЕМ ОКОЩКЛ");
        moveObject(-coordinate);

        Pane parentPane = parent.getWindow();

        parentPane.getChildren().get(0).setDisable(true);
        parentPane.getChildren().get(0).setOpacity(0.9);
        parent.setChild(this);

        parentPane.setOnMouseClicked(mouseEvent -> {
            this.cancelWindow();
            parentPane.setOnMouseClicked(null);
        });
    }


    public void cancelWindow() {
        System.out.println("CANCEL WINDOW");
        moveObject(0);

        parent.free();


        if (cancelSpecFunc != null)
            cancelSpecFunc.run();

        if (child != null && child.window.getTranslateX() != 0) {
            System.out.println(child.window.getTranslateX());
            child.cancelWindow();
            child = null;
        }
    }

    public CustomWindow getChild() {
        return child;
    }

    public void setChild(CustomWindow child) {
//        window.getChildren().get(0).setDisable(true);
//        window.getChildren().get(0).setOpacity(0.9);
//        child.setParent(this);
        this.child = child;
    }

    public void setParent(CustomWindow parent) {
        this.parent = parent;

        //////////////////////////////
        //////////////////////////////////
    }

    public void free() {
        window.getChildren().get(0).setDisable(false);
        window.getChildren().get(0).setOpacity(1);
    }

    public Pane getWindow() {
        return window;
    }

    public void setWindow(Pane window) {
        this.window = window;
    }

    public void setCancelSpecFunc(Runnable cancelSpecFunc) {
        this.cancelSpecFunc = cancelSpecFunc;
    }
}
