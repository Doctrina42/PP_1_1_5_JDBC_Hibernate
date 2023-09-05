package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import javax.transaction.SystemException;

public class Main {
    public static void main(String[] args) throws SystemException {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        userDao.createUsersTable();

        userDao.saveUser("Igor", "Nemo", (byte) 20);
        userDao.saveUser("Petr", "Olegovna", (byte) 25);
        userDao.saveUser("Ivan", "Petrova", (byte) 31);
        userDao.saveUser("Marina", "Ivanova", (byte) 38);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
