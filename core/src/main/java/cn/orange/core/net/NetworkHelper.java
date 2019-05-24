package cn.orange.core.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import retrofit2.Retrofit;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class NetworkHelper {

    @SuppressWarnings("unused")
    private static final String TAG = "RetrofitManager";
    private static final String URL = "http://10.32.233.253:9988";
    private static final String KEY_NETWORK_ID = "current_network_id";
    // 偷个懒
    private static NetworkHelper sInstance;
    private Retrofit mRetrofit;
    private Retrofit mZipRetrofit;

    private NetworkHelper() {
    }

    @NonNull
    public static NetworkHelper get() {
        return sInstance;
    }

    public static void initialize(@NonNull Context context) {
        sInstance = new NetworkHelper();
        sInstance.setUp(context);
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

    private void setUp(@NonNull Context context) {
        SharedPreferences networkPref = context.getSharedPreferences("", Context.MODE_PRIVATE);
        //networkPref.getInt();
    }
}
