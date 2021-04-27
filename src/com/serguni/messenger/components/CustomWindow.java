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


    public CustomWindow(Pane window, double coordinate, CustomWindow child) {
        this.coordinate = coordinate;
        this.window = window;
        this.child = child;
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
        moveObject(-coordinate);
    }


    public void cancelWindow() {
        moveObject(coordinate);
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
        window.getChildren().get(0).setDisable(true);
        window.getChildren().get(0).setOpacity(0.9);
        child.setParent(this);
        this.child = child;
    }

    public void setParent(CustomWindow parent) {
        this.parent = parent;
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
