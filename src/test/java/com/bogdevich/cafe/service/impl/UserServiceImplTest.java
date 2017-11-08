package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.connectionpool.ConnectionPool;
import com.bogdevich.cafe.service.UserService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UserServiceImplTest {

    private UserService userService;

    @BeforeClass
    public static void init() {
        ConnectionPool.getInstance().init();
    }

    @AfterClass
    public static void destroy() {
        ConnectionPool.getInstance().dispose();
    }



    @Test
    public void loginTest() {

    }
}
