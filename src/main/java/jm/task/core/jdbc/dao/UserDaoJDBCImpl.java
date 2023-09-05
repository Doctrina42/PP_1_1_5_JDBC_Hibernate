package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user" +
                    "(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45) ," +
                    "lastname VARCHAR(45) ," +
                    "age TINYINT(10) " +
                    ")");
        } catch (SQLException e) {
            System.out.println("При тестировании создания таблицы пользователей произошло исключение\n" + e.getMessage());
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            connection.close();
        } catch (SQLException e) {
            System.out.println("При тестировании удаления таблицы произошло исключение\n" + e.getMessage());

        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES (id,?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Во время тестирования сохранения пользователя произошло исключение\n" + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE ID = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println("При тестировании удаления пользователя по id произошло исключение\n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User")) {
            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM User");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("При попытке достать всех пользователей из базы данных произошло исключение\n" + e.getMessage());
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
            connection.close();
        } catch (SQLException e) {
            System.out.println("При тестировании очистки таблицы пользователей произошло исключение\n" + e.getMessage());
        }

    }
}