package cn.orange.core.net;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class NetworkHelper {
    @SuppressWarnings("unused")
    private static final String TAG = "NetworkHelper";

    private Retrofit mRetrofit;
    private Retrofit mZipRetrofit;

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
        String url = env.getUrl();
        mRetrofit = RetrofitConfig.getRetrofit(url);
        mZipRetrofit = RetrofitConfig.getZipRetrofit(url);
    }

    @NonNull
    Retrofit getRetrofit() {
        return mRetrofit;
    }

    Retrofit getZipRetrofit() {
        return mZipRetrofit;
    }
}
