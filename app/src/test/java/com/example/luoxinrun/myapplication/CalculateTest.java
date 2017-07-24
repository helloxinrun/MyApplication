package com.example.luoxinrun.myapplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by HelloXinrun on 2017/7/25.
 */
public class CalculateTest {
    private Calculate mCalculate;

    @Before
    public void setUp() throws Exception {
        System.out.println("+++++++++++++++++++++++Before");
        mCalculate = new Calculate();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("+++++++++++++++++++++++After");
    }

    @Test
    public void add() throws Exception {
        System.out.println("+++++++++++++++++++++++add");
        assertEquals("加法错误", 5, mCalculate.add(1,2));
    }

    @Test
    public void subtract() throws Exception {
        System.out.println("+++++++++++++++++++++++subtract");
        assertEquals("减法错误", 1, mCalculate.subtract(2,2));
    }

    @Test
    public void multiply() throws Exception {
        System.out.println("+++++++++++++++++++++++multiply");
        assertEquals("乘法错误", 4, mCalculate.multiply(2,2));
    }

    @Test
    public void divide() throws Exception {
        System.out.println("+++++++++++++++++++++++divide");
        assertEquals("除法错误", 3, mCalculate.divide(6,2));
    }

}