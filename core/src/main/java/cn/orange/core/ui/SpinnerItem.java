package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import cn.orange.core.R;
import cn.orange.core.SimpleAdapter;

/**
 * Created by ChengHe.Zhang on 2019/5/31
 * Email:chengheZhang@kaifa.cn
 */
public class SpinnerItem extends TextViewItem {

    private Spinner mSpinner;

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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpinnerItem);
        createAdapter(context, a.getString(R.styleable.SpinnerItem_adapter));
        a.recycle();
    }

    private void createAdapter(@NonNull Context context, String className) {
        if (TextUtils.isEmpty(className)) {
            return;
        }

        className = className.trim();
        className = getFullClassName(context, className);

        try {


            ClassLoader classLoader;
            if (isInEditMode()) {
                classLoader = getClass().getClassLoader();
            } else {
                classLoader = context.getClassLoader();
            }

            Class<? extends android.widget.BaseAdapter> adapterClass =
                    classLoader.loadClass(className)
                            .asSubclass(android.widget.BaseAdapter.class);


        } catch (Exception e) {

        }
    }

    public final <D, V> void setAdapter(@NonNull SimpleAdapter<D, V> adapter,
                                        @Nullable OnItemSelected<D> listener ) {
        if (mSpinner != null) {
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    D data = adapter.getItem(position);
                    listener.onItemSelected(data);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }



    private String getFullClassName(Context context, String className) {
        if (className.charAt(0) == '.') {
            return context.getPackageName() + className;
        } else {
            return className.contains(".") ? className : RecyclerView.class.getPackage().getName() + '.' + className;
        }
    }

    @Override
    protected int getChildView() {
        return R.layout.spinner_item;
    }
}
