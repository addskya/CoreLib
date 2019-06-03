package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import cn.orange.core.R;


/**
 * Created by Orange on 2019/5/28
 * Email:addskya@163.com
 */
public class EditTextItem extends TextViewItem {
    private static final String TAG = "EditTextItem";
    protected View mScannerView;

    public EditTextItem(@NonNull Context context) {
        this(context, null);
    }

    public EditTextItem(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextItem(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.layout.edittext_item);
    }

    public EditTextItem(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr,
                        @LayoutRes int layout) {
        super(context, attrs, defStyleAttr, layout);
        mScannerView = findViewById(R.id.scanner);

        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.inputType});
        setInputType(a.getInt(0, EditorInfo.TYPE_NULL));
        a.recycle();

        a = context.obtainStyledAttributes(attrs, R.styleable.EditTextItem);
        setScannable(a.getBoolean(R.styleable.EditTextItem_scannable, false));
        a.recycle();
    }

    public final void setInputType(int inputType) {
        if (mValueView != null) {
            mValueView.setInputType(inputType);
        }
    }

    public final void setScannable(boolean scannable) {
        if (mScannerView != null) {
            mScannerView.setVisibility(scannable ? VISIBLE : GONE);
        }
    }

    public final void setOnScannerListener(@Nullable OnScannerListener listener) {
        if (mScannerView != null) {
            mScannerView.setOnClickListener(listener);
        }
    }
}
