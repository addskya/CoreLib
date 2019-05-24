package cn.orange.core.option;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;
import cn.orange.core.databinding.DialogOptionBinding;


/**
 * Created by Orange on 18-11-22.
 * Email:addskya@163.com
 */
public abstract class OptionDialog extends BaseDialog
        implements OptionContract.View {

    private OptionCallBack mCallBack;

    protected OptionDialog(@NonNull Context context,
                           @Nullable OptionCallBack callBack) {
        super(context);
        mCallBack = callBack;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCallBack = null;
    }

    @Override
    protected final View onCreateView(@NonNull LayoutInflater inflater,
                                      @Nullable ViewGroup parent) {
        DialogOptionBinding binding = DialogOptionBinding.inflate(inflater);
        OptionAdapter adapter = new OptionAdapter(inflater, this);
        adapter.add(getOptionItems());
        binding.optionList.setAdapter(adapter);
        binding.setView(this);
        binding.cancel.setTextColor(getColorStateList(getCancelColor()));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onOptionItemClick(OptionItem item) {
        if (mCallBack != null) {
            mCallBack.onCallBack(item);
        }
        dismiss();
    }

    @Override
    public void onCancelClick() {
        if (mCallBack != null) {
            mCallBack.onCancel();
        }
        dismiss();
    }

    protected abstract List<OptionItem> getOptionItems();

    /**
     * 获取指定颜色
     *
     * @param colorResId color resource id
     * @return the ColorStateList
     */
    @SuppressWarnings("all")
    protected ColorStateList getColorStateList(int colorResId) {
        Resources res = getContext().getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getColorStateList(colorResId, getContext().getTheme());
        } else {
            return res.getColorStateList(colorResId);
        }
    }

    /**
     * 取消按钮颜色
     *
     * @return 取消按钮颜色
     */
    @SuppressWarnings("all")
    protected int getCancelColor() {
        return R.color.light_red;
    }

    @Override
    protected int getWindowGravity() {
        return Gravity.BOTTOM;
    }
}
