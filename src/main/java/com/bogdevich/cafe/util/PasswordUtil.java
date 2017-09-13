package com.bogdevich.cafe.util;

import static org.mindrot.jbcrypt.BCrypt.*;

public final class PasswordUtil {
    /**
     * Define the BCrypt WORKLOAD to use when generating password hashes. Minimum 4, maximum 31.
     */
    private final static int WORKLOAD = 12;

    /**
     * All of hashed strings starts with this value.
     */
    private final static String HASH_START = "$2a$";

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        return hashpw(password, gensalt(WORKLOAD));
    }

    /**
     *
     * @param password a password to be compared with <code>storedHash</code>
     * @param storedHash a value stored in database
     * @return <code>true</code> if <code>storedHash</code> equals to <code>password</code>
     * by bcrypt algorithm
     * @throws IllegalArgumentException if <code>storedHash</code> is null
     * or doesn't start with <code>HASH_START</code>
     */
    public static boolean checkPassword(String password, String storedHash) throws IllegalArgumentException {
        if (null == storedHash || !storedHash.startsWith(HASH_START)) {
            throw new IllegalArgumentException("Invalid hash is provided for comparison");
        }
        return checkpw(password, storedHash);
    }
}
