package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.UserInfoDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.transaction.IDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserInfoDAOImpl implements UserInfoDAO {

    private static final String SQL_INSERT = "" +
            "INSERT INTO `cafe`.`user_info` " +
            "   (`user_info`.`number`, " +
            "   `user_info`.`street`, " +
            "   `user_info`.`house_number`, " +
            "   `user_info`.`flat`, " +
            "   `user_info`.`user_id`) " +
            "VALUES (?, ?, ?, ?, ?);";

    private static final String SQL_SELECT_ALL = "" +
            "SELECT " +
            "   `user_info`.`id`, " +
            "   `user_info`.`number`, " +
            "   `user_info`.`street`, " +
            "   `user_info`.`house_number`, " +
            "   `user_info`.`flat`, " +
            "   `user_info`.`user_id` " +
            "FROM `cafe`.`user_info`;";

    private static final String SQL_SELECT_BY_ID = "" +
            "SELECT " +
            "   `user_info`.`id`,`number`, " +
            "   `user_info`.`street`, " +
            "   `user_info`.`house_number`, " +
            "   `user_info`.`flat`, " +
            "   `user_info`.`user_id` " +
            "FROM `cafe`.`user_info` " +
            "WHERE `id`=?;";

    private static final String SQL_SELECT_BY_USER_ID = "" +
            "SELECT " +
            "   `user_info`.`id`, " +
            "   `user_info`.`number`, " +
            "   `user_info`.`street`, " +
            "   `user_info`.`house_number`, " +
            "   `user_info`.`flat`, " +
            "   `user_info`.`user_id` " +
            "FROM `cafe`.`user_info` " +
            "WHERE `user_id`=?;";

    private static final String SQL_UPDATE = "" +
            "UPDATE `user_info` SET " +
            "   `user_info`.`number`=?, " +
            "   `user_info`.`street`=?, " +
            "   `user_info`.`house_number`=?, " +
            "   `user_info`.`flat`=? " +
            "WHERE `user_info`.`id`=?;";

    private static final String SQL_DELETE = "DELETE FROM `user_info` WHERE `user_info`.`id`=?;";

    private final IDataSource dataSource;

    public UserInfoDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<UserInfo> createUserInfo(int number, String street, String house, int flat, int userID) throws DAOException {
        Connection connection = dataSource.getConnection();
        UserInfo userInfo = null;
        int generatedID = create(
                connection,
                SQL_INSERT,
                statement -> {
                    statement.setInt(1, number);
                    statement.setString(2, street);
                    statement.setString(3, house);
                    statement.setInt(4, flat);
                    statement.setInt(5, userID);
                },
                true
        );
        if (generatedID != 0) {
            userInfo = new UserInfo(number, street, house, flat, userID);
            userInfo.setId(generatedID);
        }
        return Optional.ofNullable(userInfo);
    }

    @Override
    public List<UserInfo> findAllInfos() throws DAOException {
        Connection connection = dataSource.getConnection();
        return findAllRecords(
                connection,
                SQL_SELECT_ALL,
                this::getUserInfoFromRS
        );
    }

    @Override
    public List<UserInfo> findUserInfoByUserId(int userID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_BY_USER_ID,
                statement -> statement.setInt(1, userID),
                this::getUserInfoFromRS
        );
    }

    @Override
    public Optional<UserInfo> findUserInfoByOrderId(int orderID) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserInfo> findUserInfoById(int id) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecord(
                connection,
                SQL_SELECT_BY_ID,
                statement -> statement.setInt(1, id),
                this::getUserInfoFromRS
        );
    }


    @Override
    public boolean updateUserInfo(UserInfo userinfo) throws DAOException {
        Connection connection = dataSource.getConnection();
        return update(
                connection,
                SQL_UPDATE,
                statement -> {
                    statement.setInt(1, userinfo.getNumber());
                    statement.setString(2, userinfo.getStreet());
                    statement.setString(3, userinfo.getHouse());
                    statement.setInt(4, userinfo.getFlat());
                    statement.setInt(5, userinfo.getId());
                }
        );
    }

    @Override
    public boolean deleteUserInfo(UserInfo userinfo) throws DAOException {
        Connection connection = dataSource.getConnection();
        return delete(
                connection,
                SQL_DELETE,
                statement -> statement.setInt(1, userinfo.getId())
        );
    }

    private UserInfo getUserInfoFromRS(ResultSet resultSet) throws SQLException {
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
