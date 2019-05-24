package cn.orange.core.util;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class GzipUtil {
    private static final String TAG = "GzipUtil";

    /**
     * 将字符串进行gzip压缩
     *
     * @param data
     * @param encoding
     * @return
     */
    public static String compress(String data, String encoding) {
        if (data == null || data.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(data.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(out.toByteArray(), Base64.NO_PADDING);
    }

    public static String uncompress(String data, String encoding) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        byte[] decode = Base64.decode(data, Base64.NO_PADDING);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(decode);
        GZIPInputStream gzipStream = null;
        try {
            gzipStream = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzipStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            LogUtil.e(TAG, "e = " + e.getMessage());
        } finally {
            try {
                out.close();
                if (gzipStream != null) {
                    gzipStream.close();
                }
            } catch (IOException e) {
                LogUtil.e(TAG, "e = " + e.getMessage());
            }

        }
        return new String(out.toByteArray(), Charset.forName(encoding));
    }
}
