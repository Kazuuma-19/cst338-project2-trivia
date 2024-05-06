package com.example.triviaproject.database.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {
    User u1 = null;
    String defaultUserName = "Sample";
    String defaultPassWord = "Text";
    boolean defaultAdmin = false;
    @Before
    public void setUp(){
        u1 = new User(defaultUserName,defaultPassWord,defaultAdmin);
    }
    @After
    public void tearDown(){
        u1 = null;
    }
    @Test
    public void account_test(){
        User u2 = null;
        assertNull(u2);
        u2 = new User("Gordon", "Freeman", false);
        assertNotNull(u2);
        assertNotNull(u1);
    }

    @Test
    public void getAccount_test(){
        assertEquals(defaultUserName,u1.getUserName());
        assertEquals(defaultPassWord,u1.getPassword());
        assertEquals(defaultAdmin,u1.isAdmin());
    }

    @Test
    public void setAccount_test(){
        assertEquals(defaultUserName,u1.getUserName());
        String newName = "test";
        u1.setUserName(newName);
        assertNotEquals(defaultUserName,u1.getUserName());
        assertEquals(newName,u1.getUserName());

        assertEquals(defaultPassWord,u1.getPassword());
        String newPassWord = "chamber";
        u1.setPassword(newPassWord);
        assertNotEquals(defaultPassWord, u1.getPassword());
        assertEquals(newPassWord,u1.getPassword());

        assertEquals(defaultAdmin,u1.isAdmin());
        boolean newAdmin = true;
        u1.setAdmin(newAdmin);
        assertNotEquals(defaultAdmin, u1.isAdmin());
        assertEquals(newAdmin,u1.isAdmin());
    }

}