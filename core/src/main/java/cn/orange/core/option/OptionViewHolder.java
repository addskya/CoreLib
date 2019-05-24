package cn.orange.core.option;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.orange.core.databinding.ItemOptionBinding;

/**
 * Created by Orange on 19-1-4.
 * Email:addskya@163.com
 */

class OptionViewHolder extends RecyclerView.ViewHolder {

    private OptionViewHolder(View itemView) {
        super(itemView);
    }

    static OptionViewHolder newHolder(@NonNull LayoutInflater inflater,
                                      @Nullable ViewGroup parent,
                                      int viewType) {
        ItemOptionBinding binding = ItemOptionBinding.inflate(
                inflater, parent, false);
        return new OptionViewHolder(binding.getRoot());
    }

    void bindHolder(@Nullable OptionItem data,
                    @Nullable OptionContract.View view) {
        ItemOptionBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setOptionItem(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
