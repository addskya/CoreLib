package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.orange.core.R;

/**
 * Created by ChengHe.Zhang on 2019/5/28
 * Email:chengheZhang@kaifa.cn
 */
public class EditTextItem extends TextViewItem {

    private ImageView mScanView;

    public EditTextItem(@NonNull Context context) {
        super(context);
    }

    public EditTextItem(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextItem(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScanView = findViewById(R.id.scan);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextItem);
        setSupportScan(a.getBoolean(R.styleable.EditTextItem_supportScan, false));
        a.recycle();
    }

    @LayoutRes
    @Override
    protected int getChildView() {
        return R.layout.edittext_item;
    }

    public final void setSupportScan(boolean supportScan) {
        if (isInEditMode()) {
            return;
        }
        if (mScanView != null) {
            mScanView.setVisibility(supportScan ? VISIBLE : GONE);
        }
    }

    public final void setScanAction(OnClickListener listener) {
        if (mScanView != null) {
            mScanView.setOnClickListener(listener);
        }
    }
}
