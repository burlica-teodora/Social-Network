package com.example.social_network1.repository.db;

import com.example.social_network1.domain.Message;
import com.example.social_network1.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDBRepo implements Repository<Message,Long> {
    private final JDBCUtils jdbcUtils = new JDBCUtils();
    @Override
    public Message findOne(Long aLong) {
        String query = "SELECT * FROM messages WHERE \"Id\" = ?";
        Message message = null;

        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement =  connection.prepareStatement(query);

        ){
            statement.setLong(1,aLong);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String usernameFrom = resultSet.getString("UsernameFrom");
                String usernameTo = resultSet.getString("UsernameTo");
                String messageTxt = resultSet.getString("Message");
                message = new Message(usernameFrom,usernameTo,messageTxt);
                message.setId(aLong);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return message;
    }

    @Override
    public Iterable<Message> findAll() {
        String query = "SELECT * FROM messages";
        List<Message> messages = new ArrayList<>();

        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement =  connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()){
                Long id = resultSet.getLong("Id");
                String usernameFrom = resultSet.getString("UsernameFrom");
                String usernameTo = resultSet.getString("UsernameTo");
                String messageTxt = resultSet.getString("Message");
                Message message = new Message(usernameFrom,usernameTo,messageTxt);
                message.setId(id);
                messages.add(message);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message save(Message entity) {
        String query = "INSERT INTO messages(\"Id\",\"UsernameFrom\",\"UsernameTo\",\"Message\") VALUES (?,?,?,?)";
        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setLong(1,entity.getId());
            statement.setString(2,entity.getUsernameFROM());
            statement.setString(3,entity.getUsernameTO());
            statement.setString(4,entity.getMessage());
            statement.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public Message delete(Long aLong) {
        String query = "DELETE FROM messages WHERE \"Id\" = ?";
        Message msg = findOne(aLong);
        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.setLong(1,aLong);
            statement.executeUpdate();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return msg;
    }

    @Override
    public Message update(Message entity) {
        String query = "UPDATE messages SET \"Message\" = ? WHERE \"Id\" = ?";
        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setString(1,entity.getMessage());
            statement.setLong(2,entity.getId());
            statement.executeUpdate();

        }catch (SQLException ex){
            throw  new RuntimeException(ex);
        }
        return entity;
    }
}
