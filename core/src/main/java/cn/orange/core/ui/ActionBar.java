package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import cn.orange.core.R;

/**
 * Created by Orange on 18-11-30.
 * Email:addskya@163.com
 */
public class ActionBar extends Toolbar {
    @SuppressWarnings("unused")
    private static final String TAG = "ActionBar";

    private TextView mTitle;

    public ActionBar(@NonNull Context context) {
        super(context);
    }

    public ActionBar(@NonNull Context context,
                     @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionBar(@NonNull Context context,
                     @Nullable AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = findViewById(R.id.title);
    }

    @Override
    public void setTitle(int resId) {
        setActionBarTitle(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        setActionBarTitle(title);
    }

    public void setActionBarTitle(@StringRes int resId) {
        setActionBarTitle(getResources().getString(resId));
    }

    public void setActionBarTitle(CharSequence title) {
        if (isInEditMode()) {
            return;
        }
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

}
