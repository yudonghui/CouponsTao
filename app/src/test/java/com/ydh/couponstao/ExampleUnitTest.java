package com.ydh.couponstao;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String str = "4566.00";
        if (str.endsWith("00")) {
            System.out.println("结果：" + str.substring(0, str.length() - 3));
        }else {
            System.out.println("结果：错误" );
        }

    }
}