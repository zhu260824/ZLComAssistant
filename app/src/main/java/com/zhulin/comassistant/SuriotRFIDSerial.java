package com.zhulin.comassistant;

import com.zhulin.rk3288.ComAssistant;

public class SuriotRFIDSerial {
    private static String PORT_NAME = "/dev/ttyS0";
    private static int BAUD_RATE = 115200;
    private static SuriotRFIDSerial.DeviceListener deviceListener;

    public static void init() {
        ComAssistant.getInstance().openUart(PORT_NAME, BAUD_RATE);
        ComAssistant.getInstance().addComPortListener(PORT_NAME, ComRecData -> {
            String res = new String(ComRecData.bRec);
            if (deviceListener != null) {
                deviceListener.onInfo(res);
            }
            if (res.contains("ES:") && res.length() < 8) {
                int value = getLightValue(res);
                if (value >= 0 && deviceListener != null) {
                    deviceListener.onLightChange(value);
                }
            } else if (res.contains("FC:") && res.endsWith("\0")) {
                if (deviceListener != null) {
                    deviceListener.onCardInfo(res);
                }
            }
        });
    }

    public static int getLightValue(String rec) {
        int index = rec.lastIndexOf(":") + 1;
        if (index < rec.length()) {
            String res = rec.substring(index);
            try {
                int value = Integer.parseInt(res);
                return value;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    public static void sendMsg(String msg, boolean isHex) {
        ComAssistant.getInstance().sendMsg(PORT_NAME, msg, isHex);
    }

    public static void setDeviceListener(SuriotRFIDSerial.DeviceListener deviceListener) {
        if (null == deviceListener) return;
        SuriotRFIDSerial.deviceListener = deviceListener;
    }

    public interface DeviceListener {
        void onCardInfo(String info);

        void onLightChange(int value);

        void onInfo(String info);
    }
}
