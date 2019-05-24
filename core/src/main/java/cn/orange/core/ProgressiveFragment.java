package cn.orange.core;

import android.app.Activity;
import android.support.annotation.Nullable;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class ProgressiveFragment extends PromptFragment {

    /**
     * 显示一个转动菊花的对话框,
     * 默认不可取消
     */
    protected void showLoading() {
        getProgressive().showLoading();
    }

    /**
     * 显示一个转动菊花的对话框
     *
     * @param cancelable 是否可以隐藏对话框
     */
    protected void showLoading(boolean cancelable) {
        getProgressive().showLoading(cancelable);
    }

    /**
     * 显示一个带文字提示的加载对话框,默认不可取消
     *
     * @param message 文字提示
     */
    protected void showLoading(@Nullable CharSequence message) {
        getProgressive().showLoading(message);
    }

    /**
     * 显示一个带文字提示的加载对话框
     *
     * @param message    文字提示
     * @param cancelable 是否可以隐藏对话框
     */
    protected void showLoading(@Nullable CharSequence message,
                               boolean cancelable) {
        getProgressive().showLoading(message, cancelable);
    }

    /**
     * 隐藏加载对话框
     */
    protected void dismissLoading() {
        getProgressive().dismissLoading();
    }

    private Progressive getProgressive() {
        Activity host = getActivity();
        if (host instanceof Progressive) {
            return ((Progressive) host);
        }
        throw new IllegalArgumentException("The Host Activity is NOT Implement Progressive");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }
}
