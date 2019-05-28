package cn.orange.core;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Orange on 2019/5/16
 * Email:addskya@163.com
 */
public class CoreApp extends Application {
    private static final String TAG = "CoreApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        enableStrictMode();
    }

    /**
     * Enable StrictMode in DEBUG
     */
    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "initStrictMode");
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
