package cn.orange.core.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.orange.core.R;

/**
 * Created by Orange on 2019/5/28
 * Email:addskya@163.com
 */
public class TextViewItem extends ViewItem {

    protected final TextView mValueView;

    public TextViewItem(@NonNull Context context) {
        this(context, null);
    }

    public TextViewItem(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewItem(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mValueView = findViewById(R.id.content);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewItem);

        if (a.hasValue(R.styleable.TextViewItem_value)) {
            setValue(a.getString(R.styleable.TextViewItem_value));
        }
        if (a.hasValue(R.styleable.TextViewItem_valueColor)) {
            setValueColor(a.getColorStateList(R.styleable.TextViewItem_valueColor));
        }
        if (a.hasValue(R.styleable.TextViewItem_hint)) {
            setHint(a.getString(R.styleable.TextViewItem_hint));
        }

        if (a.hasValue(R.styleable.TextViewItem_valueBackground)) {
            setValueBackground(a.getDrawable(R.styleable.TextViewItem_valueBackground));
        }

        a.recycle();
    }

    public final void setValue(@Nullable CharSequence value) {
        if (mValueView != null) {
            mValueView.setText(value);
        }
    }

    public final CharSequence getValue() {
        return mValueView != null ? mValueView.getText() : null;
    }

    public final void setValueColor(@Nullable ColorStateList color) {
        if (mValueView != null && color != null) {
            mValueView.setTextColor(color);
        }
    }

    public final void setHint(@Nullable CharSequence hint) {
        if (mValueView != null) {
            mValueView.setHint(hint);
        }
    }

    public final void setValueBackground(Drawable background) {
        if (mValueView != null) {
            mValueView.setBackground(background);
        }
    }

    @LayoutRes
    @Override
    protected int getChildView() {
        return R.layout.textview_item;
    }
}
