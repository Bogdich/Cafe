package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.UserInfoDAO;
import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.transaction.IDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bogdevich.cafe.constant.ErrorMessage.DAOExceptionMessage.USER_FIND_BY_ID;

public class UserInfoDAOImpl implements UserInfoDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT = "INSERT INTO user_info (`number`, `street`, `house_number`, `flat`, `user_id`) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT `id`, `number`, `street`, `house_number`, `flat`, `user_id` FROM user_info";
    private static final String SQL_SELECT_BY_ID = "SELECT `id`,`number`, `street`, `house_number`, `flat`, `user_id` FROM user_info WHERE `id`=?";
    private static final String SQL_SELECT_BY_USER_ID = "SELECT `id`, `number`, `street`, `house_number`, `flat`, `user_id` FROM user_info WHERE `user_id`=?";
    /**
     * Not done
     */
    private static final String SQL_SELECT_BY_ORDER_ID = "SELECT `id`, `number`, `street`, `house_number`, `flat`, `user_id` FROM user_info ";
    private static final String SQL_UPDATE = "UPDATE user_info SET `number`=?, `street`=?, `house_number`?, `flat`? WHERE `id`=?";
    private static final String SQL_DELETE = "DELETE FROM address WHERE `id`=?";

    private final IDataSource dataSource;

    public UserInfoDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<UserInfo> create(int number, String street, String house, int flat, int userID) throws DAOException {
        UserInfo userinfo;
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, number);
            statement.setString(2, street);
            statement.setString(3, house);
            statement.setInt(4, flat);
            statement.setInt(5, userID);

            int rowsAffected = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next() && rowsAffected == 1) {
                userinfo = new UserInfo(number, street, house, flat, userID);
                userinfo.setId(resultSet.getInt(1));
            } else {
                LOGGER.log(Level.WARN, "Can not create userinfo");
                throw new DAOException();
            }
            return Optional.ofNullable(userinfo);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public List<UserInfo> findAll() throws DAOException {
        ArrayList<UserInfo> userinfos = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userinfos.add(insertData(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            throw new DAOException(ex);
        }
        return userinfos;
    }

    @Override
    public List<UserInfo> findUserInfoByUserId(int userID) throws DAOException {
        Connection connection = dataSource.getConnection();
        ArrayList<UserInfo> userinfos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_USER_ID)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userinfos.add(insertData(resultSet));
            }
            return userinfos;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public Optional<UserInfo> findUserInfoByOrderId(int orderID) throws DAOException {
//        Connection connection = dataSource.getConnection();
//        UserInfo userinfo = null;
//        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ORDER_ID)) {
//            statement.setInt(1, orderID);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                userinfo = insertData(resultSet);
//            }
//            return Optional.ofNullable(userinfo);
//        } catch (SQLException ex) {
//            throw new DAOException(USER_FIND_BY_ID, ex);
//        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserInfo> findUserInfoById(int id) throws DAOException {
        Connection connection = dataSource.getConnection();
        UserInfo userInfo = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userInfo = insertData(resultSet);
            }
            return Optional.ofNullable(userInfo);
        } catch (SQLException ex) {
            throw new DAOException(USER_FIND_BY_ID, ex);
        }
    }

    @Override
    public boolean update(UserInfo userinfo) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(UserInfo userinfo) throws DAOException {
        throw new UnsupportedOperationException();
    }

    private UserInfo insertData(ResultSet resultSet) throws SQLException {
        UserInfo userinfo = new UserInfo();
        userinfo.setId(resultSet.getInt("id"));
        userinfo.setNumber(resultSet.getInt("number"));
        userinfo.setStreet(resultSet.getString("street"));
        userinfo.setHouse(resultSet.getString("house_number"));
        userinfo.setFlat(resultSet.getInt("flat"));
        userinfo.setUserID(resultSet.getInt("user_id"));
        return userinfo;
    }
}
