package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;

import java.util.Arrays;
import java.util.List;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;

/**
 * Created by Orange on 18-11-28.
 * Email:addskya@163.com
 * <p>
 * new WheelDialog<Integer>(getContext()) {
 *
 * @NonNull
 * @Override protected Integer[] getWheelItems() {
 * int[] num = getResources().getIntArray(R.array.card_num);
 * return copyInt(num);
 * }
 * @Override protected void onItemSelected(Integer value) {
 * Log.i(TAG, "onItemSelected:" + value);
 * }
 * @Override protected int getDefaultItemIndex() {
 * return 0;
 * }
 * }.show();
 */

@SuppressWarnings("all")
public abstract class WheelDialog<T> extends BaseDialog {
    private static final String TAG = "WheelDialog";
    private static final int INVALID_INDEX = -1;
    private int mSelectedIndex = INVALID_INDEX;


    protected WheelDialog(@NonNull Context context) {
        this(context, R.style.AppThemeDialog);
    }

    private WheelDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater,
                                @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_wheel, parent, false);
    }


    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);

        WheelView wheelView = view.findViewById(R.id.wheel_view);
        @NonNull T[] data = getWheelItems();
        @NonNull final List<T> list = Arrays.asList(data);

        wheelView.setCyclic(false);
        wheelView.setOnItemSelectedListener((index) -> {
                    Log.i(TAG, "onItemSelected,index:" + index);
                    mSelectedIndex = index;
                }
        );
        wheelView.setAdapter(new ArrayWheelAdapter(list));
        final int defaultSelectedIndex = getValidIndex(data);
        wheelView.setCurrentItem(defaultSelectedIndex);

        View cancelView = view.findViewById(R.id.cancel);
        cancelView.setOnClickListener((v) -> dismiss());

        View okView = view.findViewById(R.id.ok);
        okView.setOnClickListener((v -> {
            dismiss();
            mSelectedIndex = mSelectedIndex >= 0 ? mSelectedIndex : defaultSelectedIndex;
            T value = list.get(mSelectedIndex);
            onItemSelected(value);
        }));
    }

    @Override
    protected int getWindowGravity() {
        return Gravity.BOTTOM;
    }

    @NonNull
    protected abstract T[] getWheelItems();

    private int getValidIndex(T[] data) {
        return Math.max(Math.min(getDefaultItemIndex(), data.length - 1), 0);
    }

    protected int getDefaultItemIndex() {
        return 0;
    }

    protected abstract void onItemSelected(T value);

    private class ArrayWheelAdapter implements WheelAdapter<T> {

        private final List<T> mData;

        ArrayWheelAdapter(List<T> list) {
            mData = list;
        }

        @Override
        public int getItemsCount() {
            return mData.size();
        }

        @Override
        public T getItem(int index) {
            return mData.get(index);
        }

        @Override
        public int indexOf(T t) {
            return mData.indexOf(t);
        }
    }
}
