package com.orange.corelib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.orange.core.SimpleAdapter;

/**
 * Created by ChengHe.Zhang on 2019/5/31
 * Email:chengheZhang@kaifa.cn
 */
class CountryAdapter extends SimpleAdapter<Country, MainActivity> {

    CountryAdapter(@NonNull LayoutInflater inflater,
                   @Nullable MainActivity view) {
        super(inflater, view);
    }

    @Override
    protected ViewHolder newHolder(@NonNull LayoutInflater inflater,
                                   @Nullable ViewGroup parent,
                                   int viewType) {
        return ItemCountryViewHolder.newHolder(inflater, parent);
    }

    @Override
    protected void bindViewHolder(@NonNull ViewHolder holder,
                                  @Nullable Country data,
                                  @Nullable MainActivity view) {
        if (holder instanceof ItemCountryViewHolder) {
            ((ItemCountryViewHolder) holder).bind(data, view);
        }
    }
}
