package cn.orange.core;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ChengHe.Zhang on 2019/5/16
 * Email:chengheZhang@kaifa.cn
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
}
