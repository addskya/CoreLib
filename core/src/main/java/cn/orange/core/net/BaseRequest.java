package cn.orange.core.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static cn.orange.core.net.Response.getResponseCode;
import static cn.orange.core.net.Response.getResponseMsg;
import static cn.orange.core.net.Response.getResponseValue;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
@SuppressWarnings("all")
public abstract class BaseRequest {
    private static final String TAG = "BaseRequest";
    private final Gson mGson;
    private final Api mApi;

    protected BaseRequest() {
        mGson = buildGson();

        NetworkHelper manager = NetworkHelper.get();
        mApi = getRetrofit(manager.getUrl()).create(Api.class);
    }

    /**
     * 将参数转换成字符串数据
     *
     * @param value params
     * @return the String params
     */
    protected static String valueOf(@Nullable Object value) {
        return String.valueOf(value);
    }

    /**
     * 产生一个新的Map<String,Object> 对象
     *
     * @return Map<String.Object> 对象
     */
    protected static Map<String, Object> newMap() {
        return new HashMap<>(1);
    }

    /**
     * 创建Retrofit实例
     *
     * @param url 服务器url
     * @return Retrofit实例
     */
    protected Retrofit getRetrofit(@NonNull String url) {
        return rx(url);
    }

    /**
     * 创建Gson解析器
     *
     * @return Gson解析器
     */
    protected Gson buildGson() {
        return new Gson();
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api get_version
     * @return Observable包装的响应
     */
    protected <T> Observable<Response<T>> get(@NonNull final String api,
                                              @NonNull final Type responseType) {
        Log.d(TAG, "call api : " + api);
        return get(api, newMap(), responseType);
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api get_version
     * @return Observable包装的响应
     */
    protected Observable<Response<?>> get(@NonNull final String api,
                                          @Nullable Map<String, Object> params) {
        Log.d(TAG, "call api : " + api);
        return mApi.get(api, params)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    return new Response<>(code, message);
                });
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api          get_version
     * @param responseType 返回数据的协议Entry类
     * @return Observable包装的响应
     */
    protected <T> Observable<Response<T>> get(@NonNull final String api,
                                              @Nullable Map<String, Object> params,
                                              @NonNull final Type responseType) {
        Log.d(TAG, "call api : " + api);
        return mApi.get(api, params)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    T value = getResponseValue(mGson, resp, responseType);
                    return new Response<>(code, message, value);
                });
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     *
     * @param api    api
     * @param params 参数
     * @return Call
     */
    protected Call<String> call(@NonNull final String api,
                                @Nullable Map<String, Object> params) {
        Log.d(TAG, "call api : " + api);
        return mApi.getCall(api, params);
    }

    /**
     * 请求网络接口
     *
     * @param api         api接口
     * @param params      请求参数
     * @param resonseType 泛型数据类型
     * @param <T>         泛型
     * @return 请求泛型结果
     * @throws IOException
     * @throws RuntimeException
     */
    protected <T> Response<T> call(@NonNull final String api,
                                   @Nullable Map<String, Object> params,
                                   @NonNull final Type resonseType)
            throws IOException, RuntimeException {
        Log.d(TAG, "call api : " + api);
        Call<String> call = mApi.getCall(api, params);
        String body = call.execute().body();
        int code = getResponseCode(body);
        String message = getResponseMsg(body);
        T value = getResponseValue(mGson, body, resonseType);
        return new Response<>(code, message, value);
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: http://xx.com/passport/send-sms
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api  send-sms
     * @param para 请求指定接口时,需要的参数
     * @return Observable包装的响应
     */
    protected Observable<Response<?>> post(@NonNull final String api,
                                           @Nullable final Map<String, Object> para) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.post(api, para)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    return new Response<>(code, message);
                });
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: http://xx.com/passport/send-sms
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api  send-sms
     * @param body 请求指定接口时,需要的参数
     * @return Observable包装的响应
     */
    protected Observable<Response<?>> post(@NonNull final String api,
                                           @NonNull final RequestBody body) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + body);
        return mApi.post(api, body)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    return new Response<>(code, message);
                });
    }

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api          get_version
     * @param para         请求指定接口时,需要的参数
     * @param responseType 返回数据的协议Entry类
     * @return Observable包装的响应
     */
    protected <T> Observable<Response<T>> post(@NonNull final String api,
                                               @Nullable final Map<String, Object> para,
                                               @NonNull final Type responseType) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.post(api, para)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    T value = getResponseValue(mGson, resp, responseType);
                    return new Response<>(code, message, value);
                });
    }

    protected <T> Observable<Response<T>> upload(@NonNull final String api,
                                                 @NonNull final Map<String, RequestBody> para) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.upload(api, para)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    return new Response<>(code, message);
                });
    }

    protected static Retrofit call(@NonNull String url) {
        return scalar(base(url)).build();
    }

    protected static Retrofit rx(@NonNull String url) {
        return rx(gson(scalar(base(url)))).build();
    }

    protected static Retrofit rxZip(@NonNull String url) {
        return rx(gson(scalar(zip(base(url))))).build();
    }

    protected static Retrofit.Builder base(@NonNull String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient());
    }

    protected static Retrofit.Builder scalar(@NonNull Retrofit.Builder builder) {
        return builder.addConverterFactory(ScalarsConverterFactory.create());
    }

    protected static Retrofit.Builder gson(@NonNull Retrofit.Builder builder) {
        return builder.addConverterFactory(GsonConverterFactory.create());
    }

    protected static Retrofit.Builder rx(@NonNull Retrofit.Builder builder) {
        return builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    protected static Retrofit.Builder zip(@NonNull Retrofit.Builder builder) {
        return builder.addConverterFactory(GzipConvertFactory.create());
    }

    private static final int TIME_OUT = 20;
    private static final int TIME_OUT_CONNECT = TIME_OUT;
    private static final int TIME_OUT_READ = TIME_OUT;
    private static final int TIME_OUT_WRITE = TIME_OUT;

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
