package cn.orange.core.picture;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.orange.core.BaseAdapter;

/**
 * Created by Orange on 18-12-26.
 * Email:addskya@163.com
 */
public abstract class GalleryAdapter extends BaseAdapter<String, PictureContract.View> {

    private static final int ITEM_TYPE_FOOTER = 1;
    private static final int ITEM_TYPE_DATA = 0;

    GalleryAdapter(@NonNull LayoutInflater inflater,
                   @Nullable PictureContract.View view) {
        super(inflater, view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= getDataCount() + getHeaderCount()) {
            return ITEM_TYPE_FOOTER;
        }
        return ITEM_TYPE_DATA;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType) {
        switch (viewType) {
            case ITEM_TYPE_FOOTER: {
                return PictureFooterViewHolder.newHolder(inflater, parent);
            }
            case ITEM_TYPE_DATA:
            default: {
                return PictureViewHolder.newHolder(inflater, parent);
            }
        }
    }

    @Override
    protected void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable String data,
            @Nullable PictureContract.View view) {
        if (holder instanceof PictureFooterViewHolder) {
            ((PictureFooterViewHolder) holder).bind(view);
        } else if (holder instanceof PictureViewHolder) {
            ((PictureViewHolder) holder).bind(data, view, showDelete());
        }
    }

    protected boolean showDelete() {
        return true;
    }
}
