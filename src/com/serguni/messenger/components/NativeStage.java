package com.serguni.messenger.components;

import com.serguni.messenger.Main;
import com.serguni.messenger.controllers.RootSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NativeStage {
    private Stage stage;
    private BorderPane rootScene;
    private RootSceneController controller;
    private MouseEventHandler mouseEvent;

    public NativeStage() {
        try {
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("file:resources/images/messengerfx.png"));
            stage.setTitle("MessengerFX");

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Main.class.getResource("views/RootScene.fxml"));
            rootScene = loader.load();

            System.out.println(rootScene);

            RootSceneController controller = loader.getController();
            controller.setNativeStage(this);

            stage.setScene(new Scene(rootScene));

            mouseEvent = new MouseEventHandler(stage, controller);

            mouseEvent.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setScene(Node scene) {
        rootScene.setCenter(scene);
    }

    public RootSceneController getController() {
        return controller;
    }

    public MouseEventHandler getMouseEvent() {
        return mouseEvent;
    }
}
