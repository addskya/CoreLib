package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import cn.orange.core.BaseAdapter;
import cn.orange.core.R;

/**
 * Created by Orange on 2019/5/31
 * Email:addskya@163.com
 * 选取图片
 */
public final class PictureItem extends ViewItem {
    protected final RecyclerView mRecyclerView;

    public PictureItem(@NonNull Context context) {
        this(context, null);
    }

    public PictureItem(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureItem(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.layout.picture_item);
    }

    public PictureItem(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr,
                       @LayoutRes int layout) {
        super(context, attrs, defStyleAttr, layout);
        mRecyclerView = findViewById(R.id.list);
    }

    /*public final <D, V> void setAdapter(BaseAdapter<D, V> adapter) {
        mRecyclerView.setAdapter(adapter);
    }*/
}
