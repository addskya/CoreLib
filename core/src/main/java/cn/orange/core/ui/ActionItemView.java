package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.orange.core.R;

/**
 * Created by Orange on 18-11-30.
 * Email:addskya@163.com
 * 带图标的选项设置
 * 跟系统设置项类似
 */
public class ActionItemView extends OptionItemView {

    protected ImageView mIconView;

    public ActionItemView(@NonNull Context context) {
        this(context, null);
    }

    public ActionItemView(@NonNull Context context,
                          @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionItemView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mIconView = findViewById(R.id.icon);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionItemView);
        if (a.hasValue(R.styleable.ActionItemView_icon)) {
            setIcon(a.getDrawable(R.styleable.ActionItemView_icon));
        }
        a.recycle();
    }

    @Override
    protected int getChildView() {
        return R.layout.action_item;
    }

    /**
     * 设置选项头图标
     *
     * @param iconRes 图标ID
     */
    public void setIcon(@DrawableRes int iconRes) {
        mIconView.setImageResource(iconRes);
    }

    /**
     * 设置选项头图标
     *
     * @param icon 图标Drawable
     */
    public void setIcon(@Nullable Drawable icon) {
        mIconView.setImageDrawable(icon);
    }
}
