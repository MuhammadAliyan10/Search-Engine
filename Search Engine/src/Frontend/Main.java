package Frontend;

//!Import
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import javafx.scene.control.Hyperlink;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Frontend/Search.fxml"));
        primaryStage.setTitle("Google");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Frontend/Images/logo.png")));
        // !Top google image
        Image headingImage = new Image(getClass().getResourceAsStream("/Frontend/Images/Google.png"));
        ImageView imageView = new ImageView(headingImage);
        imageView.setFitWidth(500);
        imageView.setFitHeight(170);
        // ! Input Text Field
        TextField searchInputTextField = new TextField();
        searchInputTextField.setPromptText("Search");
        searchInputTextField.setMaxWidth(550); // Set the max width to the same value as pref width
        searchInputTextField.setMinHeight(40);
        searchInputTextField.setPrefWidth(550);
        searchInputTextField.setMinHeight(40); // You can set the height as needed
        searchInputTextField.setFont(new javafx.scene.text.Font(15));
        searchInputTextField.getStyleClass().add("search_input");
        // ! Sudmit Button
        Button ButtonSearch = new Button();
        ButtonSearch.setText("Google Search");
        ButtonSearch.getStyleClass().add("search_button");
        Button feelingLucky = new Button();
        feelingLucky.setText("Feeling Lucky");
        feelingLucky.getStyleClass().add("search_button");
        // ! Hbox for button
        HBox button = new HBox(10);
        button.getChildren().addAll(ButtonSearch, feelingLucky);
        HBox.setMargin(ButtonSearch, new Insets(0, 10, 153, 500));

        TabPane tabPane = new TabPane();
        ButtonSearch.setOnAction(e -> showSearchResults(tabPane, searchInputTextField.getText()));
        // ! First vmBox
        VBox root1 = new VBox(20);
        root1.setBackground(new Background(new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
        root1.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(imageView, new Insets(100, 0, 0, 0));
        VBox.setMargin(searchInputTextField, new Insets(20, 0, 0, 0));
        VBox.setMargin(ButtonSearch, new Insets(0, 0, 120, 0));
        // !Styling Import
        String cssFile = getClass().getResource("/Frontend/Style.css").toExternalForm();
        root1.getStylesheets().add(cssFile);
        // !To show the data
        root1.getChildren().addAll(tabPane, imageView, searchInputTextField, button);
        primaryStage.setScene(new Scene(root1, 1270, 685));
        primaryStage.show();

    }

    private void showSearchResults(TabPane tabPane, String query) {
        String body;
        try {
            String apiUrl = "https://www.googleapis.com/customsearch/v1?key=AIzaSyAmRBLSpWafSw-CW8G2buGGrvSvAGnKwNo&cx=017576662512468239146:omuauf_lfve&q="
                    + query;
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            body = response.body();
            JSONObject jsonResponse = new JSONObject(body);
            if (jsonResponse.has("items")) {
                JSONArray itemsArray = jsonResponse.getJSONArray("items");
                if (itemsArray.length() > 0) {
                    List<String> titles = new ArrayList<>();
                    List<String> links = new ArrayList<>();

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject item = itemsArray.getJSONObject(i);
                        String title = item.getString("title");
                        String link = item.getString("link");
                        titles.add(title);
                        links.add(link);
                    }

                    Tab searchTab = new Tab(query);
                    VBox searchResultsContent = new VBox(20);
                    searchResultsContent.setBackground(
                            new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));
                    if (titles.isEmpty()) {
                        searchResultsContent.getChildren().add(new Label("No data get."));

                    } else {
                        for (String title : titles) {
                            searchResultsContent.getChildren().add(new Label(title));
                        }
                        for (String link : links) {
                            searchResultsContent.getChildren().add(createHyperlink(link, link));
                        }

                    }

                    searchResultsContent.setAlignment(Pos.TOP_LEFT);
                    searchTab.setContent(searchResultsContent);
                    tabPane.getTabs().add(searchTab);
                    tabPane.getSelectionModel().select(searchTab);
                    body = response.body();
                }
            } else {
                System.out.println("Not That data");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Hyperlink createHyperlink(String url, String text) {
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setOnAction(e -> openWebpage(url));
        return hyperlink;
    }

    private void openWebpage(String url) {
        HostServices hostServices = getHostServices();
        hostServices.showDocument(url);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
