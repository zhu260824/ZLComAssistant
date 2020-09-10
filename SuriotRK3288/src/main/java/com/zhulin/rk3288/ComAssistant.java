package com.zhulin.rk3288;

import java.io.IOException;
import java.util.HashMap;

public class ComAssistant {
    private static ComAssistant instance;
    private HashMap<String, SerialHelper> ComPorts;

    public static ComAssistant getInstance() {
        if (instance == null) {
            synchronized (ComAssistant.class) {
                if (instance == null) {
                    instance = new ComAssistant();
                }
            }
        }
        return instance;
    }

    public HashMap<String, SerialHelper> getComPorts() {
        if (null == ComPorts) ComPorts = new HashMap<>();
        return ComPorts;
    }

    private void addComPort(String sPort, SerialHelper comPort) {
        getComPorts().put(sPort, comPort);
    }


    private SerialHelper getComPort(String sPort) {
        return getComPorts().get(sPort);
    }


    /**
     * 打开串口
     */
    public void openUart(String sPort, int iBaudRate) {
        SerialHelper comPort = new SerialHelper(sPort, iBaudRate);
        try {
            comPort.open();
            addComPort(sPort, comPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加数据回调
     */
    public void addComPortListener(String sPort, DataReceived dataReceived) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null) {
            comPort.setDataReceived(dataReceived);
        }
    }

    /**
     * 发送msg
     */
    public void sendMsg(String sPort, String msg, boolean isHex) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null && comPort.isOpen()) {
            if (isHex) {
                comPort.sendHex(msg);
            } else {
                comPort.sendTxt(msg);
            }
        }
    }

    /**
     * 发送msg
     */
    public void sendMsg(String sPort, byte[] msg) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null && comPort.isOpen()) {
            comPort.send(msg);
        }
    }

    /**
     * 设置自动发送延时
     */
    public void SetDelayTime(String sPort, int time) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null) {
            comPort.setiDelay(time);
        }
    }

    /**
     * 设置自动发送内容
     */
    public void sendLoopData(String sPort, String msg, boolean isHex) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null && comPort.isOpen()) {
            if (isHex) {
                comPort.setHexLoopData(msg);
            } else {
                comPort.setTxtLoopData(msg);
            }
        }
    }

    /**
     * 开始自动发送
     */
    public void startSendData(String sPort) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null && comPort.isOpen()) {
            comPort.startSend();
        }
    }

    /**
     * 停止自动发送
     */
    public void stopSendData(String sPort) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null && comPort.isOpen()) {
            comPort.stopSend();
        }
    }

    /**
     * 关闭端口
     */
    public void closeUart(String sPort) {
        SerialHelper comPort = getComPort(sPort);
        if (comPort != null) {
            comPort.stopSend();
            comPort.close();
        }
    }


}
