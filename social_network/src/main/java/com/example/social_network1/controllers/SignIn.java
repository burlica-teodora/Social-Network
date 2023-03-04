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

public class SignIn {
    Service service = Service.getInstance(new FriendshipDBRepo(), new UserDBRepo(), new FriendRequestDBRepo(), new MessageDBRepo());
    @FXML
    private PasswordField psswdConfirmTF;
    @FXML
    private TextField userTF;
    @FXML
    private PasswordField psswdTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField ageTF;

    @FXML
    public void onAddNewUser(MouseEvent mouseEvent) throws IOException {
        String fname = nameTF.getText();
        String passwd = psswdTF.getText();
        String passwdConf = psswdConfirmTF.getText();
        String email = userTF.getText();
        int age = 0;
        if (!ageTF.getText().isEmpty()) {
            age = Integer.parseInt(ageTF.getText());
        }

        if (fname.isEmpty() || passwd.isEmpty() || email.isEmpty() || age == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("INVALID DATA");
            alert.setContentText("You must complete all fields!");
            alert.show();
            nameTF.setText("");
            psswdTF.setText("");
            psswdConfirmTF.setText("");
            userTF.setText("");
            ageTF.setText("");
        } else if (!passwd.equals(passwdConf)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("INVALID DATA");
            alert.setContentText("Your password doesn't match!");
            alert.show();
            nameTF.setText("");
            psswdTF.setText("");
            psswdConfirmTF.setText("");
            userTF.setText("");
            ageTF.setText("");
        } else {
            int ok = 1;
            for (User user: service.getUsers())
                if (user.getEmail().equals(email)){
                    ok = 0;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("");
                    alert.setHeaderText("INVALID DATA");
                    alert.setContentText("You already have an account, please log in");
                    alert.show();
                    nameTF.setText("");
                    psswdTF.setText("");
                    psswdConfirmTF.setText("");
                    userTF.setText("");
                    ageTF.setText("");
                }
            if (ok == 1){
                User u = new User(fname, passwd, email, age);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Action completed successfully");
                alert.setContentText("You have created your new account!");
                alert.show();
                service.setUser(u);
                service.addUser(u);
                nameTF.setText("");
                psswdTF.setText("");
                psswdConfirmTF.setText("");
                userTF.setText("");
                ageTF.setText("");
            }

        }


    }


}
