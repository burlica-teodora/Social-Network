package com.example.social_network1;

import com.example.social_network1.repository.db.FriendshipDBRepo;
import com.example.social_network1.repository.db.UserDBRepo;
import com.example.social_network1.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 403);
        stage.setTitle("My Social Network!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}