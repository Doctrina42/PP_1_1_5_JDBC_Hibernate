package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final Util util = new Util();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(Id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(45), " +
                "lastName VARCHAR(45), age TINYINT(10))";

        session.createSQLQuery(sql).executeUpdate();

        transaction.commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS users";

        session.createSQLQuery(sql).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(new User(name, lastName, age));

        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        User user = session.load(User.class, id);

        session.delete(user);

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User";

        Query query = session.createQuery(hql);

        List users = query.getResultList();

        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String hql = "DELETE FROM users";

        session.createSQLQuery(hql).executeUpdate();

        transaction.commit();
        session.close();
    }
}