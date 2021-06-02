package com.serguni.messenger.controllers;

import com.serguni.messenger.components.*;
import com.serguni.messenger.dto.SocketMessage;
import com.serguni.messenger.dto.SocketMessage.MessageType;
import com.serguni.messenger.dto.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import com.serguni.messenger.Main;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class UserChatMenuController {

    private Main main;

    @FXML
    private AnchorPane userMenu;

    // MAIN
    @FXML
    private AnchorPane userChatMenu;
    @FXML
    private AnchorPane chatArea;
    @FXML
    private ScrollPane scrollMessages;
    @FXML
    private AnchorPane chatContent;
    @FXML
    private TextArea enterMessageTextArea;

    // MAIN

    @FXML
    private AnchorPane friendsMenu;
    @FXML
    private Label searchUserWarning;

    @FXML
    private AnchorPane editProfileMenu;
    @FXML
    private Button backEditProfileButton;
    @FXML
    private AnchorPane editProfileScrollPane;


    @FXML
    private TextField searchUserTextField;

    @FXML
    private VBox userChartBox;

    @FXML
    private VBox userChats;


    @FXML
    private Label usernameLabel;
    @FXML
    private AnchorPane settingsMenu;
    @FXML
    private Label usernameInSettingsLabel;
    @FXML
    private Label emailInSettingsLabel;

    @FXML
    private Label usernameEditLabel;

    @FXML
    private AnchorPane editUsernameDialog;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private AnchorPane editAboutMeDialog;
    @FXML
    private TextField aboutMeTextField;
    @FXML
    private Label aboutMeInSettingsLabel;

    @FXML
    private Circle circleAvatarInEditMenu;
    @FXML
    private Circle circleAvatarInSettings;
    @FXML
    private Circle circleAvatarInUserMenu;

    //кнопки в окне editPrivacy
    @FXML
    private RadioButton messageFromFriendsOnlyRadioButton;
    @FXML
    private RadioButton invisibleRadioButton;
    @FXML
    private RadioButton showLastOnlineRadioButton;

    @FXML
    private AnchorPane editPrivacyDialog;


    // USER INFO MENU
    @FXML
    private AnchorPane foundUserInfoMenu;
    public static UserInfoDto selectedUser;
    // USER INFO MENU


    //SESSIONS MENU
    @FXML
    private AnchorPane sessionsMenu;
    @FXML
    private Label deviceAndOs;
    @FXML
    private Label ipAndLocation;
    @FXML
    private Label signInFirstTime;
    @FXML
    private VBox sessionsChartBox;
    //SESSIONS MENU

    //CONFIRM DELETE SESSION
    @FXML
    private AnchorPane confirmDeleteSessionDialog;
    private SessionChart sessionOnDelete;

    //CONFIRM DELETE SESSION



    public static final TrackingElementCollection TRACKING_ELEMENT_COLLECTION = new TrackingElementCollection();
//    private static final Map<Long, UserTrackingImpl> USER_TRACKING_MAP = new HashMap<>();
    public static final Map<Long, Map<Long, SessionChart>> USERS_SESSIONS = new HashMap<>();
    public static final Map<Long, Object[]> CHAT_AREA_MAP = new HashMap<>();
    public static final Map<Long, UserInfoDto> USERS_MEM = new HashMap<>();
//    public static final Set<UserInfoDto> TRACKING_USERS = new HashSet<>();

    private CustomWindow userChatMenuWindow;
    private CustomWindow friendsWindow;
    private CustomWindow settingsWindow;
    private CustomWindow userMenuWindow;
    private CustomWindow editProfileWindow;
    private CustomWindow editUsernameDialogWindow;
    private CustomWindow editAboutMeDialogWindow;
    private CustomWindow editPrivacyDialogWindow;
    private CustomWindow foundUserInfoMenuWindow;
    private CustomWindow sessionsWindow;
    private CustomWindow confirmDeleteSessionWindow;
//    private CustomWindow chatAreaWindow;



    @FXML
    private void initialize() {
        userChatMenuWindow = new CustomWindow(userChatMenu, 0, null);
        userMenuWindow = new CustomWindow(userMenu,-320, userChatMenuWindow);
        friendsWindow = new CustomWindow(friendsMenu, 345, userChatMenuWindow);
        settingsWindow = new CustomWindow(settingsMenu, -380, userMenuWindow);
        editProfileWindow = new CustomWindow(editProfileMenu, -380, settingsWindow);
        editUsernameDialogWindow = new CustomWindow(editUsernameDialog, -380, editProfileWindow);
        editAboutMeDialogWindow = new CustomWindow(editAboutMeDialog, -380, editProfileWindow);
        editPrivacyDialogWindow = new CustomWindow(editPrivacyDialog, -365, settingsWindow);
        foundUserInfoMenuWindow = new CustomWindow(foundUserInfoMenu, 345, friendsWindow);
        sessionsWindow = new CustomWindow(sessionsMenu, -381, settingsWindow);
        confirmDeleteSessionWindow = new CustomWindow(confirmDeleteSessionDialog, -310, sessionsWindow);
//        chatAreaWindow = new CustomWindow(chatArea, 370, userChatMenuWindow);

    }


    public void setMain(Main main) {
        this.main = main;
        this.main.client.setUserChatMenuController(this);

//        scrollPane.vvalueProperty().bind(mainGrid.heightProperty());

        // ИНИЦИАЛИЗАЦИЯ ЧАТОВ
        for (Map.Entry<UserInfoDto, WatchedChatDto> wc: Main.user.getWatchedPrivateChats().entrySet()) {
//            userChats.getChildren().add();
            UserInfoDto userInfoDto = wc.getKey();
            if (userInfoDto.getId() == Main.user.getId()) {
                userInfoDto.setLastOnline(new Date(0));
            }

            WatchedChatDto watchedChatDto = wc.getValue();
            USERS_MEM.put(userInfoDto.getId(), userInfoDto);

            //ДОБАВЛЕНИЕ СООБЩЕНИЙ


            ChatPane chatPane = new ChatPane(watchedChatDto, userInfoDto, this);

            CHAT_AREA_MAP.put(wc.getKey().getId(), new Object[] {chatPane, new ChatArea(watchedChatDto)});
            System.out.println("ПРОВЕРКА ПРИ ДОБАВЛЕНИИ ВИДИМЫХ ЧАТОВ");
            System.out.println(CHAT_AREA_MAP);

            TRACKING_ELEMENT_COLLECTION.putToLong(wc.getKey().getId(), chatPane);
            System.out.println("ДОБАВЛЯЕМ В КОЛЛЕКЦИЮ В setMain - инициализация чатов - 537 - UserChartMenu");
            System.out.println(TRACKING_ELEMENT_COLLECTION.tempTrackingElements + " ЭТО ЖЕ MAP");
            System.out.println(wc.getKey().getId());
            userChats.getChildren().add(chatPane.getChatPane());
        }
        // ИНИЦИАЛИЗАЦИЯ ЧАТОВ


        //ИНИЦИАЛИЗАЦИЯ ТЕКУЩЕЙ СЕССИИ (В ОКНЕ)
//        currentSessionLastOnline.setText("Online");
        deviceAndOs.setText(main.session.getDevice() + " " + main.session.getOs());
        ipAndLocation.setText(main.session.getIp() + " " + main.session.getLocation());
        signInFirstTime.setText(main.session.getSignInTime().toString());
        //ИНИЦИАЛИЗАЦИЯ ТЕКУЩЕЙ СЕССИИ (В ОКНЕ)

        // ДОБАВЛЕНИЕ ДРУГИХ СЕССИЙ
        Set<SessionDto> userSessions = Main.user.getSessions();
        for (SessionDto userSession : userSessions) {
            if (userSession.getId() != main.session.getId()) {
                SessionChart sessionChart = new SessionChart(userSession, this);

                sessionsChartBox.getChildren().add(sessionChart.getSessionPane());

//                Map<Long, SessionChart> userSessionChart = new HashMap<>();

                Map<Long, SessionChart> userSessionChart = USERS_SESSIONS.computeIfAbsent(Main.user.getId(), k -> new HashMap<>());
                userSessionChart.put(userSession.getId(), sessionChart);
            }
        }
        System.out.println(USERS_SESSIONS);
        // ДОБАВЛЕНИЕ ДРУГИХ СЕССИЙ
        emailInSettingsLabel.setText(Main.user.getEmail());
        updateUsernameAnywhere();
        updateAboutMeAnyWhere();
        updateAvatarAnywhere();
        updateConfigurationAnywhere(Main.user.getConfiguration());

    }

    private void changeUsernameLabel() {
        String nameLabel = Main.user.getLastName() + " " + Main.user.getFirstName();
        System.out.println(nameLabel.equals(" "));
        if (nameLabel.equals(" ")) {
            usernameLabel.setText(Main.user.getNickname());
        } else {
            usernameLabel.setText(nameLabel);
        }
    }

    private void userChatMouseEvent(CustomWindow window) {

        userChatMenu.setOpacity(1);
        userChatMenu.getChildren().get(0).setDisable(false);
        userChatMenu.setOnMouseClicked(null);

        window.cancelWindow();
        userChatMenu.setOnMouseClicked(null);
    }

    @FXML
    private void showUserMenu() {
        userMenuWindow.showWindow();
        userMenuWindow.setParent(userChatMenuWindow);
    }

    @FXML
    private void showFriendsMenu() {
        userMenuWindow.cancelWindow();
        friendsWindow.showWindow();
    }

    @FXML
    private void handleSearchUsers() {
        TRACKING_ELEMENT_COLLECTION.tempTrackingElements.clear();

        if (!searchUserTextField.getText().equals("")) {
            SocketMessage searchUser = new SocketMessage(SocketMessage.MessageType.SEARCH_USER,
                    searchUserTextField.getText());
            main.client.sendSocketMessage(searchUser);
        }
    }

    //SESSION MENU
    @FXML
    private void handleDeleteAllOtherSessions() {
        main.client.sendSocketMessage(new SocketMessage(MessageType.DELETE_ALL_OTHER_SESSIONS, null));

        USERS_SESSIONS.clear();
        sessionsChartBox.getChildren().clear();
    }

    @FXML
    private void showSessions() {
        sessionsWindow.showWindow();
    }

    @FXML
    public void handleCancelSessionWindow() {
        sessionsWindow.cancelWindow();
    }

    public void showConfirmDeleteSessionDialog(SessionChart sessionChart) {
        System.out.println("ТЕКУЩАЯ СЕССИЯ " + main.session.getId());
        System.out.println("СЕССИЯ НА УДАЛЕНИЕ =" + sessionChart.getSessionInfo().getId());
        Platform.runLater(() -> {
            confirmDeleteSessionWindow.showWindow();
            confirmDeleteSessionWindow.setParent(sessionsWindow);

            sessionOnDelete = sessionChart;
        });
    }

    public void deleteOtherSession(long deletedSessionId) {
        Platform.runLater(() -> {
//            sessionsChartBox.getChildren().remove(USERS_SESSIONS.remove(deletedSessionId).getSessionPane());
            sessionsChartBox.getChildren().remove(USERS_SESSIONS.get(Main.user.getId()).remove(deletedSessionId).getSessionPane());
//            sessionsChartBox.getChildren().remove(USERS_SESSIONS.get());
        });
    }

    @FXML
    private void handleCloseSession() throws IOException {
        System.out.println(USERS_SESSIONS);
        System.out.println("ТЕКУЩАЯ СЕССИЯ " + main.session.getId());
        System.out.println("СЕССИЯ НА УДАЛЕНИЕ" + sessionOnDelete);

        System.out.println(sessionOnDelete.getSessionInfo().getId());
        sessionsChartBox.getChildren().remove(sessionOnDelete.getSessionPane());
        confirmDeleteSessionWindow.cancelWindow();

        main.client.out.writeObject(new SocketMessage(MessageType.DELETE_OTHER_SESSION, sessionOnDelete.getSessionInfo().getId()));
    }

    @FXML
    private void handleCancelConfirmDialog() {
        confirmDeleteSessionWindow.cancelWindow();
    }

    public void addSessionsInfo(SessionDto sessionDto) {
        Platform.runLater(() ->{
            Main.user.getSessions().add(sessionDto);
            SessionChart sessionChart = new SessionChart(sessionDto, this);
            sessionsChartBox.getChildren().add(0, sessionChart.getSessionPane());

            System.out.println("UserChatMenuController -> 314 -> ДОБАВЛЯЕМ НОВУЮ СЕССИЮ КАК ЧАРТ");
            System.out.println(sessionDto.getId());


//            Map<Long, SessionChart> userSessionChart = new HashMap<>();
            Map<Long, SessionChart> userSessionChart = USERS_SESSIONS.computeIfAbsent(Main.user.getId(), k -> new HashMap<>());
            userSessionChart.put(sessionDto.getId(), sessionChart);
        });
    }
    //SESSION MENU

    public void updateUsernameInfo(long userId, String lastName, String firstName) {
        if (Main.user.getId() == userId) {
            Main.user.setLastName(lastName);
            Main.user.setFirstName(firstName);
            updateUsernameAnywhere();
        }

//        USER_TRACKING_MAP.get(userId).updateFullName(lastName + " " + firstName);

        Set<UserTrackingImpl> trackingElements = TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId);
        if (trackingElements != null) {
            for (UserTrackingImpl tracking : TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId)) {
                tracking.updateFullName(lastName, firstName);
            }
        }

        UserInfoDto userInfoDto = USERS_MEM.get(userId);
        if (userInfoDto != null) {
            userInfoDto.setFirstName(firstName);
            userInfoDto.setLastName(lastName);
        }

        Set<UserTrackingImpl> longTracking = TRACKING_ELEMENT_COLLECTION.longTrackingElements.get(userId);
        if (longTracking != null) {
            for (UserTrackingImpl tracking : longTracking) {
                tracking.updateFullName(lastName, firstName);
            }
        }
    }

    public void updateAboutMeInfo(long userId, String newAboutMe) {
        if (Main.user.getId() == userId) {
            Main.user.setAboutMe(newAboutMe);
            updateAboutMeAnyWhere();
        }

        USERS_MEM.get(userId).setAboutMe(newAboutMe);
//        USER_TRACKING_MAP.get(userId).updateAboutMe(newAboutMe);

        Set<UserTrackingImpl> trackingElements = TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId);
        if (trackingElements != null) {
            for (UserTrackingImpl tracking : TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId)) {
                tracking.updateAboutMe(newAboutMe);
            }
        }

        Set<UserTrackingImpl> longTracking = TRACKING_ELEMENT_COLLECTION.longTrackingElements.get(userId);
        if (longTracking != null) {
            for (UserTrackingImpl tracking : longTracking) {
                tracking.updateAboutMe(newAboutMe);
            }
        }


    }

    public void updateLastOnlineOfTrackedUsers(long userId, Date newLastOnline) {
        if (Main.user.getId() == userId) {
            Main.user.setLastOnline(newLastOnline);
        }
//        USER_TRACKING_MAP.get(userId).updateLastOnline(newLastOnline);

        Set<UserTrackingImpl> trackingElements = TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId);
        if (trackingElements != null) {
            for (UserTrackingImpl tracking : TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId)) {
                tracking.updateLastOnline(newLastOnline);
            }
        }

        USERS_MEM.get(userId).setLastOnline(newLastOnline);

        Set<UserTrackingImpl> longTracking = TRACKING_ELEMENT_COLLECTION.longTrackingElements.get(userId);
        if (longTracking != null) {
            for (UserTrackingImpl tracking : longTracking) {
                tracking.updateLastOnline(newLastOnline);
            }
        }
    }

    public void updateLastOnlineOfOtherSession(long userId, long sessionId, Date newLastOnline) {
        System.out.println(USERS_SESSIONS);
        System.out.println("USERCHATMENU -> 371 -> userId=" + userId + " sessionId=" + sessionId);
        Map<Long, SessionChart> userSessions = USERS_SESSIONS.get(userId);
        SessionChart sessionChart = userSessions.get(sessionId);

//        if (userSessions == null)
//            return;

        sessionsChartBox.getChildren().remove(sessionChart.getSessionPane());

        sessionChart.setLastOnline(newLastOnline);
        sessionsChartBox.getChildren().add(0, sessionChart.getSessionPane());

    }

    public void updateAvatar(long userId, byte[] newAvatar) {
        if (Main.user.getId() == userId) {
            Main.user.setAvatar(newAvatar);
            updateAvatarAnywhere();
        } else {
            UserInfoDto userInfoDto = USERS_MEM.get(userId);
            if (userInfoDto != null) {
                userInfoDto.setAvatar(newAvatar);
            }
        }
//        else if (selectedUser != null && userId == selectedUser.getId()) {
//            UserInfoDto userInfoDto = USERS_MEM.get(userId);
//            if (userInfoDto != null) {
//                userInfoDto.setAvatar(newAvatar);
//            }
//        }

//        USER_TRACKING_MAP.get(userId).updateAvatar(newAvatar);
//        if (selectedUser != null && userId == selectedUser.getId()) {
//        }

        Set<UserTrackingImpl> trackingElements = TRACKING_ELEMENT_COLLECTION.tempTrackingElements.get(userId);
        System.out.println("ОБНОВЛЕНИЕ АВАТАРКИ ->" + trackingElements);
        if (trackingElements != null) {
            for (UserTrackingImpl tracking : trackingElements) {
                tracking.updateAvatar(newAvatar);
            }
        }

        Set<UserTrackingImpl> longTracking = TRACKING_ELEMENT_COLLECTION.longTrackingElements.get(userId);
        if (longTracking != null) {
            for (UserTrackingImpl tracking : longTracking) {
                tracking.updateAvatar(newAvatar);
            }
        }
    }



    // FOUND USER MENU
    public void showFoundUserInfoMenu(UserInfoDto userInfoDto) {
        selectedUser = userInfoDto;
        UserPane userPane = new UserPane(userInfoDto);
        System.out.println(TRACKING_ELEMENT_COLLECTION.tempTrackingElements);
        TRACKING_ELEMENT_COLLECTION.putToTemp(userInfoDto.getId(), userPane);
        System.out.println(TRACKING_ELEMENT_COLLECTION.tempTrackingElements);

        Platform.runLater(() -> {
            foundUserInfoMenu.getChildren().set(0, userPane.getAnchorPane());
            foundUserInfoMenuWindow.showWindow();

            foundUserInfoMenuWindow.setParent(friendsWindow);
        });
    }

//    public void addWatchedChat(WatchedChatDto watchedChatDto) {
//
//        long chatId = watchedChatDto.getChatId();
//
//        enterMessageTextArea.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
//            if (keyEvent.getCode() == KeyCode.ENTER) {
//                try {
//                    enterMessageTextArea.deletePreviousChar();
//                    MessageDto messageDto = new MessageDto(enterMessageTextArea.getText(),
//                            chatId,
//                            main.user.getId());
//
//                    System.out.println(enterMessageTextArea.getText());
//                    main.client.out.writeObject(new SocketMessage(MessageType.CHAT_MESSAGE, messageDto));
//                    enterMessageTextArea.clear();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        if (main.getPrimaryStage().getWidth() < 370 * 2) {
//            chatAreaWindow.showWindow();
//            chatAreaWindow.getWindow().setMinWidth(main.getPrimaryStage().getWidth());
//        }
//    }

    public void addNewChat(UserInfoDto userInfoDto, WatchedChatDto watchedChatDto) {

        System.out.println("МЫ ТУТ НО ПОЧЕМУ НИЧЕГО НЕ ДОБАВИЛОСЬ?");
        ChatArea chatArea = new ChatArea(watchedChatDto);
        ChatPane chatPane = new ChatPane(watchedChatDto, userInfoDto, this);

        CHAT_AREA_MAP.put(userInfoDto.getId(), new Object[]{chatPane, chatArea});
        USERS_MEM.put(userInfoDto.getId(), userInfoDto);

        TRACKING_ELEMENT_COLLECTION.putToLong(userInfoDto.getId(), chatPane);
        userChats.getChildren().add(chatPane.getChatPane());

        scrollMessages.setContent(chatArea.getvBox());

//        chatContent.getChildren().set(0, chatArea.getScrollPane());
    }

    public void addNewMessage(long otherUserId, MessageDto messageDto) {
        System.out.println("ID другого пользователя" + otherUserId);
        System.out.println("А ЭТО ЧАТЫ ВИДИМЫЕ -" + Main.user.getWatchedPrivateChats());

        Object[] objects = CHAT_AREA_MAP.get(otherUserId);
        ChatPane chatPane = (ChatPane) objects[0];

        MessagePane.setMessageAuthor(messageDto.getUserSenderNickname(), chatPane.getAuthor());
//
//        String nickname;
//
//        if (Main.user.getNickname().equals(messageDto.getUserSenderNickname())) {
//            nickname = "Me:";
//            chatPane.getAuthor().setStyle("-fx-text-fill: #ee686f");
//        } else {
//            nickname = messageDto.getUserSenderNickname();
//            chatPane.getAuthor().setStyle("-fx-text-fill: #6541cf");
//        }
//
//        chatPane.getAuthor().setText(nickname);

//        chatPane.getAuthor().setText(messageDto.getUserSenderNickname());
        chatPane.getDateLabel().setText(messageDto.getSendTime().toString());
        chatPane.getMessageLabel().setText(messageDto.getText());

        ChatArea chatArea = (ChatArea) objects[1];

        if (chatArea != null) {
            MessagePane messagePane = new MessagePane(messageDto);
            chatArea.getvBox().getChildren().add(messagePane.getAnchorPane());
            chatArea.getMessages().put(messageDto.getId(), messagePane);
        }
    }

    public static void setSelectedUser(long selectedUserId) {
        selectedUser = USERS_MEM.get(selectedUserId);
    }

    @FXML
    public void handleShowChatArea() {
//        friendsWindow.cancelWindow();
//        chatAreaWindow.showWindow();
//        chatAreaWindow.setParent(userChatMenuWindow);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #1d1d1d");
        scrollMessages.setContent(new AnchorPane());
//        chatContent.getChildren().set(0, new AnchorPane());

        // ПОТОМ ЛУЧШЕ УДАЛИТЬ!!!
        UserPane userPane = new UserPane(selectedUser);
        TRACKING_ELEMENT_COLLECTION.putToLong(selectedUser.getId(), userPane);
        chatArea.getChildren().set(0, userPane.getAnchorPane());
        if (CHAT_AREA_MAP.containsKey(selectedUser.getId())) {
            System.out.println("ОТКУДА У ПОЛЬЗОВАТЕЛЯ ЧУЖИЕ ЧАТЫ???");
            System.out.println(CHAT_AREA_MAP);
            Object[] objects = CHAT_AREA_MAP.get(selectedUser.getId());
            ChatArea chatArea = (ChatArea) objects[1];

            scrollMessages.setContent(chatArea.getvBox());
            scrollMessages.vvalueProperty().bind(chatArea.getvBox().heightProperty());
//            chatContent.getChildren().set(0, chatArea.getScrollPane());
        }

        enterMessageTextArea.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    enterMessageTextArea.deletePreviousChar();
                    MessageDto messageDto = new MessageDto(enterMessageTextArea.getText(),
                            -1,
                            Main.user.getNickname());


                    Object[] objects = new Object[] {messageDto, selectedUser.getId()};
                    main.client.out.writeObject(new SocketMessage(MessageType.CHAT_MESSAGE, objects));
                    enterMessageTextArea.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        enterMessageTextArea.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
//        });
//        WatchedChatDto watchedChatDto = main.user.getWatchedPrivateChats().get(selectedUser);
//        if (watchedChatDto == null) {
//            main.client.out.writeObject(new SocketMessage(MessageType.CREATE_NEW_CHAT, selectedUser));
//            return;
//        }

        //ДОБАВИТЬ LISTENER ЕСЛИ размер ОКНА СТАНОВИТСЯ БОЛЬШЕ

//        chatAreaWindo;
    }

    @FXML
    private void handleCloseFoundUserMenu() {
        foundUserInfoMenuWindow.cancelWindow();
//        UserTrackingImpl userTracking = USER_TRACKING_MAP.get(selectedUser.getId());
//
//        userTracking.setPrevWindow();
//        UserChart.TranslateToUserChart(userTracking, this);
    }

    // FOUND USER MENU



    public void fillUserChart(Set<UserInfoDto> users) {
        userChartBox.getChildren().clear();

        if (users.isEmpty()) {
            searchUserWarning.setVisible(true);
        } else {
            searchUserWarning.setVisible(false);
            for (UserInfoDto userDto : users) {
                UserChart userPane = new UserChart(userDto,this);

                TRACKING_ELEMENT_COLLECTION.putToTemp(userDto.getId(), userPane);
                System.out.println("ДОБАВЛЯЕМ В КОЛЛЕКЦИЮ В fillCartUser - 537 - UserChartMenu");
                System.out.println(TRACKING_ELEMENT_COLLECTION.tempTrackingElements + " ЭТО ЖЕ MAP");
                System.out.println(userDto.getId());
                userChartBox.getChildren().add(userPane.getAnchorPane());
            }
        }
    }

    @FXML
    private void handleLogout() {
        main.client.sendSocketMessage(new SocketMessage(MessageType.LOGOUT, null));
        main.exit();
//        main.client.getSocket().close();
//        main.showStartMenu();
    }

//    @FXML
//    private void handleBack() {
//        cancelWindow(windowsStack.pop());
//    }

    @FXML
    private void handleCancelSettings() {
        settingsWindow.cancelWindow();
    }

    @FXML
    private void showSettings() {
        settingsWindow.showWindow();
    }

    private void showEdit(CustomWindow customWindow) {
        customWindow.showWindow();
    }

    @FXML
    private void showEditProfile() {
        showEdit(editProfileWindow);
    }

    @FXML
    private void handleCancelEditProfile() {
        editProfileWindow.cancelWindow();
    }

    @FXML
    private void handleCancelEditUsernameDialog() {
        editUsernameDialogWindow.cancelWindow();
        editProfileMenu.setOnMouseClicked(null);
    }

    @FXML
    private void handleCancelEditAboutMeDialog() {
        editAboutMeDialogWindow.cancelWindow();
        editProfileMenu.setOnMouseClicked(null);
    }

    private void handleOpenEditProfileDialog(CustomWindow window){
        window.setCancelSpecFunc(() -> {
            editProfileScrollPane.setDisable(false);
            backEditProfileButton.setDisable(false);

//            settingsMenu.getChildren().get(0).setDisable(false);
        });

        window.showWindow();
        window.setParent(editProfileWindow);

        editProfileScrollPane.setDisable(true);
        backEditProfileButton.setDisable(true);

        settingsMenu.getChildren().get(0).setDisable(true);

    }


    @FXML
    private void showEditUsername() {
        handleOpenEditProfileDialog(editUsernameDialogWindow);
    }

    private void updateUsernameAnywhere() {
        String lastName = Main.user.getLastName();
        String firstName = Main.user.getFirstName();

        lastNameTextField.setText(lastName);
        firstNameTextField.setText(firstName);

        String username = lastName + " " + firstName;
        usernameEditLabel.setText(username);
        usernameInSettingsLabel.setText(username);

        changeUsernameLabel();
    }

    @FXML
    private void handleSaveEditUsername() {
        String lastName = lastNameTextField.getText();
        String firstName = firstNameTextField.getText();

        editUsernameDialogWindow.cancelWindow();
        editProfileMenu.setOnMouseClicked(null);

        if (lastName.equals(Main.user.getLastName()) && firstName.equals(Main.user.getFirstName()))
            return;

        SocketMessage socketMessage = new SocketMessage(MessageType.EDIT_NAME,
                new String[]{lastName, firstName});

        //ВАЖНО
        //ОБНОВИТСЯ ПО EDIT USERINFO
        main.client.sendSocketMessage(socketMessage);
        //ВАЖНО
        //СДЕЛАТЬ AUTOCLOSED ИНТЕРФЕЙС
    }

    @FXML
    private void showEditAboutMe() {
        handleOpenEditProfileDialog(editAboutMeDialogWindow);
    }

    private void updateAboutMeAnyWhere() {
        String aboutMe = Main.user.getAboutMe();
        aboutMeTextField.setText(aboutMe);
        aboutMeInSettingsLabel.setText(aboutMe);
    }

    @FXML
    private void handleSaveEditAboutMe() {
        String aboutMe = aboutMeTextField.getText();

        if (aboutMe.equals(Main.user.getAboutMe()))
            return;

        SocketMessage socketMessage = new SocketMessage(MessageType.EDIT_ABOUT_ME, aboutMe);

        //ОБНОВЛЯЕТСЯ АВТОМАТИЧЕСКИ
        main.client.sendSocketMessage(socketMessage);

        editAboutMeDialogWindow.cancelWindow();
        editProfileMenu.setOnMouseClicked(null);
    }

    private ImagePattern getAvatarPattern(byte[] avatar) {
        ByteArrayInputStream in = new ByteArrayInputStream(avatar);
        Image image = new Image(in);
        return new ImagePattern(image);
    }



    private void updateAvatarAnywhere() {
        byte[] avatar = Main.user.getAvatar();
        if (avatar == null)
            return;
        ImagePattern imagePattern = getAvatarPattern(avatar);

        circleAvatarInEditMenu.setFill(imagePattern);
        circleAvatarInSettings.setFill(imagePattern);
        circleAvatarInUserMenu.setFill(imagePattern);
    }

    @FXML
    private void handleChoosePhoto() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage chooseWindow = new Stage();

        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File imagePath = fileChooser.showOpenDialog(chooseWindow);
        if (imagePath == null || imagePath.length() > 5 * 1024 * 1024){
            return;
        }

        FileInputStream fileIn = new FileInputStream(imagePath);
        byte[] avatar = fileIn.readAllBytes();
        Main.user.setAvatar(avatar);
        main.client.sendSocketMessage(new SocketMessage(MessageType.EDIT_AVATAR, avatar));

        updateAvatarAnywhere();

    }

    @FXML
    private void showEditPrivacy() {
        showEdit(editPrivacyDialogWindow);
    }

    @FXML
    private void handleCancelEditPrivacy() {
        editPrivacyDialogWindow.cancelWindow();

        if (invisibleRadioButton.isSelected() && !Main.user.getConfiguration().isInvisible())
            invisibleRadioButton.fire();
        if (messageFromFriendsOnlyRadioButton.isSelected() && !Main.user.getConfiguration().isMsgFromFriendsOnly())
            messageFromFriendsOnlyRadioButton.fire();
        if (showLastOnlineRadioButton.isSelected() && !Main.user.getConfiguration().isShowLastOnline())
            showLastOnlineRadioButton.fire();
    }
    @FXML
    private void handleSaveEditPrivacy() {
        editPrivacyDialogWindow.cancelWindow();

        ConfigurationDto configurationDto = new ConfigurationDto(
                Main.user.getId(),
                messageFromFriendsOnlyRadioButton.isSelected(),
                showLastOnlineRadioButton.isSelected(),
                invisibleRadioButton.isSelected());

        main.client.sendSocketMessage(new SocketMessage(MessageType.EDIT_CONFIGURATION, configurationDto));
        Main.user.setConfiguration(configurationDto);
        //ИСПРАВИТЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    }

    private void updateConfigurationAnywhere(ConfigurationDto configurationDto) {
        if (!invisibleRadioButton.isSelected() && configurationDto.isInvisible()) {
            invisibleRadioButton.fire();
        }
        if (!showLastOnlineRadioButton.isSelected() && configurationDto.isShowLastOnline())
            showLastOnlineRadioButton.fire();
        if (!messageFromFriendsOnlyRadioButton.isSelected() &&configurationDto.isMsgFromFriendsOnly())
            messageFromFriendsOnlyRadioButton.fire();
    }


    public void handleEnterPressed(KeyEvent keyEvent) {
//        System.out.println(keyEvent.getCode());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleSearchUsers();
        }
    }
}
