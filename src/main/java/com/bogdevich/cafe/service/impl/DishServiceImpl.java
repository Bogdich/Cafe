package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.dao.DishDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.dao.factory.DAOFactory;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.entity.wrapper.AnswerWrapper;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.util.Validator;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.exception.TransactionException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class DishServiceImpl implements DishService {

    private final ITransactionManager transactionManager;
    private final DAOFactory daoFactory;

    public DishServiceImpl(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.daoFactory = DAOFactory.getInstance();
    }

    @Override
    public void createDish(String name, String price, String weight,
                           String description, String picture, String categoryID)
            throws ServiceException, InvalidDataException {

        String validName = Validator.parseDishName(name);
        BigDecimal validPrice = Validator.parseDishPrice(price);
        int validWeight = Validator.parseDishWeight(weight);
        String validDescription = Validator.parseDishDescription(description);
        String validPictureName = Validator.parseDishPictureName(picture);
        Category validCategory = Validator.parseDishCategory(categoryID);

        Dish dish = new Dish(validName, validPrice,
                validWeight, validDescription,
                validPictureName, validCategory);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(
                    () -> {
                        boolean created = dishDAO.createDish(dish);
                        if (!created) {
                            throw new DAOException("Exception while creating a dish.");
                        }
                        return Optional.empty();
                    }
            );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void createOrderDishRecord(String orderID, String dishID, String quantity)
            throws ServiceException, InvalidDataException {

        int validOrderID = Validator.parseID(orderID);
        int validDishID = Validator.parseID(dishID);
        int validQuantity = Validator.parseDishQuantity(quantity);

        DishDAO dishDAO = daoFactory.getDishDAO();

        try {
            transactionManager.executeInTransaction(() -> {
                dishDAO.createOrderDishRecord(validOrderID, validDishID, validQuantity);
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void createUserDishRecord(int userID, String dishID, String quantity)
            throws ServiceException, InvalidDataException {

        Dish dish = findDishById(dishID)
                .orElseThrow(() -> new InvalidDataException("Dish does not exist"));
        int validQuantity = Validator.parseDishQuantity(quantity);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(
                    () -> {
                        Map<Dish, Integer> shoppingCart = dishDAO.findUserDishMap(userID);
                        if (shoppingCart.containsKey(dish)) {
                            int total = shoppingCart.get(dish) + validQuantity;
                            dishDAO.updateUserDishRecord(userID, dish.getId(), total);
                        } else {
                            dishDAO.createUserDishRecord(userID, dish.getId(), validQuantity);
                        }
                        return Optional.empty();
                    });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findAllDishes() throws ServiceException, InvalidDataException {
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(dishDAO::findAllDishes);
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findDishByCategoryId(String categoryID)
            throws ServiceException, InvalidDataException {
        Category category = Validator.parseDishCategory(categoryID);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(
                    () -> dishDAO.findDishByCategoryId(category.getId())
            );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findDishByCategoryName(String categoryName)
            throws ServiceException, InvalidDataException {
        Category category = Optional
                .ofNullable(Category.defineCategory(categoryName))
                .orElseThrow(() -> new InvalidDataException(
                        "Invalid category name."));
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(
                    () -> dishDAO.findDishByCategoryId(category.getId())
            );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findDishByOrderId(String orderID)
            throws ServiceException, InvalidDataException {
        int validOrderID = Validator.parseID(orderID);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(() ->
                    dishDAO.findDishByOrderId(validOrderID)
            );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Dish> findDishFromShoppingCartByUserId(String userID)
            throws ServiceException, InvalidDataException {
        Integer validID = Validator.parseID(userID);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(
                    () -> dishDAO.findDishFromSCByUserId(validID));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Optional<Dish> findDishById(String dishID)
            throws ServiceException, InvalidDataException {
        Integer validID = Validator.parseID(dishID);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(
                    () -> dishDAO.findDishById(validID));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Optional<Dish> findDishByName(String name)
            throws ServiceException, InvalidDataException {
        String validName = Validator.parseDishName(name);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(
                    () -> dishDAO.findDishByName(validName));
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Map<Dish, Integer> findUserDishMap(int userID)
            throws ServiceException, InvalidDataException {
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager
                    .executeInTransaction(
                            () -> dishDAO.findUserDishMap(userID)
                    );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Map<Dish, Integer> findOrderDishMap(String orderID)
            throws ServiceException, InvalidDataException {
        Integer validID = Validator.parseID(orderID);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager
                    .executeInTransaction(
                            () -> dishDAO.findOrderDishMap(validID)
                    );
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Optional<Dish> updateDish(String name, String price,
                                     String weight, String description,
                                     String picture, String category,
                                     String dishID)
            throws ServiceException, InvalidDataException {

        String validName = Validator.parseDishName(name);
        BigDecimal validPrice = Validator.parseDishPrice(price);
        Integer validWeight = Validator.parseDishWeight(weight);
        String validDescription = Validator.parseDishDescription(description);
        String validPictureName = Validator.parseDishPictureName(picture);
        Category validCategory = Validator.parseDishCategory(category);
        Integer validID = Validator.parseID(dishID);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            return transactionManager.executeInTransaction(() -> {
                Dish dish = new Dish(validName, validPrice,
                        validWeight, validDescription,
                        validPictureName, validCategory);
                dish.setId(validID);
                boolean updated = dishDAO.updateDish(dish);
                if (updated) {
                    return Optional.of(dish);
                } else {
                    throw new DAOException("Exception while dish updating.");
                }
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void updateOrderDishRecord(String orderID, String dishID,
                                      String quantity)
            throws ServiceException, InvalidDataException {

        int validOrderID = Validator.parseID(orderID);
        int validDishID = Validator.parseID(dishID);
        int validQuantity = Validator.parseDishQuantity(quantity);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                boolean updated = dishDAO
                        .updateOrderDishRecord(validOrderID, validDishID, validQuantity);
                if (!updated) {
                    throw new DAOException("Exception while order dish updating.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void updateUserDishRecord(int userID, String dishID,
                                     String quantity)
            throws ServiceException, InvalidDataException {

        int validDishID = Validator.parseID(dishID);
        int validQuantity = Validator.parseDishQuantity(quantity);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                boolean updated = dishDAO
                        .updateUserDishRecord(userID, validDishID, validQuantity);
                if (!updated) {
                    throw new DAOException("Exception while shopping cart updating.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void deleteDish(String dishID)
            throws ServiceException, InvalidDataException {

        Integer validID = Validator.parseID(dishID);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                boolean deleted = dishDAO.deleteDish(validID);
                if (!deleted) {
                    throw new DAOException("Exception while dish deleting.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void deleteOrderDishRecords(String orderID)
            throws ServiceException, InvalidDataException {
        Integer validOrderID = Validator.parseID(orderID);
        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                boolean deleted = dishDAO
                        .deleteOrderDishRecords(validOrderID);
                if (!deleted) {
                    throw new DAOException(
                            "Exception while deleting order dishes.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void deleteUserDishRecord(int userID, String dishID)
            throws ServiceException, InvalidDataException {

        Integer validDishID = Validator.parseID(dishID);

        DishDAO dishDAO = daoFactory.getDishDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                boolean deleted = dishDAO
                        .deleteUserDishRecord(userID, validDishID);
                if (!deleted) {
                    throw new DAOException(
                            "Exception while deleting shopping cart record.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void addDishToShoppingCart()
            throws ServiceException, InvalidDataException {

    }

    @Override
    public void changeDishQuantityInShoppingCart(int userID, String dishID, String quantity)
            throws ServiceException, InvalidDataException {

        Dish dish = this.findDishById(dishID).orElseThrow(
                () -> new InvalidDataException(
                "Try to change quantity of dish that doesn't exist. User id: " + userID)
        );
        Map<Dish, Integer> shoppingCart = this.findUserDishMap(userID);
        if (shoppingCart.containsKey(dish)) {
            this.updateUserDishRecord(userID, dishID, quantity);
        } else {
            this.createUserDishRecord(userID, dishID, quantity);
        }

    }

    @Override
    public void changeDishQuantityInShoppingCart(HttpSession session, String dishID, String quantity)
            throws ServiceException, InvalidDataException {

        int validQuantity = Validator.parseDishQuantity(quantity);
        Dish dish = this.findDishById(dishID).orElseThrow(
                () -> new InvalidDataException(
                        "Try to change quantity of dish that doesn't exist.")
        );
        Map<Dish, Integer> shoppingCart = this.getMapAttributeFromSession(session);
        shoppingCart.put(dish, validQuantity);
    }

    @Override
    public void deleteDishFromShoppingCart(int userID, String dishID)
            throws ServiceException, InvalidDataException {

        if (this.findDishById(dishID).isPresent()) {
            this.deleteUserDishRecord(userID, dishID);
        } else {
            throw new InvalidDataException(
                    "Try to delete not existing dish from customer shopping cart.");
        }

    }

    @Override
    public void deleteDishFromShoppingCart(HttpSession session, String dishID)
            throws ServiceException, InvalidDataException {
        Dish dish = this.findDishById(dishID).orElseThrow(
                () -> new InvalidDataException(
                        "Try to delete not existing dish from guest shopping cart.")
        );
        this.getMapAttributeFromSession(session).remove(dish);
    }

    @Override
    public Integer getShoppingCartSize(int userID) throws ServiceException, InvalidDataException {
        return this.findUserDishMap(userID).size();
    }

    @Override
    public Integer getShoppingCartSize(HttpSession session) throws ServiceException, InvalidDataException {
        return getMapAttributeFromSession(session).size();
    }

    @Override
    public BigDecimal getShoppingCartTotalCost(int userID)
            throws ServiceException, InvalidDataException {
        Map<Dish, Integer> shoppingCart = this.findUserDishMap(userID);
        return this.countShoppingCartTotalCost(shoppingCart);
    }

    @Override
    public BigDecimal getShoppingCartTotalCost(HttpSession session)
            throws ServiceException, InvalidDataException {
        Map<Dish, Integer> shoppingCart = this.getMapAttributeFromSession(session);
        return this.countShoppingCartTotalCost(shoppingCart);
    }

    @SuppressWarnings("unchecked")
    public Map<Dish, Integer> getMapAttributeFromSession(HttpSession session) {
        Object mapObject = Optional
                .ofNullable(session.getAttribute(Constant.AttributeName.SHOPPING_CART))
                .orElseGet(
                        () -> {
                            Map<Dish, Integer> dishIntegerMap = new HashMap<>();
                            session.setAttribute(Constant.AttributeName.SHOPPING_CART, dishIntegerMap);
                            return dishIntegerMap;
                        });
        return (HashMap<Dish, Integer>) mapObject;
    }

    private BigDecimal countShoppingCartTotalCost(Map<Dish, Integer> shoppingCart) {
        AnswerWrapper<BigDecimal> resultWrapper = new AnswerWrapper<>(BigDecimal.ZERO);
        BiConsumer<? super BigDecimal, ? super AnswerWrapper<BigDecimal>> adder = (item, wrapper) -> {
            BigDecimal mediator = wrapper.getAnswer();
            wrapper.setAnswer(mediator.add(item));
        };
        if (!shoppingCart.isEmpty()) {
            shoppingCart.forEach((dish, integer) -> {
                BigDecimal quantity = BigDecimal.valueOf(integer);
                BigDecimal itemCost = dish.getPrice().multiply(quantity);
                adder.accept(itemCost, resultWrapper);
            });
        }
        return resultWrapper.getAnswer();
    }
}
