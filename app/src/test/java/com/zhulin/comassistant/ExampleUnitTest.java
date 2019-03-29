package com.zhulin.comassistant;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testASCII() {
//        2107294780 表示为 32 31 30 37 32 39 34 37 38 30
        byte[] data1 = {51, 52, 30, 37, 32, 39, 34, 37, 38, 30};
        byte[] data2 = {51, 52, 53, 48, 53, 52, 57, 50, 54, 54};
        byte[] data3 = {-86, 0, 6, 0, 0, 0, 0, -43, 119, -94, 85};
        byte[] data4 = {-86, 0, 6, 0, 0, 0, 0, -43, 119, -94, 85};
        System.out.println(Arrays.equals(data3,data4));
        System.out.println(new String(data1));
        System.out.println(new String(data2));
        System.out.println(new String(data3));
        String nRcvString;
        StringBuffer tStringBuf = new StringBuffer();
        char[] tChars = new char[data2.length];
        for (int i = 0; i < data2.length; i++) {
            tChars[i] = (char) data2[i];
        }
        tStringBuf.append(tChars);
        nRcvString = tStringBuf.toString();
        System.out.println(nRcvString);
    }
}