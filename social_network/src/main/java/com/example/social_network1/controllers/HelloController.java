package com.example.social_network1.controllers;

import com.example.social_network1.HelloApplication;
import com.example.social_network1.domain.FriendRequest;
import com.example.social_network1.domain.Friendship;
import com.example.social_network1.domain.Message;
import com.example.social_network1.domain.User;
import com.example.social_network1.repository.db.FriendRequestDBRepo;
import com.example.social_network1.repository.db.FriendshipDBRepo;
import com.example.social_network1.repository.db.MessageDBRepo;
import com.example.social_network1.repository.db.UserDBRepo;
import com.example.social_network1.service.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HelloController {
    Service service = Service.getInstance(new FriendshipDBRepo(), new UserDBRepo(), new FriendRequestDBRepo(), new MessageDBRepo());
    private final ObservableList<User> userModel = FXCollections.observableArrayList();
    private final ObservableList<FriendRequest> requestModel = FXCollections.observableArrayList();

    private final ObservableList<Message> messageModel = FXCollections.observableArrayList();
    @FXML
    private TableColumn<FriendRequest,String> usernameTF;

    @FXML
    private Pane msgPane;

    @FXML
    private TextField msgTextField;

    @FXML
    private ListView<Message> msgListView;

    @FXML
    private TableColumn<FriendRequest,LocalDateTime> requestTime;
    @FXML
    private TableColumn<FriendRequest,String> status;
    @FXML
    private TableView<FriendRequest> tableRequest;
    @FXML
    private TableColumn<FriendRequest,Long> idRequest;
    @FXML
    private TextField userNameFriendTF;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Long> idColumn;
    @FXML
    private TableColumn<User, String> fnameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Integer> ageColumn;


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        idRequest.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameTF.setCellValueFactory(new PropertyValueFactory<>("username1"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestTime.setCellValueFactory(new PropertyValueFactory<>("requestSentAt"));
        tableRequest.setItems(requestModel);
        tableView.setItems(userModel);
        initModelUser();
        initModelRequest();

    }

    private void initModelUser() {
        Long id = service.getUser().getId();
        List<User> users1 = new ArrayList<>();
        for (Friendship fr : service.getFriendships()) {
            if (Objects.equals(fr.getIdUser1(), id)) {
                users1.add(service.findOneUser(fr.getIdUser2()));
            } else if (Objects.equals(fr.getIdUser2(), id)) {
                users1.add(service.findOneUser(fr.getIdUser1()));
            }

        }
        userModel.setAll(users1);
    }

    private void initModelRequest() {
        String username = service.getUser().getEmail();
        List<FriendRequest> requests = new ArrayList<>();
        for (FriendRequest friendRequest : service.getAllRequests()) {
            if (friendRequest.getUsername2().equals(username) && !friendRequest.getStatus().equals("Accepted")) {
                requests.add(friendRequest);
            }
        }
        requestModel.setAll(requests);
    }

    @FXML
    public void onRemoveFriend(MouseEvent mouseEvent) {
        Long idU = tableView.getSelectionModel().getSelectedItem().getId();
        String email = tableView.getSelectionModel().getSelectedItem().getEmail();
        for (FriendRequest request : service.getAllRequests()) {
            if ((request.getUsername2().equals(email) && service.getUser().getEmail().equals(request.getUsername1()) && request.getStatus().equals("Accepted"))
                    || (request.getUsername1().equals(email) && service.getUser().getEmail().equals(request.getUsername2())) && request.getStatus().equals("Accepted")) {
                service.deleteRequest(request.getId());
            }
        }
        for (Friendship f : service.getFriendships()) {
            if (Objects.equals(f.getIdUser1(), idU) || Objects.equals(f.getIdUser2(), idU)) {
                service.deleteFriendship(f.getId());
            }
        }
        initModelUser();
        initModelRequest();
    }
    @FXML
    public void onCancelRequest(ActionEvent mouseEvent){
        String email = userNameFriendTF.getText();
        if(email.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("INVALID DATA");
            alert.setContentText("Field cannot be empty!");
            alert.show();
        }
        int ok1 = 0;
        int ok = 0;
        if (!email.isEmpty()) {
            for (User u : service.getUsers()) {
                if (Objects.equals(u.getEmail(), email)) {
                    ok = 1;
                    break;
                }
            }
            for (FriendRequest request : service.getAllRequests()) {
                if ((request.getUsername2().equals(email) && request.getUsername1().equals(service.getUser().getEmail()) && request.getStatus().equals("Pending"))) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok == 1 && ok1 == 1){
                for (FriendRequest request : service.getAllRequests())
                    if (request.getUsername2().equals(email) && request.getUsername1().equals(service.getUser().getEmail())){
                        service.deleteRequest(request.getId());
                        break;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Action completed successfully");
                alert.setContentText("You have cancelled your request!");
                alert.show();
            }
            else if (ok == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("INVALID DATA");
                alert.setContentText("Invalid User!");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("INVALID DATA");
                alert.setContentText("Can't cancel a request that doesn't exist!");
                alert.show();
            }

            userNameFriendTF.setText("");
            initModelRequest();
        }
    }

    @FXML
    public void onSendRequest(MouseEvent mouseEvent) {
        String email = userNameFriendTF.getText();
        if(email.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("INVALID DATA");
            alert.setContentText("Field cannot be empty!");
            alert.show();
        }
        int ok = 0;
        int ok1 = 1;
        if (!email.isEmpty()) {
            for (User u : service.getUsers()) {
                if (Objects.equals(u.getEmail(), email)) {
                    ok = 1;
                    break;
                }
            }
            for (FriendRequest request: service.getAllRequests())
                if ((request.getUsername1().equals(email) && request.getUsername2().equals(service.getUser().getEmail()) && !request.getStatus().equals("Rejected"))
                        ||(request.getUsername2().equals(email) && request.getUsername1().equals(service.getUser().getEmail()) && !request.getStatus().equals("Rejected"))) {
                    ok1 = 0;
                    break;
                }

            if (ok == 1 && ok1 == 1){
                service.addRequest(service.getUser().getEmail(), email);
            }
           else if (ok == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("INVALID DATA");
                alert.setContentText("Invalid User!");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("INVALID DATA");
                alert.setContentText("Request already exists.You cannot send it again!");
                alert.show();
            }

            userNameFriendTF.setText("");
        }
    }

    @FXML
    public void onLogOutClick(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 403);
        stage.setTitle("My Social Network");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onAcceptButton(ActionEvent actionEvent) {
        Long  friendRequestId = tableRequest.getSelectionModel().getSelectedItem().getId();
        FriendRequest frUpdate = new FriendRequest(service.getUser().getEmail(),tableRequest.getSelectionModel().getSelectedItem().getUsername1(),LocalDateTime.now(),"Accepted");
        frUpdate.setId(friendRequestId);
        service.updateRequest(frUpdate);
        Long id = 0L;
        for (User u : service.getUsers())
            if (u.getEmail().equals(tableRequest.getSelectionModel().getSelectedItem().getUsername1()))
            {
                id = u.getId();
            }
        service.addFriendship(new Friendship(service.getUser().getId(),id,LocalDateTime.now()));
        initModelRequest();
        initModelUser();
    }
    @FXML
    public void onRejectButton(ActionEvent actionEvent) {
        Long  friendRequestId = tableRequest.getSelectionModel().getSelectedItem().getId();
        FriendRequest frUpdate = new FriendRequest(service.getUser().getEmail(),tableRequest.getSelectionModel().getSelectedItem().getUsername1(),LocalDateTime.now(),"Rejected");
        frUpdate.setId(friendRequestId);
        service.updateRequest(frUpdate);
        initModelRequest();
    }

    @FXML
    public void onFindUsers(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("all_users.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 478, 354);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onSendMessage(MouseEvent mouseEvent) {
        String emailTO = tableView.getSelectionModel().getSelectedItem().getEmail();
        String emailFrom = service.getUser().getEmail();
        if (emailTO != null) {
            msgPane.setVisible(true);
            List<Message> messages = new ArrayList<>();
            for (Message msg : service.getMessages()) {
                if ((msg.getUsernameFROM().equals(emailFrom) && msg.getUsernameTO().equals(emailTO)) ||
                        (msg.getUsernameTO().equals(emailFrom) && msg.getUsernameFROM().equals(emailTO)))
                    messages.add(msg);
            }
            messageModel.setAll(messages);
            msgListView.setItems(messageModel);
        }
    }

    @FXML
    public void onSetMessagePaneInvisible(MouseEvent mouseEvent) {
        msgPane.setVisible(false);
    }

    @FXML
    public void onSendMessageButton(ActionEvent actionEvent) {
        String emailTO = tableView.getSelectionModel().getSelectedItem().getEmail();
        String emailFrom = service.getUser().getEmail();
        String msgTxt = msgTextField.getText();
        service.addMessage(emailFrom, emailTO, msgTxt);
        List<Message> messages = new ArrayList<>();
        for (Message msg : service.getMessages()) {
            if ((msg.getUsernameFROM().equals(emailFrom) && msg.getUsernameTO().equals(emailTO)) ||
                    (msg.getUsernameTO().equals(emailFrom) && msg.getUsernameFROM().equals(emailTO)))
                messages.add(msg);
        }
        messageModel.setAll(messages);
        msgTextField.setText("");
    }

    @FXML
    public void onCloseChat(MouseEvent mouseEvent) {
        msgPane.setVisible(false);
    }
}