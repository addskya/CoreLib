package cn.orange.core.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.orange.core.R;

/**
 * Created by Orange on 2019/6/3
 * Email:addskya@163.com
 */
public abstract class ViewItem extends FrameLayout {

    protected final View mAsteriskView;
    protected final TextView mTextView;

    public ViewItem(@NonNull Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr,
                    @LayoutRes int layout) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, layout, this);

        mAsteriskView = findViewById(R.id.asterisk);
        mTextView = findViewById(R.id.text);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewItem);
        setAsteriskVisible(a.getBoolean(R.styleable.ViewItem_asterisk, false));

        setText(a.getString(R.styleable.ViewItem_text));
        if (a.hasValue(R.styleable.ViewItem_textColor)) {
            setTextColor(a.getColorStateList(R.styleable.ViewItem_textColor));
        }

        a.recycle();
    }


    public final void setAsteriskVisible(boolean visible) {
        if (mAsteriskView != null) {
            mAsteriskView.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    public final void setText(@Nullable CharSequence text) {
        if (mTextView != null) {
            mTextView.setText(text);
        }
    }

    public final void setTextColor(ColorStateList color) {
        if (mTextView != null && color != null) {
            mTextView.setTextColor(color);
        }
    }
}
