package com.serguni.messenger.controllers;

import com.google.gson.Gson;
import com.serguni.messenger.dto.SessionCookie;
import com.serguni.messenger.dto.models.SessionDto;
import com.serguni.messenger.dto.models.UserDto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.serguni.messenger.Main;
import com.serguni.messenger.dto.SessionContext;
import com.serguni.messenger.utils.RegExValidUtil;
import com.serguni.messenger.utils.RequestUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ValidationDialogController {
    @FXML
    private TextField keyField;
    private Stage dialogStage;
    private UserDto user;
    private Main main;

    @FXML
    private void handleOk() throws UnknownHostException {
        String errorMsg = "";

        if (keyField == null ||
                !RegExValidUtil.checkEmail(keyField.getText()) &&
                        !RegExValidUtil.checkKey(keyField.getText())) {
            errorMsg += "No valid key!";
        }

        if (errorMsg.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid key");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMsg);
            alert.showAndWait();
        } else {
            String[] inetAddress = InetAddress.getLocalHost().toString().split("/");
            String device = inetAddress[0];
            String ip = inetAddress[1];

            RequestUtil requestUtil = new RequestUtil("/valid/", "POST");
            SessionDto session = new SessionDto(
                    ip,
                    device,
                    System.getProperty("os.name"),
                    null);

            Gson gson = new Gson();

            requestUtil.setJson(gson.toJson(new SessionContext(user.getId(), keyField.getText(), session)));
            requestUtil.run();

            if (requestUtil.getResponseCode() == 200) {

                SessionCookie sessionCookie = gson.fromJson(requestUtil.getResponse(), SessionCookie.class);
                System.out.println("ПОЛУЧИЛИ КУКИ");
                main.sessionCookie = sessionCookie;

                main.login();

                dialogStage.close();
                if (dialogStage.getOwner() != null) {
                    dialogStage.getOwner().hide();
                }

            }

        }

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
