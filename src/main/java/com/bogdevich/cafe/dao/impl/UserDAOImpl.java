package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.transaction.IDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bogdevich.cafe.constant.ErrorMessage.DAOExceptionMessage.*;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_SELECT_ALL_USERS = "SELECT `id`, `login`, `password`, `account_balance`, `role_id` FROM user";
    private static final String SQL_CREATE = "INSERT INTO `user`(`login`, `password`, `account_balance`, `role_id`) VALUES(?,?,?,?)";
    private static final String SQL_SELECT_BY_LOGIN = "SELECT `id`, `login`, `password`, `account_balance`, `role_id` FROM user WHERE `login`=?";
    private static final String SQL_SELECT_BY_ID = "SELECT `id`, `login`, `password`, `account_balance`, `role_id` FROM user WHERE `id`=?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET `login`=?, `password`=?, `account_balance`=?, `role_id`=? WHERE `id`=?";
    private static final String SQL_DELETE_BY_NUMBER = "DELETE FROM `user` WHERE `number`=?";

    private final IDataSource dataSource;

    public UserDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> create(String login, String password, BigDecimal balance, Role role) throws DAOException {
        User user;
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setBigDecimal(3, balance);
            statement.setInt(4, role.getId());

            int rowsAffected = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next() && rowsAffected == 1) {
                user = new User(login, password, role, balance);
                user.setId(resultSet.getInt(1));
            } else {
                LOGGER.log(Level.WARN, "Can not create user");
                throw new DAOException(USER_CREATE);
            }
            return Optional.ofNullable(user);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public List<User> findAll() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(insertData(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            throw new DAOException(USER_FIND_ALL, ex);
        }
        return users;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DAOException {
        Connection connection = dataSource.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = insertData(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException ex) {
            throw new DAOException(USER_FIND_BY_NUMBER, ex);
        }
    }

    @Override
    public Optional<User> findUserById(int id) throws DAOException {
        Connection connection = dataSource.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = insertData(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException ex) {
            throw new DAOException(USER_FIND_BY_ID, ex);
        }
    }

    @Override
    public boolean update(User user) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setBigDecimal(3, user.getAccountBalance());
            statement.setInt(4, user.getRole().getId());
            statement.setInt(5, user.getId());
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected==1);
        } catch (SQLException ex) {
            throw new DAOException(USER_UPDATE,ex);
        }
    }

    @Override
    public boolean updateBalance(BigDecimal balance) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updatePassword(String password) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateLogin(String login) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User user) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_NUMBER)) {
            statement.setString(1, user.getLogin());
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected==1);
        }catch (SQLException ex) {
            throw new DAOException(USER_DELETE, ex);
        }
    }

    private User insertData(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setAccountBalance(resultSet.getBigDecimal("account_balance"));
        user.setRole(Role.getRoleById(resultSet.getInt("role_id")));
        return user;
    }
}
