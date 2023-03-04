package com.example.social_network1.controllers;

import com.example.social_network1.HelloApplication;
import com.example.social_network1.domain.User;
import com.example.social_network1.repository.db.FriendRequestDBRepo;
import com.example.social_network1.repository.db.FriendshipDBRepo;
import com.example.social_network1.repository.db.MessageDBRepo;
import com.example.social_network1.repository.db.UserDBRepo;
import com.example.social_network1.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApp {

    Service service = Service.getInstance(new FriendshipDBRepo(),new UserDBRepo(),new FriendRequestDBRepo(), new MessageDBRepo());
    private User user = null;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    public void onMouseClickedLogin(MouseEvent mouseEvent) throws IOException {
        String email = usernameText.getText();
        String password = passwordText.getText();
        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("INVALID DATA");
            alert.setContentText("The fields cannot be empty");
            alert.show();
        } else {
            for (User u : service.getUsers()) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password))
                    user = u;
            }

            if (user != null) {
                service.setUser(user);
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 700, 627);
                stage.setTitle("HI " + user.getFirstName() + "!");
                stage.setScene(scene);
                stage.show();
                usernameText.setText("");
                passwordText.setText("");
                user = null;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("INVALID DATA");
                alert.setContentText("Email or password wrong, try again");
                alert.show();
                usernameText.setText("");
                passwordText.setText("");
            }

        }

    }

    @FXML
    public void onMouseClickedSignin(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("My Social Network!");
        stage.setScene(scene);
        stage.show();
    }
}
