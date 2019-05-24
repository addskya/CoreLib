package cn.orange.core;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import cn.orange.core.ui.ActionBar;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * 带有自定义标题栏风格的Activity.
 */
public abstract class ActionBarActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "ActionBarActivity";
    private ActionBar mActionBar;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mActionBar = findViewById(R.id.action_bar);

        if (mActionBar == null) {
            throw new IllegalStateException("You must include the action_bar view.");
        }
        setSupportActionBar(mActionBar);

        int navigationIcon = getNavigationIcon();
        if (navigationIcon != 0) {
            mActionBar.setNavigationIcon(navigationIcon);
        }

        setUiTitle(getUiTitle());
    }

    @DrawableRes
    protected int getNavigationIcon() {
        return R.drawable.ic_back;
    }

    /**
     * 返回当前Activity的标题
     *
     * @return 当前Activity的标题
     */
    protected CharSequence getUiTitle() {
        return getString(R.string.app_name);
    }

    /**
     * 设置当前Activity标题
     *
     * @param text string.
     */
    public void setUiTitle(@Nullable CharSequence text) {
        if (mActionBar != null) {
            mActionBar.setActionBarTitle(text);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
