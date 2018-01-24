package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainWindowView.fxml"));
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMinWidth(615);
        primaryStage.setMinHeight(430);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
