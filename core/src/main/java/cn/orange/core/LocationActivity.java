package cn.orange.core;

import android.Manifest;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

import cn.orange.core.util.CollectionUtil;
import cn.orange.core.util.LogUtil;

/**
 * Created by Orange on 2019/5/28
 * Email:addskya@163.com
 */
@SuppressWarnings("all")
public abstract class LocationActivity extends ActionBarActivity {
    private static final String TAG = "LocationActivity";
    private static final int REQUEST_CODE_LOCATION = 0x70;

    // 定位是否成功Notify
    private boolean isLocationPresent;
    private boolean isLocationLocked;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    /**
     * 发起定位请求,定位结果在{@link #onLocationChanged(Location)}
     */
    protected final void requestLocate() {
        LogUtil.i(TAG, "requestLocate");
        if (!isSupportLocation()) {
            LogUtil.w(TAG, "NOT Support location");
            return;
        }

        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        requestPermissions(permissions, this::locate);
    }

    /**
     * 定位动作
     */
    private void locate() {
        if (!isSupportLocation()) {
            return;
        }

        // 如果用户还没有启用定位服务,则提示打开
        if (!mLocationManager.isLocationEnabled()) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, REQUEST_CODE_LOCATION);
            return;
        }

        String provider = getBestProvider(mLocationManager, getCriteria(), true);
        if (!TextUtils.isEmpty(provider)) {
            try {
                mLocationManager.requestLocationUpdates(provider, 5 * 1000, 10, mLocationListener);
            } catch (SecurityException e) {
                LogUtil.e(TAG, "locate", e);
                e.printStackTrace();
            }
        }
    }

    @Nullable
    private static String getBestProvider(@NonNull LocationManager locationManager,
                                          @NonNull Criteria criteria,
                                          boolean enableOnly) {
        // 精准Provider
        String bestProvider = locationManager.getBestProvider(criteria, enableOnly);
        if (!TextUtils.isEmpty(bestProvider)) {
            return bestProvider;
        }

        // 模糊Provider
        List<String> providers = locationManager.getProviders(criteria, enableOnly);
        String enabledProvider = getEnabledProvider(locationManager, providers);
        if (!TextUtils.isEmpty(enabledProvider)) {
            return enabledProvider;
        }

        // 能用就行
        providers = locationManager.getProviders(enableOnly);
        enabledProvider = getEnabledProvider(locationManager, providers);
        if (!TextUtils.isEmpty(enabledProvider)) {
            return enabledProvider;
        }
        // 没有可用的!!!!
        return null;
    }

    @Nullable
    private static String getEnabledProvider(@NonNull LocationManager locationManager,
                                             @Nullable List<String> providers) {
        if (CollectionUtil.isEmpty(providers)) {
            return null;
        }
        for (String provider : providers) {
            if (locationManager.isProviderEnabled(provider)) {
                return provider;
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION: {
                // 如果用户启用了定位服务,则重走定位流程即可,但如果用户不启用呢?
                if (resultCode == RESULT_OK) {
                    requestLocate();
                }
                break;
            }
            default: {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 如果定位锁定了,有两种情况需要处理:
            // 1. 如果定位还未成功后,则继续定位
            // 2. 如果定位已经成功了,则不再定位
            if (isLocationLocked && isLocationPresent) {
                stopLocate();
            } else {
                isLocationPresent = true;
                LocationActivity.this.onLocationChanged(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            requestLocate();
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    protected final void stopLocate() {
        if (isSupportLocation()) {
            LogUtil.w(TAG, "NOT Support location");
            return;
        }

        try {
            mLocationManager.removeUpdates(mLocationListener);
        } catch (Throwable error) {
            LogUtil.e(TAG, "stopLocate", error);
        }
    }

    /**
     * 锁定定位,
     * 如果当前还未获取到GPS位置,会继续定位
     * 如果当前已经获取到GPS位置,位置不再更新
     */
    protected final void lockLocation() {
        if (isSupportLocation()) {
            LogUtil.w(TAG, "NOT Support location");
            return;
        }
        isLocationLocked = true;
    }


    private boolean isSupportLocation() {
        return mLocationManager != null && isLocateEnable();
    }

    protected abstract void onLocationChanged(Location location);

    /**
     * 是否需要定位,目前安装类工单需要定位,但移除类工单不需要
     *
     * @return true 如果需要提供定位
     */
    protected boolean isLocateEnable() {
        return true;
    }
}
