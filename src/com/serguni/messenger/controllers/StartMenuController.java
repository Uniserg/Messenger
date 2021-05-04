package com.serguni.messenger.controllers;

import com.google.gson.Gson;
import com.serguni.messenger.dto.models.UserDto;
import com.serguni.messenger.dto.models.UserDtoJson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.serguni.messenger.Main;
import com.serguni.messenger.utils.RegExValidUtil;
import com.serguni.messenger.utils.RequestUtil;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.nio.charset.StandardCharsets;
import java.util.Date;

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
//                System.out.println(requestUtil.getResponse());
                UserDtoJson userDtoJson = new Gson().fromJson(requestUtil.getResponse(), UserDtoJson.class);

                byte[] avatar = null;

                if (userDtoJson.getAvatar() != null) {
                    avatar = userDtoJson.getAvatar().getBytes(StandardCharsets.UTF_8);
                }

                UserDto userDto = new UserDto(
                        userDtoJson.getId(),
                        userDtoJson.getNickname(),
                        userDtoJson.getEmail(),
                        userDtoJson.getFirstName(),
                        userDtoJson.getLastName(),
                        userDtoJson.getAboutMe(),
                        avatar,
                        new Date(0)
                );

//                UserDto user = new Gson().fromJson(requestUtil.getResponse(), UserDto.class);
                main.showValidationKeyDialog(null, userDto);
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
