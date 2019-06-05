package cn.orange.core.net;

import android.support.annotation.NonNull;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 *
 * 只用来记录服务器地址
 */
@SuppressWarnings("unused")
public class NetworkHelper {
    @SuppressWarnings("unused")
    private static final String TAG = "NetworkHelper";

    private String mServerUrl;

    // 偷个懒
    private static NetworkHelper sInstance;

    private NetworkHelper() {
    }

    @NonNull
    public static NetworkHelper get() {
        return sInstance;
    }

    public static void initialize(@NonNull BaseEnvironment env) {
        if (sInstance == null) {
            sInstance = new NetworkHelper();
        }
        sInstance.createRetrofit(env);
    }

    public void setEnvironment(@NonNull BaseEnvironment env) {
        createRetrofit(env);
    }

    private void createRetrofit(@NonNull BaseEnvironment env) {
        mServerUrl = env.getUrl();
    }

    String getUrl() {
        return mServerUrl;
    }
}
