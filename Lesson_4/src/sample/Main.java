package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Test_Chat");
        Scene scene = new Scene(root, 460D, 320D);
        primaryStage.setScene(scene);
        scene.setCursor(Cursor.HAND);
        scene.getStylesheets().add((getClass().getResource("/css/Style.css").toExternalForm()));
        primaryStage.show();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setOpacity(0.90);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/16.png")));
        primaryStage.show();

    }
}
