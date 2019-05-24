package cn.orange.core.picture;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.orange.core.databinding.ItemPictureBinding;

/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 */
class PictureViewHolder extends RecyclerView.ViewHolder {

    private PictureViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemPictureBinding binding = ItemPictureBinding.inflate(
                inflater, parent, false);
        return new PictureViewHolder(binding.getRoot());
    }

    void bind(@Nullable String uri,
              @Nullable PictureContract.View view,
              boolean showDelete) {
        ItemPictureBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setUri(uri);
        binding.setView(view);
        binding.setDelete(showDelete);
        binding.executePendingBindings();
    }
}
