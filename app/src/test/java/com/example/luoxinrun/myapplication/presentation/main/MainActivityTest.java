package com.example.luoxinrun.myapplication.presentation.main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by luoxinrun on 2017/7/27.
 */
public class MainActivityTest {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("+++++++++++++++++++++++beforeClass" + System.currentTimeMillis());
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("+++++++++++++++++++++++afterClass" + System.currentTimeMillis());
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("+++++++++++++++++++++++Before" + System.currentTimeMillis());
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("+++++++++++++++++++++++After" + System.currentTimeMillis());
    }

    @Ignore
    public void ignore() {
        System.out.println("+++++++++++++++++++++++ignore" + System.currentTimeMillis());
    }

    @Test
    public void loadData() throws Exception {
        System.out.println("+++++++++++++++++++++++loadData" + System.currentTimeMillis());
    }

    @Test(timeout = 2000)
    public void test() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++++++++test" + System.currentTimeMillis());
    }

}