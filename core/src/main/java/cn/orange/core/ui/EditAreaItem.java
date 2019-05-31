package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.orange.core.R;

/**
 * Created by ChengHe.Zhang on 2019/5/28
 * Email:chengheZhang@kaifa.cn
 */
public class EditAreaItem extends EditTextItem {


    public EditAreaItem(@NonNull Context context) {
        super(context);
    }

    public EditAreaItem(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditAreaItem(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @LayoutRes
    @Override
    protected int getChildView() {
        return R.layout.editarea_item;
    }
}
