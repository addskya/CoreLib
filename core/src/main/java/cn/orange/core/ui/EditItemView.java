package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.orange.core.R;

/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 * <p>
 * 可以编辑的选项框
 */
public class EditItemView extends OptionItemView {

    public EditItemView(@NonNull Context context) {
        super(context);
    }

    public EditItemView(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditItemView(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @LayoutRes
    protected int getChildView() {
        return R.layout.edit_item;
    }
}
