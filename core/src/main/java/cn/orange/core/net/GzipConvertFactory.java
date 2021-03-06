package cn.orange.core.net;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
class GzipConvertFactory extends Converter.Factory {

    private GzipConvertFactory() {
    }

    static Converter.Factory create() {
        return new GzipConvertFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new GzipResponseBodyConverter();
    }


    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        return new GzipRequestBodyConverter<>();
    }

}
