package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args)  {
        Util.getConnection();
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Igor", "Nemo", (byte) 20);
        userService.saveUser("Petr", "Olegovna", (byte) 25);
        userService.saveUser("Ivan", "Petrova", (byte) 31);
        userService.saveUser("Marina", "Ivanova", (byte) 38);



        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
