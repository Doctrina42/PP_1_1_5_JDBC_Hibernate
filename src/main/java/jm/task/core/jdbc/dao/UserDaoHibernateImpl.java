package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHibernateSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("""
                    CREATE TABLE IF NOT EXISTS users (
                      `id` INT NOT NULL auto_increment primary key,
                      `name` VARCHAR(45) NULL,
                      `last_name` VARCHAR(45) NULL,
                      `age` INT NULL,
                      UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
                    """).executeUpdate();

            transaction.commit();
        } catch (Exception ex) {
            ofNullable(transaction).ifPresent(Transaction::rollback);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHibernateSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("""
                    DROP TABLE IF EXISTS users
                    """).executeUpdate();

            transaction.commit();
        } catch (Exception ex) {
            ofNullable(transaction).ifPresent(Transaction::rollback);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getHibernateSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception ex) {
            ofNullable(transaction).ifPresent(Transaction::rollback);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getHibernateSession()) {
            transaction = session.beginTransaction();
            ;
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (Exception ex) {
            ofNullable(transaction).ifPresent(Transaction::rollback);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getHibernateSession()) {
            return session.createQuery("""
                    FROM User
                    """, User.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHibernateSession()) {
            transaction = session.beginTransaction();
            session.createQuery("""
                    DELETE FROM User
                    """).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ofNullable(transaction).ifPresent(Transaction::rollback);
        }
    }
}