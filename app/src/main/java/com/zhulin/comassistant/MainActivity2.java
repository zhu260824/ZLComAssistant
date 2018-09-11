package com.zhulin.comassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhulin.rk3288.ComBean;
import com.zhulin.rk3288.DeviceListener;
import com.zhulin.rk3288.SuriotDevice;


public class MainActivity2 extends AppCompatActivity {
    private EditText etG, etR, etW;
    private Button btnG, btnR, btnW, btnRE, btnSW;
    private TextView tvR, tvW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etG = findViewById(R.id.et_g);
        etR = findViewById(R.id.et_r);
        etW = findViewById(R.id.et_w);
        btnG = findViewById(R.id.btn_g);
        btnR = findViewById(R.id.btn_r);
        btnW = findViewById(R.id.btn_w);
        tvR = findViewById(R.id.tv_res);
        tvW = findViewById(R.id.tv_swt);
        btnRE = findViewById(R.id.btn_res);
        btnSW = findViewById(R.id.btn_swt);
        btnG.setOnClickListener(v -> {
            String value = etG.getText().toString();
            SuriotDevice.sendMsg("WGR=," + value + ",", false);
        });
        btnR.setOnClickListener(v -> {
            String value = etR.getText().toString();
            SuriotDevice.sendMsg( "WGR=,," + value, false);
        });
        btnW.setOnClickListener(v -> {
            String value = etW.getText().toString();
            SuriotDevice.sendMsg("WGR=" + value + ",,", false);
        });
        btnRE.setOnClickListener(v -> {
            SuriotDevice.sendMsg( "RES?", false);
        });
        btnSW.setOnClickListener(v -> {
            SuriotDevice.sendMsg( "SWT?", false);
        });
        SuriotDevice.init();
    }

    private void showMsg(String msg, boolean isRES) {
        runOnUiThread(() -> {
            if (isRES) {
                tvR.setText(msg);
            } else {
                tvW.setText(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SuriotDevice.onResume();
        SuriotDevice.addDeviceListener(new DeviceListener() {
            @Override
            public void onLightChange(int value) {
                showMsg(value+"",true);
            }

            @Override
            public void onBodyChange(boolean hasBody) {
                showMsg(hasBody+"",false);
            }

            @Override
            public void onDataReceived(ComBean ComRecData) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SuriotDevice.onPause();
    }
}
