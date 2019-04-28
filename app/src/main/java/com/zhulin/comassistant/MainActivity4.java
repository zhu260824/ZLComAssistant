package com.zhulin.comassistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends Activity {
    private TextView tvMsg, tvRes, tvNfc;
    private Button btnW, btnG, btnR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        btnR = (Button) findViewById(R.id.btn_r);
        btnG = (Button) findViewById(R.id.btn_g);
        btnW = (Button) findViewById(R.id.btn_w);
        tvRes = (TextView) findViewById(R.id.tv_res);
        tvNfc = (TextView) findViewById(R.id.tv_nfc);
        SuriotRFIDSerial.init();
        SuriotRFIDSerial.setDeviceListener(new SuriotRFIDSerial.DeviceListener() {
            @Override
            public void onCardInfo(String info) {
                runOnUiThread(() -> tvNfc.setText("NFC卡号：" + info));
            }

            @Override
            public void onLightChange(int value) {
                runOnUiThread(() -> tvRes.setText("光强：" + value));
            }

            @Override
            public void onInfo(String info) {
                showMsg(info);
            }

        });
        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuriotRFIDSerial.sendMsg("WGR=0,100,0", false);
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuriotRFIDSerial.sendMsg("WGR=0,0,100", false);
            }
        });
        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuriotRFIDSerial.sendMsg("WGR=100,0,0", false);
            }
        });
    }

    public void showMsg(String msg) {
        if (null == tvMsg || null == msg) return;
        String last = tvMsg.getText().toString();
        msg = last + "\n" + msg;
        String finalMsg = msg;
        runOnUiThread(() -> tvMsg.setText(finalMsg));
    }
}
