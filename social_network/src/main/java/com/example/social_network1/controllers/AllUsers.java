package com.example.social_network1.controllers;

import com.example.social_network1.domain.User;
import com.example.social_network1.repository.db.FriendRequestDBRepo;
import com.example.social_network1.repository.db.FriendshipDBRepo;
import com.example.social_network1.repository.db.MessageDBRepo;
import com.example.social_network1.repository.db.UserDBRepo;
import com.example.social_network1.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class AllUsers {
    Service service = Service.getInstance(new FriendshipDBRepo(), new UserDBRepo(), new FriendRequestDBRepo(), new MessageDBRepo());
    private final ObservableList<User> usersModel = FXCollections.observableArrayList();
    @FXML
    private ListView <User> list_of_users;

    @FXML
    public void initialize(){
        User user = service.getUser();
        List<User> users1 = (List<User>) service.getUsers();
        users1.remove(user);
        usersModel.setAll(users1);
        list_of_users.setItems(usersModel);
    }
}
