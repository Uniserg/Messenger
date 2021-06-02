package com.serguni.messenger.controllers;

import com.google.gson.Gson;
import com.serguni.messenger.dto.models.UserDto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.serguni.messenger.Main;
import com.serguni.messenger.utils.RegExValidUtil;
import com.serguni.messenger.utils.RequestUtil;

public class SignUpDialogController {
    @FXML
    private TextField nickname;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField aboutMe;
    @FXML
    private Label warningLabel;

    private Stage dialogStage;
    private Main main;

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            RequestUtil requestUtil = new RequestUtil("/users", "POST");
            UserDto userDto = new UserDto(
                    nickname.getText(),
                    email.getText(),
                    firstName.getText(),
                    lastName.getText(),
                    aboutMe.getText()
            );

            requestUtil.setJson(new Gson().toJson(userDto));
            requestUtil.run();


            //ОТВЕТ ОТ СЕРВЕРА
            if (requestUtil.getResponseCode() == 201) {
                System.out.println("ID нового = " + requestUtil.getResponse());
                long userId = Long.parseLong(requestUtil.getResponse());
                main.showValidationKeyDialog(dialogStage, userId);
            } else {
                warningLabel.setText(requestUtil.getResponse());
                warningLabel.setVisible(true);
            }
            // ЗДЕСЬ ПРОПИСАТЬ КОД ОТВЕТА ОТ СЕРВЕРА (ЕСЛИ OK, - Закрыть и Войти в учетную запись, ИНАЧЕ - вывести сообщение)

            //ДОПИСАТЬ ВХОД (добавить main и вызвать из него acceptLogin)
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMsg = "";

        if (email == null || !RegExValidUtil.checkEmail(email.getText())) {
            errorMsg += "No valid email!\n";
        }
        if (nickname == null || !RegExValidUtil.checkStandard(nickname.getText())) {
            errorMsg += "No valid nickname!\n";
        }

        if (errorMsg.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return false;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
