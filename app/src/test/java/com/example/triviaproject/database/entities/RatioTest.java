package com.example.triviaproject.database.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class RatioTest {
    private Ratio ratio;

    @Before
    public void setUp() {
        ratio = new Ratio(10, 5, "admin1");
    }

    @Test
    public void testGetId() {
        ratio.setId(1);
        assertEquals(1, ratio.getId());
    }

    @Test
    public void testSetId() {
        ratio.setId(2);
        assertEquals(2, ratio.getId());
    }

    @Test
    public void testGetWins() {
        assertEquals(10, ratio.getWins());
    }

    @Test
    public void testSetWins() {
        ratio.setWins(15);
        assertEquals(15, ratio.getWins());
    }

    @Test
    public void testGetLosses() {
        assertEquals(5, ratio.getLosses());
    }

    @Test
    public void testSetLosses() {
        ratio.setLosses(8);
        assertEquals(8, ratio.getLosses());
    }

    @Test
    public void testGetName() {
        assertEquals("admin1", ratio.getName());
    }

    @Test
    public void testSetName() {
        ratio.setName("testUser");
        assertEquals("testUser", ratio.getName());
    }

    @Test
    public void testEquals() {
        Ratio adminRatio = new Ratio(10, 5, "admin1");
        assertEquals(ratio, adminRatio);

        adminRatio.setWins(11);
        assertNotEquals(ratio, adminRatio);
    }

    @Test
    public void testHashCode() {
        Ratio adminRatio = new Ratio(10, 5, "admin1");
        assertEquals(ratio.hashCode(), adminRatio.hashCode());
    }
}
