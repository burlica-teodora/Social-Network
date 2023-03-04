package com.example.social_network1.domain;


import java.util.Objects;

public class Message extends Entity<Long>{
    private String usernameFROM;
    private String usernameTO;
    private String message;

    public Message(String usernameFROM, String usernameTO, String message) {
        this.usernameFROM = usernameFROM;
        this.usernameTO = usernameTO;
        this.message = message;
    }

    public String getUsernameFROM() {
        return usernameFROM;
    }

    public void setUsernameFROM(String usernameFROM) {
        this.usernameFROM = usernameFROM;
    }

    public String getUsernameTO() {
        return usernameTO;
    }

    public void setUsernameTO(String usernameTO) {
        this.usernameTO = usernameTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return  usernameFROM +
                ":   " + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(usernameFROM, message1.usernameFROM) && Objects.equals(usernameTO, message1.usernameTO) && Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameFROM, usernameTO, message);
    }
}
