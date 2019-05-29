package cn.orange.core.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static cn.orange.core.net.Response.getResponseCode;
import static cn.orange.core.net.Response.getResponseMsg;
import static cn.orange.core.net.Response.getResponseValue;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class BaseRequest {

    private static final String TAG = "BaseRequest";
    private final Gson mGson;
    private final Api mApi;

    protected BaseRequest() {
        mGson = new Gson();

        NetworkHelper manager = NetworkHelper.get();
        mApi = getClientRetrofit(manager).create(Api.class);
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

    protected Retrofit getClientRetrofit(@NonNull NetworkHelper manager) {
        return manager.getRetrofit();
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
     * 如: http://xx.com/passport/send-sms
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api  send-sms
     * @param para 请求指定接口时,需要的参数
     * @return Observable包装的响应
     */
    @SuppressWarnings("unused")
    protected Observable<Response<?>> post(@NonNull final String api,
                                           @Nullable final Map<String, Object> para) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.post(api, para)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    //handleResponseCode(code);
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
    @SuppressWarnings("unused")
    protected Observable<Response<?>> post(@NonNull final String api,
                                           @NonNull final RequestBody body) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + body);
        return mApi.post(api, body)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    //handleResponseCode(code);
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
    @SuppressWarnings("unused")
    protected <T> Observable<Response<T>> post(@NonNull final String api,
                                               @Nullable final Map<String, Object> para,
                                               @NonNull final Type responseType) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.post(api, para)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    T value = getResponseValue(mGson, resp, responseType);
                    //handleResponseCode(code);
                    return new Response<>(code, message, value);
                });
    }

    @SuppressWarnings("unused")
    protected <T> Observable<Response<T>> upload(@NonNull final String api,
                                                 @NonNull final Map<String, RequestBody> para) {
        Log.d(TAG, "call api : " + api + "\n" + "parameters : " + para);
        return mApi.upload(api, para)
                .map(resp -> {
                    int code = getResponseCode(resp);
                    String message = getResponseMsg(resp);
                    //handleResponseCode(code);
                    return new Response<>(code, message);
                });
    }
}
