package cn.orange.core.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.orange.core.R;


/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 * <p>
 * 文本展示的选项框
 */
public class OptionItemView extends FrameLayout {

    protected TextView mTextView;
    protected TextView mValueView;
    protected ImageView mArrowView;

    public OptionItemView(@NonNull Context context) {
        this(context, null);
    }

    public OptionItemView(@NonNull Context context,
                          @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionItemView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, getChildView(), this);
        mTextView = findViewById(R.id.text);
        mValueView = findViewById(R.id.content);
        mArrowView = findViewById(R.id.arrow);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionItemView);
        setText(a.getString(R.styleable.OptionItemView_text));
        if (a.hasValue(R.styleable.OptionItemView_textColor)) {
            setTextColor(a.getColorStateList(R.styleable.OptionItemView_textColor));
        }

        setValue(a.getString(R.styleable.OptionItemView_value));
        if (a.hasValue(R.styleable.OptionItemView_valueColor)) {
            setValueColor(a.getColorStateList(R.styleable.OptionItemView_valueColor));
        }
        if (a.hasValue(R.styleable.OptionItemView_hint)) {
            setValueHint(a.getString(R.styleable.OptionItemView_hint));
        }

        setArrowVisible(a.getBoolean(R.styleable.OptionItemView_arrow, true));
        a.recycle();
    }

    public static void setValue(@NonNull View view,
                                @Nullable CharSequence value) {
        if (view instanceof OptionItemView) {
            ((OptionItemView) view).setValue(value);
        }
    }

    @LayoutRes
    protected int getChildView() {
        return R.layout.option_item;
    }

    public final void setText(@Nullable CharSequence text) {
        if (mTextView != null) {
            mTextView.setText(text);
        }
    }

    public final void setTextColor(ColorStateList color) {
        if (mTextView != null) {
            mTextView.setTextColor(color);
        }
    }

    public final void setValue(@Nullable CharSequence content) {
        if (mValueView != null) {
            mValueView.setText(content);
        }
    }

    public final CharSequence getValue() {
        return (mValueView != null) ? mValueView.getText() : null;
    }

    public final void setValue(@StringRes int text) {
        if (text != 0 && mValueView != null) {
            mValueView.setText(text);
        }
    }

    public final void setValueColor(ColorStateList color) {
        if (mValueView != null) {
            mValueView.setTextColor(color);
        }
    }

    public final void setValueHint(@Nullable CharSequence hint) {
        if (mValueView != null) {
            mValueView.setHint(hint);
        }
    }

    public final void setArrowVisible(boolean visible) {
        if (mArrowView != null) {
            mArrowView.setVisibility(visible ? VISIBLE : INVISIBLE);
        }
    }
}
