package com.bogdevich.cafe.dao.impl;

import com.bogdevich.cafe.dao.DishDAO;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.transaction.IDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by Grodno on 20.09.2017.
 */
public class DishDAOImpl implements DishDAO {
    private static final Logger LOGGER = LogManager.getLogger();



    private final IDataSource dataSource;

    public DishDAOImpl(IDataSource dataSource) {
        this.dataSource = dataSource;
    }


    private static final String SQL_INSERT = "INSERT INTO `cafe`.`dish` (`name`, `price`, `weight`, `description`, `picture_path`, `category_id`) VALUES (?,?,?,?,?,?)";
    @Override
    public Optional<Dish> create(Dish dish) throws DAOException {
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dish.getName());
            statement.setBigDecimal(2, dish.getPrice());
            statement.setInt(3, dish.getWeight());
            statement.setString(4, dish.getDescription());
            statement.setString(5, dish.getPicture());
            statement.setInt(6, dish.getCategory().getId());

            int rowsAffected = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next() && rowsAffected == 1) {
                dish.setId(resultSet.getInt(1));

            } else {
                dish = null;
            }
            return Optional.ofNullable(dish);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    private static final String SQL_SELECT_ALL = "SELECT ";
    @Override
    public List<Dish> findAll() throws DAOException {
        return null;
    }

    @Override
    public List<Dish> findDishByCategoryId(int categoryID) throws DAOException {
        return null;
    }

    @Override
    public List<Dish> findDishByOrderId(int orderID) throws DAOException {
        return null;
    }

    @Override
    public List<Dish> findDishByShoppingCartUserId(int userID) throws DAOException {
        return null;
    }

    @Override
    public Optional<Dish> findDishById(int dishID) throws DAOException {
        return null;
    }

    @Override
    public Optional<Dish> findDishByName(String name) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Dish dish) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(int dishID) throws DAOException {
        return false;
    }
}
