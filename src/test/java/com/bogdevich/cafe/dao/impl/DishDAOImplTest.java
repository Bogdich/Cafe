package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.connectionpool.ConnectionPool;
import com.bogdevich.cafe.dao.DishDAO;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.fail;

public class DishDAOImplTest {

    private static Connection connection;
    private static DishDAO dishDAO;



    @BeforeClass
    public static void init() {
        connection = ConnectionPool.getInstance().takeConnection();
        dishDAO = new DishDAOImpl(() -> connection);
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
    public void createShoppingCart() {

    }
}
