package com.orange.corelib;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.orange.corelib.databinding.ActivityMainBinding;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import cn.orange.core.ActionBarActivity;
import cn.orange.core.net.BaseRequest;
import cn.orange.core.net.BaseZipRequest;
import cn.orange.core.net.NetworkHelper;
import cn.orange.core.net.Response;
import cn.orange.core.util.LogUtil;
import io.reactivex.Observable;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main);
        binding.setHost(this);
        LogUtil.i(TAG, "onCreate");
        CountryAdapter adapter = new CountryAdapter(getLayoutInflater(), this);
        for(int i = 0 ;i <= 50; i++) {
            adapter.add(new Country("Name:" + i));
        }
        binding.list.setAdapter(adapter);
    }

    public void onClick(View v) {

    }


}
