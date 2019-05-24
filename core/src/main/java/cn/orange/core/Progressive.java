package cn.orange.core;

import android.support.annotation.Nullable;

/**
 * Created by ChengHe.Zhang on 2019/4/1.
 * Email:chengheZhang@kaifa.cn
 */
public interface Progressive extends Promptable {

    /**
     * 显示一个转动菊花的对话框,
     * 默认不可取消
     */
    void showLoading();

    /**
     * 显示一个转动菊花的对话框
     *
     * @param cancelable 是否可以隐藏对话框
     */
    void showLoading(boolean cancelable);

    /**
     * 显示一个带文字提示的加载对话框,默认不可取消
     *
     * @param message 文字提示
     */
    void showLoading(@Nullable CharSequence message);

    /**
     * 显示一个带文字提示的加载对话框
     *
     * @param message    文字提示
     * @param cancelable 是否可以隐藏对话框
     */
    void showLoading(@Nullable CharSequence message,
                     boolean cancelable);

    /**
     * 隐藏加载对话框
     */
    void dismissLoading();
}
