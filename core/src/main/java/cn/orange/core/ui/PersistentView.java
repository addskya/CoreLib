package cn.orange.core.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;

import cn.orange.core.R;

/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 * <p>
 * 可持久化存储的EditText
 * 用以实现如: 记住账号功能
 */
public class PersistentView extends AppCompatEditText {
    private static final String PERSISTENT_FILE = "persistent";
    private SharedPreferences mSharePreference;
    private String mPersistentKey;

    public PersistentView(@NonNull Context context) {
        this(context, null);
    }

    public PersistentView(@NonNull Context context,
                          @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersistentView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(@NonNull Context context,
                                  @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PersistentView);
        if (a.hasValue(R.styleable.PersistentView_persistentKey)) {
            mPersistentKey = a.getString(R.styleable.PersistentView_persistentKey);
        }

        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setup();
        readPersistentText();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mSharePreference = null;
    }

    public void persistentText() {
        if (TextUtils.isEmpty(mPersistentKey)) {
            return;
        }

        mSharePreference.edit().
                putString(mPersistentKey, String.valueOf(getText()))
                .apply();
    }

    private void readPersistentText() {
        if (TextUtils.isEmpty(mPersistentKey)) {
            return;
        }
        @Nullable String persistentText = mSharePreference.getString(mPersistentKey, null);
        setText(persistentText);
        Editable text = getText();
        int length = text == null ? 0 : text.length();
        try {
            setSelection(length);
        } catch (Exception e) {
            // Nothing to do
        }
    }

    private void setup() {
        if (mSharePreference == null) {
            mSharePreference = getContext().getSharedPreferences(
                    PERSISTENT_FILE, Context.MODE_PRIVATE);
        }
    }
}
