package com.orange.corelib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.orange.core.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.i(TAG, "onCreate");
    }
}
