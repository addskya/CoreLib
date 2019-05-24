package cn.orange.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by ChengHe.Zhang on 2019/4/1.
 * Email:chengheZhang@kaifa.cn
 */
public interface Promptable {

    /**
     * 使用对话框方式提示#错误码#
     *
     * @param errorCode 错误码
     */
    void showCode(int errorCode);

    /**
     * 以对话框形式显示错误码
     *
     * @param errorCode 错误码
     */
    void showDialogCode(int errorCode);

    /**
     * 以对话框形式显示错误
     *
     * @param message 显示错误
     */
    void showDialog(@NonNull CharSequence message);

    /**
     * 使用对话框方式提示错误信息
     *
     * @param error 错误信息
     */
    void showError(Throwable error);

    /**
     * 显示提示信息
     *
     * @param message 提示信息
     */
    void showPrompt(@StringRes int message);

    /**
     * 显示提示信息
     *
     * @param message 提示信息
     */
    void showPrompt(@Nullable CharSequence message);
}
