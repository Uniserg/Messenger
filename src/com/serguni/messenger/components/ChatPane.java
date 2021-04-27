package com.serguni.messenger.components;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class ChatPane {

    private final AnchorPane anchorPane;

    public ChatPane() {
        anchorPane = new AnchorPane();

        Circle circle = new Circle();
        Label usernameLabel = new Label();
        Label messageLabel = new Label();
        Label dateLabel = new Label();

        circle.setRadius(30);
        circle.setCenterX(45);
        circle.setCenterY(50);
        circle.setFill(Paint.valueOf("#352482"));


        usernameLabel.setText("Username");
        usernameLabel.setLayoutX(90);
        usernameLabel.setLayoutY(20);

        messageLabel.setText("Last Message");
        messageLabel.setLayoutX(90);
        messageLabel.setLayoutY(60);
        messageLabel.setStyle("-fx-font-family: Calibri");

        dateLabel.setText("Date");
        dateLabel.setLayoutX(320);
        dateLabel.setLayoutY(20);

        anchorPane.setMinHeight(100);
        anchorPane.getChildren().addAll(circle, usernameLabel, messageLabel, dateLabel);
        anchorPane.setOnMouseEntered(e -> anchorPane.setStyle("-fx-background-color: #3a3a3a"));
        anchorPane.setOnMouseExited(e -> anchorPane.setStyle("-fx-background-color: #1d1d1d"));

    }

    public AnchorPane getChatPane() {
        return anchorPane;
    }
}
