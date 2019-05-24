package cn.orange.core.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;


/**
 * Created by Orange on 18-12-14.
 * Email:addskya@163.com
 */
public class ConfirmDialog extends BaseDialog {

    private CharSequence mMessage;
    private DialogInterface.OnClickListener mOnClickListener;

    private ConfirmDialog(@NonNull Context context,
                          @NonNull CharSequence message,
                          @Nullable DialogInterface.OnClickListener listener) {
        super(context);
        mMessage = message;
        mOnClickListener = listener;
    }

    /**
     * 显示确认对话框
     *
     * @param context  application context
     * @param message  对话框内显示的提示内容
     * @param listener 用户点击动作
     */
    public static void intentTo(@NonNull Context context,
                                @NonNull CharSequence message,
                                @Nullable DialogInterface.OnClickListener listener) {
        BaseDialog dialog = new ConfirmDialog(context, message, listener);
        dialog.show();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater,
                                @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_confirm, parent, false);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);

        TextView messageView = view.findViewById(R.id.message);
        messageView.setText(mMessage);
        View sureView = view.findViewById(R.id.POSITIVE);
        sureView.setOnClickListener((v) -> notifyButtonClick(BUTTON_POSITIVE));

        View cancelView = view.findViewById(R.id.NEGATIVE);
        cancelView.setOnClickListener((v) -> notifyButtonClick(BUTTON_NEGATIVE));
    }

    private void notifyButtonClick(int which) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this, which);
        }
        dismiss();
    }

    @Override
    protected int getWindowGravity() {
        return Gravity.CENTER;
    }
}
