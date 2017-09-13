package com.bogdevich.cafe.util;

import com.bogdevich.cafe.exception.InvalidDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;

public class Validator {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String LOGIN_REGEX = "^[A-zА-я0-9 _-]{4,45}$";
    private static final String PASSWORD_REGEX = "^.{4,150}$";
    private static final String NUMBER_REGEX = "^(25|29|33|44)[0-9]{7}$";
    private static final String STREET_REGEX = "^[A-zА-я0-9 -]{4,45}$";
    private static final String HOUSE_REGEX = "^[1-9][0-9]{0,2}(\\/[0-9]{1,2})?$";
    private static final String FLAT_REGEX = "^[1-9]([0-9]{1,3})?$";
    private static final String DISH_NAME_REGEX = "^[A-zА-я -]{4,45}$";
    private static final String DISH_PRICE_REGEX = "^[1-9][0-9]{0,4}(\\.[0-9]{1,2})?$";
    private static final String DISH_WEIGHT_REGEX = "^[1-9][0-9]{0,3}$";
    private static final String DISH_DESCRIPTION_REGEX = "^[1-9][0-9]{0,3}$";
    private static final String DISH_CATEGORY_REGEX = "";

    public static String parseLogin(String login) throws InvalidDataException {
        validate(login, LOGIN_REGEX);
        return login;
    }

    public static String parsePasword(String password) throws InvalidDataException{
        validate(password, PASSWORD_REGEX);
        return password;
    }

    public static int parseNumber(String number) throws InvalidDataException{
        validate(number, NUMBER_REGEX);
        return Integer.parseInt(number);
    }

    public static String parseStreet(String street) throws InvalidDataException{
        validate(street, STREET_REGEX);
        return street;
    }

    public static String parseHouse(String house) throws InvalidDataException{
        validate(house, HOUSE_REGEX);
        return house;
    }

    public static int parseFlat(String flat) throws InvalidDataException{
        validate(flat, FLAT_REGEX);
        return Integer.parseInt(flat);
    }

    public static String parseName(String name) throws InvalidDataException{
        validate(name, DISH_NAME_REGEX);
        return name;
    }

    public static BigDecimal parsePrice(String price) throws InvalidDataException{
        validate(price, DISH_PRICE_REGEX);
        return BigDecimal.valueOf(Double.parseDouble(price));
    }

    public static int parseWeight(String weight) throws InvalidDataException{
        validate(weight, DISH_WEIGHT_REGEX);
        return Integer.parseInt(weight);
    }

    public static String parseDescription(String description) throws InvalidDataException{
        validate(description, DISH_DESCRIPTION_REGEX);
        return description;
    }

    public static String parseCategory(Object object) throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }

    private static void validate(String value, String regex) throws InvalidDataException{
        if (null == value || value.isEmpty() || !value.matches(regex)){
            throw new InvalidDataException();
        }
    }
}
