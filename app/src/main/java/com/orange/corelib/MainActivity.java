package com.orange.corelib;

import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.orange.corelib.databinding.ActivityMainBinding;

import cn.orange.core.LocationActivity;
import cn.orange.core.ui.SpinnerItem;
import cn.orange.core.util.LogUtil;

public class MainActivity extends LocationActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main);
        binding.setHost(this);
        LogUtil.i(TAG, "onCreate");

        CountryAdapter adapter = new CountryAdapter(getLayoutInflater(), this);
        for (int i = 0; i <= 10; i++) {
            adapter.add(new Country("Name:" + i));
        }

        SpinnerItem item = binding.spinnerView;
        item.setAdapter(adapter, (item1) -> LogUtil.i(TAG, "item:" + item1.getName()));
    }

    public void onClick(View v) {
        requestLocate();
    }

    public void onClick() {
        LogUtil.i(TAG, "onClick");
    }

    public void onClick(Country data) {
        LogUtil.i(TAG, "click country:" + data.getName());
    }

    @Override
    protected void onLocationChanged(Location location) {
        LogUtil.i(TAG, "onLocationChanged:" + location);
    }

    public void onItemSelected() {

    }
}
