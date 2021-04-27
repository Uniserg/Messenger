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
import javafx.fxml.FXMLLoader;
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
        userMenuWindow = new CustomWindow(userMenu,-320, settingsWindow);
        friendsWindow = new CustomWindow(friendsMenu, 345, null);
        settingsWindow = new CustomWindow(settingsMenu, -380, null); // назначать
        editProfileWindow = new CustomWindow(editProfileMenu, -380, null);
        editUsernameDialogWindow = new CustomWindow(editUsernameDialog, -380, null);
        editAboutMeDialogWindow = new CustomWindow(editAboutMeDialog, -380, null);
        editPrivacyDialogWindow = new CustomWindow(editPrivacyDialog, -365, null);
        foundUserInfoMenuWindow = new CustomWindow(foundUserInfoMenu, 345, null);
        sessionsWindow = new CustomWindow(sessionsMenu, -381, null);

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
        updateUsernameAnywhere(main.user.getLastName(), main.user.getFirstName());
        updateAboutMeAnyWhere(main.user.getAboutMe());
        updateAvatarAnywhere(main.user.getAvatar());
        updateConfigurationAnywhere(main.user.getConfiguration());

        TRACKING_ELEMENT_COLLECTION.put(main.user.getId(), userInfo -> Platform.runLater(() ->{
            UserInfoDto userInfoDto = (UserInfoDto) userInfo;
            usernameLabel.setText(userInfoDto.getLastName() + " " + userInfoDto.getFirstName());
        }));
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

        String nameLabel = main.user.getLastName() + " " + main.user.getFirstName();
        if (nameLabel.equals(" ")) {
            usernameLabel.setText(main.user.getNickname());
        } else {
            usernameLabel.setText(nameLabel);
        }

        userMenuWindow.showWindow();
        userChatMenuWindow.setChild(userMenuWindow);

        userChatMenu.setOnMouseClicked(mouseEvent -> userChatMouseEvent(userMenuWindow));
    }

    @FXML
    private void showFriendsMenu() {
        userMenuWindow.cancelWindow();

        friendsWindow.showWindow();
        userChatMenuWindow.setChild(friendsWindow);

        userChatMenu.setOnMouseClicked(mouseEvent -> {
            userChatMouseEvent(friendsWindow);
        });
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
        settingsWindow.setChild(sessionsWindow);
        settingsMenu.setOnMouseClicked(mouseEvent -> sessionsWindow.cancelWindow());
    }

    @FXML
    public void handleCancelSessionWindow() {
        sessionsWindow.cancelWindow();
    }


    public void showConfirmDeleteSessionDialog(SessionChart sessionChart) {
        FXMLLoader loader = new FXMLLoader();
    }

    public void addSessionsInfo(SessionDto sessionDto) {
        Platform.runLater(() ->{
            main.user.getSessions().add(sessionDto);
            AnchorPane anchorPane = new SessionChart(sessionDto, this).getSessionPane();
            sessionsChartBox.getChildren().add(anchorPane);
            USERS_SESSIONS.put(sessionDto.getId(), sessionsChartBox.getChildren().size() - 1);
        });
    }
    //SESSION MENU


    public void updateUserInfo(UserInfoDto userInfoDto) {
        if (userInfoDto.getId() == main.user.getId()) {
            main.user.setLastName(userInfoDto.getLastName());
            main.user.setLastName(userInfoDto.getFirstName());
            main.user.setAvatar(userInfoDto.getAvatar());
            main.user.setAboutMe(userInfoDto.getAboutMe());

            Platform.runLater(() -> {
                updateUsernameAnywhere(userInfoDto.getLastName(), userInfoDto.getFirstName());
                updateAboutMeAnyWhere(userInfoDto.getAboutMe());
                updateAvatarAnywhere(userInfoDto.getAvatar());
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
                System.out.println(userDto.getLastOnline());
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
        userMenuWindow.setChild(settingsWindow);

//        windowsStack.push(settingsMenu);
//        moveObject(settingsMenu, 380);
    }

    private void showEdit(CustomWindow customWindow) {
        customWindow.showWindow();
//        customWindow.setCancelSpecFunc(this::handleEditBack);

        settingsWindow.setChild(customWindow);
        settingsMenu.setOnMouseClicked(mouseEvent -> {

            customWindow.cancelWindow();
        });
    }

    @FXML
    private void showEditProfile() {
        showEdit(editProfileWindow);
    }

    @FXML
    private void handleCancelEditProfile() {
        editProfileWindow.cancelWindow();
        editProfileMenu.setOnMouseClicked(null);
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
        editProfileWindow.setChild(window);

        editProfileScrollPane.setDisable(true);
        backEditProfileButton.setDisable(true);

        settingsMenu.getChildren().get(0).setDisable(true);

        editProfileMenu.setOnMouseClicked(mouseEvent -> {
            window.cancelWindow();
            editProfileMenu.setOnMouseClicked(null);
        });
    }


    @FXML
    private void showEditUsername() {
        handleOpenEditProfileDialog(editUsernameDialogWindow);
    }

    private void updateUsernameAnywhere(String lastName, String firstName) {
        lastNameTextField.setText(lastName);
        firstNameTextField.setText(firstName);

        String username = lastName + " " + firstName;
        usernameLabel.setText(username);
        usernameEditLabel.setText(username);
        usernameInSettingsLabel.setText(username);
    }

    @FXML
    private void handleSaveEditUsername() throws IOException {
        String lastName = lastNameTextField.getText();
        String firstName = firstNameTextField.getText();

        editUsernameDialogWindow.cancelWindow();
        editProfileMenu.setOnMouseClicked(null);

        if (lastName.equals(main.user.getLastName()) && firstName.equals(main.user.getFirstName()))
            return;

        SocketMessage socketMessage = new SocketMessage(MessageType.EDIT_NAME,
                new String[]{lastName, firstName});

        main.client.sendSocketMessage(socketMessage);
        main.user.setLastName(lastName);
        main.user.setFirstName(firstName);

        updateUsernameAnywhere(lastName, firstName);

        //СДЕЛАТЬ AUTOCLOSED ИНТЕРФЕЙС
    }

    @FXML
    private void showEditAboutMe() {
        handleOpenEditProfileDialog(editAboutMeDialogWindow);
    }

    private void updateAboutMeAnyWhere(String aboutMe) {
        aboutMeTextField.setText(aboutMe);
        aboutMeInSettingsLabel.setText(aboutMe);
    }

    @FXML
    private void handleSaveEditAboutMe() throws IOException {
        String aboutMe = aboutMeTextField.getText();

        if (aboutMe.equals(main.user.getAboutMe()))
            return;

        SocketMessage socketMessage = new SocketMessage(MessageType.EDIT_ABOUT_ME, aboutMe);

        main.client.sendSocketMessage(socketMessage);
        main.user.setAboutMe(aboutMe);
        updateAboutMeAnyWhere(aboutMe);

        editAboutMeDialogWindow.cancelWindow();

        editProfileMenu.setOnMouseClicked(null);
    }

    private ImagePattern getAvatarPattern(byte[] avatar) {
        ByteArrayInputStream in = new ByteArrayInputStream(avatar);
        Image image = new Image(in);
        return new ImagePattern(image);
    }



    private void updateAvatarAnywhere(byte[] avatar) {
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

        updateAvatarAnywhere(avatar);

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
    private void handleSaveEditPrivacy() throws IOException {
        editPrivacyDialogWindow.cancelWindow();

        ConfigurationDto configurationDto = new ConfigurationDto(
                main.user.getId(),
                messageFromFriendsOnlyRadioButton.isSelected(),
                showLastOnlineRadioButton.isSelected(),
                invisibleRadioButton.isSelected());

        main.client.sendSocketMessage(new SocketMessage(MessageType.EDIT_CONFIGURATION, configurationDto));
        main.user.setConfiguration(configurationDto);

    }

    private void updateConfigurationAnywhere(ConfigurationDto configurationDto) {
        if (!invisibleRadioButton.isSelected() && configurationDto.isInvisible()) {
            invisibleRadioButton.fire();
            System.out.println("мы тут и кнопка выбрана??" + invisibleRadioButton.isSelected());
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
