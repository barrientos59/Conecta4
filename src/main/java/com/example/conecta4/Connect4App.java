package com.example.conecta4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Connect4App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/conecta4/connect4.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Connect 4 Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
