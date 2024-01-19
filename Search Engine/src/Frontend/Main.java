package Frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Main extends Application {
    private Tab searchTab;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Frontend/Search.fxml"));
        primaryStage.setTitle("Google");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Frontend/Images/logo.png")));

        Image headingImage = new Image(getClass().getResourceAsStream("/Frontend/Images/Google.png"));
        ImageView imageView = new ImageView(headingImage);
        imageView.setFitWidth(500);
        imageView.setFitHeight(170);

        TextField searchInputTextField = new TextField();
        searchInputTextField.setPromptText("Search");
        searchInputTextField.setMaxWidth(550); // Set the max width to the same value as pref width
        searchInputTextField.setMinHeight(40);
        searchInputTextField.setPrefWidth(550);
        searchInputTextField.setMinHeight(40); // You can set the height as needed
        searchInputTextField.setFont(new javafx.scene.text.Font(15));
        searchInputTextField.getStyleClass().add("search_input");

        Button ButtonSearch = new Button();
        ButtonSearch.setText("Google Search");
        ButtonSearch.getStyleClass().add("search_button");
        Button feelingLucky = new Button();
        feelingLucky.setText("Feeling Lucky");
        feelingLucky.getStyleClass().add("search_button");

        HBox button = new HBox(10);
        button.getChildren().addAll(ButtonSearch, feelingLucky);
        HBox.setMargin(ButtonSearch, new Insets(0, 10, 153, 500));

        Label bottomLabel = new Label("Pakistan");
        bottomLabel.getStyleClass().add("location");
        bottomLabel.setPrefWidth(Double.MAX_VALUE);

        Label Privacy = new Label("Privacy");
        Privacy.getStyleClass().add("bottom_style");
        Label term = new Label("Term");
        term.getStyleClass().add("bottom_style");
        Label Setting = new Label("Setting");
        Setting.getStyleClass().add("bottom_style");

        Label about = new Label("About");
        about.getStyleClass().add("bottom_style");
        Label advertising = new Label("Advertising");
        advertising.getStyleClass().add("bottom_style");
        Label business = new Label("Business");
        business.getStyleClass().add("bottom_style");
        Label HowsearchWorks = new Label("How Search Works");
        HowsearchWorks.getStyleClass().add("bottom_style");

        HBox bottom = new HBox(10);
        bottom.getChildren().addAll(about, advertising, business, HowsearchWorks, Privacy, term, Setting);
        HBox.setMargin(Privacy, new Insets(0, 0, 0, 670));

        VBox root1 = new VBox(20);
        root1.setBackground(new Background(new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
        root1.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(imageView, new Insets(100, 0, 0, 0));
        VBox.setMargin(searchInputTextField, new Insets(20, 0, 0, 0));
        VBox.setMargin(ButtonSearch, new Insets(0, 0, 120, 0));

        VBox root2 = new VBox(10);
        root2.setBackground(new Background(new BackgroundFill(Color.web("#171717"), CornerRadii.EMPTY, Insets.EMPTY)));
        root2.setAlignment(Pos.BOTTOM_LEFT);

        root2.getChildren().addAll(bottomLabel, bottom);

        searchTab = new Tab("Search Results");
        searchTab.setContent(new VBox());
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(searchTab);

        String cssFile = getClass().getResource("/Frontend/Style.css").toExternalForm();
        root1.getStylesheets().add(cssFile);

        ButtonSearch.setOnAction(e -> {
            String query = searchInputTextField.getText();
            updateSearchResults(query);
        });

        root1.getChildren().addAll(imageView, searchInputTextField, button, root2, tabPane);
        primaryStage.setScene(new Scene(root1, 1270, 685));
        primaryStage.show();

    }

    private void updateSearchResults(String query) {

        if (searchTab != null) {
            ((VBox) searchTab.getContent()).getChildren().clear();
            VBox searchResultsContent = new VBox();
            searchResultsContent.getChildren().add(new Label("Search results for: " + query));

            searchTab.setContent(searchResultsContent);
        } else {
            System.out.println(query);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
