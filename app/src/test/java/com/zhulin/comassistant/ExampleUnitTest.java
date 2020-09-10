package com.zhulin.comassistant;

import com.zhulin.comassistant.util.StringUtil;
import com.zhulin.rk3288.DataFunc;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

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
        System.out.println(Arrays.equals(data3, data4));
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

    @Test
    public void testHexString() {
        byte[] date01 = {-86, 0, 13, 17, 1, 0, 8, -95, 2, -16, -95, 25, 8, 5, -117, 12};
        System.out.println(StringUtil.bytesToHexString(date01));
//        AA 00 0D 11 01 00 08 A1 02 F0 A1 19 08 05 8B 0C
        String send01 = "AA000101010000000000000000000003";
        System.out.println(Arrays.toString(StringUtil.hexStringToByteArray(send01)));
        byte[] send02 = new byte[]{(byte) 0xAA, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03};
        System.out.println(Arrays.toString(send02));
        byte[] send03 = DataFunc.HexToByteArr(send01);
        System.out.println(Arrays.toString(send03));
        String[] sendMsg = {"AA", "00", "01", "02", "00", "xx", "08", "FF", "00", "00", "00", "00", "00", "00", "00", "ss" };
        String send04 = "AA00010200xx08FF00000000000000ss";
        String send05 = send04.replace("xx", "0D").replace("ss", "0C");
        System.out.println(send04);
        System.out.println(send05);
        String date02 = "AA000D11010008A102F0A11908058B0C";
        System.out.println(date02.length());
        String address = date02.substring(4, 6);
        String check = date02.substring(date02.length() - 2);
        System.out.println(address);
        System.out.println(check);
        String sum01 = "000102000D080000000000000000";
        System.out.println(StringUtil.makeChecksum(sum01));
    }

    @Test
    public void testCheckSum() {
        String sum01 = "000102000E08FF00000000000000";
        System.out.println("查询闸机状态--->" + StringUtil.makeChecksum(sum01));
        String sum02 = "000102000D080000000000000000";
        System.out.println("进向开闸--->" + StringUtil.makeChecksum(sum02));
        String sum03 = "000102000D080001000000000000";
        System.out.println("进向开闸保持--->" + StringUtil.makeChecksum(sum03));
        String sum04 = "000102000D080002000000000000";
        System.out.println("进向关闸--->" + StringUtil.makeChecksum(sum04));
        String sum05 = "000102000D080003000000000000";
        System.out.println("出向开闸--->" + StringUtil.makeChecksum(sum05));
        String sum06 = "000102000D080004000000000000";
        System.out.println("出向开闸保持--->" + StringUtil.makeChecksum(sum06));
        String sum07 = "000102000D080005000000000000";
        System.out.println("出向关闸--->" + StringUtil.makeChecksum(sum07));
        String sum08 = "000102020E010000000000000000";
        System.out.println("查询输入接口状态--->" + StringUtil.makeChecksum(sum08));
    }

    @Test
    public void testHexInteger() {
        String cmdInfo = "AA000E12000008000201117001117032";
        String hex01 = cmdInfo.substring(18, 24);
        System.out.println(hex01);
        System.out.println(Integer.parseInt(hex01, 16));
    }


    @Test
    public void testRule() {
        String date01 = "AA000E12000008000201117001117032";
        String rule = "^[A-Fa-f0-9]{32}$";
        String number = "^[0-9]{6}$";
        Pattern r1 = Pattern.compile(rule);
        Matcher m1 = r1.matcher(date01);
        System.out.println(m1.matches());
//        String data02 = date01.substring(18, 24);
//        String data03 = date01.substring(24, 30);
        String data02="0111700111";
        String data03="000E12";
        Pattern r2 = Pattern.compile(number);
        Matcher m2 = r2.matcher(data02);
        Matcher m3 = r2.matcher(data03);
        System.out.println(m2.matches());
        System.out.println(m3.matches());
    }
}