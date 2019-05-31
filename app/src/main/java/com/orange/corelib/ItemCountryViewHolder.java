package com.orange.corelib;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.corelib.databinding.ItemCountryBinding;

import cn.orange.core.SimpleAdapter;


/**
 * Created by ChengHe.Zhang on 2019/5/31
 * Email:chengheZhang@kaifa.cn
 */
class ItemCountryViewHolder extends SimpleAdapter.ViewHolder {

    private ItemCountryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    static SimpleAdapter.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemCountryBinding binding = ItemCountryBinding.inflate(
                inflater, parent, false);

        return new ItemCountryViewHolder(binding.getRoot());
    }

    void bind(Country data, MainActivity view) {
        ItemCountryBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setData(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
