package com.bogdevich.cafe.service.util;

import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.service.exception.InvalidDataException;

import java.math.BigDecimal;
import java.util.Optional;

public class Validator {

    private static final String LOGIN_REGEX = "^\\p{LD}([\\p{LD}\\p{Space}-_]{0,43}\\p{LD})?$";
    private static final String PASSWORD_REGEX = "^.{4,150}$";
    private static final String PHONE_NUMBER_REGEX = "^(25|29|33|44)[0-9]{7}$";
    private static final String STREET_REGEX = "^\\p{LD}([\\p{LD}\\p{Space}-_]{0,43}\\p{LD})?$";
    private static final String HOUSE_REGEX = "^[1-9][0-9]{0,2}(/[0-9]{1,2})?$";
    private static final String FLAT_REGEX = "^[1-9]([0-9]{1,3})?$";
    private static final String DISH_NAME_REGEX = "^\\p{Lu}([\\p{LD}\\p{Space}]{0,43}\\p{LD})?$";
    private static final String DISH_PRICE_REGEX = "^[1-9][0-9]{0,4}(\\.[0-9]{1,2})?$";
    private static final String DISH_WEIGHT_REGEX = "^[1-9]\\p{N}{0,2}$";
    private static final String DISH_DESCRIPTION_REGEX = "^[\\p{LD}][\\p{LD}\\p{Space}\\p{P}]{1,65534}$";
    private static final String DISH_PICTURE_NAME = "^([\\\\_\\-\\p{LD}\\p{Space}]+)?\\p{LD}(\\.jpg|\\.png)$";
    private static final String DISH_CATEGORY_ID_REGEX = "^[1-9]\\p{Digit}{0,10}$";
    private static final String NUMBER_VALUE_REGEX = "^[1-9]\\p{Digit}{0,9}$";

    public static String parseLogin(String login) throws InvalidDataException {
        validate(login, LOGIN_REGEX, "Invalid login");
        return login;
    }

    public static String parsePassword(String password) throws InvalidDataException{
        validate(password, PASSWORD_REGEX, "Invalid password");
        return password;
    }

    public static int parsePhoneNumber(String number) throws InvalidDataException{
        validate(number, PHONE_NUMBER_REGEX, "Invalid phone number");
        return Integer.parseInt(number);
    }

    public static String parseStreet(String street) throws InvalidDataException{
        validate(street, STREET_REGEX, "Invalid street");
        return street;
    }

    public static String parseHouse(String house) throws InvalidDataException{
        validate(house, HOUSE_REGEX, "Invalid house number");
        return house;
    }

    public static int parseFlat(String flat) throws InvalidDataException{
        validate(flat, FLAT_REGEX, "Invalid flat number");
        return Integer.parseInt(flat);
    }

    public static String parseDishName(String name) throws InvalidDataException{
        validate(name, DISH_NAME_REGEX, "Invalid dish name");
        return name;
    }

    public static BigDecimal parseDishPrice(String price) throws InvalidDataException{
        validate(price, DISH_PRICE_REGEX, "Invalid dish price");
        try {
            Double valueDouble = Double.parseDouble(price);
            return BigDecimal.valueOf(valueDouble);
        } catch (NumberFormatException ex) {
            throw new InvalidDataException("");
        }
    }

    public static int parseDishWeight(String weight) throws InvalidDataException{
        validate(weight, DISH_WEIGHT_REGEX, "Invalid dish weight");
        try {
            return Integer.parseInt(weight);
        } catch (NumberFormatException ex) {
            throw new InvalidDataException("");
        }
    }

    public static String parseDishDescription(String description) throws InvalidDataException{
        validate(description, DISH_DESCRIPTION_REGEX, "Invalid dish description");
        return description;
    }

    public static String parseDishPictureName(String picture) throws InvalidDataException{
        validate(picture, DISH_PICTURE_NAME, "Invalid file name");
        return picture;
    }

    public static Category parseDishCategory(String category) throws InvalidDataException{
        validate(category, DISH_CATEGORY_ID_REGEX, "Invalid dish category");
        return Optional
                .ofNullable(Category.defineCategory(Integer.parseInt(category)))
                .orElseThrow(() -> new InvalidDataException("Invalid dish category"));
    }

    public static short parseDishQuantity(String quantity) throws InvalidDataException {
        validate(quantity, NUMBER_VALUE_REGEX, "Invalid dish quantity");
        try {
            return Short.parseShort(quantity);
        } catch (NumberFormatException ex) {
            throw new InvalidDataException("Invalid dish quantity");
        }
    }

    public static int parseID(String id) throws InvalidDataException{
        String message = "Invalid id: \"" + id + "\"";
        validate(id, NUMBER_VALUE_REGEX, message);
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new InvalidDataException(message);
        }
    }

    private static void validate(String value, String regex, String message) throws InvalidDataException{
        if (null == value || value.isEmpty() || !value.matches(regex)){
            throw new InvalidDataException(message);
        }
    }
}
