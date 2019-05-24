package cn.orange.core.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class OkDialog extends BaseDialog {

    private CharSequence mMessage;
    private DialogInterface.OnClickListener mOnClickListener;

    /**
     * 显示确认对话框
     *
     * @param context  application context
     * @param message  对话框提示
     * @param listener 用户点击动作
     */
    private OkDialog(@NonNull Context context,
                     @NonNull CharSequence message,
                     @Nullable DialogInterface.OnClickListener listener) {
        super(context);
        mMessage = message;
        mOnClickListener = listener;
    }

    public static void intentTo(
            @NonNull Context context,
            @NonNull CharSequence message,
            @Nullable DialogInterface.OnClickListener listener) {
        BaseDialog dialog = new OkDialog(context, message, listener);
        dialog.show();
    }

    @NonNull
    @Override
    protected View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_ok, parent, false);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);

        TextView messageView = view.findViewById(R.id.message);
        messageView.setText(mMessage);
        View sureView = view.findViewById(R.id.POSITIVE);
        sureView.setOnClickListener((v) -> notifyButtonClick(BUTTON_POSITIVE));
    }

    private void notifyButtonClick(int which) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this, which);
        }
        dismiss();
    }
}