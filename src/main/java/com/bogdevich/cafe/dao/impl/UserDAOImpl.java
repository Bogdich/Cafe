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

import static com.bogdevich.cafe.constant.RBErrorMessage.DAOMessage.*;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_FIND_ALL = new StringBuilder()
            .append("SELECT ")
            .append("`id`, `number`, `password`, `name`, `role_id` ")
            .append("FROM user")
            .toString();
    private static final String SQL_CREATE = "INSERT INTO user(`number`, `password`, `name`, `role_id`) VALUES(?,?,?,?)";
    private static final String SQL_FIND_BY_NUMBER = "SELECT `id`, `number`, `password`, `name`, `role_id` FROM user WHERE `number`=?";
    private static final String SQL_FIND_BY_ID = "SELECT `id`, `number`, `password`, `name`, `role_id` FROM user WHERE `id`=?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET `number`=?, `password`=?, `name`=? WHERE `id`=?";
    private static final String SQL_DELETE_BY_NUMBER = "DELETE FROM user WHERE `number`=?";

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
            throw new DAOException(USER_FIND_ALL, ex);
        }
        return users;
    }

    @Override
    public Optional<User> create(int number, String password, String name, BigDecimal price,Role role) throws DAOException {
        User user;
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, number);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setInt(4, role.getId());

            int rowsAffected = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next() && rowsAffected == 1) {
                user = new User(number, password, name, price, role);
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
    public Optional<User> findUserByNumber(int number) throws DAOException {
        Connection connection = dataSource.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NUMBER)) {
            statement.setInt(1, number);
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
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
            statement.setInt(1, user.getNumber());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getId());
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
    public boolean updateNumber(int number) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User user) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_NUMBER)) {
            statement.setInt(1, user.getNumber());
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected==1);
        }catch (SQLException ex) {
            throw new DAOException(USER_DELETE, ex);
        }
    }

    private User insertData(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setNumber(resultSet.getInt("number"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.getRoleById(resultSet.getInt("role_id")));
        return user;
    }
}
