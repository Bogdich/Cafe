package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.connectionpool.ConnectionPool;
import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.type.Role;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.*;

public class UserDAOImplTest {

    private static Connection connection;
    private static UserDAO userDAO;

    private String login = "test";
    private String password = "test";
    private Role role = Role.CUSTOMER;
    private BigDecimal balance = new BigDecimal("1234.1").setScale(2, BigDecimal.ROUND_HALF_UP);

    @BeforeClass
    public static void init() {
        connection = ConnectionPool.getInstance().takeConnection();
        userDAO = new UserDAOImpl(() -> connection);
    }

    @AfterClass
    public static void destroy() {
        ConnectionPool.getInstance().retrieveConnection(connection);
        ConnectionPool.getInstance().dispose();
    }

    @Before
    public void beforeTest() {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException ex) {
            fail(ex.getMessage());
        }
    }

    @After
    public void afterTest() {
        try {
            connection.rollback();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void create() {
        try {
            User user = new User(login, password, role, balance);
            userDAO
                    .createUser(login, password, balance, role)
                    .ifPresent(user1 -> user.setId(user1.getId()));
            User createdUser = userDAO
                    .findUserById(user.getId())
                    .orElseThrow(() -> new DAOException("User not found."));
            assertEquals(user, createdUser);
        } catch (DAOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void find() {
        try {
            User user = userDAO
                    .createUser(login, password, balance, role)
                    .orElseThrow(() -> new DAOException("Exception while creating user"));
            boolean contains = userDAO
                    .findAllUsers()
                    .contains(user);
            assertTrue(contains);
        } catch (DAOException ex) {
            fail(ex.getMessage());
        }
    }
}
