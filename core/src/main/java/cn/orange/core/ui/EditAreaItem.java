package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.orange.core.R;

/**
 * Created by Orange on 2019/5/28
 * Email:addskya@163.com
 */
public class EditAreaItem extends EditTextItem {

    public EditAreaItem(@NonNull Context context) {
        this(context, null);
    }

    public EditAreaItem(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditAreaItem(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getChildView() {
        return R.layout.editarea_item;
    }
}