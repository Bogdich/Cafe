package com.bogdevich.cafe.command.constant;

public final class Constant {

    public static final String en_EN = "en-EN";
    public static final String ru_RU = "ru-RU";

    public static final String CONTROLLER = "/controller?command=";

    public static final String JSON_CONTENT_TYPE = "application/json";

    public final class ParameterName {
        public static final String COMMAND = "command";

        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String CONFIRM_PASSWORD = "confirmPassword";
        public static final String REMEMBER_USER = "remember";
        public static final String NUMBER = "number";
        public static final String STREET = "street";
        public static final String HOUSE = "house";
        public static final String FLAT = "flat";
        public static final String BALANCE = "balance";
        public static final String LANGUAGE = "lang";
        public static final String CATEGORY = "category";
        public static final String DISH_ID = "dishID";
        public static final String DISH_QUANTITY = "quantity";
    }

    public final class AttributeName {
        public static final String LANGUAGE = "lang";
        public static final String ROLE = "role";
        public static final String CATEGORY_LIST = "categories";

        public static final String USER_ID = "userID";

        public static final String SHOPPING_CART = "shoppingCart";

        public static final String ERROR_MESSAGE = "errorMessage";
    }

    public final class ActionName {
        public static final String LOGIN = "login";
        public static final String LOGOUT ="logout";
        public static final String REGISTRATION = "registration";
        public static final String MAIN_PAGE = "main-page";
        public static final String MENU_PAGE = "menu-page";
        public static final String REGISTRATION_PAGE = "registration-page";
        public static final String LOGIN_PAGE = "login-page";
        public static final String CHANGE_LANG = "change-language";
        public static final String GET_DISHES_BY_CATEGORY = "get-dishes";
        public static final String ADD_DISH_TO_SC = "add-dish";
        public static final String GET_SHOPPING_CART = "get-shopping-cart";
        public static final String CHANGE_QUANTITY = "change-quantity";
        public static final String DELETE_DISH_FROM_SHOPPING_CART = "delete-dish-from-shopping-cart";
        public static final String CHECK_SHOPPING_CART_SIZE = "check-shopping-cart-size";
        public static final String FILL_ORDER_FORM_WITH_USER_INFO = "fill-order-form-with-user-info";
        public static final String FILL_ORDER_FORM_WITH_TOTAL_COST = "fill-order-form-with-total-cost";
        public static final String CREATE_ORDER = "create-order";
    }

    private Constant() {
    }
}
