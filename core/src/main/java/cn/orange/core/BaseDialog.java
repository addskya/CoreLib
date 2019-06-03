package cn.orange.core;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Orange on 2019/6/3
 * Email:addskya@163.com
 */
public abstract class BaseDialog extends AppCompatDialog {

    private final OnClickListener mListener;

    protected BaseDialog(@NonNull Context context) {
        this(context, R.style.AppThemeDialog);
    }

    protected BaseDialog(@NonNull Context context, int theme) {
        this(context, theme, null);
    }

    protected BaseDialog(@NonNull Context context,
                         @Nullable OnClickListener listener) {
        this(context, R.style.AppThemeDialog, listener);
    }

    protected BaseDialog(@NonNull Context context,
                         int theme,
                         @Nullable OnClickListener listener) {
        super(context, theme);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(getWindowAnimation());
            window.setGravity(getWindowGravity());
        }
        View view = onCreateView(LayoutInflater.from(getContext()), null);
        onViewCreated(view);
        setContentView(view);
    }

    @NonNull
    protected abstract View onCreateView(@NonNull LayoutInflater inflater,
                                         @Nullable ViewGroup parent);

    protected void onViewCreated(@NonNull View view) {
    }

    /**
     * get the Dialog display Gravity
     *
     * @return Gravity
     */
    protected int getWindowGravity() {
        return Gravity.CENTER;
    }

    protected int getWindowAnimation() {
        return R.style.AnimBottomDialog;
    }

    protected void notifyCallback(int which) {
        if (mListener != null) {
            mListener.onClick(this, which);
        }
    }

    /**
     * Whether or NOT the View is Visible for User,
     *
     * @return for Activity return !isFinish(),
     * for Fragment return isAdded(),
     * for Dialog return isShowing()
     */
    public final boolean isAdded() {
        return isShowing();
    }

    protected final Context getBaseContext() {
        Context context = getContext();
        if (context instanceof ContextThemeWrapper) {
            return ((ContextThemeWrapper) context).getBaseContext();
        } else if (context instanceof ContextWrapper) {
            return ((ContextWrapper) context).getBaseContext();
        }
        return context;
    }
}
