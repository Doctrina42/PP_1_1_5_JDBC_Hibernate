package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import javax.transaction.SystemException;
import java.util.List;

public interface UserService {
    void createUsersTable() throws SystemException;

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
