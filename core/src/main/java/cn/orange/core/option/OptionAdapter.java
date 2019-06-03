package cn.orange.core.option;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.orange.core.BaseAdapter;


/**
 * Created by Orange on 18-11-22.
 * Email:addskya@163.com
 */
class OptionAdapter extends BaseAdapter<Option, OptionContract.View> {

    OptionAdapter(@NonNull LayoutInflater inflater,
                  @Nullable OptionContract.View view) {
        super(inflater, view);
    }

    @Override
    @NonNull
    protected RecyclerView.ViewHolder newHolder(@NonNull LayoutInflater inflater,
                                                @Nullable ViewGroup parent,
                                                int viewType) {
        return OptionViewHolder.newHolder(inflater, parent, viewType);
    }

    @Override
    protected void bindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                  @Nullable Option data,
                                  @Nullable OptionContract.View view) {
        if (holder instanceof OptionViewHolder) {
            ((OptionViewHolder) holder).bindHolder(data, view);
        }
    }
}
