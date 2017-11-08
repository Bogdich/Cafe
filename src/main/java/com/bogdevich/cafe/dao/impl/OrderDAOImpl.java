package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.OrderDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.Order;
import com.bogdevich.cafe.transaction.IDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private static final String SQL_INSERT_ORDER_RECORD = "" +
            "INSERT INTO cafe.`order` " +
            "(`order`.`total_price`, " +
            "`order`.`number`, " +
            "`order`.`street`, " +
            "`order`.`house_number`, " +
            "`order`.`flat`, " +
            "`order`.`user_id`) " +
            "VALUES (?,?,?,?,?,?);";

    private static final String SQL_SELECT_ALL_ORDER_RECORDS = "" +
            "SELECT " +
            "  `order`.`id`, " +
            "  `order`.`total_price`, " +
            "  `order`.`create_time`, " +
            "  `order`.`number`, " +
            "  `order`.`street`, " +
            "  `order`.`house_number`, " +
            "  `order`.`flat`, " +
            "  `order`.`user_id` " +
            "FROM `cafe`.`order`;";

    private static final String SQL_SELECT_ORDER_BY_USER_ID = "" +
            "SELECT " +
            "  `order`.`id`, " +
            "  `order`.`total_price`, " +
            "  `order`.`create_time`, " +
            "  `order`.`number`, " +
            "  `order`.`street`, " +
            "  `order`.`house_number`, " +
            "  `order`.`flat`, " +
            "  `order`.`user_id` " +
            "FROM `cafe`.`order` " +
            "WHERE `order`.`user_id` = ?";

    private static final String SQL_SELECT_ORDER_BY_DISH_ID = "" +
            "SELECT " +
            "  `order`.`id`, " +
            "  `order`.`total_price`, " +
            "  `order`.`create_time`, " +
            "  `order`.`number`, " +
            "  `order`.`street`, " +
            "  `order`.`house_number`, " +
            "  `order`.`flat`, " +
            "  `order`.`user_id` " +
            "FROM `cafe`.`order` " +
            "JOIN `order_has_dish` " +
            "ON `order`.`id` = `order_has_dish`.`order_id`" +
            "WHERE `order_has_dish`.`dish_id` = ?";

    private static final String SQL_SELECT_ORDER_BY_ID = "" +
            "SELECT " +
            "  `order`.`id`, " +
            "  `order`.`total_price`, " +
            "  `order`.`create_time`, " +
            "  `order`.`number`, " +
            "  `order`.`street`, " +
            "  `order`.`house_number`, " +
            "  `order`.`flat`, " +
            "  `order`.`user_id` " +
            "FROM `cafe`.`order` " +
            "WHERE `order`.`id` = ?";

    private static final String SQL_DELETE_ORDER_RECORD = "" +
            "DELETE " +
            "FROM `cafe`.`order` " +
            "WHERE `order`.`id` = ?";

    private final IDataSource dataSource;

    public OrderDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int createOrder(Order order) throws DAOException {
        Connection connection = dataSource.getConnection();
        return create(
                connection,
                SQL_INSERT_ORDER_RECORD,
                ps -> {
                    ps.setBigDecimal(1, order.getTotal());
                    ps.setInt(2, order.getNumber());
                    ps.setString(3, order.getStreet());
                    ps.setString(4, order.getHouse());
                    ps.setInt(5, order.getFlat());
                    ps.setInt(6, order.getUserID());
                },
                true
        );
    }

    @Override
    public List<Order> findAll() throws DAOException {
        Connection connection = dataSource.getConnection();
        return findAllRecords(
                connection,
                SQL_SELECT_ALL_ORDER_RECORDS,
                this::getOrderFromRS
        );
    }

    @Override
    public List<Order> findOrderByUserId(int userID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_ORDER_BY_USER_ID,
                ps -> ps.setInt(1, userID),
                this::getOrderFromRS
        );
    }

    @Override
    public List<Order> findOrderByDishId(int dishID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_ORDER_BY_DISH_ID,
                ps -> ps.setInt(1, dishID),
                this::getOrderFromRS
        );
    }

    @Override
    public Optional<Order> findOrderById(int orderID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecord(
                connection,
                SQL_SELECT_ORDER_BY_ID,
                ps -> ps.setInt(1, orderID),
                this::getOrderFromRS
        );
    }

    @Override
    public List<Dish> findOrderByAddress(String street, String house, int flat) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Dish> findOrderByPhoneNumber(int phoneNumber) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateOrder(Order order) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteOrder(int orderID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return delete(
                connection,
                SQL_DELETE_ORDER_RECORD,
                ps -> ps.setInt(1, orderID)
        );
    }

    private Order getOrderFromRS(ResultSet rs) throws SQLException{
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setTotal(rs.getBigDecimal("total_price"));
        order.setOrderTime(rs.getTimestamp("create_time"));
        order.setStreet(rs.getString("street"));
        order.setHouse(rs.getString("house_number"));
        order.setFlat(rs.getInt("flat"));
        order.setNumber(rs.getInt("number"));
        order.setUserID(rs.getInt("user_id"));
        return order;
    }
}
