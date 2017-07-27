package com.bogdevich.cafe.connectionpool;

class ConnectionParameter {
    static final String DB_PROPERTIES_PATH = "mysqldb";
    static final String DB_DRIVER = "db.driver";
    static final String DB_URL = "db.url";
    static final String DB_USER = "db.user";
    static final String DB_PASSWORD = "db.password";
    static final String DB_POOL_SIZE = "db.poolsize";

    static final int DB_DEFAULT_POOL_SIZE = 16;

    private ConnectionParameter() {
    }
}
