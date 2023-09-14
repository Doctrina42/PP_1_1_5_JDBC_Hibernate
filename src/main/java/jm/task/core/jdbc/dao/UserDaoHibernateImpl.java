package jm.task.core.jdbc.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import javax.persistence.Query;
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
                "(Id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(20), " +
                "lastName VARCHAR(20), age TINYINT(128))";

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
        Transaction transaction = null;
        try( Session session = Util.getSessionFactory().getCurrentSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try( Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            User user = session.load(User.class, id);

            session.delete(user);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
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
        Transaction transaction = null;
        try( Session session = Util.getSessionFactory().getCurrentSession()) {

            transaction = session.beginTransaction();

            String hql = "DELETE FROM users";

            session.createSQLQuery(hql).executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}