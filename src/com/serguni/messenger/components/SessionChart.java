package com.serguni.messenger.components;

import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.models.SessionDto;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Date;

public class SessionChart {
    private AnchorPane sessionPane;
    private final Label deviceAndOs;
    private final Label ipAndLocation;
    private final Label signInFirst;

    private final Label lastOnline;
    private SessionDto sessionInfo;

    public SessionChart(SessionDto sessionDto, UserChatMenuController userChatMenuController) {
        this.sessionInfo = sessionDto;

        sessionPane = new AnchorPane();

        deviceAndOs = new Label();
        ipAndLocation = new Label();
        lastOnline = new Label();
        signInFirst = new Label();

        deviceAndOs.setLayoutX(14);
        deviceAndOs.setLayoutY(14);

        ipAndLocation.setLayoutX(14);
        ipAndLocation.setLayoutY(34);

        signInFirst.setLayoutX(14);
        signInFirst.setLayoutY(55);

        lastOnline.setLayoutX(200);
        lastOnline.setLayoutY(35);

        deviceAndOs.setText(sessionDto.getDevice() + ", " + sessionDto.getOs());
        ipAndLocation.setText(sessionDto.getIp() + " - " + sessionDto.getLocation());
        setLastOnline(sessionInfo.getLastOnline());
        signInFirst.setText(sessionDto.getSignInTime().toString());

        sessionPane.maxHeight(90);
        sessionPane.setStyle("-fx-background-color: #1d1d1d");
        sessionPane.getChildren().addAll(deviceAndOs, ipAndLocation, lastOnline, signInFirst);


        sessionPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            userChatMenuController.showConfirmDeleteSessionDialog(this);
        });


        sessionPane.setOnMouseEntered(e -> sessionPane.setStyle("-fx-background-color: #3a3a3a"));
        sessionPane.setOnMouseExited(e -> sessionPane.setStyle("-fx-background-color: #1d1d1d"));
    }

    public void setSessionPane(AnchorPane sessionPane) {
        this.sessionPane = sessionPane;
    }

    public Label getDeviceAndOs() {
        return deviceAndOs;
    }

    public Label getIpAndLocation() {
        return ipAndLocation;
    }

    public Label getSignInFirst() {
        return signInFirst;
    }

    public Label getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date newLastOnline) {
        sessionInfo.setLastOnline(newLastOnline);

        if (newLastOnline.equals(new Date(0))) {
            lastOnline.setText("Online");
            lastOnline.setStyle("-fx-text-fill: #6541cf");
        } else {
            lastOnline.setText(newLastOnline.toString());
            lastOnline.setStyle("-fx-text-fill: #b7b6ba");
        }
    }

    public SessionDto getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionDto sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public AnchorPane getSessionPane() {
        return sessionPane;
    }
}
