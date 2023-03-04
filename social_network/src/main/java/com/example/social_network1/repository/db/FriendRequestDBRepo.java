package com.example.social_network1.repository.db;

import com.example.social_network1.domain.FriendRequest;
import com.example.social_network1.domain.Friendship;
import com.example.social_network1.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDBRepo implements Repository<FriendRequest,Long> {
    private final JDBCUtils jdbcUtils = new JDBCUtils();
    @Override
    public FriendRequest findOne(Long aLong) {
        String query = "SELECT * FROM friendrequest WHERE \"Id\" = ?";
        FriendRequest friendRequest   = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);

        ) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String username1 = resultSet.getString("username1");
                String username2 = resultSet.getString("username2");
                LocalDateTime request = resultSet.getTimestamp("requestSentAt").toLocalDateTime();
                String status = resultSet.getString("status");
                friendRequest = new FriendRequest(username1,username2,request,status);
                friendRequest.setId(aLong);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendRequest;
    }

    @Override
    public Iterable<FriendRequest> findAll() {
        String query = "SELECT * FROM friendrequest";
        List<FriendRequest> friendRequests   = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();
        ) {

            while(resultSet.next()){
                Long id = resultSet.getLong("Id");
                String username1 = resultSet.getString("username1");
                String username2 = resultSet.getString("username2");
                LocalDateTime request = resultSet.getTimestamp("requestSentAt").toLocalDateTime();
                String status = resultSet.getString("status");
                FriendRequest friendRequest = new FriendRequest(username1,username2,request,status);
                friendRequest.setId(id);
                friendRequests.add(friendRequest);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendRequests;
    }

    @Override
    public FriendRequest save(FriendRequest entity) {
        String query = "INSERT INTO friendrequest(\"Id\",\"username1\",\"username2\",\"requestSentAt\",\"status\") VALUES (?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getUsername1());
            statement.setString(3, entity.getUsername2());
            Timestamp request = java.sql.Timestamp.valueOf(entity.getRequestSentAt());
            statement.setTimestamp(4, request);
            statement.setString(5,entity.getStatus());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public FriendRequest delete(Long aLong) {
        String query = "DELETE FROM friendrequest WHERE \"Id\" = ?";
        FriendRequest friendRequest = findOne(aLong);
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, aLong);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendRequest;
    }

    @Override
    public FriendRequest update(FriendRequest entity) {
        String query = "UPDATE friendrequest SET \"status\" = ? WHERE \"Id\" = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, entity.getStatus());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
}
