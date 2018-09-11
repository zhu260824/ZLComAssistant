package com.zhulin.rk3288;

public interface DeviceListener {

    void onLightChange(int value);

    void onBodyChange(boolean hasBody);

    void onDataReceived(ComBean ComRecData);
}
