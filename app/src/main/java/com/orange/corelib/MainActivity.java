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
        NetworkHelper.initialize(() -> "http://10.32.233.253:9988/");
    }

    public void onClick(View v) {
        new ZipRequest().searchWorkOrder("4045", null).subscribe();
    }

    private final class ZipRequest extends BaseRequest {

        protected ZipRequest() {
            super();
        }

        Observable<Response<List<WorkOrder>>> searchWorkOrder(
                @NonNull CharSequence userId,
                @NonNull CharSequence accountNo) {
            Map<String, Object> params = newMap();
            params.put("userId", valueOf(userId));
            if (!TextUtils.isEmpty(accountNo)) {
                params.put("pointNo", valueOf(accountNo));
            }
            return get("/api/app/ivy/new/workorder", params,
                    new TypeToken<List<WorkOrder>>() {
                    }.getType());
        }

        @Override
        protected Gson buildGson() {
            return new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerAdapter())
                    .registerTypeAdapter(int.class, new IntegerAdapter())
                    .create();
        }
    }

    private static final class WorkOrder {
        private String workId;
        private int workType;
        private int communicationType;
        private String currentDeviceNo;
        private String currentRatio;
        private String customerName;
        private String deviceLocation;
        private String deviceSubType;
        private String deviceType;
        private String espId;
        private String espNo;
        private String feederNo;
        private String finishTime;
        private String installSite;
        private String oldCustomerId;
        private String orderSource;
        private String phoneNo;
        private String planDate;
        private String pointId;
        private String pointNo;
        private String poleNo;
        private String regionId;
        private String regionName;
        private String remark;
        private String reservedDeviceNo;
        private String reservedStatus;
        private String teamId;
        private String trPower;
        private Integer status;
    }

    private static class IntegerAdapter implements JsonSerializer<Integer> ,JsonDeserializer<Integer> {
        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }

        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  {
            try {
                String text = json.getAsString();
                return Integer.parseInt(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}
