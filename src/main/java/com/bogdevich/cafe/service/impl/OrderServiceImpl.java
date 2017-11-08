package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.dao.DishDAO;
import com.bogdevich.cafe.dao.OrderDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.dao.factory.DAOFactory;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.Order;
import com.bogdevich.cafe.service.OrderService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.util.Validator;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.exception.TransactionException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class OrderServiceImpl implements OrderService {

    private final ITransactionManager transactionManager;
    private final DAOFactory daoFactory;

    public OrderServiceImpl(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.daoFactory = DAOFactory.getInstance();
    }

    @Override
    public Optional<Order> createOrder(String phoneNumber, String street, String house, String flat, int userID)
            throws ServiceException, InvalidDataException {

        int validNumber = Validator.parsePhoneNumber(phoneNumber);
        String validStreet = Validator.parseStreet(street);
        String validHouse = Validator.parseHouse(house);
        int validFlat = Validator.parseFlat(flat);

        OrderDAO orderDAO = daoFactory.getOrderDAO();
        DishDAO dishDAO = daoFactory.getDishDAO();

        try {

            BiConsumer<? super BigDecimal, ? super Order> adder = (item, order) -> {
                BigDecimal total = order.getTotal();
                order.setTotal(total.add(item));
            };

            return transactionManager.executeInTransaction(() -> {

                Map<Dish, Integer> shoppingCart = dishDAO.findUserDishMap(userID);
                Order order = new Order(BigDecimal.ZERO, validNumber,
                        validStreet, validHouse, validFlat, userID);
                if (!shoppingCart.isEmpty()) {
                    shoppingCart.forEach((dish, integer) -> {
                        BigDecimal quantity = BigDecimal.valueOf(integer);
                        BigDecimal itemCost = dish.getPrice().multiply(quantity);
                        adder.accept(itemCost, order);
                    });
                } else {
                    return Optional.empty();
                }

                int generatedID = orderDAO.createOrder(order);
                if (generatedID != 0) {
                    for (Map.Entry<Dish, Integer> entry : shoppingCart.entrySet()) {
                        dishDAO.createOrderDishRecord(generatedID, entry.getKey().getId(), entry.getValue());
                        dishDAO.deleteUserDishRecord(userID, entry.getKey().getId());
                    }
                    return Optional.of(order);
                } else {
                    throw new DAOException("Exception while creating order.");
                }
             });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        OrderDAO orderDAO = daoFactory.getOrderDAO();
         try {
             return transactionManager.executeInTransaction(orderDAO::findAll);
         } catch (TransactionException ex) {
             throw new ServiceException(ex);
         }
    }

    @Override
    public List<Order> findOrderByUserId(int userID) throws ServiceException {
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            return transactionManager.executeInTransaction(() -> orderDAO.findOrderByUserId(userID));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Order> findOrderByUserId(String userID) throws ServiceException, InvalidDataException {
        Integer validUserID = Validator.parseID(userID);
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            return transactionManager.executeInTransaction(() -> orderDAO.findOrderByUserId(validUserID));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Order> findOrderByDishId(String dishID) throws ServiceException, InvalidDataException {
        Integer validDishID = Validator.parseID(dishID);
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            return transactionManager.executeInTransaction(() -> orderDAO.findOrderByUserId(validDishID));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findOrderByPhoneNumber(String phoneNumber) throws ServiceException, InvalidDataException {
        Integer validPhoneNumber = Validator.parsePhoneNumber(phoneNumber);
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            return transactionManager.executeInTransaction(() -> orderDAO.findOrderByPhoneNumber(validPhoneNumber));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findOrderByAddress(String street, String house, String flat) throws ServiceException, InvalidDataException {
        String validStreet = Validator.parseStreet(street);
        String validHouse = Validator.parseHouse(house);
        Integer validFlat = Validator.parseFlat(flat);
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            return transactionManager.executeInTransaction(() ->
                    orderDAO.findOrderByAddress(validStreet, validHouse, validFlat)
            );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Optional<Order> findOrderById(String orderID) throws ServiceException, InvalidDataException {
        Integer validID = Validator.parseID(orderID);
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            return transactionManager.executeInTransaction(() ->
                    orderDAO.findOrderById(validID)
            );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean updateOrder(Order order) throws ServiceException, InvalidDataException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteOrder(String orderID)throws ServiceException, InvalidDataException {
        Integer validID = Validator.parseID(orderID);
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                boolean orderDeleted = orderDAO.deleteOrder(validID);
                boolean orderDishRecordsDeleted = dishDAO.deleteOrderDishRecords(validID);
                if (!(orderDeleted && orderDishRecordsDeleted)) {
                    throw new DAOException("Exception while order deleting.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }
}
