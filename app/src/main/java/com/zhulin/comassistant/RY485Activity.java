package com.zhulin.comassistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhulin.comassistant.util.StringUtil;
import com.zhulin.rk3288.ComAssistant;

import java.util.regex.Pattern;

public class RY485Activity extends Activity {
    private static final String TAG = "RS485";
    private static final String PORT = "/dev/ttyS4";
    private static final int BAUD = 9600;
    private static final String SOI = "AA";
    private static final String POST_HEX_RULE="^[A-Fa-f0-9]{32}$";
    private TextView tvReceive, tvSend;
    private ScrollView sReceive, sSend;
    private String address;
    private boolean analysisAddress = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ry_rs485);
        tvReceive = findViewById(R.id.tv_receive);
        tvSend = findViewById(R.id.tv_send);
        sReceive = findViewById(R.id.scroll_receive);
        sSend = findViewById(R.id.scroll_send);
        openPort();
        findViewById(R.id.btn_01).setOnClickListener(v -> {
            String cmd = "AA000101010000000000000000000003";
            analysisAddress = true;
            sendMsg(cmd, true);
//            sendMsg(StringUtil.hexStringToByteArray(cmd));
        });
        findViewById(R.id.btn_02).setOnClickListener(v -> {
            String cmd = "00010202xx010000000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_03).setOnClickListener(v -> {
            String cmd = "00010200xx08FF00000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_04).setOnClickListener(v -> {
            String cmd = "00010200xx080000000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_05).setOnClickListener(v -> {
            String cmd = "00010200xx080001000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_06).setOnClickListener(v -> {
            String cmd = "00010200xx080002000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_07).setOnClickListener(v -> {
            String cmd = "00010200xx080003000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_08).setOnClickListener(v -> {
            String cmd = "00010200xx080004000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_09).setOnClickListener(v -> {
            String cmd = "00010200xx080005000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_10).setOnClickListener(v -> {
            String cmd = "00010200xx080201000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_11).setOnClickListener(v -> {
            String cmd = "00010200xx080200000000000000";
            sendRY485Msg(cmd);
        });
        findViewById(R.id.btn_12).setOnClickListener(v -> {
            String cmd = "00010105xx010E00000000000000";
            sendRY485Msg(cmd);
        });
    }


    private void sendRY485Msg(String cmd) {
        cmd = cmd.replace("xx", address);
        String check = StringUtil.makeChecksum(cmd);
        cmd = SOI + cmd + check;
        showSend(cmd);
        sendMsg(cmd, true);
    }

    private void openPort() {
        Pattern hexRule = Pattern.compile(POST_HEX_RULE);
        ComAssistant.getInstance().openUart(PORT, BAUD);
        ComAssistant.getInstance().addComPortListener(PORT, ComRecData -> {
            String info = StringUtil.bytesToHexString(ComRecData.bRec);
            String msg = "ComRecData{" + "bRec=" + info + ", sRecTime='" + ComRecData.sRecTime + '\'' + ", sComPort='" + ComRecData.sComPort + '\'' + '}';
//           if (info.length()>20) {
               Log.i(TAG, msg);
//           }
            if (!hexRule.matcher(info).matches() || !info.startsWith("AA")){
                return;
            }
            showReceive(info);
//            Log.i(TAG, msg);
            if (analysisAddress) {
                address = info.substring(4, 6);
                analysisAddress = false;
            }
        });
    }

    public void sendMsg(String msg, boolean isHex) {
        ComAssistant.getInstance().sendMsg(PORT, msg, isHex);
    }

    public void sendMsg(byte[] msg) {
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
