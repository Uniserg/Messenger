package com.serguni.messenger.sockets;

import com.serguni.messenger.Main;
import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.SocketMessage;
import com.serguni.messenger.dto.models.SessionDto;
import com.serguni.messenger.dto.models.UserInfoDto;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;

public class Client {

    private Main main;

    private Socket socket;

    public ObjectInputStream in;
    public ObjectOutputStream out;

    private UserChatMenuController userChatMenuController;


    public Client(Socket socket, ObjectInputStream in,  ObjectOutputStream out) throws IOException {
        new Thread(() -> {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }).start();


        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    while (true) {
                            try {
                                SocketMessage message = (SocketMessage) in.readObject();
                                System.out.println(message.getType());

                                switch (message.getType()) {
                                    case SEARCH_USER -> foundUsers((Set<UserInfoDto>) message.getBody());
                                    case UPDATE_TRACKING_USER -> updateTrackingUserInfo((UserInfoDto) message.getBody());
                                    case ADD_NEW_SESSION -> userChatMenuController.addSessionsInfo((SessionDto) message.getBody());
                                }

                            } catch (EOFException e) {
                                Platform.runLater(() -> main.showStartMenu());
                                break;
                            }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };


        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void sendSocketMessage(SocketMessage socketMessage) {
        try {
            out.writeObject(socketMessage);
        } catch (SocketException e) {
            main.showStartMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void foundUsers(Set<UserInfoDto> users) {
        main.getUserChatMenuController().fillUserChart(users);
    }

    public void updateTrackingUserInfo(UserInfoDto userInfoDto) {

        main.getUserChatMenuController().updateUserInfo(userInfoDto);
    }

    public void setUserChatMenuController(UserChatMenuController userChatMenuController) {
        this.userChatMenuController = userChatMenuController;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
