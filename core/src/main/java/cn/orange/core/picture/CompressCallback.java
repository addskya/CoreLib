package cn.orange.core.picture;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Orange on 18-12-14.
 * Email:addskya@163.com
 * <p>
 * 图片处理回调
 */
public interface CompressCallback<T> {
    void onCallback(@NonNull List<T> pictureUri);
}
