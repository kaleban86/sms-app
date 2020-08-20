package org.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyCalculatorTest {

    @Test
    public void addCheck(){
        MyCalculator myCalculator = new MyCalculator();
        int res = myCalculator.add(1,2);
        assertTrue(res==3);
        assertSame(res, 3);

        assertFalse(res==10);
        assertNotSame(res, 10);


        assertEquals(res, 3);
    }

}
