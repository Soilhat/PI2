package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/DataGrue.fxml"));
        primaryStage.setTitle("Hello World");
        FileChooser fileChooser = new FileChooser();
        /*fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(primaryStage);*/
        primaryStage.setScene(new Scene(root, 700, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
