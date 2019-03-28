package com.zhulin.comassistant;

import com.zhulin.rk3288.ComAssistant;
import com.zhulin.rk3288.ComBean;

public class SuriotLedDevice {
    private static String PORT_NAME = "/dev/ttyS4";
    private static int BAUD_RATE = 115200;

    public static void setPortName(String portName) {
        PORT_NAME = portName;
    }

    public static void setBaudRate(int baudRate) {
        BAUD_RATE = baudRate;
    }

    public static void init() {
        ComAssistant.getInstance().openUart(PORT_NAME, BAUD_RATE);
    }

    public static void addDeviceListener(DeviceListener deviceListener) {
        if (null == deviceListener) return;
        ComAssistant.getInstance().addComPortListener(PORT_NAME, ComRecData -> {
            String res = new String(ComRecData.bRec);
            System.out.println("ComRecData{" + "bRec=" + res + ", sRecTime='" + ComRecData.sRecTime + '\'' + ", sComPort='" + ComRecData.sComPort + '\'' + '}');
            if (res.contains("ES:") && res.length() < 8) {
                int value = getLightValue(res);
                if (value >= 0) deviceListener.onLightChange(value);
            } else if (res.contains("WT:") && res.length() < 8) {
                deviceListener.onBodyChange(getHasBody(res));
            } else {
                deviceListener.onDataReceived(ComRecData);
            }
        });
    }

    public static void sendMsg(String msg, boolean isHex) {
        ComAssistant.getInstance().sendMsg(PORT_NAME, msg, isHex);
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

    public static boolean getHasBody(String rec) {
        int index = rec.lastIndexOf(":") + 1;
        if (index < rec.length()) {
            String res = rec.substring(index);
            if (res.equals("on")) {
                return true;
            } else if (res.equals("off")) {
                return false;
            }
        }
        return true;
    }


    public static void onResume() {
        ComAssistant.getInstance().SetDelayTime(PORT_NAME, 500);
        ComAssistant.getInstance().sendLoopData(PORT_NAME, "RES?", false);
        ComAssistant.getInstance().startSendData(PORT_NAME);
    }


    public static void onPause() {
        ComAssistant.getInstance().stopSendData(PORT_NAME);
        ComAssistant.getInstance().closeUart(PORT_NAME);
    }

    public interface DeviceListener {

        void onLightChange(int value);

        void onBodyChange(boolean hasBody);

        void onDataReceived(ComBean ComRecData);
    }
}
