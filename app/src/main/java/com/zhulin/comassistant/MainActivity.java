package com.zhulin.comassistant;

import android.os.Bundle;
import android.os.Power;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvG1, tvG2, tvG3, tvG4;
    private Button btnGG1, btnGG2, btnGG3, btnGG4, btnHG1, btnHG2, btnHG3, btnHG4, btnLG1, btnLG2, btnLG3, btnLG4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvG1 = findViewById(R.id.tv_g1);
        tvG2 = findViewById(R.id.tv_g2);
        tvG3 = findViewById(R.id.tv_g3);
        tvG4 = findViewById(R.id.tv_g4);
        btnGG1 = findViewById(R.id.btn_g_g1);
        btnGG2 = findViewById(R.id.btn_g_g2);
        btnGG3 = findViewById(R.id.btn_g_g3);
        btnGG4 = findViewById(R.id.btn_g_g4);
        btnHG1 = findViewById(R.id.btn_h_g1);
        btnHG2 = findViewById(R.id.btn_h_g2);
        btnHG3 = findViewById(R.id.btn_h_g3);
        btnHG4 = findViewById(R.id.btn_h_g4);
        btnLG1 = findViewById(R.id.btn_l_g1);
        btnLG2 = findViewById(R.id.btn_l_g2);
        btnLG3 = findViewById(R.id.btn_l_g3);
        btnLG4 = findViewById(R.id.btn_l_g4);
        btnGG1.setOnClickListener(v -> {
            int value = Power.get_zysj_gpio_value(1);
            tvG1.setText(value + "");
        });
        btnGG2.setOnClickListener(v -> {
            int value = Power.get_zysj_gpio_value(2);
            tvG2.setText(value + "");
        });
        btnGG3.setOnClickListener(v -> {
            int value = Power.get_zysj_gpio_value(3);
            tvG3.setText(value + "");
        });
        btnGG4.setOnClickListener(v -> {
            int value = Power.get_zysj_gpio_value(4);
            tvG4.setText(value + "");
        });
        btnHG1.setOnClickListener(v -> Power.set_zysj_gpio_value(1, 1));
        btnHG2.setOnClickListener(v -> Power.set_zysj_gpio_value(2, 1));
        btnHG3.setOnClickListener(v -> Power.set_zysj_gpio_value(3, 1));
        btnHG4.setOnClickListener(v -> Power.set_zysj_gpio_value(4, 1));
        btnLG1.setOnClickListener(v -> Power.set_zysj_gpio_value(1, 0));
        btnLG2.setOnClickListener(v -> Power.set_zysj_gpio_value(2, 0));
        btnLG3.setOnClickListener(v -> Power.set_zysj_gpio_value(3, 0));
        btnLG4.setOnClickListener(v -> Power.set_zysj_gpio_value(4, 0));
    }
}
