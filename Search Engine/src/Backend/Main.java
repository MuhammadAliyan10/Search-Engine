package Backend;

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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Backend/Search.fxml"));
        primaryStage.setTitle("Google");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Backend/Images/logo.png")));

        Image headingImage = new Image(getClass().getResourceAsStream("/Backend/Images/Google.png"));
        ImageView imageView = new ImageView(headingImage);
        imageView.setFitWidth(500);
        imageView.setFitHeight(170);

        VBox root1 = new VBox(20);
        root1.setBackground(new Background(new BackgroundFill(Color.web("#202124"), CornerRadii.EMPTY, Insets.EMPTY)));
        root1.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(imageView, new Insets(100, 0, 0, 0));
        primaryStage.setScene(new Scene(root1, 1270, 685));

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
