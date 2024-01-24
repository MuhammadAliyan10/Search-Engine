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
    private TabPane loginTabPane;
    private String userName = "user";

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ! Main tab for initial UI setup
        loginTabPane = new TabPane();
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

        Label header = new Label();
        header.setText("Login");
        header.setStyle("-fx-font-family: 'Montserrat', sans-serif; -fx-font-size: 22px;");
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
        TextField passwordTextField = new TextField();
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

        // ! OnClick handler

        // *Sign Up
        SignUp.setOnAction(event -> {
            String name = fullName.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null);
                alert.setContentText("Please enter you full name.");
                alert.showAndWait();

            } else if (email.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null);
                alert.setContentText("Please enter you email address.");
                alert.showAndWait();

            } else if (password.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null);
                alert.setContentText("Please enter you password.");
                alert.showAndWait();

            } else if (password.length() < 8) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Security Error");
                alert.setHeaderText(null);
                alert.setContentText("Password must be minimum of 8 character.");
                alert.showAndWait();

            } else {
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
                alert.setContentText("Please enter your email address.");
                alert.showAndWait();

            } else if (password.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Empty Field");
                alert.setHeaderText(null); // No header text
                alert.setContentText("Please enter you password.");
                alert.showAndWait();
            } else {

                try {
                    String finalValue = authencation(email, password);
                    if (finalValue == "Finded") {
                        loginTabPane.getTabs().remove(LoginmainTab);
                        Tab mainTab = new Tab("Google");
                        mainTab.setClosable(false);
                        VBox mainTabContent = new VBox(20);
                        mainTabContent.setBackground(
                                new Background(
                                        new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
                        mainTabContent.setAlignment(Pos.TOP_CENTER);

                        Label userNameLabel = new Label();
                        userNameLabel.setText("Wellcome " + userName);
                        userNameLabel.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
                        userNameLabel.getStyleClass().add("username");
                        mainTabContent.getChildren().add(userNameLabel);
                        VBox.setMargin(userNameLabel, new Insets(18, 0, 0, 1000));

                        // ! Top Google image
                        Image headingImage = new Image(getClass().getResourceAsStream("/Frontend/Images/Google.png"));
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
                        alert.setContentText("No user found. If you dont have account please sign up.");
                        alert.showAndWait();
                    }
                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });

        // ! Submit Button
        Button logIn = new Button();
        logIn.setText("Login");
        logIn.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
        logIn.getStyleClass().add("search_button");
        logIn.setDisable(true);

        Button signUp = new Button();
        signUp.setStyle("-fx-font-family: 'Montserrat', sans-serif;");
        signUp.setText("Signup");
        signUp.getStyleClass().add("search_button");

        // !Hide and display
        logIn.setOnAction(event -> {
            logIn.setDisable(true);
            signUp.setDisable(false);
            header.setText("Log In");
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
            signUp.setDisable(true);
            logIn.setDisable(false);
            header.setText("Sign Up");
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
        HBox.setMargin(logIn, new Insets(0, 0, 150, 380));
        // HBox.setMargin(logIn, new Insets(0, 10, 153, 380));
        LoginmainTabContent.getChildren().addAll(button);

        LoginmainTab.setContent(LoginmainTabContent);
        loginTabPane.getTabs().add(LoginmainTab);
        VBox.setMargin(emailTextField, new Insets(10, 0, 0, 0));
        VBox.setMargin(passwordTextField, new Insets(10, 0, 0, 0));
        VBox.setMargin(header, new Insets(0, 0, 50, 0));
        // VBox.setMargin(log, new Insets(0, 290, 0, 0));
        String cssFile = getClass().getResource("/Frontend/Style.css").toExternalForm();
        LoginmainTabContent.getStylesheets().add(cssFile);
        primaryStage.setTitle("Login");
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

    public String authencation(String email, String password) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        String finalValue = null;
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
                    finalValue = "Finded";
                    userName = name;
                } else {
                    finalValue = "NotFinded";
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return finalValue;

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
