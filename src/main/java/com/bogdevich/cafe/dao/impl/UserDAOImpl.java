package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.connectionpool.ProxyConnection;
import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.enums.Role;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.transaction.IDataSource;
import com.bogdevich.cafe.transaction.impl.TransactionManager;
import com.sun.istack.internal.Nullable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.*;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String SQL_FIND_ALL = "SELECT * FROM user";
    private static final String SQL_CREATE = "INSERT INTO user(login, email, password, role_id) VALUES(?,?,?,?)";
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id=?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET login=?, email=?, password=? WHERE id=?";
    private static final String SQL_DELETE_BY_LOGIN = "DELETE FROM user WHERE login=?";

    private final IDataSource dataSource;

    public UserDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(insertData(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            throw new DAOException("Error while processing request", ex);
        }
        return users;
    }

    @Override
    public Optional<User> create(String login, String email, String password, Role role) throws DAOException {
        User user;
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, login);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, role.getId());

            int rowsAffected = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next() && rowsAffected == 1) {
                user = new User(login, email, password, role);
                user.setId(resultSet.getInt(1));
                return Optional.of(user);
            } else {
                LOGGER.log(Level.WARN, "Can not create user");
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }


    /**
     * @param login
     * @return
     * @throws DAOException
     */
    @Override
    public Optional<User> findUserByLogin(String login) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(insertData(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public Optional<User> findUserById(int id) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(insertData(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while processing request", ex);
        }
    }

    @Override
    public boolean update(User user) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected==1);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean delete(User user) throws DAOException{
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_LOGIN)) {
            statement.setString(1, user.getLogin());
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected==1);
        }catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * @param resultSet  ResultSet object that was got after executing sql query
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    private User insertData(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.getRoleById(resultSet.getInt("role_id")));
        return user;
    }
}
