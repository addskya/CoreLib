package cn.orange.core.net;

import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import cn.orange.core.util.LogUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
class GzipGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final String TAG = "Gzip";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(@NonNull T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, toJSONBytes(value));
    }

    private byte[] toJSONBytes(T object) throws IOException {
        LogUtil.i(TAG, "value:" + object);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        baos.close();
        return baos.toByteArray();
    }
}
