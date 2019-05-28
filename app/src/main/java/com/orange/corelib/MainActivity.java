package com.orange.corelib;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.orange.core.LocationActivity;
import cn.orange.core.util.LogUtil;

public class MainActivity extends LocationActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.i(TAG, "onCreate");
    }

    public void onClick(View v) {
        requestLocate();
    }

    @Override
    protected void onLocationChanged(Location location) {
        LogUtil.i(TAG, "onLocationChanged:" + location);
    }
}
