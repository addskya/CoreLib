package cn.orange.core.filter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class BaseFilter<D> {

    private final BaseFilter<D> mBase;

    protected BaseFilter() {
        this(null);
    }

    protected BaseFilter(@Nullable BaseFilter<D> base) {
        mBase = base;
    }

    protected static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    protected static boolean contains(@Nullable String longString,
                                      @Nullable String shortString) {
        if (TextUtils.isEmpty(shortString) || shortString == null) {
            return true;
        }

        return longString != null && longString.contains(shortString);
    }

    public final boolean isAccept(@Nullable D data) {
        return acceptBase(data) && accept(data);
    }

    private boolean acceptBase(@Nullable D data) {
        return mBase == null || mBase.accept(data);
    }

    protected abstract boolean accept(@Nullable D data);
}
