package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.transaction.IDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_SELECT_ALL_USERS = "" +
            "SELECT " +
            "   `user`.`id`, " +
            "   `user`.`login`, " +
            "   `user`.`password`, " +
            "   `user`.`account_balance`, " +
            "   `user`.`role_id` " +
            "FROM `cafe`.`user`;";

    private static final String SQL_CREATE = "" +
            "INSERT INTO `user` (" +
            "   `user`.`login`, " +
            "   `user`.`password`, " +
            "   `user`.`account_balance`, " +
            "   `user`.`role_id`" +
            ") VALUES(?,?,?,?);";

    private static final String SQL_SELECT_BY_LOGIN = "" +
            "SELECT " +
            "   `user`.`id`, " +
            "   `user`.`login`, " +
            "   `user`.`password`, " +
            "   `user`.`account_balance`, " +
            "   `user`.`role_id` " +
            "FROM `cafe`.`user` " +
            "WHERE `user`.`login`=?;";

    private static final String SQL_SELECT_BY_ID = "" +
            "SELECT " +
            "   `user`.`id`, " +
            "   `user`.`login`, " +
            "   `user`.`password`, " +
            "   `user`.`account_balance`, " +
            "   `user`.`role_id` " +
            "FROM `cafe`.`user` " +
            "WHERE `id`=?;";

    private static final String SQL_SELECT_BY_ROLE_ID = "" +
            "SELECT " +
            "   `user`.`id`, " +
            "   `user`.`login`, " +
            "   `user`.`password`, " +
            "   `user`.`account_balance`, " +
            "   `user`.`role_id` " +
            "FROM `cafe`.`user` " +
            "WHERE `role_id`=?;";

    private static final String SQL_UPDATE_USER = "" +
            "UPDATE `cafe`.`user` SET " +
            "   `user`.`login`=?, " +
            "   `user`.`password`=?, " +
            "   `user`.`account_balance`=?, " +
            "   `user`.`role_id`=? " +
            "WHERE `user`.`id`=?;";

    private static final String SQL_DELETE = "" +
            "DELETE " +
            "FROM `cafe`.`user` " +
            "WHERE `user`.`id`=?";

    private final IDataSource dataSource;

    public UserDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> createUser(String login, String password, BigDecimal balance, Role role) throws DAOException {
        Connection connection = dataSource.getConnection();
        User user = null;
        int generatedID = create(
                connection,
                SQL_CREATE,
                statement -> {
                    statement.setString(1, login);
                    statement.setString(2, password);
                    statement.setBigDecimal(3, balance);//.setScale(2, BigDecimal.ROUND_HALF_UP));
                    statement.setInt(4, role.getId());
                },
                true
        );
        if (generatedID != 0) {
            user = new User(login, password, role, balance);
            user.setId(generatedID);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAllUsers() throws DAOException {
        Connection connection = dataSource.getConnection();
        return findAllRecords(
                connection,
                SQL_SELECT_ALL_USERS,
                this::getUserFromRS
        );
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecord(
                connection,
                SQL_SELECT_BY_LOGIN,
                statement -> statement.setString(1, login),
                this::getUserFromRS
        );
    }

    @Override
    public Optional<User> findUserById(int id) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecord(
                connection,
                SQL_SELECT_BY_ID,
                statement -> statement.setInt(1, id),
                this::getUserFromRS
        );
    }

    @Override
    public List<User> findUserByRoleId(int roleID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_BY_ROLE_ID,
                preparedStatement -> preparedStatement.setInt(1, roleID),
                this::getUserFromRS
        );
    }

    @Override
    public boolean updateUser(User user) throws DAOException {
        Connection connection = dataSource.getConnection();
        return update(
                connection,
                SQL_UPDATE_USER,
                statement -> {
                    statement.setString(1, user.getLogin());
                    statement.setString(2, user.getPassword());
                    statement.setBigDecimal(3, user.getAccountBalance());
                    statement.setInt(4, user.getRole().getId());
                    statement.setInt(5, user.getId());
                }
        );
    }

    @Override
    public boolean deleteUser(User user) throws DAOException {
        Connection connection = dataSource.getConnection();
        return delete(
                connection,
                SQL_DELETE,
                statement -> statement.setInt(1, user.getId())
        );
    }

    private User getUserFromRS(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setAccountBalance(resultSet.getBigDecimal("account_balance"));
        user.setRole(Role.getRoleById(resultSet.getInt("role_id")));
        return user;
    }
}
