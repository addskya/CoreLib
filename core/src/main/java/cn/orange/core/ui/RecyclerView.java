package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.util.AttributeSet;

import cn.orange.core.R;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * 支持水平分隔线的RecyclerView
 */
public class RecyclerView extends android.support.v7.widget.RecyclerView {

    public RecyclerView(@NonNull Context context) {
        super(context);
    }

    public RecyclerView(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    public RecyclerView(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(@NonNull Context context,
                                  @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerView);
        if (a.hasValue(R.styleable.RecyclerView_listDivider)) {
            Drawable drawable = a.getDrawable(R.styleable.RecyclerView_listDivider);
            if (drawable != null) {
                DividerItemDecoration item = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                item.setDrawable(drawable);
                addItemDecoration(item);
            }
        }
        a.recycle();
    }
}
