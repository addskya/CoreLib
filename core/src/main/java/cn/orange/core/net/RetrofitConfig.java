package cn.orange.core.net;

import android.support.annotation.NonNull;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
class RetrofitConfig {
    @SuppressWarnings("unused")
    private static final String TAG = "RetrofitConfig";
    @SuppressWarnings("unused")
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final int TIME_OUT = 20;
    private static final int TIME_OUT_CONNECT = TIME_OUT;
    private static final int TIME_OUT_READ = TIME_OUT;
    private static final int TIME_OUT_WRITE = TIME_OUT;

    private RetrofitConfig() {
    }

    static Retrofit getRetrofit(@NonNull String baseUrl) {
        return with(base(baseUrl)).build();
    }

    static Retrofit getZipRetrofit(@NonNull String baseUrl) {
        return with(base(baseUrl)
                .addConverterFactory(GzipConvertFactory.create()))
                .build();
    }

    private static Retrofit.Builder base(@NonNull String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient());
    }

    private static Retrofit.Builder with(@NonNull Retrofit.Builder builder) {
        return builder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(new LogInterceptor())
                .build();
    }
}
