package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;


/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * <p>
 * 加载转圈对话框
 */
public class LoadingDialog extends BaseDialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater,
                                @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_loading,
                parent, false);
    }

    @Override
    protected int getWindowAnimation() {
        return 0;
    }
}
