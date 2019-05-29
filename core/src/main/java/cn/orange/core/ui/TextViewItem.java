package cn.orange.core.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.orange.core.R;

/**
 * Created by ChengHe.Zhang on 2019/5/28
 * Email:chengheZhang@kaifa.cn
 */
public class TextViewItem extends FrameLayout {

    protected TextView mAsteriskView;
    protected TextView mTextView;
    protected TextView mValueView;

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

        View.inflate(context, getChildView(), this);

        mAsteriskView = findViewById(R.id.asterisk);
        mTextView = findViewById(R.id.text);
        mValueView = findViewById(R.id.content);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewItem);

        setAsteriskVisible(a.getBoolean(R.styleable.TextViewItem_asterisk, false));

        setText(a.getString(R.styleable.TextViewItem_text));
        if (a.hasValue(R.styleable.TextViewItem_textColor)) {
            setTextColor(a.getColorStateList(R.styleable.TextViewItem_textColor));
        }

        if (a.hasValue(R.styleable.TextViewItem_value)) {
            setValue(a.getString(R.styleable.TextViewItem_value));
        }
        if (a.hasValue(R.styleable.TextViewItem_hint)) {
            setHint(a.getString(R.styleable.TextViewItem_hint));
        }

        if (a.hasValue(R.styleable.TextViewItem_valueColor)) {
            setValueColor(a.getColorStateList(R.styleable.TextViewItem_valueColor));
        }

        a.recycle();
    }

    public final void setAsteriskVisible(boolean visible) {
        if (mAsteriskView != null) {
            mAsteriskView.setVisibility(visible ? VISIBLE : INVISIBLE);
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

    protected int getChildView() {
        return R.layout.textview_item;
    }
}
