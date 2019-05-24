package cn.orange.core.util;

import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class CollectionUtil {

    public static int sizeOf(@Nullable Collection<?> obj) {
        return obj != null ? obj.size() : 0;
    }

    public static boolean isEmpty(@Nullable Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Collection<?> obj) {
        return !isEmpty(obj);
    }
}
