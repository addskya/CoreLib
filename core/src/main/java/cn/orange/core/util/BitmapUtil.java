package cn.orange.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Orange on 18-12-06.
 * Email:addskya@163.com
 */
public class BitmapUtil {

    /**
     * 将Bitmap图片转换为Base64编码
     *
     * @param bitmap Bitmap图片
     * @return Base64编码
     */
    public static String bitmapToBase64(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * 将Base64编码转换为Bitmap对象
     *
     * @param base64Code Base64编码
     * @return Bitmap对象
     */
    public static Bitmap base64CodeToBitmap(@NonNull String base64Code) {
        byte[] data = Base64.decode(base64Code, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    private static Bitmap getCacheBitmap(String url) {
        File localFile = null;
        if (!TextUtils.isEmpty(url)) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                    .getEncodedCacheKey(ImageRequest.fromUri(url), null);
            if (ImagePipelineFactory.getInstance()
                    .getMainFileCache()
                    .hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance()
                        .getMainFileCache()
                        .getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance()
                    .getSmallImageFileCache()
                    .hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance()
                        .getSmallImageFileCache()
                        .getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }

        if (localFile != null) {
            return BitmapFactory.decodeFile(localFile.getPath());
        }
        return null;
    }

    /**
     * 请求指定路径图片
     *
     * @param context app context
     * @param uri     指定路径
     * @return 请求指定路径图片Or null
     */
    @Nullable
    public static Bitmap requireBitmap(@NonNull Context context,
                                       @NonNull String uri) {
        if (TextUtils.isEmpty(uri)) {
            return null;
        }

        uri = uri.toLowerCase();
        if (uri.startsWith("content://") ||
                uri.startsWith("file://")) {
            InputStream is = null;
            try {
                is = context.getContentResolver()
                        .openInputStream(Uri.parse(uri));
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException | SecurityException e) {
                e.printStackTrace();
            } finally {
                IOUtil.closeQuietly(is);
            }
            return null;
        } else {
            return getCacheBitmap(uri);
        }
    }
}
