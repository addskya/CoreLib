package cn.orange.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Orange on 2019/5/31
 * Email:addskya@163.com
 * <p>
 * 提供给ListView,GridView,Spinner等Container的数据容器
 */
@SuppressWarnings("all")
public abstract class SimpleAdapter<D, V> extends android.widget.BaseAdapter {
    private static final String TAG = "SimpleAdapter";

    private final ArrayList<D> mData;
    private final LayoutInflater mInflater;
    protected final V mView;
    private boolean mAllowRepeated;
    private boolean mInsertToHead;

    protected SimpleAdapter(@NonNull LayoutInflater inflater,
                            @Nullable V view) {
        mInflater = inflater;
        mView = view;
        mData = new ArrayList<>();
        mData.clear();
    }

    /**
     * Insert new Data into Adapter
     *
     * @param data the data
     */
    public void add(@Nullable D data) {
        addWithNotify(data, true);
    }

    public void add(@Nullable D[] data) {
        if (data == null) {
            return;
        }
        for (D d : data) {
            addWithNotify(d, false);
        }
        notifyDataSetChanged();
    }

    public void add(@Nullable Collection<D> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (D data : list) {
            addWithNotify(data, false);
        }
        notifyDataSetChanged();
    }

    private void addWithNotify(@Nullable D data, boolean notify) {
        if (data == null) {
            return;
        }
        int insertIndex = mInsertToHead ? 0 : mData.size();
        if (mAllowRepeated) {
            mData.add(insertIndex, data);
        } else if (!contains(data)) {
            mData.add(insertIndex, data);
        } else {
            return;
        }
        if (notify) {
            notifyDataSetChanged();
        }
    }

    /**
     * @param item item
     * @return whether the datas contain item
     */
    public boolean contains(@Nullable D item) {
        return mData.contains(item);
    }

    /**
     * Insert new Data into Adapter
     *
     * @param index the insert index
     * @param data  the data
     */
    public void insert(int index, @Nullable D data) {
        boolean contain = contains(data);
        if (contain && !mAllowRepeated) {
            return;
        }
        index = Math.max(0, Math.min(index, mData.size()));
        mData.add(index, data);
        notifyDataSetChanged();
    }

    /**
     * Remove Data from Adapter
     *
     * @param data the data removed
     * @return whether or not success
     */
    public boolean remove(@Nullable D data) {
        return removeWithChanged(data, true);
    }

    /**
     * Remove Data from Adapter
     *
     * @param data   the data removed
     * @param notify whether or not notify to update ui
     * @return true remove success or otherwise
     */
    private boolean removeWithChanged(@Nullable D data, boolean notify) {
        if (data == null) {
            return true;
        }

        boolean result = mData.remove(data);
        if (notify) {
            notifyDataSetChanged();
        }
        return result;
    }

    /**
     * Remove Data from Adapter
     *
     * @param list the Remove data list
     * @return whether or not success
     */
    public boolean remove(@Nullable Collection<D> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        boolean removed = false;
        for (D data : list) {
            removed = removeWithChanged(data, false) || removed;
        }
        notifyDataSetChanged();
        return removed;
    }

    /**
     * Clear All the Data
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    /**
     * Replace the data
     *
     * @param data the new data
     */
    public void replace(@Nullable D data) {
        int index = mData.indexOf(data);
        if (index != -1) {
            mData.set(index, data);
            notifyDataSetChanged();
        }
    }

    /**
     * Set New Data,Clear the old data first.
     *
     * @param list the new data list
     */
    public void set(@Nullable List<D> list) {
        clear();
        add(list);
    }

    /**
     * Add Or Replace the data
     *
     * @param list    the data list
     * @param replace whether or not replace the data exist
     */
    public void add(@Nullable List<D> list, boolean replace) {
        if (replace) {
            set(list);
        } else {
            add(list);
        }
    }

    /**
     * get the origin data list.
     *
     * @return the origin data list.
     */
    @NonNull
    public ArrayList<D> getAll() {
        return mData;
    }

    /**
     * get the specified data
     *
     * @param data the queryUnique data
     * @return the origin data in list
     */
    @Nullable
    public D get(@NonNull D data) {
        int index = mData.indexOf(data);
        if (index != -1) {
            return mData.get(index);
        }
        return null;
    }

    /**
     * Set whether or not allow data repeated
     *
     * @param allowRepeated whether or not allow repeated
     */
    public void setAllowRepeated(boolean allowRepeated) {
        mAllowRepeated = allowRepeated;
    }

    /**
     * Set whether or not insert new data into list header
     *
     * @param insertToHead whether or not insert into the first position
     */
    public void setInsertToHead(boolean insertToHead) {
        mInsertToHead = insertToHead;
    }

    @Override
    public final int getCount() {
        return getAll().size();
    }

    @Override
    public final D getItem(int position) {
        return getAll().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = newHolder(mInflater, parent, getItemViewType(position));
            convertView = holder.getRoot();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final D data = getItem(position);
        bindViewHolder(holder, data, mView);
        return convertView;
    }

    /**
     * create the list view holder
     *
     * @param inflater layout inflater
     * @param parent   view parent maybe null
     * @param viewType the item view type {@link #getItemViewType(int)} ()}
     * @return the list view holder
     */
    protected abstract ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType);

    /**
     * bind data into view
     *
     * @param holder view holder
     * @param data   the data at position
     * @param view   the View Contract
     */
    protected abstract void bindViewHolder(
            @NonNull ViewHolder holder,
            @Nullable D data,
            @Nullable V view);

    public static abstract class ViewHolder {
        @NonNull
        protected final View itemView;

        public ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
        }

        public View getRoot() {
            return itemView;
        }
    }
}
