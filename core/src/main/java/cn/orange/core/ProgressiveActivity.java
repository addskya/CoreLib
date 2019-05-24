package cn.orange.core;

import android.support.annotation.Nullable;

import cn.orange.core.ui.LoadingDialog;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class ProgressiveActivity extends PromptActivity implements Progressive {

    private BaseDialog mLoadingDialog;

    /**
     * 显示一个转动菊花的对话框,
     * 默认不可取消
     */
    @Override
    public void showLoading() {
        showLoading(false);
    }

    /**
     * 显示一个转动菊花的对话框
     *
     * @param cancelable 是否可以隐藏对话框
     */
    @Override
    public void showLoading(boolean cancelable) {
        showLoading(null, cancelable);
    }

    /**
     * 显示一个带文字提示的加载对话框,默认不可取消
     *
     * @param message 文字提示
     */
    @Override
    public void showLoading(@Nullable CharSequence message) {
        showLoading(message, false);
    }

    /**
     * 显示一个带文字提示的加载对话框
     *
     * @param message    文字提示
     * @param cancelable 是否可以隐藏对话框
     */
    @Override
    public void showLoading(@Nullable CharSequence message,
                            boolean cancelable) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    /**
     * 隐藏加载对话框
     */
    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }
}
