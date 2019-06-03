package cn.orange.core.option;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Orange on 18-11-22.
 * Email:addskya@163.com
 */
public class OptionTextView extends AppCompatTextView {

    public OptionTextView(Context context) {
        super(context);
    }

    public OptionTextView(Context context,
                          AttributeSet attrs) {
        super(context, attrs);
    }

    public OptionTextView(Context context,
                          AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Bind the Option
     *
     * @param item the Option
     */
    public void setOption(@Nullable Option item) {
        Resources res = getResources();

        int textId = item != null ? item.getTextId() : 0;
        if (textId != 0) {
            setText(textId);
        } else {
            setText(null);
        }

        int textColorId = item != null ? item.getTextColor() : 0;
        if (textColorId != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextColor(res.getColorStateList(textColorId,
                        getContext().getTheme()));
            } else {
                setTextColor(res.getColorStateList(textColorId));
            }
        }
    }
}
