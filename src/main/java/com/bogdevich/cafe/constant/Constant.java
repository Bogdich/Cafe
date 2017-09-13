package com.bogdevich.cafe.constant;

public final class Constant {

    public static final String en_EN = "en_EN";
    public static final String ru_RU = "ru_RU";

    public static final String CONTROLLER = "/controller?command=";

    public final class ParameterName {
        public static final String COMMAND = "command";

        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String CONFIRM_PASSWORD = "confirm-password";
        public static final String REMEMBER_USER = "remember";
        public static final String NUMBER = "number";
        public static final String STREET = "street";
        public static final String HOUSE = "house";
        public static final String FLAT = "flat";
        public static final String BALANCE = "balance";
        public static final String AMOUNT = "amount";
        public static final String LANGUAGE = "lang";
    }

    public final class AttributeName {
        public static final String LANGUAGE = "lang";
        public static final String ROLE = "role";

        public static final String USER_ID = "userID";

        public static final String ERROR_MESSAGE = "errorMassage";
    }

    public final class ActionName {
        public static final String LOGIN = "login";
        public static final String LOGOUT ="logout";
        public static final String REGISTRATION = "registration";
        public static final String MAIN_PAGE = "main-page";
        public static final String REGISTRATION_PAGE = "registration-page";
        public static final String LOGIN_PAGE = "login-page";
        public static final String CHANGE_LANG = "change-language";
    }

    private Constant() {
    }
}
