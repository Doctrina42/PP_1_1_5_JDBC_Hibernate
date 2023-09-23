package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Gon", "Frick", (byte) 12);
        userService.saveUser("killua", "Zoldik", (byte) 13);
        userService.saveUser("Kurapika", "Kill", (byte) 18);
        userService.saveUser("Liorio", "Jecs", (byte) 24);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
