package com.serguni.messenger.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.serguni.messenger.Main;
import com.serguni.messenger.utils.RegExValidUtil;
import com.serguni.messenger.utils.RequestUtil;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class StartMenuController {
    @FXML
    private TextField loginField;
    @FXML
    private Label notFoundUserLabel;

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleLogIn() {
        String errorMsg = "";

        if (loginField == null ||
                !RegExValidUtil.checkEmail(loginField.getText()) &&
                        !RegExValidUtil.checkStandard(loginField.getText())) {
            errorMsg += "No valid Login!";
        }

        if (errorMsg.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Invalid Fieserlds");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMsg);
            alert.showAndWait();
        } else {

            RequestUtil requestUtil = new RequestUtil("/users/" + loginField.getText() , "GET");
            requestUtil.run();

            if (requestUtil.getResponseCode() == 200) {
                notFoundUserLabel.setVisible(false);
                long userId = Long.parseLong(requestUtil.getResponse());

                main.showValidationKeyDialog(null, userId);
            } else {
                notFoundUserLabel.setVisible(true);
                System.out.println(requestUtil.getResponseCode());
            }
        }
    }

    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleLogIn();
        }
    }

    @FXML
    private void handleSignUp() {
        main.showSignUpDialog();
    }
}
