package cn.orange.core;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class PromptActivity extends PermissionsActivity implements Promptable {

    @SuppressWarnings("unused")
    private static final String TAG = "PromptActivity";
    private Dialog mDialog;

    @Override
    public void showCode(int errorCode) {
        String message = ErrorMapper.getErrorMessage(this, errorCode);
        showPrompt(message);
    }

    /**
     * Clear all notification
     */
    private void clearAllNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        if (nm != null) {
            nm.cancelAll();
        }
    }

    @Override
    public void showDialogCode(int errorCode) {
        String message = ErrorMapper.getErrorMessage(this, errorCode);
        showDialog(message);
    }

    @Override
    public void showDialog(@NonNull CharSequence message) {
        showPrompt("showDialog:" + message);
    }

    @Override
    public void showError(Throwable error) {
        showPrompt(R.string.error_code_unknown);
    }

    @Override
    public void showPrompt(int message) {
        showPrompt(getString(message));
    }

    @Override
    public void showPrompt(@Nullable CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}