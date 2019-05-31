package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.orange.core.R;


/**
 * Created by ChengHe.Zhang on 2019/5/28
 * Email:chengheZhang@kaifa.cn
 */
public class EditTextItem extends TextViewItem {
    private static final String TAG = "EditTextItem";
    private View mScannerView;

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
        super(context, attrs, defStyleAttr);

        mScannerView = findViewById(R.id.scanner);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextItem);
        setScannable(a.getBoolean(R.styleable.EditTextItem_scannable, false));
        a.recycle();
    }

    @LayoutRes
    @Override
    protected int getChildView() {
        return R.layout.edittext_item;
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
