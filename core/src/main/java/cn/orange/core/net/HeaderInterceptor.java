package cn.orange.core.net;

import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * 普通请求头信息
 */
class HeaderInterceptor extends BaseInterceptor {

    HeaderInterceptor() {
    }

    @Override
    void addHeaders(@NonNull Map<String, String> headers) {
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("User-Agent", "Android");
        // 添加以下Header,方便以后查日志
        headers.put("x_phone_manufacturer", Build.MANUFACTURER);
        headers.put("x_phone_device", Build.DEVICE);
        headers.put("x_phone_product", Build.PRODUCT);
    }
}
