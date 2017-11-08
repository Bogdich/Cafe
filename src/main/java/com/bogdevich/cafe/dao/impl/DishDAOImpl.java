package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.DishDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.transaction.IDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DishDAOImpl implements DishDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_DISH = "" +
            "INSERT INTO `cafe`.`dish` " +
            "(`dish`.`name`, " +
            "`dish`.`price`, " +
            "`dish`.`weight`, " +
            "`dish`.`description`, " +
            "`dish`.`category_id`, " +
            "`dish`.`picture_path`) " +
            "VALUES (?,?,?,?,?,?)";

    private static final String SQL_INSERT_ORDER_DISH_RECORD = "" +
            "INSERT " +
            "INTO `cafe`.`order_has_dish` (" +
            "  `order_has_dish`.`order_id`, " +
            "  `order_has_dish`.`dish_id`, " +
            "  `order_has_dish`.`quantity`" +
            ") " +
            "VALUES (?,?,?);";

    private static final String SQL_INSERT_USER_DISH = "" +
            "INSERT " +
            "INTO `cafe`.`shopping_cart` (" +
            "  `shopping_cart`.`user_id`, " +
            "  `shopping_cart`.`dish_id`, " +
            "  `shopping_cart`.`quantity`" +
            ") " +
            "VALUES (?,?,?);";

    private static final String SQL_SELECT_ALL_DISHES = "" +
            "SELECT " +
            "  `dish`.`id`, " +
            "  `dish`.`name`, " +
            "  `dish`.`price`, " +
            "  `dish`.`description`, " +
            "  `dish`.`weight`, " +
            "  `dish`.`category_id`, " +
            "  `dish`.`picture_path` " +
            "  FROM `cafe`.`dish`;";

    private static final String SQL_SELECT_DISH_BY_CATEGORY_ID = "" +
            "SELECT " +
            "  `dish`.`id`, " +
            "  `dish`.`name`, " +
            "  `dish`.`price`, " +
            "  `dish`.`description`, " +
            "  `dish`.`weight`, " +
            "  `dish`.`category_id`, " +
            "  `dish`.`picture_path` " +
            "FROM `cafe`.`dish` " +
            "WHERE `dish`.`category_id` = (?);";

    private static final String SQL_SELECT_DISH_BY_ORDER_ID = "" +
            "SELECT " +
            "`dish`.`id`, " +
            "`dish`.`name`, " +
            "`dish`.`price`, " +
            "`dish`.`description`, " +
            "`dish`.`weight`, " +
            "`dish`.`category_id`, " +
            "`dish`.`picture_path` " +
            "FROM `cafe`.`dish` " +
            "JOIN `order_has_dish` " +
            "ON `dish`.`id` = `order_has_dish`.`dish_id` " +
            "JOIN `order` " +
            "ON `order_has_dish`.`order_id` = `order`.`id` " +
            "WHERE `order`.`id` =(?);" +
            "";

    private static final String SQL_SELECT_DISH_FROM_SHOPPING_CART_USER_ID = "" +
            "SELECT " +
            "  `dish`.`id`, " +
            "  `dish`.`name`, " +
            "  `dish`.`price`, " +
            "  `dish`.`description`, " +
            "  `dish`.`weight`, " +
            "  `dish`.`category_id`, " +
            "  `dish`.`picture_path` " +
            "FROM `dish` " +
            "JOIN `shopping_cart` " +
            "ON dish.id = `shopping_cart`.`dish_id` " +
            "JOIN `user` " +
            "ON `shopping_cart`.`user_id` = `user`.`id` " +
            "WHERE `user`.`id` = (?);";

    private static final String SQL_SELECT_DISH_BY_ID = "" +
            "SELECT " +
            "`dish`.`id`, " +
            "`dish`.`name`, " +
            "`dish`.`price`, " +
            "`dish`.`description`, " +
            "`dish`.`weight`, " +
            "`dish`.`category_id`, " +
            "`dish`.`picture_path` " +
            "FROM `dish` " +
            "WHERE `dish`.`id` = (?); ";

    private static final String SQL_SELECT_DISH_BY_NAME = "" +
            "SELECT " +
            "  `dish`.`id`, " +
            "  `dish`.`name`, " +
            "  `dish`.`price`, " +
            "  `dish`.`description`, " +
            "  `dish`.`weight`, " +
            "  `dish`.`category_id`, " +
            "  `dish`.`picture_path` " +
            "FROM `dish` " +
            "WHERE `dish`.`name` = (?);";

    private static final String SQL_SELECT_ORDER_DISHES_AND_Q_BY_ORDER_ID = "" +
            "SELECT " +
            "  `dish`.`id`, " +
            "  `dish`.`name`, " +
            "  `dish`.`price`, " +
            "  `dish`.`description`, " +
            "  `dish`.`weight`, " +
            "  `dish`.`category_id`, " +
            "  `dish`.`picture_path`, " +
            "  `order_has_dish`.`quantity` " +
            "FROM `dish` " +
            "JOIN `order_has_dish` " +
            "ON `dish`.`id` = `order_has_dish`.`dish_id` " +
            "JOIN `order` " +
            "ON `order_has_dish`.`order_id` = `order`.`id` " +
            "WHERE `order`.`id` = (?);";

    private static final String SQL_SELECT_SC_DISHES_AND_Q_BY_USER_ID = "" +
            "SELECT " +
            "  `dish`.`id`, " +
            "  `dish`.`name`, " +
            "  `dish`.`price`, " +
            "  `dish`.`description`, " +
            "  `dish`.`weight`, " +
            "  `dish`.`category_id`, " +
            "  `dish`.`picture_path`, " +
            "  `shopping_cart`.`quantity` " +
            "FROM `dish` " +
            "JOIN `shopping_cart` " +
            "ON `dish`.`id` = `shopping_cart`.`dish_id` " +
            "JOIN `user` " +
            "ON `shopping_cart`.`user_id` = `user`.`id` " +
            "WHERE `user`.`id` = (?);";

    private static final String SQL_UPDATE_DISH = "" +
            "UPDATE `dish` " +
            "SET " +
            "`dish`.`name` = ?, " +
            "`dish`.`price` = ?, " +
            "`dish`.`description` = ?, " +
            "`dish`.`weight` = ?, " +
            "`dish`.`category_id` = ?, " +
            "`dish`.`picture_path` = ? " +
            "WHERE `dish`.`id` = ?;";

    private static final String SQL_UPDATE_ORDER_DISH_RECORD = "" +
            "UPDATE `cafe`.`order_has_dish` " +
            "SET " +
            "  `order_has_dish`.`quantity`=? " +
            "WHERE " +
            "  `order_has_dish`.`order_id`=? " +
            "AND " +
            "  `order_has_dish`.`dish_id`=?;";

    private static final String SQL_UPDATE_USER_DISH_RECORD = "" +
            "UPDATE `cafe`.`shopping_cart` " +
            "SET " +
            "  `shopping_cart`.`quantity`=? " +
            "WHERE " +
            "  `shopping_cart`.`user_id`=? " +
            "AND " +
            "  `shopping_cart`.`dish_id`=?;";

    private static final String SQL_DELETE_DISH = "" +
            "DELETE " +
            "FROM `dish` " +
            "WHERE `dish`.`id` = ?;";

    private static final String SQL_DELETE_USER_DISH_RECORD = "" +
            "DELETE " +
            "FROM `shopping_cart` " +
            "WHERE " +
            "  `shopping_cart`.`user_id`=? " +
            "AND " +
            "    `shopping_cart`.`dish_id`=?;";

    private static final String SQL_DELETE_ORDER_DISH_RECORDS = "" +
            "DELETE " +
            "FROM `order_has_dish` " +
            "WHERE " +
            "  `order_has_dish`.`order_id`=?;";

    /**
     * {@code dataSource} provides DAO with connection.
     */
    private final IDataSource dataSource;

    public DishDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createDish(Dish dish) throws DAOException {
        Connection connection = dataSource.getConnection();
        int generatedID = create(
                connection,
                SQL_INSERT_DISH,
                (statement) -> {
                    statement.setString(1, dish.getName());
                    statement.setBigDecimal(2, dish.getPrice());
                    statement.setInt(3, dish.getWeight());
                    statement.setString(4, dish.getDescription());
                    statement.setInt(5, dish.getCategory().getId());
                    statement.setString(6, dish.getPicture());
                },
                true
        );
        if (generatedID != 0) {
            dish.setId(generatedID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createOrderDishRecord(int orderID, int dishID, int quantity) throws DAOException {
        Connection connection = dataSource.getConnection();
        int result = create(
                connection,
                SQL_INSERT_ORDER_DISH_RECORD,
                ps -> {
                    ps.setInt(1, orderID);
                    ps.setInt(2, dishID);
                    ps.setInt(3, quantity);
                },
                false
        );
        if (result == 0) {
            throw new DAOException("Exception while creating OrderDish record.");
        }
    }

    @Override
    public void createUserDishRecord(int userID, int dishID, int quantity) throws DAOException {
        Connection connection = dataSource.getConnection();
        int result = create(
                connection,
                SQL_INSERT_USER_DISH,
                ps -> {
                    ps.setInt(1, userID);
                    ps.setInt(2, dishID);
                    ps.setInt(3, quantity);
                },
                false
        );
        if (result == 0) {
            throw new DAOException("Exception while creating UserDish record.");
        }
    }

    @Override
    public List<Dish> findAllDishes() throws DAOException {
        Connection connection = dataSource.getConnection();
        return findAllRecords(
                connection,
                SQL_SELECT_ALL_DISHES,
                this::getDishFromRS
        );
    }

    @Override
    public List<Dish> findDishByCategoryId(int categoryID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_DISH_BY_CATEGORY_ID,
                preparedStatement -> preparedStatement.setInt(1, categoryID),
                this::getDishFromRS
        );
    }

    @Override
    public List<Dish> findDishByOrderId(int orderID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_DISH_BY_ORDER_ID,
                preparedStatement -> preparedStatement.setInt(1, orderID),
                this::getDishFromRS
        );
    }

    @Override
    public List<Dish> findDishFromSCByUserId(int userID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordList(
                connection,
                SQL_SELECT_DISH_FROM_SHOPPING_CART_USER_ID,
                preparedStatement -> preparedStatement.setInt(1, userID),
                this::getDishFromRS
        );
    }

    @Override
    public Optional<Dish> findDishById(int dishID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecord(
                connection,
                SQL_SELECT_DISH_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, dishID),
                this::getDishFromRS
        );
    }

    @Override
    public Optional<Dish> findDishByName(String name) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecord(
                connection,
                SQL_SELECT_DISH_BY_NAME,
                preparedStatement -> preparedStatement.setString(1, name),
                this::getDishFromRS
        );
    }

    @Override
    public Map<Dish, Integer> findUserDishMap(int userID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordMap(
                connection,
                SQL_SELECT_SC_DISHES_AND_Q_BY_USER_ID,
                statement -> statement.setInt(1, userID),
                this::getDishFromRS,
                this::getDishQuantityFromRS

        );
    }

    @Override
    public Map<Dish, Integer> findOrderDishMap(int orderID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return findRecordMap(
                connection,
                SQL_SELECT_ORDER_DISHES_AND_Q_BY_ORDER_ID,
                statement -> statement.setInt(1, orderID),
                this::getDishFromRS,
                this::getDishQuantityFromRS
        );
    }

    @Override
    public boolean updateDish(Dish dish) throws DAOException {
        Connection connection = dataSource.getConnection();
        return update(
                connection,
                SQL_UPDATE_DISH,
                statement -> {
                    statement.setString(1, dish.getName());
                    statement.setBigDecimal(2, dish.getPrice());
                    statement.setString(3, dish.getDescription());
                    statement.setInt(4, dish.getWeight());
                    statement.setInt(5, dish.getCategory().getId());
                    statement.setString(6, dish.getPicture());
                    statement.setInt(7, dish.getId());
                }
        );
    }

    @Override
    public boolean updateOrderDishRecord(int orderID, int dishID, int quantity) throws DAOException {
        Connection connection = dataSource.getConnection();
        return update(
                connection,
                SQL_UPDATE_ORDER_DISH_RECORD,
                ps -> {
                    ps.setInt(1, quantity);
                    ps.setInt(2, orderID);
                    ps.setInt(3, dishID);
                }
        );
    }

    @Override
    public boolean updateUserDishRecord(int userID, int dishID, int quantity) throws DAOException {
        Connection connection = dataSource.getConnection();
        return update(
                connection,
                SQL_UPDATE_USER_DISH_RECORD,
                ps -> {
                    ps.setInt(1, quantity);
                    ps.setInt(2, userID);
                    ps.setInt(3, dishID);
                }
        );
    }

    @Override
    public boolean deleteDish(int dishID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return delete(
                connection,
                SQL_DELETE_DISH,
                statement -> statement.setInt(1, dishID)
        );
    }

    @Override
    public boolean deleteOrderDishRecords(int orderID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return delete(
                connection,
                SQL_DELETE_ORDER_DISH_RECORDS,
                ps -> ps.setInt(1, orderID)
        );
    }

    @Override
    public boolean deleteUserDishRecord(int userID, int dishID) throws DAOException {
        Connection connection = dataSource.getConnection();
        return delete(
                connection,
                SQL_DELETE_USER_DISH_RECORD,
                ps -> {
                    ps.setInt(1, userID);
                    ps.setInt(2, dishID);
                }
        );
    }

    /**
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Dish getDishFromRS(ResultSet resultSet) throws SQLException {
        Dish dish = new Dish();
        dish.setId(resultSet.getInt("id"));
        dish.setName(resultSet.getString("name"));
        dish.setPrice(resultSet.getBigDecimal("price"));
        dish.setWeight(resultSet.getInt("weight"));
        dish.setDescription(resultSet.getString("description"));
        dish.setPicture(resultSet.getString("picture_path"));
        dish.setCategory(Category.defineCategory(resultSet.getInt("category_id")));
        return dish;
    }

    private Integer getDishQuantityFromRS(ResultSet resultSet) throws SQLException {
        return resultSet.getInt("quantity");
    }
}
