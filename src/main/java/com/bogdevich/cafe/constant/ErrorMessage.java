package com.bogdevich.cafe.constant;

public final class ErrorMessage {

    public static final String INVALID_LOGIN_OR_PASSWORD = "invalid.login_or_password";
    public static final String INVALID_INPUT = "invalid.input";

    public static final String INVALID_LOGIN = "invalid.login";
    public static final String INVALID_PASSWORD = "invalid.password";
    public static final String INVALID_NUMBER = "invalid.number";
    public static final String INVALID_STREET = "invalid.street";
    public static final String INVALID_HOUSE = "invalid.house";
    public static final String INVALID_FLAT = "invalid.flat";

    public static final String LOGIN_EXISTS = "error.login";

    public final class DAOExceptionMessage {
        public static final String USER_CREATE = "user.create";
        public static final String USER_FIND_ALL = "user.find";
        public static final String USER_FIND_BY_NUMBER = "";
        public static final String USER_FIND_BY_ID = "";
        public static final String USER_UPDATE = "user.update";
        public static final String USER_DELETE = "user.delete";

        public static final String USER_INFO_CREATE = "userinfo.create";
        public static final String USER_INFO_FIND = "userinfo.find";
        public static final String USER_INFO_UPDATE = "userinfo.update";
        public static final String USER_INFO_DELETE = "userinfo.delete";
    }

    public final class TransactionExceptionMessage {
        public static final String TRANSACTION_ERROR_MESSAGE = "";
    }

    public final class ServiceExceptionMessage {


        public final static String LOGIN_ERROR = "message.loginerror";
        public final static String PAGE_NOT_FOUND = "message.nullpage";
        public final static String WRONG_ACTION = "message.wrongaction";
    }

    public final class InvalidDataExceptionMessage {

    }

    private ErrorMessage(){}
}
