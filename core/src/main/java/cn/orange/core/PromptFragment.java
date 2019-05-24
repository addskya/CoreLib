package cn.orange.core;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class PromptFragment extends PermissionsFragment {

    /**
     * 统一显示错误码,默认使用Toast方案
     *
     * @param errorCode 错误码
     */
    protected void showCode(int errorCode) {
        getPromptable().showCode(errorCode);
    }

    /**
     * 以对话框形式显示错误码
     *
     * @param errorCode 错误码
     */
    protected void showDialogCode(int errorCode) {
        getPromptable().showDialogCode(errorCode);
    }

    /**
     * 以对话框形式显示错误
     *
     * @param message 错误string id
     */
    protected void showDialog(int message) {
        showDialog(getString(message));
    }

    /**
     * 以对话框形式显示错误
     *
     * @param message 错误
     */
    protected void showDialog(@NonNull CharSequence message) {
        getPromptable().showDialog(message);
    }

    /**
     * 统一显示错误信息
     *
     * @param error 错误信息
     */
    protected void showError(Throwable error) {
        getPromptable().showError(error);
    }

    /**
     * 显示文本提示
     *
     * @param message string id
     */
    protected void showPrompt(@StringRes int message) {
        getPromptable().showPrompt(message);
    }

    /**
     * 显示文本提示
     *
     * @param message 显示文本
     */
    protected void showPrompt(@Nullable CharSequence message) {
        getPromptable().showPrompt(message);
    }

    private Promptable getPromptable() {
        Activity host = getActivity();
        if (host instanceof Promptable) {
            return ((Promptable) host);
        }
        throw new IllegalArgumentException("The Host Activity is NOT Implement Promptable");
    }
}
