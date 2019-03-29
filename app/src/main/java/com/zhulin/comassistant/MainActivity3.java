package com.zhulin.comassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhulin.rk3288.ComAssistant;

public class MainActivity3 extends AppCompatActivity {
    private TextView tvMsg;
    private EditText etPort, etBaud;
    private Button btnOpen;
    private String lastPort = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        tvMsg = findViewById(R.id.tv_msg);
        etPort = findViewById(R.id.et_port);
        etBaud = findViewById(R.id.et_baud);
        btnOpen = findViewById(R.id.btn_open);
        btnOpen.setOnClickListener(v -> openPort());
    }

    private void showMsg(String msg) {
        if (null == tvMsg || null == msg) return;
        String last = tvMsg.getText().toString();
        msg = last + "\n" + msg;
        String finalMsg = msg;
        runOnUiThread(() -> tvMsg.setText(finalMsg));
    }

    private void openPort() {
        String port = etPort.getText().toString();
        String bauds = etBaud.getText().toString();
        int iBaudRate = Integer.valueOf(bauds);
        if (null != lastPort) {
            ComAssistant.getInstance().closeUart(lastPort);
        }
        ComAssistant.getInstance().openUart(port, iBaudRate);
        ComAssistant.getInstance().addComPortListener(port, ComRecData -> {
            String cardInfo = new String(ComRecData.bRec);
            String msg = "ComRecData{" + "bRec=" + cardInfo + ", sRecTime='" + ComRecData.sRecTime + '\'' + ", sComPort='" + ComRecData.sComPort + '\'' + '}';
            showMsg(msg);
        });
        lastPort = port;
    }


    private void conve() {
        String nRcvString;
        StringBuffer tStringBuf = new StringBuffer();
        byte[] tBytes = new byte[]{0x31, 0x32, 0x33};   //实际上是ascii码字符串"123"
        char[] tChars = new char[tBytes.length];
        for (int i = 0; i < tBytes.length; i++) {
            tChars[i] = (char) tBytes[i];
        }
        tStringBuf.append(tChars);
        nRcvString = tStringBuf.toString();
    }
}
