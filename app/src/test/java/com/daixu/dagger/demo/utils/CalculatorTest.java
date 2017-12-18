package com.daixu.dagger.demo.utils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 32422 on 2017/12/13.
 */
public class CalculatorTest {

    @Mock
    Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
//        mCalculator = mock(Calculator.class);
    }

    @Test
    public void sum() throws Exception {
        assertEquals("Current user expected 6", 6d, mCalculator.sum(1d, 5d));
//        assertEquals(6d, mCalculator.sum(1d, 5d), 0);

//        mCalculator.sum(1d, 5d);
//        verify(mCalculator).sum(1d, 5d);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(1d, mCalculator.substract(5d, 4d), 0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(4d, mCalculator.divide(20d, 5d), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore("not implemented yet")
    public void testDivide() {
        mCalculator.divide(20d, 5d);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10d, mCalculator.multiply(2d, 5d), 0);
    }

}