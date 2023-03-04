package com.example.social_network1.repository.db;



import com.example.social_network1.domain.User;
import com.example.social_network1.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepo implements Repository<User, Long> {

    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public User findOne(Long aLong) {
        String query = "SELECT * FROM users WHERE \"Id\" = ?";
        User user1   = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);

        ) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String firstName = resultSet.getString("FirstName");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");
                Integer age = resultSet.getInt("Age");
                user1 = new User(firstName, password, email, age);
                user1.setId(aLong);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user1;
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Long id = resultSet.getLong("Id");
                String firstName = resultSet.getString("FirstName");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");
                Integer age = resultSet.getInt("Age");
                User user1 = new User(firstName, password, email, age);
                user1.setId(id);
                users.add(user1);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;

    }

    @Override
    public User save(User entity) {
        String query = "INSERT INTO users(\"Id\",\"FirstName\",\"Password\",\"Email\",\"Age\") VALUES (?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            statement.setInt(5, entity.getAge());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public User delete(Long aLong) {
        String query = "DELETE FROM users WHERE \"Id\" = ?";
        User user = findOne(aLong);
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, aLong);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User update(User entity) {
        String query = "UPDATE users SET \"FirstName\" = ?,\"Password\" = ?,\"Email\" = ?,\"Age\" = ? WHERE \"Id\" = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getAge());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
}
