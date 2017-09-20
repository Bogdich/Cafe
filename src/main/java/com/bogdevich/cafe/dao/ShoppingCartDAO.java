package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.ShoppingCart;
import com.bogdevich.cafe.exception.DAOException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDAO {
    Optional<ShoppingCart> create(int userID, HashMap<Dish, Integer> dishQuantityMap)
            throws DAOException;

    List<ShoppingCart> findAll() throws DAOException;
    List<ShoppingCart> findByUserId(int userID) throws DAOException;
    List<ShoppingCart> findByDishId(int dishID) throws DAOException;
    Optional<ShoppingCart> findOrderByUserAndDishIDs(int orderID, int dishID) throws DAOException;

    boolean update(ShoppingCart shoppingCart) throws DAOException;
    boolean uodateDishQuantity(ShoppingCart shoppingCart) throws DAOException;

    boolean delete(ShoppingCart shoppingCart) throws DAOException;
    boolean deleteAllForUserId(int userID) throws DAOException;
}
