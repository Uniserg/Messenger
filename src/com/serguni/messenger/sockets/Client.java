package com.serguni.messenger.sockets;

import com.serguni.messenger.Main;
import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.SocketMessage;
import com.serguni.messenger.dto.models.MessageDto;
import com.serguni.messenger.dto.models.SessionDto;
import com.serguni.messenger.dto.models.UserInfoDto;
import com.serguni.messenger.dto.models.WatchedChatDto;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
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
                new Thread(() -> {
                    while (true) {
                        try {
                            System.out.println("Client -> 45 -> МЫ ТУТ ЧИТАЕМ ПРИСЛАННЫЕ ОТ СЕРВЕРА СООБЩЕНИЯ");
                            SocketMessage message = (SocketMessage) in.readObject();
                            System.out.println(message.getType());

                            final Object[] body;

                            switch (message.getType()) {
                                case SEARCH_USER:
                                    foundUsers((Set<UserInfoDto>) message.getBody());
                                    break;
                                case EDIT_NAME:
                                    body = (Object[]) message.getBody();
                                    editName((long)body[0], (String) body[1], (String) body[2]);
                                    break;

                                case EDIT_AVATAR:
                                    body = (Object[]) message.getBody();
                                    editAvatar((long) body[0], (byte[]) body[1]);
                                    break;

                                case EDIT_ABOUT_ME:
                                    body = (Object[]) message.getBody();
                                    editAboutMe((long) body[0], (String) body[1]);
                                    break;

                                case UPDATE_LAST_ONLINE_TO_TRACKING_USERS:
                                    body = (Object[]) message.getBody();
                                    updateLastOnlineOfTrackedUsers((long) body[0], (Date) body[1]);
                                    break;

                                case UPDATE_LAST_ONLINE_TO_OTHER_USER_SESSIONS:
                                    body = (Object[]) message.getBody();
                                    updateLastOnlineOfOtherSession((long) body[0], (Date) body[1]);
                                    break;

//                                case UPDATE_TRACKING_USER -> updateTrackingUserInfo((UserInfoDto) message.getBody());
                                case ADD_NEW_SESSION:
                                    userChatMenuController.addSessionsInfo((SessionDto) message.getBody());
                                    break;
                                case DELETE_OTHER_SESSION:
                                    userChatMenuController.deleteOtherSession((long) message.getBody());
                                    break;

//                                case ADD_NEW_SESSION -> userChatMenuController.addSessionsInfo((SessionDto) message.getBody());
//                                case DELETE_OTHER_SESSION -> userChatMenuController.deleteOtherSession((long) message.getBody());

                                case CHAT_MESSAGE:
                                    body = (Object[]) message.getBody();
                                    Platform.runLater(() -> userChatMenuController.addNewMessage((long) body[0], (MessageDto) body[1]));
                                    break;

                                case CREATE_NEW_CHAT:
                                    body = (Object[]) message.getBody();
                                    Platform.runLater(() -> userChatMenuController.addNewChat((UserInfoDto) body[0], (WatchedChatDto) body[1]));
                                    break;
//                                case CHAT_MESSAGE ->  {
//                                    Object[] objects = (Object[]) message.getBody();
//                                    Platform.runLater(() -> userChatMenuController.addNewMessage((long) objects[0], (MessageDto) objects[1]));
//                                }
//
//                                case CREATE_NEW_CHAT -> {
//                                    Object[] objects = (Object[]) message.getBody();
//                                    Platform.runLater(() -> userChatMenuController.addNewChat((UserInfoDto) objects[0], (WatchedChatDto) objects[1]));
//                                }

//                                case CREATE_NEW_CHAT -> userChatMenuController.addWatchedChat((WatchedChatDto) message.getBody());
//                                case EDIT_AVATAR -> userChatMenuController.updateUserInfo((UserInfoDto) message.getBody());
                            }

                        } catch (IOException | ClassNotFoundException e) {
                            Platform.runLater(() -> main.exit());
                            break;
                        }
                    }
                }).start();
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

    private void editName(long userId, String lastName, String firstName) {
        Platform.runLater(() -> userChatMenuController.updateUsernameInfo(userId, lastName, firstName));
    }

    private void editAvatar(long userId, byte[] newAvatar) {
        Platform.runLater(() -> userChatMenuController.updateAvatar(userId, newAvatar));
    }

    private void editAboutMe(long userId, String newAboutMe) {
        Platform.runLater(() -> userChatMenuController.updateAboutMeInfo(userId, newAboutMe));
    }

    private void updateLastOnlineOfTrackedUsers(long userId, Date newLastOnline) {
        Platform.runLater(() -> userChatMenuController.updateLastOnlineOfTrackedUsers(userId, newLastOnline));
    }

    private void updateLastOnlineOfOtherSession(long sessionId, Date newLastOnline) {
        Platform.runLater(() -> userChatMenuController.updateLastOnlineOfOtherSession(Main.user.getId(), sessionId, newLastOnline));
    }

    public void foundUsers(Set<UserInfoDto> users) {
        Platform.runLater(() ->main.getUserChatMenuController().fillUserChart(users));
    }

//    public void updateTrackingUserInfo(UserInfoDto userInfoDto) {
//
////        main.getUserChatMenuController().updateUserInfo(userInfoDto);
//    }

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
