package cn.orange.core.net;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class RetrofitManager {

    @SuppressWarnings("unused")
    private static final String TAG = "RetrofitManager";
    private static final String URL = "http://10.32.233.253:9988";
    // 偷个懒
    private static RetrofitManager sInstance = new RetrofitManager();
    private Retrofit mRetrofit;
    private Retrofit mZipRetrofit;

    private RetrofitManager() {
        //Service now = ConfigHelper.get().getService();
        //onEnvironmentChanged(now);
    }

    @NonNull
    public static RetrofitManager get() {
        return sInstance;
    }

    public void onEnvironmentChanged() {
        String url = URL;
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
