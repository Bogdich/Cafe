package com.bogdevich.cafe.connectionpool;

final class ConnectionParameter {
    static final String DB_PROPERTIES_PATH = "database";
    static final String DB_DRIVER = "db.driver";
    static final String DB_URL = "db.url";
    static final String DB_USER = "db.user";
    static final String DB_PASSWORD = "db.password";
    static final String DB_POOL_SIZE = "db.poolsize";

    static final int DB_DEFAULT_POOL_SIZE = 5;

    private ConnectionParameter() {
    }
}
