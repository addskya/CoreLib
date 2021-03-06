package cn.orange.core.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
abstract class BaseInterceptor implements Interceptor {

    protected BaseInterceptor() {

    }

    /**
     * 添加请求头参数
     *
     * @param headers the request headers
     */
    abstract void addHeaders(@NonNull Map<String, String> headers);

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Map<String, String> headers = new HashMap<>(1);
        addHeaders(headers);
        // 写入请求头
        if (!headers.isEmpty()) {
            Request.Builder builder = request.newBuilder();
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                if (!TextUtils.isEmpty(value)) {
                    builder.header(key, value);
                }
            }
            request = builder.build();
        }
        return chain.proceed(request);
    }
}
