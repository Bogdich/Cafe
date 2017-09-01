package com.bogdevich.cafe.service.util;

import static org.mindrot.jbcrypt.BCrypt.*;

public class PasswordUtil {
    /**
     * Define the BCrypt WORKLOAD to use when generating password hashes. Minimum 4, maximum 31.
     */
    private final static int WORKLOAD = 12;

    /**
     * All of hashed strings start with this value.
     */
    private final static String HASH_START = "$2a$";

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        return hashpw(password, gensalt(WORKLOAD));
    }

    public static boolean checkPassword(String password, String storedHash) throws IllegalArgumentException {
        if (null == storedHash || !storedHash.startsWith(HASH_START)) {
            throw new IllegalArgumentException("Invalid hash is provided for comparison");
        }
        return checkpw(password, storedHash);
    }
}
