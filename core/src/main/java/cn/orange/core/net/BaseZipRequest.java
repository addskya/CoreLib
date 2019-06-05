package cn.orange.core.net;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class BaseZipRequest extends BaseRequest {

    protected BaseZipRequest() {
        super();
    }

    @Override
    protected Retrofit getRetrofit(@NonNull String url) {
        return rxZip(url);
    }
}
