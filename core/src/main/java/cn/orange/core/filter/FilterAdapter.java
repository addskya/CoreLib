package cn.orange.core.filter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import java.util.ArrayList;

import cn.orange.core.BaseAdapter;
import cn.orange.core.util.CollectionUtil;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class FilterAdapter<D, V> extends BaseAdapter<D, V> {
    private ArrayList<D> mPartialData;
    private BaseFilter<D> mFilter;

    protected FilterAdapter(@NonNull LayoutInflater inflater,
                            @Nullable V view) {
        super(inflater, view);
        mPartialData = new ArrayList<>();
    }

    public final void setFilter(@Nullable BaseFilter<D> filter) {
        mPartialData.clear();
        mFilter = filter;
        ArrayList<D> full = super.getAll();

        if (filter == null) {
            mPartialData = full;
        } else {
            for (D data : full) {
                if (filter.isAccept(data)) {
                    mPartialData.add(data);
                }
            }
        }

        notifyDataChanged();
    }

    @NonNull
    @Override
    public ArrayList<D> getAll() {
        return mFilter == null ? super.getAll() : mPartialData;
    }

    @Override
    protected void onDataChanged() {
        onDataCountChanged(CollectionUtil.sizeOf(getAll()));
    }

    protected void onDataCountChanged(int count) {

    }
}
