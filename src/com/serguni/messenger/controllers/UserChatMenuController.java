package com.serguni.messenger.controllers;

import com.serguni.messenger.components.CustomWindow;
import com.serguni.messenger.components.SessionChart;
import com.serguni.messenger.components.TrackingElementCollection;
import com.serguni.messenger.components.UserChart;
import com.serguni.messenger.dto.SocketMessage;
import com.serguni.messenger.dto.SocketMessage.MessageType;
import com.serguni.messenger.dto.models.ConfigurationDto;
import com.serguni.messenger.dto.models.SessionDto;
import com.serguni.messenger.dto.models.UserInfoDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import com.serguni.messenger.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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

    @FXML
    private AnchorPane userChatMenu;

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

    // Добавляем сюда чаты
    @FXML
    private VBox userChartBox;

    //
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
    @FXML
    private Circle foundUserInfoAvatar;
    @FXML
    private Label foundUserInfoFullname;
    @FXML
    private Label foundUserInfoLastOnline;
    @FXML
    private Label foundUserInfoNickname;
    // USER INFO MENU

    //SESSIONS MENU
    @FXML
    private AnchorPane sessionsMenu;
    @FXML
    private Label currentSessionLastOnline;
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
    private long sessionOnDeleteId;

    //CONFIRM DELETE SESSION



    public static final TrackingElementCollection TRACKING_ELEMENT_COLLECTION = new TrackingElementCollection();
    public static final Map<Long, Integer> USERS_SESSIONS = new HashMap<>();
    private final Set<Long> tempSearch = new HashSet<>();
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

    }


    public void setMain(Main main) {
        this.main = main;
        this.main.client.setUserChatMenuController(this);

        //ИНИЦИАЛИЗАЦИЯ ТЕКУЩЕЙ СЕССИИ (В ОКНЕ)
        currentSessionLastOnline.setText("Online");
        deviceAndOs.setText(main.session.getDevice() + " " + main.session.getOs());
        ipAndLocation.setText(main.session.getIp() + " " + main.session.getLocation());
        signInFirstTime.setText(main.session.getSignInTime().toString());
        //ИНИЦИАЛИЗАЦИЯ ТЕКУЩЕЙ СЕССИИ (В ОКНЕ)

        // ДОБАВЛЕНИЕ ДРУГИХ СЕССИЙ
        Set<SessionDto> userSessions = main.user.getSessions();
        for (SessionDto userSession : userSessions) {
            if (userSession.getId() != main.session.getId()) {
                sessionsChartBox.getChildren().add(new SessionChart(userSession, this).getSessionPane());
                USERS_SESSIONS.put(userSession.getId(), (sessionsChartBox.getChildren().size() - 1));
            }
        }
        // ДОБАВЛЕНИЕ ДРУГИХ СЕССИЙ


        emailInSettingsLabel.setText(main.user.getEmail());
        updateUsernameAnywhere();
        updateAboutMeAnyWhere();
        updateAvatarAnywhere();
        updateConfigurationAnywhere(main.user.getConfiguration());

//        TRACKING_ELEMENT_COLLECTION.put(main.user.getId(), userInfo -> Platform.runLater(() ->{
//            UserInfoDto userInfoDto = (UserInfoDto) userInfo;
////
//        }));
    }

    private void changeUsernameLabel() {
        String nameLabel = main.user.getLastName() + " " + main.user.getFirstName();
        System.out.println(nameLabel.equals(" "));
        if (nameLabel.equals(" ")) {
            usernameLabel.setText(main.user.getNickname());
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
        TRACKING_ELEMENT_COLLECTION.removeAll(tempSearch);
        tempSearch.clear();

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
        Platform.runLater(() -> {
            confirmDeleteSessionWindow.showWindow();
            confirmDeleteSessionWindow.setParent(sessionsWindow);

            sessionOnDeleteId = sessionChart.getSessionInfo().getId();
        });
    }

    public void deleteOtherSession(long deletedSessionId) {
        Platform.runLater(() -> {
            sessionsChartBox.getChildren().remove((int)USERS_SESSIONS.remove(deletedSessionId));
        });
    }

    @FXML
    private void handleCloseSession() throws IOException {
        sessionsChartBox.getChildren().remove((int)USERS_SESSIONS.remove(sessionOnDeleteId));
        confirmDeleteSessionWindow.cancelWindow();

        main.client.out.writeObject(new SocketMessage(MessageType.DELETE_OTHER_SESSION, sessionOnDeleteId));
    }

    @FXML
    private void handleCancelConfirmDialog() {
        confirmDeleteSessionWindow.cancelWindow();
    }

    public void addSessionsInfo(SessionDto sessionDto) {
        Platform.runLater(() ->{
            main.user.getSessions().add(sessionDto);
            AnchorPane sessionPane = new SessionChart(sessionDto, this).getSessionPane();
            sessionsChartBox.getChildren().add(sessionPane);
            USERS_SESSIONS.put(sessionDto.getId(), sessionsChartBox.getChildren().size() - 1);
        });
    }
    //SESSION MENU


    public void updateUserInfo(UserInfoDto userInfoDto) {
        if (userInfoDto.getId() == main.user.getId()) {
            main.user.setLastName(userInfoDto.getLastName());
            main.user.setFirstName(userInfoDto.getFirstName());
            main.user.setAvatar(userInfoDto.getAvatar());
            main.user.setAboutMe(userInfoDto.getAboutMe());

            System.out.println("UserChatMenuController -> 310 -> ПРОВЕРКА ПОЛЬЗОВАТЕЛЯ");
            System.out.println(main.user.getLastName() + " " + main.user.getFirstName());

            Platform.runLater(() -> {
                updateUsernameAnywhere();
                updateAboutMeAnyWhere();
                updateAvatarAnywhere();
                //ДЛЯ КОНФИГУРАЦИИ ОТДЕЛЬНО
            });

        }

        TRACKING_ELEMENT_COLLECTION.updateInfo(userInfoDto);
    }

    public void showFoundUserInfoMenu(UserInfoDto userInfoDto) {
        foundUserInfoMenuWindow.showWindow();
        friendsMenu.setOnMouseClicked(mouseEvent -> foundUserInfoMenuWindow.cancelWindow());
        foundUserInfoFullname.setText(userInfoDto.getLastName() + " " + userInfoDto.getFirstName());
        foundUserInfoNickname.setText(userInfoDto.getNickname());
        foundUserInfoAvatar.setFill(getAvatarPattern(userInfoDto.getAvatar()));
        //ПРЕОБРАЗОВЫВАТЬ ДАТУ В СТРОКУ ЧЕРЕЗ ПАТТЕРН
        foundUserInfoLastOnline.setText(userInfoDto.getLastOnline().toString());
    }

    public void fillUserChart(Set<UserInfoDto> users) {
        Platform.runLater(() -> userChartBox.getChildren().clear());

        if (users.isEmpty()) {
            searchUserWarning.setVisible(true);
        } else {
            searchUserWarning.setVisible(false);
            for (UserInfoDto userDto : users) {
                UserChart userChart = new UserChart(userDto, this);
                TRACKING_ELEMENT_COLLECTION.put(userDto.getId(), userChart);
                Platform.runLater(() -> userChartBox.getChildren().add(userChart.getAnchorPane()));
            }
        }
    }

    @FXML
    private void handleLogout() throws IOException {
        main.client.sendSocketMessage(new SocketMessage(MessageType.LOGOUT, null));
        main.client.getSocket().close();
        main.showStartMenu();
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
        String lastName = main.user.getLastName();
        String firstName = main.user.getFirstName();

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

        if (lastName.equals(main.user.getLastName()) && firstName.equals(main.user.getFirstName()))
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
        String aboutMe = main.user.getAboutMe();
        aboutMeTextField.setText(aboutMe);
        aboutMeInSettingsLabel.setText(aboutMe);
    }

    @FXML
    private void handleSaveEditAboutMe() {
        String aboutMe = aboutMeTextField.getText();

        if (aboutMe.equals(main.user.getAboutMe()))
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
        byte[] avatar = main.user.getAvatar();
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
        fileChooser.setInitialDirectory(new File(System.getenv("HOMEPATH")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File imagePath = fileChooser.showOpenDialog(chooseWindow);
        if (imagePath.length() > 5 * 1024 * 1024){
            return;
        }

        FileInputStream fileIn = new FileInputStream(imagePath);
        byte[] avatar = fileIn.readAllBytes();
        main.user.setAvatar(avatar);
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

        if (invisibleRadioButton.isSelected() && !main.user.getConfiguration().isInvisible())
            invisibleRadioButton.fire();
        if (messageFromFriendsOnlyRadioButton.isSelected() && !main.user.getConfiguration().isMsgFromFriendsOnly())
            messageFromFriendsOnlyRadioButton.fire();
        if (showLastOnlineRadioButton.isSelected() && !main.user.getConfiguration().isShowLastOnline())
            showLastOnlineRadioButton.fire();
    }
    @FXML
    private void handleSaveEditPrivacy() {
        editPrivacyDialogWindow.cancelWindow();

        ConfigurationDto configurationDto = new ConfigurationDto(
                main.user.getId(),
                messageFromFriendsOnlyRadioButton.isSelected(),
                showLastOnlineRadioButton.isSelected(),
                invisibleRadioButton.isSelected());

        main.client.sendSocketMessage(new SocketMessage(MessageType.EDIT_CONFIGURATION, configurationDto));
        main.user.setConfiguration(configurationDto);
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
        System.out.println(keyEvent.getCode());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleSearchUsers();
        }
    }
}
