package com.serguni.messenger;

import com.serguni.messenger.components.NativeStage;
import com.serguni.messenger.controllers.*;
import com.serguni.messenger.dto.SessionCookie;
import com.serguni.messenger.dto.models.SessionDto;
import com.serguni.messenger.dto.models.UserDto;
import com.serguni.messenger.dto.SocketMessage;
import com.serguni.messenger.sockets.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/**
 * Главный класс запуска. В нем содержатся методы для открытия окон.
 *
 */
public class Main extends Application {

    private Stage primaryStage;
    public double curWidth;
    public double curHeight;
    public SessionCookie sessionCookie;
    public SessionDto session;
    public static UserDto user;
    public Client client;
    public static String SERVER_SOCKET_ADDRESS = "localhost";
    public static int SERVER_SOCKET_PORT = 8081;

    private UserChatMenuController userChatMenuController;

    private final static String SESSION_PATH = "data/cookie/";
    private final static String COOKIE_FILE = SESSION_PATH + "cookie.msfx";
//    private final static String USER_CACHE_FILE = "data/cookie/" + "cache.msfx";

    @Override
    public void start(Stage primaryStage) {
        NativeStage nativeStage = new NativeStage();

        this.primaryStage = nativeStage.getStage();

        this.primaryStage.setMinWidth(440);
        this.primaryStage.setMinHeight(540);

        curHeight = 540;
        curWidth = 440;
        this.primaryStage.setWidth(curWidth);
        this.primaryStage.setHeight(curHeight);

        showStartMenu();
        autologin();
    }

    public void login() {

        try {
            Socket socket = new Socket(SERVER_SOCKET_ADDRESS, SERVER_SOCKET_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(sessionCookie);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            SocketMessage message = (SocketMessage) in.readObject();

            if (message.getType() != SocketMessage.MessageType.LOGIN ||
                    message.getBody() == null){
                return;
            }

            user = (UserDto) message.getBody();
            System.out.println(user);

            for (SessionDto sessionDto : user.getSessions()) {
                if (sessionDto.getId() == sessionCookie.getSessionId()) {
                    session = sessionDto;
                    break;
                }

            }

            client = new Client(socket, in, out);
            client.setMain(this);

            showUserChatMenu();

            saveCookie();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Сервер не отвечает!");
            e.printStackTrace();
        }
    }

    public void exit() {
        UserChatMenuController.CHAT_AREA_MAP.clear();
        UserChatMenuController.USERS_SESSIONS.clear();
        UserChatMenuController.TRACKING_ELEMENT_COLLECTION.longTrackingElements.clear();
        UserChatMenuController.TRACKING_ELEMENT_COLLECTION.tempTrackingElements.clear();
        UserChatMenuController.USERS_MEM.clear();
        showStartMenu();
        try {
            client.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void autologin() {
        try {
            FileInputStream cookieIs = new FileInputStream(COOKIE_FILE);
            ObjectInputStream objectCookieIs = new ObjectInputStream(cookieIs);

            sessionCookie = (SessionCookie) objectCookieIs.readObject();

            objectCookieIs.close();

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
//        System.out.println(session);

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("views/UserChatMenu.fxml"));

        try {
            Parent userMenu = loader.load();
            ((BorderPane) primaryStage.getScene().getRoot()).setCenter(userMenu);

            userChatMenuController = loader.getController();
            userChatMenuController.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showValidationKeyDialog(Stage ownerStage, long userId) {
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
            validationController.setUserId(userId);
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
            NativeStage nativeStage = new NativeStage();
            Stage signUpStage = nativeStage.getStage();

            signUpStage.setMinWidth(500);
            signUpStage.setMinHeight(420);

            signUpStage.initModality(Modality.WINDOW_MODAL);
            signUpStage.initOwner(primaryStage);

            Node signUpDialog = loader.load();
            nativeStage.setScene(signUpDialog);

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
    }

    public void saveCookie() {
        try {
            normalPath();

            FileOutputStream cookieOs = new FileOutputStream(COOKIE_FILE);
            ObjectOutputStream objectCookieOs = new ObjectOutputStream(cookieOs);

            objectCookieOs.writeObject(sessionCookie);
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


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
