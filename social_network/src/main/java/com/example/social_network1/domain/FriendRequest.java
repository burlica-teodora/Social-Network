package com.example.social_network1.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class FriendRequest extends Entity<Long>{
    private String username1;
    private String username2;
    private LocalDateTime requestSentAt;
    private String status;

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public LocalDateTime getRequestSentAt() {
        return requestSentAt;
    }

    public void setRequestSentAt(LocalDateTime requestSentAt) {
        this.requestSentAt = requestSentAt;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(username1, that.username1) && Objects.equals(username2, that.username2) && Objects.equals(requestSentAt, that.requestSentAt) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username1, username2, requestSentAt, status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FriendRequest(String username1, String username2, LocalDateTime requestSentAt, String status) {
        this.username1 = username1;
        this.username2 = username2;
        this.requestSentAt = requestSentAt;
        this.status = status;
    }
}
