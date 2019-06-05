package com.orange.corelib;

import android.os.Bundle;
import android.view.View;

import cn.orange.core.ActionBarActivity;
import cn.orange.core.util.LogUtil;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {

    }


}
