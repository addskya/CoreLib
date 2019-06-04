package cn.orange.core.net;

import android.support.annotation.NonNull;

import java.io.IOException;

import cn.orange.core.util.GzipUtil;
import cn.orange.core.util.LogUtil;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
class GzipResponseBodyConverter implements Converter<ResponseBody, String> {
    private static final String TAG = "Gzip";

    GzipResponseBodyConverter() {
    }

    @Override
    public String convert(@NonNull ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String content = GzipUtil.uncompress(bufferedSource.readUtf8(), "UTF-8");
        bufferedSource.close();
        LogUtil.i(TAG, content);
        return content;
    }
}
