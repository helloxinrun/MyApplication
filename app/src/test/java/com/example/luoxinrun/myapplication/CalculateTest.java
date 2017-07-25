package com.example.luoxinrun.myapplication;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by HelloXinrun on 2017/7/25.
 */
public class CalculateTest {
    private Calculate mCalculate;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("+++++++++++++++++++++++beforeClass"+System.currentTimeMillis());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("+++++++++++++++++++++++afterClass"+System.currentTimeMillis());
    }

    @Ignore
    public void ignore(){
        System.out.println("+++++++++++++++++++++++ignore"+System.currentTimeMillis());
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("+++++++++++++++++++++++Before"+System.currentTimeMillis());
        mCalculate = new Calculate();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("+++++++++++++++++++++++After"+System.currentTimeMillis());
    }

    @Test
    public void add() throws Exception {
        System.out.println("+++++++++++++++++++++++add"+System.currentTimeMillis());
        assertEquals("加法错误", 5, mCalculate.add(1,2));
    }

    @Test
    public void subtract() throws Exception {
        System.out.println("+++++++++++++++++++++++subtract"+System.currentTimeMillis());
        assertEquals("减法错误", 1, mCalculate.subtract(2,2));
    }

    @Test
    public void multiply() throws Exception {
        System.out.println("+++++++++++++++++++++++multiply"+System.currentTimeMillis());
        assertEquals("乘法错误", 4, mCalculate.multiply(2,2));
    }

    @Test
    public void divide() throws Exception {
        System.out.println("+++++++++++++++++++++++divide"+System.currentTimeMillis());
        assertEquals("除法错误", 3, mCalculate.divide(6,0));
    }

    @Test(timeout = 2000)
    public void test(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++++++++test"+System.currentTimeMillis());
    }

}