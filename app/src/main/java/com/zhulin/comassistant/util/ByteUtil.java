package com.zhulin.comassistant.util;

public class ByteUtil {
    public static byte getXor(byte[] data) {
        byte temp = data[0];
        for (int i = 1; i < data.length; i++) {
            temp ^= data[i];
        }
        return temp;
    }

}
