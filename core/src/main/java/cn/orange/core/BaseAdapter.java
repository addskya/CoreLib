package cn.orange.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
@SuppressWarnings("all")
public abstract class BaseAdapter<D, V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @SuppressWarnings("unused")
    private static final String TAG = "BaseAdapter";

    private final ArrayList<D> mData;
    private final LayoutInflater mInflater;
    protected final V mView;
    private boolean mAllowRepeated;
    private boolean mInsertToHead;

    protected BaseAdapter(@NonNull LayoutInflater inflater,
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
        onDataChanged();
    }

    public void add(@Nullable D[] data) {
        if (data == null) {
            return;
        }
        for (D d : data) {
            addWithNotify(d, false);
        }
        notifyDataChanged();
    }

    /**
     * Insert new Data list into Adapter
     *
     * @param list the new data list
     */
    public void add(@Nullable List<D> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (D data : list) {
            addWithNotify(data, false);
        }
        notifyDataChanged();
    }

    /**
     * Insert new Data into Adapter
     *
     * @param data   the data
     * @param notify whether or not notify update
     */
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
            insertIndex += getHeaderCount();
            notifyItemInserted(insertIndex);
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
        notifyItemInserted(index);
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
        int position = mData.indexOf(data);
        if (position == -1) {
            return true;
        }
        position += getHeaderCount();
        boolean removed = mData.remove(data);
        if (removed && notify) {
            notifyItemRemoved(position);
        }
        onDataChanged();
        return removed;
    }

    /**
     * Remove Data from Adapter
     *
     * @param list the Remove data list
     * @return whether or not success
     */
    public boolean remove(@Nullable List<D> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        boolean removed = false;
        for (D data : list) {
            removed = removeWithChanged(data, false) || removed;
        }
        notifyDataChanged();
        return removed;
    }

    /**
     * Clear All the Data
     */
    public void clear() {
        mData.clear();
        notifyDataChanged();
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
            notifyItemChanged(index + getHeaderCount());
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
     * Returns the element at the specified position in this list.
     *
     * @param  position index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public D getItem(int position) {
        return getAll().get(position);
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
    public final RecyclerView.ViewHolder onCreateViewHolder(
            @Nullable ViewGroup parent,
            int viewType) {
        return newHolder(mInflater, parent, viewType);
    }

    /**
     * create the ItemView Holder for this
     *
     * @param inflater the layout inflater
     * @param parent   the parent viewGroup
     * @param viewType the item viewType
     * @return the item ViewHolder
     */
    @NonNull
    protected abstract RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        D data;
        final int headerCount = getHeaderCount();
        final int dataSize = getAll().size();
        final int footerCount = getFooterCount();

        if (position < headerCount) {
            data = null;
        } else if (position >= headerCount && position <= headerCount + dataSize - 1) {
            int dataIndex = position - headerCount;
            data = getAll().get(dataIndex);
        } else {
            data = null;
        }
        bindViewHolder(holder, data, mView);
    }

    protected abstract void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable D data,
            @Nullable V view);

    @Override
    public int getItemCount() {
        return getDataCount() + getHeaderCount() + getFooterCount();
    }

    /**
     * How many Header in AdapterView
     *
     * @return the header count in RecyclerView
     */
    public int getHeaderCount() {
        return 0;
    }

    /**
     * How many Data Item in AdapterView
     *
     * @return the data list size
     */
    public final int getDataCount() {
        return getAll().size();
    }

    /**
     * How many Footer in AdapterView
     *
     * @return the footer count in RecyclerView
     */
    protected int getFooterCount() {
        return 0;
    }

    /**
     * Hook Api,Call Back when the list Changed
     */
    protected void onDataChanged() {

    }

    /**
     * Send Notify when data list Changed
     */
    public final void notifyDataChanged() {
        notifyDataSetChanged();
        onDataChanged();
    }
}
