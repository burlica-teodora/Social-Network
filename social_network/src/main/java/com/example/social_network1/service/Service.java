package com.example.social_network1.service;


import com.example.social_network1.domain.FriendRequest;
import com.example.social_network1.domain.Friendship;
import com.example.social_network1.domain.Message;
import com.example.social_network1.domain.User;
import com.example.social_network1.repository.db.FriendRequestDBRepo;
import com.example.social_network1.repository.db.FriendshipDBRepo;
import com.example.social_network1.repository.db.MessageDBRepo;
import com.example.social_network1.repository.db.UserDBRepo;


import java.time.LocalDateTime;

public class Service{
    private final FriendshipDBRepo repoFriendshipDB;
    private final UserDBRepo repoUserDB;
    private final FriendRequestDBRepo repoFriendRequestDB;

    private final MessageDBRepo messageDBRepo;

    private User user = null;
    private static Service service = null;


    private Service(FriendshipDBRepo repoFriendship, UserDBRepo repoUser, FriendRequestDBRepo repoFriendRequestDB, MessageDBRepo messageDBRepo) {
        this.repoFriendshipDB = repoFriendship;
        this.repoUserDB = repoUser;
        this.repoFriendRequestDB = repoFriendRequestDB;
        this.messageDBRepo = messageDBRepo;
    }

    public static Service getInstance(FriendshipDBRepo repoFriendship, UserDBRepo repoUser, FriendRequestDBRepo repoFriendRequestDB, MessageDBRepo messageDBRepo) {
        if (service == null) {
            service = new Service(repoFriendship, repoUser, repoFriendRequestDB, messageDBRepo);
        }
        return service;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public Long getNewIdUser() {
        Long id = 0L;
        for (User u : repoUserDB.findAll())
            id = u.getId();
        return id + 1;
    }

    public void addUser(User user) {
        user.setId(getNewIdUser());
        repoUserDB.save(user);
    }

    public User findOneUser(Long id) {
        return repoUserDB.findOne(id);
    }

    public Friendship findOneFriendship(Long id) {
        return repoFriendshipDB.findOne(id);
    }

    public Iterable<User> getUsers() {
        return repoUserDB.findAll();
    }

    public Iterable<Friendship> getFriendships() {
        return repoFriendshipDB.findAll();
    }

    public Iterable<FriendRequest> getAllRequests() {
        return repoFriendRequestDB.findAll();
    }

    public void deleteUser(Long id) {
        try {
            User t = repoUserDB.findOne(id);
            if (t == null)
                throw new IllegalArgumentException("The user does not exist!");
            for (Friendship fr : repoFriendshipDB.findAll())
                if (fr.getIdUser2().equals(id) || fr.getIdUser1().equals(id))
                    repoFriendshipDB.delete(fr.getId());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user!");
        }

    }

    public void updateUser(User new_user) {
        repoUserDB.update(new_user);
    }

    public Long getNewIdFriendship() {
        Long id = 0L;
        for (Friendship u : repoFriendshipDB.findAll())
            id = u.getId();
        return id + 1;
    }

    public void addFriendship(Friendship friendship) {
        for (Friendship f : getFriendships())
            if (f.getIdUser1().equals(friendship.getIdUser1()) && f.getIdUser2().equals(friendship.getIdUser2()))
                throw new IllegalArgumentException("The friendship already exist!");
            else if (repoUserDB.findOne(friendship.getIdUser1()) == null || repoUserDB.findOne(friendship.getIdUser2()) == null)
                throw new IllegalArgumentException("The user does not exist");
        if (friendship.getIdUser2().equals(friendship.getIdUser1()))
            throw new IllegalArgumentException("The ids can not be the same!");
        friendship.setId(getNewIdFriendship());
        repoFriendshipDB.save(friendship);
    }

    public void deleteFriendship(Long id) {
        if (repoFriendshipDB.findOne(id) == null)
            throw new IllegalArgumentException("The id is invalid!");
        repoFriendshipDB.delete(id);
    }

    public void updateFriendship(Friendship friendship) {
        repoFriendshipDB.update(friendship);
    }

    public Long getNewIdFrRequest() {
        Long id = 0L;
        for (FriendRequest friendRequest : repoFriendRequestDB.findAll())
            id = friendRequest.getId();
        return id + 1;
    }

    public void addRequest(String username1, String username2) {
        String status = "Pending";
        FriendRequest friendRequest = new FriendRequest(username1, username2, LocalDateTime.now(), status);
        friendRequest.setId(getNewIdFrRequest());
        repoFriendRequestDB.save(friendRequest);
    }

    public void deleteRequest(Long id) {
        if (repoFriendRequestDB. findOne(id) == null)
            throw new IllegalArgumentException("The id is invalid!");
        repoFriendRequestDB.delete(id);
    }

    public Long getNewIdMessage() {
        Long id = 0L;
        for (Message msg : messageDBRepo.findAll())
        {
            if (id <= msg.getId())
            {
                id = msg.getId();
            }
        }
        return id + 1;
    }
    public void addMessage(String usernameFrom,String usernameTo, String messageTxt){
        Message message = new Message(usernameFrom,usernameTo,messageTxt);
        message.setId(getNewIdMessage());
        messageDBRepo.save(message);
    }
    public void deleteMessage(Long aLong){
        if (messageDBRepo.findOne(aLong) == null){
            throw new IllegalArgumentException("This id is invalid!");
        }
        messageDBRepo.delete(aLong);
    }

    public void updateRequest(FriendRequest friendRequest) {
        repoFriendRequestDB.update(friendRequest);
    }
    public Iterable<Message> getMessages() {
        return messageDBRepo.findAll();
    }

}