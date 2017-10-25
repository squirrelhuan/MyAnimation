package com.example.huan.myanimation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.huan.myanimation.R;
import com.example.huan.myanimation.utils.AnimationUtil;
import com.example.huan.myanimation.view.AnimationViewGroup;

public class Main2Activity extends AppCompatActivity {

    private AnimationViewGroup avg_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        avg_test = (AnimationViewGroup) findViewById(R.id.avg_test);
        Bundle bundle = getIntent().getExtras();
        avg_test.startAnimation(bundle.getInt("AnimationType"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        avg_test.canelAnimation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_DPAD_DOWN_LEFT) {
                finish();
            }
        return super.onKeyDown(keyCode, event);
    }
}
