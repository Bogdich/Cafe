package com.bogdevich.cafe;

import com.bogdevich.cafe.entity.bean.Order;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.OrderService;
import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.factory.ServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class App {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();

    public static void main(String... args) {
        //register("bogdevich", "qwerty", "292838788", "Дзержинского", "58/2", "40");
//        list();
        //createDish();
//        Map<String, String> map = new HashMap<>();
//        map.put("3", "4");
//        map.put("4", "6");
        //createShoppingCart(1, map);
        //displayUserDishMap(1);
        //createOrder("292838788", "Дзержинского", "58/2", "40", 1);
        //findOrderAndOrderMap("2");
/*        Arrays
                .asList(Category.values())
                .forEach(category -> echo(category.name()));*/
/*        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("localization/category", Locale.forLanguageTag("en-EN"));
            echo(resourceBundle.getString("category.twister"));
        } catch (Exception ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
        }
        */
        DishService dishService = serviceFactory.getDishService();
        try {
            String map;
            Gson gson1 = new GsonBuilder().enableComplexMapKeySerialization().create();
            Gson gson2 = new GsonBuilder().disableHtmlEscaping().create();
            Gson gson3 = new GsonBuilder().disableHtmlEscaping().enableComplexMapKeySerialization().create();
            Gson gson4 = new GsonBuilder().enableComplexMapKeySerialization().create();
            map = gson1.toJson(dishService.findUserDishMap(1).entrySet().toArray());
            echo(map);
            map = gson2.toJson(dishService.findUserDishMap(1).entrySet().toArray());
            echo(map);
            map = gson3.toJson(dishService.findUserDishMap(1));
            echo(map);
            map = gson4.toJsonTree(map).getAsString();
            echo(map);
        } catch (Exception ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex);
        }


    }

    private static void echo(int i) {
        System.out.println(i);
    }

    private static <T> void echo(T obj) {
        System.out.println(obj);
    }

    private static <T, V> void echo(T t, V v) {
        System.out.println(t + ": " + v);
    }

    private static void register(String login, String password, String number, String street, String house, String flat) {
        UserService userService = serviceFactory.getUserService();
        try {
            userService
                    .register(login, password, number, street, house, flat)
                    .ifPresent(System.out::println);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void list() {
        UserService userService = serviceFactory.getUserService();
        try {
            userService
                    .findAllUsers()
                    .forEach(System.out::println);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void createDish() {
        DishService dishService = serviceFactory.getDishService();
        try {
            dishService.createDish("Name1", "1.01", "100", "test", "test.png", "1");
            dishService
                    .findAllDishes()
                    .forEach(System.out::println);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void createOrder(String number, String street,
                                    String house, String flat,
                                    int userID) {
        OrderService orderService = serviceFactory.getOrderService();
        DishService dishService = serviceFactory.getDishService();
        try {
            Order order = new Order();
            orderService
                    .createOrder(number, street, house, flat, userID)
                    .ifPresent(o -> {
                        echo(o);
                        order.setId(o.getId());
                    });
            dishService
                    .findOrderDishMap(String.valueOf(order.getId()))
                    .forEach(App::echo);
            dishService
                    .findUserDishMap(userID)
                    .forEach(App::echo);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void findOrderAndOrderMap(String orderID){
        OrderService orderService = serviceFactory.getOrderService();
        DishService dishService = serviceFactory.getDishService();
        try {
            orderService
                    .findOrderById(orderID)
                    .ifPresent(App::echo);
            dishService
                    .findOrderDishMap(orderID)
                    .forEach(App::echo);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void createShoppingCart(int userID, Map<String, String> dishMap) {
        DishService dishService = serviceFactory.getDishService();
        try {
            for (Map.Entry<String, String> entry :
                    dishMap.entrySet()) {
                dishService.createUserDishRecord(userID, entry.getKey(), entry.getValue());
            }
            dishService
                    .findUserDishMap(userID)
                    .forEach(App::echo);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void displayUserDishMap(int userID) {
        DishService dishService = serviceFactory.getDishService();
        try {
            dishService
                    .findUserDishMap(userID)
                    .forEach(App::echo);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
        }
    }

    private static void update(String login, String password,
                               String number, String street,
                               String house,
                               String flat) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
