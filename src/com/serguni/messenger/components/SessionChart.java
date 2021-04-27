package com.serguni.messenger.components;

import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.models.SessionDto;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SessionChart implements Tracking {
    private int id;
    private AnchorPane sessionPane;
    private final Label deviceAndOs;
    private final Label ipAndLocation;
    private final Label signInFirst;

    private Label lastOnline;
    private SessionDto sessionInfo;

    public SessionChart(SessionDto sessionDto, UserChatMenuController userChatMenuController) {
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
        lastOnline.setText(sessionDto.getLastOnline().toString());
        signInFirst.setText(sessionDto.getSignInTime().toString());

        sessionPane.maxHeight(90);
        sessionPane.setStyle("-fx-background-color: #1d1d1d");
        sessionPane.getChildren().addAll(deviceAndOs, ipAndLocation, lastOnline, signInFirst);

        sessionPane.setOnMouseClicked(mouseEvent -> userChatMenuController.showConfirmDeleteSessionDialog(this));
        sessionPane.setOnMouseEntered(e -> sessionPane.setStyle("-fx-background-color: #3a3a3a"));
        sessionPane.setOnMouseExited(e -> sessionPane.setStyle("-fx-background-color: #1d1d1d"));
    }

    @Override
    public void updateInfo(Object sessionObject) {
        SessionDto sessionDto = (SessionDto) sessionObject;

        sessionInfo.setLastOnline(sessionDto.getLastOnline());

        lastOnline.setText(sessionInfo.getLastOnline().toString());
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

    public void setLastOnline(Label lastOnline) {
        this.lastOnline = lastOnline;
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
