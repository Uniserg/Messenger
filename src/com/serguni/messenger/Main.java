package com.serguni.messenger;

import com.serguni.messenger.components.NativeStage;
import com.serguni.messenger.controllers.*;
import com.serguni.messenger.dto.models.SessionDto;
import com.serguni.messenger.dto.models.UserDto;
import com.serguni.messenger.dto.SocketMessage;
import com.serguni.messenger.sockets.Client;
import com.serguni.messenger.components.ChatPane;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Main extends Application {

    private Stage primaryStage;
    public double curWidth;
    public double curHeight;

    public SessionDto session;
    public UserDto user;
    public Client client;

    private UserChatMenuController userChatMenuController;

    private final static String SESSION_PATH = "data/cookie/";
    private final static String COOKIE_FILE = SESSION_PATH + "cookie.msfx";
    private final static String USER_CACHE_FILE = "data/cookie/" + "cache.msfx";

    @Override
    public void start(Stage primaryStage) {
        NativeStage nativeStage = new NativeStage();

        this.primaryStage = nativeStage.getStage();

        this.primaryStage.setMinWidth(370);
        this.primaryStage.setMinHeight(510);

        curHeight = 510;
        curWidth = 370;
        this.primaryStage.setWidth(curWidth);
        this.primaryStage.setHeight(curHeight);

        showStartMenu();
        autologin();
    }

    public void login() {

        try {
            Socket socket = new Socket("localhost", 8081);
//            OutputStream os = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(session);
            out.flush();
//            InputStream is = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            SocketMessage message = (SocketMessage) in.readObject();

            if (message.getType() != SocketMessage.MessageType.LOGIN){
                return;
            }
            if (message.getBody() == null) {
                return;
            }

//            if (is.read() == 0) {
//                return;
//            }

            user = (UserDto) message.getBody();
            System.out.println(user);
            client = new Client(socket, in, out);
            client.setMain(this);

            showUserChatMenu();

            saveCookie();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Сервер не отвечает!");
            e.printStackTrace();
        }
    }

    public void autologin() {
        try {
            FileInputStream cookieIs = new FileInputStream(COOKIE_FILE);
            ObjectInputStream objectCookieIs = new ObjectInputStream(cookieIs);

            SessionDto sessionDto = (SessionDto) objectCookieIs.readObject();

            FileInputStream sessionIs = new FileInputStream(USER_CACHE_FILE);
            ObjectInputStream objectSessionIs = new ObjectInputStream(sessionIs);

            user = (UserDto) objectSessionIs.readObject();
            session = sessionDto;

            objectCookieIs.close();
            objectSessionIs.close();

            login();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Нет файла с сессией");
        }
    }

    public void showStartMenu() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/StartMenu.fxml"));

        try {
            BorderPane startMenu = loader.load();

            ((BorderPane) primaryStage.getScene().getRoot()).setCenter(startMenu);

            primaryStage.setMinWidth(350);
            primaryStage.setMinHeight(510);
            primaryStage.centerOnScreen();

            StartMenuController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserChatMenu() {
        System.out.println(session);

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("views/UserChatMenu.fxml"));

        try {
            Parent userMenu = loader.load();
            ((BorderPane) primaryStage.getScene().getRoot()).setCenter(userMenu);

            userChatMenuController = loader.getController();
            userChatMenuController.setMain(this);

            AnchorPane userMenuBack = (AnchorPane) ((AnchorPane) ((AnchorPane) userMenu.getChildrenUnmodifiable().get(0)).getChildren().get(0)).getChildren().get(0);

            HBox hBox = (HBox) userMenuBack.getChildrenUnmodifiable().get(0);
            ScrollPane scrollPane = (ScrollPane) hBox.getChildrenUnmodifiable().get(0);
            VBox vBox = (VBox) scrollPane.getContent();
            ObservableList<Node> chats = vBox.getChildren();


            for (int i = 0; i < 4; i ++) {
                chats.add(0, new ChatPane().getChatPane());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showValidationKeyDialog(Stage ownerStage, UserDto user) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/ValidationDialog.fxml"));

        try {
            NativeStage nativeStage = new NativeStage();
            Stage validationKeyStage = nativeStage.getStage();
            validationKeyStage.setMinWidth(300);
            validationKeyStage.setMinHeight(200);

            validationKeyStage.setResizable(false);

            validationKeyStage.initModality(Modality.WINDOW_MODAL);
            validationKeyStage.initOwner(ownerStage);

            Parent validationKeyDialog = loader.load();
            nativeStage.setScene(validationKeyDialog);

            ValidationDialogController validationController = loader.getController();

            validationController.setDialogStage(validationKeyStage);
            validationController.setUser(user);
            validationController.setMain(this);

            validationKeyStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSignUpDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/SignUpDialog.fxml"));

        try {
//            Stage signUpStage = new Stage();
            NativeStage nativeStage = new NativeStage();
            Stage signUpStage = nativeStage.getStage();

            signUpStage.setMinWidth(500);
            signUpStage.setMinHeight(420);

            signUpStage.initModality(Modality.WINDOW_MODAL);
            signUpStage.initOwner(primaryStage);

            Node signUpDialog = loader.load();
            nativeStage.setScene(signUpDialog);

//            Scene signUpScene = new Scene(signUpDialog);
//            signUpStage.setScene(signUpScene);

            SignUpDialogController controller = loader.getController();
            controller.setDialogStage(signUpStage);
            controller.setMain(this);

            signUpStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void normalPath() throws IOException {
        File dirs = new File(SESSION_PATH);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }

        File cookieFile = new File(COOKIE_FILE);
        if (!cookieFile.exists()) {
            cookieFile.createNewFile();
        }

        File sessionFile = new File(USER_CACHE_FILE);
        if (!sessionFile.exists()) {
            sessionFile.createNewFile();
        }
    }

//    public void saveSession() {
//        try {
//            normalPath();
//
//        }
//
//    }

    public void saveCookie() {
        try {
            normalPath();

            FileOutputStream cookieOs = new FileOutputStream(COOKIE_FILE);
            ObjectOutputStream objectCookieOs = new ObjectOutputStream(cookieOs);

            objectCookieOs.writeObject(session);

            FileOutputStream sessionOs = new FileOutputStream(USER_CACHE_FILE);
            ObjectOutputStream objectSessionOs = new ObjectOutputStream(sessionOs);

            objectSessionOs.writeObject(user);

        } catch (IOException ignored) {

        }
    }

    public void setLightTheme() {
        //???
    }

    public void setDarkTheme() {
        //???
    }

    public UserChatMenuController getUserChatMenuController() {
        return userChatMenuController;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }

    public SessionDto getSession() {
        return session;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
