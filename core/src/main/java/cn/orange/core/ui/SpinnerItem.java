package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import cn.orange.core.DefaultOnItemSelectedListener;
import cn.orange.core.R;
import cn.orange.core.SimpleAdapter;

/**
 * Created by Orange on 2019/5/31
 * Email:addskya@163.com
 */
public class SpinnerItem extends ViewItem {

    protected final Spinner mSpinner;

    public SpinnerItem(@NonNull Context context) {
        this(context, null);
    }

    public SpinnerItem(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpinnerItem(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSpinner = findViewById(R.id.spinner);
    }

    public final <D, V> void setAdapter(@NonNull SimpleAdapter<D, V> adapter,
                                        @Nullable OnItemSelected<D> listener) {
        mSpinner.setAdapter(adapter);
        if (listener != null) {
            mSpinner.setOnItemSelectedListener(new DefaultOnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    D data = adapter.getItem(position);
                    listener.onItemSelected(data);
                }
            });
        }
    }

    @LayoutRes
    @Override
    protected int getChildView() {
        return R.layout.spinner_item;
    }
}
