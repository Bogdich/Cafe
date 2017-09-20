package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishDAO {

    Optional<Dish> create(Dish dish) throws DAOException;

    List<Dish> findAll() throws DAOException;
    List<Dish> findDishByCategoryId(int categoryID) throws DAOException;
    List<Dish> findDishByOrderId(int orderID) throws DAOException;
    List<Dish> findDishByShoppingCartUserId(int userID) throws DAOException;
    Optional<Dish> findDishById(int dishID) throws DAOException;
    Optional<Dish> findDishByName(String name) throws DAOException;

    boolean update(Dish dish) throws DAOException;

    boolean delete(int dishID) throws DAOException;
}
