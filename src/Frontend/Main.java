package Frontend;

//!Import
import org.json.JSONArray;
import javafx.scene.control.ScrollPane;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.scene.control.Hyperlink;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {
    private TabPane loginTabPane, userProfile;
    private String userName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ! Main tab for initial UI setup
        loginTabPane = new TabPane();
        userProfile = new TabPane();
        Tab LoginmainTab = new Tab("Login");
        LoginmainTab.setClosable(false);
        VBox LoginmainTabContent = new VBox(20);
        LoginmainTabContent.setBackground(
                new Background(new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
        LoginmainTabContent.setAlignment(Pos.CENTER);
        Font.loadFont(getClass().getResourceAsStream(
                "./Fonts/OpenSans-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream(
                "./Fonts/Montserrat-Regular.ttf"), 12);

        // !To lable
        Button header = new Button();
        header.setText("LogIn Page");
        header.setStyle(
                "-fx-font-family: 'Montserrat', sans-serif; -fx-background-color: #ffb600;-fx-border-color: #ffb600; -fx-text-fill: #fff;");
        header.getStyleClass().add("search_button");
        LoginmainTabContent.getChildren().add(header);
        Label log = new Label();
        log.getStyleClass().add("label-text");
        log.setStyle("-fx-font-family: 'Montserrat', sans-serif; -fx-font-size: 18px;");
        log.setText("Enter your email and password");
        LoginmainTabContent.getChildren().add(log);

        // !For Signup
        TextField fullName = new TextField();
        fullName.setPromptText("Enter your full Name");
        fullName.setMaxWidth(550);
        fullName.setMinHeight(40);
        fullName.setPrefWidth(550);
        fullName.setMinHeight(40);
        fullName.setFont(new javafx.scene.text.Font(15));
        fullName.setStyle("-fx-font-family:'Open Sans', sans-serif;");
        fullName.getStyleClass().add("search_input");
        fullName.setVisible(false);
        fullName.setManaged(false);
        LoginmainTabContent.getChildren().addAll(fullName);
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Enter your email address");
        emailTextField.setMaxWidth(550);
        emailTextField.setMinHeight(40);
        emailTextField.setPrefWidth(550);
        emailTextField.setMinHeight(40);
        emailTextField.setFont(new javafx.scene.text.Font(15));
        emailTextField.setStyle("-fx-font-family:'Open Sans', sans-serif;");
        emailTextField.getStyleClass().add("search_input");
        LoginmainTabContent.getChildren().addAll(emailTextField);
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Enter your password");
        passwordTextField.setMaxWidth(550);
        passwordTextField.setMinHeight(40);
        passwordTextField.setPrefWidth(550);
        passwordTextField.setMinHeight(40);
        passwordTextField.setStyle("-fx-font-family:'Open Sans', sans-serif;");
        passwordTextField.setFont(new javafx.scene.text.Font(15));
        passwordTextField.getStyleClass().add("search_input");
        LoginmainTabContent.getChildren().addAll(passwordTextField);

        // !Buttons
        Button Login = new Button();
        Login.setText("Log In");
        Login.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
        Login.getStyleClass().add("button");
        LoginmainTabContent.getChildren().add(Login);
        Button SignUp = new Button();
        SignUp.setVisible(false);
        SignUp.setManaged(false);
        SignUp.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
        SignUp.setText("Sign Up");
        SignUp.getStyleClass().add("button");
        LoginmainTabContent.getChildren().add(SignUp);

        // *Sign Up
        SignUp.setOnAction(event -> {
            String name = fullName.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null);
                alert.setContentText("Name is required.");
                alert.showAndWait();

            } else if (email.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null);
                alert.setContentText("Email is required.");
                alert.showAndWait();

            } else if (password.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null);
                alert.setContentText("Password is required.");
                alert.showAndWait();

            } else if (password.length() < 8) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Security Error");
                alert.setHeaderText(null);
                alert.setContentText("Password must be minimum of 8 character.");
                alert.showAndWait();
            } else {
                if (email.contains("@gmail.com")) {
                    try {
                        addUser(name, email, password);
                        fullName.clear();
                        emailTextField.clear();
                        passwordTextField.clear();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null); // No header text
                    alert.setContentText("Please enter a valid email.");
                    alert.showAndWait();

                }
            }

        });

        // *Log In
        Login.setOnAction(event -> {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            if (email.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null); // No header text
                alert.setContentText("Email is required.");
                alert.showAndWait();

            } else if (password.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null); // No header text
                alert.setContentText("Password is required.");
                alert.showAndWait();
            } else {
                if (email.contains("@gmail.com")) {
                    try {
                        boolean finalValue = authencation(email, password);
                        if (finalValue) {
                            loginTabPane.getTabs().remove(LoginmainTab);
                            Tab mainTab = new Tab("Google");
                            mainTab.setClosable(false);
                            VBox mainTabContent = new VBox(20);
                            mainTabContent.setBackground(
                                    new Background(
                                            new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
                            mainTabContent.setAlignment(Pos.TOP_CENTER);

                            Button showProfile = new Button();
                            showProfile.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
                            showProfile.setText("Show Profile");
                            showProfile.getStyleClass().add("button");
                            VBox.setMargin(showProfile, new Insets(20, 0, 20, 1100));
                            mainTabContent.getChildren().add(showProfile);

                            showProfile.setOnAction(e -> {
                                showUserProfile(userName, email, password, loginTabPane, mainTab, LoginmainTab);
                                emailTextField.clear();
                                passwordTextField.clear();
                                fullName.clear();
                            });

                            // ! Top Google image
                            Image headingImage = new Image(
                                    getClass().getResourceAsStream("/Frontend/Images/Google.png"));
                            ImageView imageView = new ImageView(headingImage);
                            imageView.setFitWidth(500);
                            imageView.setFitHeight(170);
                            mainTabContent.getChildren().addAll(imageView);

                            // ! Input Text Field
                            TextField searchInputTextField = new TextField();
                            searchInputTextField.setPromptText("Search Google or type a URL");
                            searchInputTextField.setMaxWidth(550);
                            searchInputTextField.setMinHeight(40);
                            searchInputTextField.setPrefWidth(550);
                            searchInputTextField.setMinHeight(40);
                            searchInputTextField.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
                            searchInputTextField.setFont(new javafx.scene.text.Font(15));
                            searchInputTextField.getStyleClass().add("search_input");
                            mainTabContent.getChildren().addAll(searchInputTextField);

                            // ! Submit Button
                            Button ButtonSearch = new Button();
                            ButtonSearch.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
                            ButtonSearch.setText("Google Search");
                            ButtonSearch.getStyleClass().add("button");
                            Button feelingLucky = new Button();
                            feelingLucky.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
                            feelingLucky.setText("Feeling Lucky");
                            feelingLucky.getStyleClass().add("button");

                            // ! Hbox for button
                            HBox button = new HBox(10);
                            button.getChildren().addAll(ButtonSearch, feelingLucky);
                            HBox.setMargin(ButtonSearch, new Insets(0, 10, 153, 500));
                            mainTabContent.getChildren().addAll(button);

                            mainTab.setContent(mainTabContent);
                            loginTabPane.getTabs().add(mainTab);
                            VBox.setMargin(imageView, new Insets(50, 0, 0, 0));
                            VBox.setMargin(searchInputTextField, new Insets(10, 0, 0, 0));
                            VBox.setMargin(ButtonSearch, new Insets(0, 0, 0, 0));

                            // ! Event handler for search button
                            ButtonSearch.setOnAction(e -> {
                                try {
                                    showSearchResults(loginTabPane, searchInputTextField.getText());
                                    searchInputTextField.clear();
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            });

                            String cssFile = getClass().getResource("/Frontend/Style.css").toExternalForm();
                            mainTabContent.getStylesheets().add(cssFile);
                            primaryStage.setTitle("Google");
                            primaryStage.getIcons()
                                    .add(new Image(getClass().getResourceAsStream("/Frontend/Images/logo.png")));
                            primaryStage.setScene(new Scene(loginTabPane, 1270, 685));
                            primaryStage.show();
                        } else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("No User Found");
                            alert.setHeaderText(null); // No header text
                            alert.setContentText("No user found. If you dont have account please Sign Up.");
                            alert.showAndWait();
                        }
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid email.");
                    alert.showAndWait();
                }
            }
        });

        Button logIn = new Button();
        logIn.setText("Login");
        logIn.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
        logIn.getStyleClass().add("search_button");
        logIn.setDisable(true);
        Button signUp = new Button();
        signUp.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
        signUp.setText("Signup");
        signUp.getStyleClass().add("search_buttonL");

        // !Hide and display
        logIn.setOnAction(event -> {
            header.getStyleClass().remove("search_buttonL");
            header.getStyleClass().add("search_button");
            logIn.setDisable(true);
            signUp.setDisable(false);
            header.setText("LogIn Page");
            fullName.setVisible(false);
            fullName.setManaged(false);
            log.setVisible(true);
            log.setManaged(true);
            SignUp.setVisible(false);
            SignUp.setManaged(false);
            Login.setVisible(true);
            Login.setManaged(true);
            fullName.clear();
            emailTextField.clear();
            passwordTextField.clear();
        });

        signUp.setOnAction(event -> {
            header.getStyleClass().remove("search_button");
            header.getStyleClass().add("search_buttonL");
            signUp.setDisable(true);
            logIn.setDisable(false);
            header.setText("SignUp Page");
            fullName.setVisible(true);
            fullName.setManaged(true);
            log.setVisible(false);
            log.setManaged(false);
            Login.setVisible(false);
            Login.setManaged(false);
            SignUp.setVisible(true);
            SignUp.setManaged(true);
            fullName.clear();
            emailTextField.clear();
            passwordTextField.clear();
        });

        // ! Hbox for button
        HBox button = new HBox(10);
        button.getChildren().addAll(logIn, signUp);
        HBox.setMargin(logIn, new Insets(0, 0, 150, 385));
        LoginmainTabContent.getChildren().addAll(button);

        LoginmainTab.setContent(LoginmainTabContent);
        loginTabPane.getTabs().add(LoginmainTab);
        VBox.setMargin(emailTextField, new Insets(10, 0, 0, 0));
        VBox.setMargin(passwordTextField, new Insets(10, 0, 0, 0));
        VBox.setMargin(header, new Insets(80, 0, 50, 0));
        String cssFile = getClass().getResource("/Frontend/Style.css").toExternalForm();
        LoginmainTabContent.getStylesheets().add(cssFile);
        primaryStage.setTitle("Login");
        primaryStage.getIcons()
                .add(new Image(getClass().getResourceAsStream("/Frontend/Images/logo.png")));
        primaryStage.setScene(new Scene(loginTabPane, 1270, 685));
        primaryStage.show();
    }

    public void addUser(String name, String email, String password) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(
                "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
            properties.load(input);
        }
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String passwordE = properties.getProperty("db.password");
        try (
                Connection con = DriverManager.getConnection(url, username, passwordE);
                Statement stmt = con.createStatement()) {

            String strSelect = "select name, email, password from users";
            ResultSet rset = stmt.executeQuery(strSelect);
            boolean emailExists = false;
            while (rset.next()) {
                String emaildb = rset.getString("email");
                if (emaildb.equals(email)) {
                    emailExists = true;
                    break;
                }
            }
            if (emailExists) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("This email hase already an account");
                alert.showAndWait();
            } else {
                String added = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(added)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("New User");
                        alert.setHeaderText(null);
                        alert.setContentText("You signed up successfully. Log in to continue");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Error occurred during sign-up. Please try again.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showUserProfile(String userName, String email, String Password, TabPane tabPane, Tab currentTab,
            Tab targetTab) {
        Tab profileTab = new Tab("Profile");
        VBox profileBox = new VBox(20);
        profileBox.setBackground(new Background(
                new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
        profileBox.setAlignment(Pos.CENTER);
        profileTab.setContent(profileBox);
        // !Name
        Label profileName = new Label("Full Name: ");
        profileName.setStyle("-fx-font-family: 'Montserrat', sans-serif; -fx-font-size: 20px;");
        TextField pName = new TextField();
        pName.setEditable(false);
        pName.setText(userName);
        pName.setMaxWidth(550);
        pName.setMinHeight(40);
        pName.setPrefWidth(550);
        pName.setMinHeight(40);
        pName.setFont(new javafx.scene.text.Font(15));
        pName.setStyle("-fx-font-family:'Open Sans', sans-serif;");
        pName.getStyleClass().add("search_input");
        // ! Name Button
        Button updateName = new Button();
        updateName.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        updateName.setText("Update Name");
        updateName.getStyleClass().add("button");
        updateName.getStyleClass().add("button");
        Button confirmUpdateName = new Button();
        confirmUpdateName.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        confirmUpdateName.setText("Confirm");
        confirmUpdateName.getStyleClass().add("button");
        confirmUpdateName.setVisible(false);
        confirmUpdateName.setManaged(false);
        Button cancelUpdateName = new Button();
        cancelUpdateName.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        cancelUpdateName.setText("Cancel");
        cancelUpdateName.getStyleClass().add("button");
        cancelUpdateName.setVisible(false);
        cancelUpdateName.setManaged(false);
        HBox buttonName = new HBox(10);
        buttonName.getChildren().addAll(cancelUpdateName, confirmUpdateName);
        HBox.setMargin(cancelUpdateName, new Insets(0, 10, 0, 650));

        // !Button Function
        updateName.setOnAction(e -> {
            pName.setEditable(true);
            updateName.setVisible(false);
            updateName.setManaged(false);
            confirmUpdateName.setVisible(true);
            confirmUpdateName.setManaged(true);
            cancelUpdateName.setVisible(true);
            cancelUpdateName.setManaged(true);

        });
        confirmUpdateName.setOnAction(e -> {
            pName.setEditable(false);
            updateName.setVisible(true);
            updateName.setManaged(true);
            confirmUpdateName.setVisible(false);
            confirmUpdateName.setManaged(false);
            cancelUpdateName.setVisible(false);
            cancelUpdateName.setManaged(false);
            String newName = pName.getText();
            updateName(userName, newName, email);

        });
        cancelUpdateName.setOnAction(e -> {
            pName.setEditable(false);
            updateName.setVisible(true);
            updateName.setManaged(true);
            confirmUpdateName.setVisible(false);
            confirmUpdateName.setManaged(false);
            cancelUpdateName.setVisible(false);
            cancelUpdateName.setManaged(false);

        });

        // !Email
        Label profileEmail = new Label("Email : ");
        profileEmail.setStyle("-fx-font-family: 'Montserrat', sans-serif; -fx-font-size: 20px;");
        TextField pEmail = new TextField();
        pEmail.setEditable(false);
        pEmail.setText(email);
        pEmail.setMaxWidth(550);
        pEmail.setMinHeight(40);
        pEmail.setPrefWidth(550);
        pEmail.setMinHeight(40);
        pEmail.setFont(new javafx.scene.text.Font(15));
        pEmail.setStyle("-fx-font-family:'Open Sans', sans-serif;");
        pEmail.getStyleClass().add("search_input");
        // !Email Button Updates
        Button updateEmail = new Button();
        updateEmail.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        updateEmail.setText("Update Email");
        updateEmail.getStyleClass().add("button");
        updateEmail.getStyleClass().add("button");
        Button confirmUpdate = new Button();
        confirmUpdate.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        confirmUpdate.setText("Confirm");
        confirmUpdate.getStyleClass().add("button");
        confirmUpdate.setVisible(false);
        confirmUpdate.setManaged(false);
        Button cancelUpdate = new Button();
        cancelUpdate.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        cancelUpdate.setText("Cancel");
        cancelUpdate.getStyleClass().add("button");
        cancelUpdate.setVisible(false);
        cancelUpdate.setManaged(false);
        HBox button = new HBox(10);
        button.getChildren().addAll(cancelUpdate, confirmUpdate);
        HBox.setMargin(cancelUpdate, new Insets(0, 10, 0, 650));

        updateEmail.setOnAction(e -> {
            pEmail.setEditable(true);
            updateEmail.setVisible(false);
            updateEmail.setManaged(false);
            confirmUpdate.setVisible(true);
            confirmUpdate.setManaged(true);
            cancelUpdate.setVisible(true);
            cancelUpdate.setManaged(true);

        });
        confirmUpdate.setOnAction(e -> {
            pEmail.setEditable(false);
            updateEmail.setVisible(true);
            updateEmail.setManaged(true);
            confirmUpdate.setVisible(false);
            confirmUpdate.setManaged(false);
            cancelUpdate.setVisible(false);
            cancelUpdate.setManaged(false);
            String newEmail = pEmail.getText();
            updateEmail(email, newEmail);

        });
        cancelUpdate.setOnAction(e -> {
            pEmail.setEditable(false);
            updateEmail.setVisible(true);
            updateEmail.setManaged(true);
            confirmUpdate.setVisible(false);
            confirmUpdate.setManaged(false);
            cancelUpdate.setVisible(false);
            cancelUpdate.setManaged(false);

        });

        // !Password
        Label profilePassword = new Label("Password : ");
        profilePassword.setStyle("-fx-font-family: 'Montserrat', sans-serif; -fx-font-size: 20px;");
        TextField pPassword = new TextField();
        pPassword.setEditable(false);
        pPassword.setText(Password);
        pPassword.setMaxWidth(550);
        pPassword.setMinHeight(40);
        pPassword.setPrefWidth(550);
        pPassword.setMinHeight(40);
        pPassword.setFont(new javafx.scene.text.Font(15));
        pPassword.setStyle("-fx-font-family:'Open Sans', sans-serif;");
        pPassword.getStyleClass().add("search_input");
        // !Password button Update
        Button updatePassword = new Button();
        updatePassword.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        updatePassword.setText("Update Password");
        updatePassword.getStyleClass().add("button");
        Button confirmUpdatePassword = new Button();
        confirmUpdatePassword.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        confirmUpdatePassword.setText("Confirm");
        confirmUpdatePassword.getStyleClass().add("button");
        confirmUpdatePassword.setVisible(false);
        confirmUpdatePassword.setManaged(false);
        Button cancelUpdatePassword = new Button();
        cancelUpdatePassword.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        cancelUpdatePassword.setText("Cancel");
        cancelUpdatePassword.getStyleClass().add("button");
        cancelUpdatePassword.setVisible(false);
        cancelUpdatePassword.setManaged(false);
        HBox buttonPassword = new HBox(10);
        buttonPassword.getChildren().addAll(cancelUpdatePassword, confirmUpdatePassword);
        HBox.setMargin(cancelUpdatePassword, new Insets(0, 10, 0, 650));
        // !Button Update
        updatePassword.setOnAction(e -> {
            pPassword.setEditable(true);
            updatePassword.setVisible(false);
            updatePassword.setManaged(false);
            confirmUpdatePassword.setVisible(true);
            confirmUpdatePassword.setManaged(true);
            cancelUpdatePassword.setVisible(true);
            cancelUpdatePassword.setManaged(true);

        });
        confirmUpdatePassword.setOnAction(e -> {
            pPassword.setEditable(false);
            updatePassword.setVisible(true);
            updatePassword.setManaged(true);
            confirmUpdatePassword.setVisible(false);
            confirmUpdatePassword.setManaged(false);
            cancelUpdatePassword.setVisible(false);
            cancelUpdatePassword.setManaged(false);
            String newPassword = pPassword.getText();
            updatePassword(Password, newPassword, email);

        });
        cancelUpdatePassword.setOnAction(e -> {
            pPassword.setEditable(false);
            updatePassword.setVisible(true);
            updatePassword.setManaged(true);
            confirmUpdatePassword.setVisible(false);
            cancelUpdatePassword.setManaged(false);
            cancelUpdatePassword.setVisible(false);
            cancelUpdatePassword.setManaged(false);

        });

        // !Basic Button
        Button logOut = new Button();
        logOut.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        logOut.setText("LogOut");
        logOut.getStyleClass().add("button");
        Button deleteAccount = new Button();
        deleteAccount.setStyle("-fx-font-family: 'Montserrat', sans-serif");
        deleteAccount.setText("Delete Account");
        deleteAccount.getStyleClass().add("button");
        HBox basicButton = new HBox(10);
        basicButton.getChildren().addAll(logOut, deleteAccount);
        HBox.setMargin(logOut, new Insets(0, 950, 0, 30));
        logOut.setOnAction(e -> {
            tabPane.getTabs().remove(currentTab);
            tabPane.getTabs().remove(profileTab);
            tabPane.getTabs().add(targetTab);
        });
        deleteAccount.setOnAction(e -> {
            deleteAccount(email);
            tabPane.getTabs().remove(currentTab);
            tabPane.getTabs().remove(profileTab);
            tabPane.getTabs().add(targetTab);
        });

        // !Margin Setting

        VBox.setMargin(profileName, new Insets(0, 700, 0, 0));
        VBox.setMargin(pName, new Insets(0, 100, 0, 0));
        VBox.setMargin(profileEmail, new Insets(0, 735, 0, 0));
        VBox.setMargin(pEmail, new Insets(0, 100, 0, 0));
        VBox.setMargin(profilePassword, new Insets(0, 700, 0, 0));
        VBox.setMargin(pPassword, new Insets(0, 100, 0, 0));
        VBox.setMargin(updateEmail, new Insets(0, 0, 0, 230));
        VBox.setMargin(updatePassword, new Insets(0, 0, 0, 230));
        VBox.setMargin(updateName, new Insets(0, 0, 0, 230));
        VBox.setMargin(logOut, new Insets(20, 20, 0, 1100));
        VBox.setMargin(deleteAccount, new Insets(60, 20, 0, 1100));

        profileBox.getChildren().addAll(basicButton, profileName, pName, updateName, buttonName, profileEmail, pEmail,
                updateEmail,
                button,
                profilePassword,
                pPassword,
                updatePassword,
                buttonPassword);
        String cssFile = getClass().getResource("/Frontend/Style.css").toExternalForm();
        profileBox.getStylesheets().add(cssFile);
        tabPane.getTabs().add(profileTab);
        tabPane.getSelectionModel().select(profileTab);
    }

    public void updateName(String oldName, String newName, String userEmail) {

        if (oldName.equals(newName)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Duplicated");
            alert.setHeaderText(null);
            alert.setContentText("It seems like you re enter your old name.");
            alert.showAndWait();
        } else {

            Properties properties = new Properties();
            try (InputStream input = new FileInputStream(
                    "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
                properties.load(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String passwordE = properties.getProperty("db.password");
            boolean emailFinded = false;
            try (
                    Connection con = DriverManager.getConnection(url, username, passwordE);
                    Statement stmt = con.createStatement()) {

                String strSelect = "select name, email, password from users";
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    String name = rset.getString("name");
                    String emaildb = rset.getString("email");
                    System.out.println(name);
                    if (emaildb.equals(userEmail)) {
                        emailFinded = true;
                        break;
                    } else {
                        emailFinded = false;
                    }

                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (emailFinded) {
                try (Connection con = DriverManager.getConnection(url, username, passwordE);
                        PreparedStatement pstmt = con
                                .prepareStatement("UPDATE users SET name = ? WHERE email = ?")) {
                    pstmt.setString(1, newName);
                    pstmt.setString(2, userEmail);
                    int rowsUpdated = pstmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Status 200");
                        alert.setHeaderText(null);
                        alert.setContentText("Your name has been updated.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Email not found");
                        alert.setHeaderText(null);
                        alert.setContentText("The email to be updated was not found.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occurred while updating the email.");
                    alert.showAndWait();
                }

            }
        }

    }

    public void updatePassword(String oldPassword, String newPassword, String userEmail) {
        if (newPassword.length() < 8) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Length Error");
            alert.setHeaderText(null);
            alert.setContentText("Password must me 8 charcter.");
            alert.showAndWait();

        } else {
            if (oldPassword.equals(newPassword)) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Duplicated");
                alert.setHeaderText(null);
                alert.setContentText("It seems like you re enter your old password.");
                alert.showAndWait();
            } else {

                Properties properties = new Properties();
                try (InputStream input = new FileInputStream(
                        "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
                    properties.load(input);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String passwordE = properties.getProperty("db.password");
                boolean emailFinded = false;

                try (
                        Connection con = DriverManager.getConnection(url, username, passwordE);
                        Statement stmt = con.createStatement()) {

                    String strSelect = "select name, email, password from users";
                    ResultSet rset = stmt.executeQuery(strSelect);

                    while (rset.next()) {
                        String name = rset.getString("name");
                        String emaildb = rset.getString("email");
                        String passworddb = rset.getString("password");
                        System.out.println(emaildb + ", " + passworddb + ", " + name);

                        if (emaildb.equals(userEmail)) {
                            emailFinded = true;
                            break;
                        } else {
                            emailFinded = false;
                        }

                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (emailFinded) {
                    try (Connection con = DriverManager.getConnection(url, username, passwordE);
                            PreparedStatement pstmt = con
                                    .prepareStatement("UPDATE users SET password = ? WHERE email = ?")) {
                        pstmt.setString(1, newPassword);
                        pstmt.setString(2, userEmail);
                        int rowsUpdated = pstmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Status 200");
                            alert.setHeaderText(null);
                            alert.setContentText("Your password has been updated.");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Email not found");
                            alert.setHeaderText(null);
                            alert.setContentText("The email to be updated was not found.");
                            alert.showAndWait();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error");
                        alert.setHeaderText(null);
                        alert.setContentText("An error occurred while updating the email.");
                        alert.showAndWait();
                    }
                }
            }
        }
    }

    public void updateEmail(String previousEmail, String newEmail) {
        if (previousEmail.equals(newEmail)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Duplicated");
            alert.setHeaderText(null);
            alert.setContentText("It seems like you reenter your old email.");
            alert.showAndWait();
        } else {
            if (newEmail.contains("@gmail.com")) {
                Properties properties = new Properties();
                try (InputStream input = new FileInputStream(
                        "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
                    properties.load(input);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String passwordE = properties.getProperty("db.password");
                boolean emailFinded = false;

                try (
                        Connection con = DriverManager.getConnection(url, username, passwordE);
                        Statement stmt = con.createStatement()) {

                    String strSelect = "select name, email, password from users";
                    ResultSet rset = stmt.executeQuery(strSelect);

                    while (rset.next()) {
                        String name = rset.getString("name");
                        String emaildb = rset.getString("email");
                        String passworddb = rset.getString("password");
                        System.out.println(emaildb + ", " + passworddb + ", " + name);

                        if (emaildb.equals(newEmail)) {
                            emailFinded = true;
                            break;
                        } else {
                            emailFinded = false;
                        }

                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (emailFinded) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Dublicate");
                    alert.setHeaderText(null);
                    alert.setContentText("The user with same email exist. Please chose a different email.");
                    alert.showAndWait();

                } else {
                    try (Connection con = DriverManager.getConnection(url, username, passwordE);
                            PreparedStatement pstmt = con
                                    .prepareStatement("UPDATE users SET email = ? WHERE email = ?")) {
                        pstmt.setString(1, newEmail);
                        pstmt.setString(2, previousEmail);
                        int rowsUpdated = pstmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Status 200");
                            alert.setHeaderText(null);
                            alert.setContentText("Your email has been updated.");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Email not found");
                            alert.setHeaderText(null);
                            alert.setContentText("The email to be updated was not found.");
                            alert.showAndWait();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error");
                        alert.setHeaderText(null);
                        alert.setContentText("An error occurred while updating the email.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid email.");
                alert.showAndWait();
            }
        }
    }

    public void deleteAccount(String email) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(
                "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        try (Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users WHERE email = ?")) {
            preparedStatement.setString(1, email);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Status 200");
                alert.setHeaderText(null);
                alert.setContentText("Your Account deleted successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("DataBase Erro");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete account.Please try again later. ");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authencation(String email, String password) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        boolean emailFinded = false;
        try (InputStream input = new FileInputStream(
                "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
            properties.load(input);
        }
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String passwordE = properties.getProperty("db.password");
        try (
                Connection con = DriverManager.getConnection(url, username, passwordE);
                Statement stmt = con.createStatement()) {

            String strSelect = "select name, email, password from users";
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String name = rset.getString("name");
                String emaildb = rset.getString("email");
                String passworddb = rset.getString("password");
                System.out.println(emaildb + ", " + passworddb + ", " + name);
                if (emaildb.equals(email) && passworddb.equals(password)) {
                    emailFinded = true;
                    userName = name;
                    break;
                } else {
                    emailFinded = false;
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return emailFinded;
    }

    private void showSearchResults(TabPane tabPane, String query) throws FileNotFoundException, IOException {
        String body;
        String search = URLEncoder.encode(query, StandardCharsets.UTF_8);
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(
                "E:\\Programming\\Java\\Search_Engine_Java\\src\\Frontend\\config.properties")) {
            properties.load(input);
        }
        String key = properties.getProperty("key");
        try {
            String apiUrl1 = "https://www.googleapis.com/customsearch/v1?key=" + key
                    + "&cx=017576662512468239146:omuauf_lfve&q="
                    + search;
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl1))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            body = response.body();
            JSONObject jsonResponse = new JSONObject(body);
            if (jsonResponse.has("items")) {
                JSONArray itemsArray = jsonResponse.getJSONArray("items");

                int result = itemsArray.length();
                if (itemsArray.length() > 0) {
                    String[] titles = new String[itemsArray.length()];
                    String[] links = new String[itemsArray.length()];
                    String[] snippets = new String[itemsArray.length()];
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject item = itemsArray.getJSONObject(i);
                        String title = item.getString("title");
                        String link = item.getString("link");
                        String snippet = item.getString("snippet");
                        titles[i] = (title);
                        links[i] = (link);
                        snippets[i] = (snippet);
                    }
                    Tab searchTab = new Tab(query);
                    VBox searchResultsContent = new VBox(20);
                    searchResultsContent.setBackground(
                            new Background(new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
                    searchResultsContent.getChildren().add(createLabelWithWhiteText("Total results = " + result));

                    for (int i = 0; i < titles.length; i++) {
                        searchResultsContent.getChildren().add(createLabelWithWhiteText(titles[i]));
                        searchResultsContent.getChildren().add(createHyperlink(links[i], links[i]));
                        searchResultsContent.getChildren().add(createLabelWithWhiteText(snippets[i]));
                        searchResultsContent.getChildren().add(new Label("-------------------------------"));

                    }
                    searchResultsContent.setAlignment(Pos.TOP_LEFT);
                    // !Scrollbar
                    VBox containerVBox = new VBox(searchResultsContent);
                    ScrollPane scrollPane = new ScrollPane(containerVBox);
                    scrollPane.setFitToWidth(true);
                    scrollPane.setFitToHeight(true);
                    searchTab.setContent(scrollPane);
                    loginTabPane.getTabs().add(searchTab);
                    loginTabPane.getSelectionModel().select(searchTab);
                }
            } else {
                Tab tab404 = new Tab("404");
                VBox notFoundContent = new VBox(20);
                notFoundContent.setBackground(
                        new Background(new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
                notFoundContent.setAlignment(Pos.CENTER);
                notFoundContent.getChildren().add(createLabelWithWhiteText("404"));
                notFoundContent.getChildren().add(createLabelWithWhiteText("Data not found"));
                tab404.setContent(notFoundContent);
                tabPane.getTabs().add(tab404);
                tabPane.getSelectionModel().select(tab404);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Label createLabelWithWhiteText(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #ffb600;-fx-font-family: 'Montserrat', sans-serif;");
        VBox.setMargin(label, new Insets(0, 0, 0, 30));
        return label;
    }

    private Hyperlink createHyperlink(String url, String text) {
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-text-fill: #fff;");

        hyperlink.setOnAction(e -> openWebpage(url));
        VBox.setMargin(hyperlink, new Insets(0, 0, 0, 40));
        return hyperlink;
    }

    private void openWebpage(String url) {
        HostServices hostServices = getHostServices();
        hostServices.showDocument(url);
    }

    public static void main(String[] args) throws Exception {
        launch(args);

    }

}
