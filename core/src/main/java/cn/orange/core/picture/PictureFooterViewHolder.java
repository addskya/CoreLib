package cn.orange.core.picture;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.orange.core.databinding.ItemPictureFooterBinding;


/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 */
class PictureFooterViewHolder extends RecyclerView.ViewHolder {

    private PictureFooterViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemPictureFooterBinding binding = ItemPictureFooterBinding.inflate(
                inflater, parent, false);
        return new PictureFooterViewHolder(binding.getRoot());
    }

    void bind(@Nullable PictureContract.View view) {
        ItemPictureFooterBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setView(view);
        binding.executePendingBindings();
    }
}
