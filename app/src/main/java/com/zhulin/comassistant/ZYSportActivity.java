package com.zhulin.comassistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhulin.comassistant.util.ByteUtil;
import com.zhulin.comassistant.util.StringUtil;
import com.zhulin.rk3288.ComAssistant;
import com.zhulin.rk3288.DataFunc;

import java.util.Arrays;

public class ZYSportActivity extends Activity {
    private static final String TAG = "RS485";
    private static final String PORT = "/dev/ttyS4";
//    private static final String PORT = "/dev/ttyUSB0";
    private static final int BAUD = 9600;
    private TextView tvReceive, tvSend;
    private ScrollView sReceive, sSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zy_sport);
        tvReceive = findViewById(R.id.tv_receive);
        tvSend = findViewById(R.id.tv_send);
        sReceive = findViewById(R.id.scroll_receive);
        sSend = findViewById(R.id.scroll_send);
        openPort();
        findViewById(R.id.btn_01).setOnClickListener(v -> {
            byte[] msg = {(byte) 0xAA, 0x11, 0x02, 0x01, 0x01};
            sendMsg(msg);
        });
        findViewById(R.id.btn_02).setOnClickListener(v -> {
            byte[] data = {0x02, 0x00, 0x00, 0x00, 0x01};
            byte chk = ByteUtil.getXor(data);
            byte[] msg = {(byte) 0xAA, 0x11, 0x06, 0x02, 0x00, 0x00, 0x00, 0x01, chk};
            sendMsg(msg);
        });
        findViewById(R.id.btn_03).setOnClickListener(v -> {
            byte[] data = {0x03, 0x00, 0x00, 0x00, 0x01};
            byte chk = ByteUtil.getXor(data);
            byte[] msg = {(byte) 0xAA, 0x11, 0x06, 0x03, 0x00, 0x00, 0x00, 0x01, chk};
            sendMsg(msg);
        });
        findViewById(R.id.btn_04).setOnClickListener(v -> {
            byte[] msg = {(byte) 0xAA, 0x11, 0x02, 0x04, 0x04};
            sendMsg(msg);
        });
    }



    private void openPort() {
        ComAssistant.getInstance().openUart(PORT, BAUD);
        ComAssistant.getInstance().addComPortListener(PORT, ComRecData -> {
            String info = StringUtil.bytesToHexString(ComRecData.bRec);
            String msg = "ComRecData{" + "bRec=" + info + ", sRecTime='" + ComRecData.sRecTime + '\'' + ", sComPort='" + ComRecData.sComPort + '\'' + '}';
//           if (info.length()>20) {
            Log.i(TAG, msg);
//           }
        });
    }

    public void sendMsg(String msg, boolean isHex) {
        ComAssistant.getInstance().sendMsg(PORT, msg, isHex);
    }

    public void sendMsg(byte[] msg) {
        Log.i(TAG, "写入数据：" + Arrays.toString(msg));
        Log.i(TAG, "写入数据：" + DataFunc.ByteArrToHex(msg));
        ComAssistant.getInstance().sendMsg(PORT, msg);
    }

    private void showReceive(String msg) {
        if (null == tvReceive || null == msg) return;
        String last = tvReceive.getText().toString();
        msg = last + "\n" + msg;
        String finalMsg = msg;
        runOnUiThread(() -> {
            tvReceive.setText(finalMsg);
            sReceive.fullScroll(ScrollView.FOCUS_DOWN);
        });
    }

    private void showSend(String msg) {
        if (null == tvSend || null == msg) return;
        String last = tvSend.getText().toString();
        msg = last + "\n" + msg;
        String finalMsg = msg;
        runOnUiThread(() -> {
            tvSend.setText(finalMsg);
            sSend.fullScroll(ScrollView.FOCUS_DOWN);
        });
    }
}
