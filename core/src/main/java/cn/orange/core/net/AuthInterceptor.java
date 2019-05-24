package cn.orange.core.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
class AuthInterceptor extends BaseInterceptor {

    @SuppressWarnings("unused")
    private static final String TAG = "AuthInterceptor";


    AuthInterceptor() {

    }

    @Override
    void addHeaders(@NonNull Map<String, String> headers) {
        String accessToken = getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            headers.put("access-token", accessToken);
        }
    }

    private String getAccessToken() {
        return null;
    }
}
