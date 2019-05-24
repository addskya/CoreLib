package cn.orange.core.pick;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;

import cn.orange.core.ActionBarActivity;
import cn.orange.core.DefaultTextWatcher;
import cn.orange.core.R;
import cn.orange.core.databinding.ActivityPickTextBinding;

/**
 * Created by Orange on 18-11-21.
 * Email:addskya@163.com
 */
public class PickTextActivity extends ActionBarActivity {

    private static final String TAG = "PickTextActivity";
    private static final String EXTRA_TITLE
            = "cn.orange.core.EXTRA_TITLE";

    private static final String EXTRA_TEXT
            = "cn.orange.core.EXTRA_TEXT";

    private static final String EXTRA_HINT
            = "cn.orange.core.EXTRA_HINT";

    private static final String EXTRA_LENGTH
            = "cn.orange.core.EXTRA_LENGTH";
    private ActivityPickTextBinding mBinding;

    /**
     * 启动文本输入
     *
     * @param host        host activity
     * @param titleResId  显示的标题
     * @param text        原来的文字内容
     * @param hint        输入框无内容时的提示文本
     * @param maxLength   输入框最大字符数
     * @param requestCode the activity request code
     */
    public static void intentTo(@NonNull Fragment host,
                                @StringRes int titleResId,
                                @Nullable CharSequence text,
                                @Nullable CharSequence hint,
                                int maxLength,
                                int requestCode) {
        Intent intent = new Intent(host.getContext(), PickTextActivity.class);
        intent.putExtra(EXTRA_TITLE, titleResId);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_HINT, hint);
        intent.putExtra(EXTRA_LENGTH, maxLength);
        host.startActivityForResult(intent, requestCode);
    }

    /**
     * 解析 PickTextActivity返回数据Intent
     *
     * @param data the result intent
     * @return the text
     */
    public static CharSequence parseResult(@NonNull Intent data) {
        return data.getCharSequenceExtra(EXTRA_TEXT);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick_text);

        Intent intent = getIntent();
        @StringRes int titleResId = intent.getIntExtra(EXTRA_TITLE, 0);
        @Nullable CharSequence text = intent.getCharSequenceExtra(EXTRA_TEXT);
        @Nullable CharSequence hint = intent.getCharSequenceExtra(EXTRA_HINT);
        final int maxLength = intent.getIntExtra(EXTRA_LENGTH, 50);

        setTitle(titleResId);

        mBinding.area.setText(text);
        mBinding.area.setHint(hint);
        mBinding.area.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        int length = mBinding.area.length();
        mBinding.area.setSelection(length, length);
        calcRemainText(text, maxLength);

        mBinding.area.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calcRemainText(s, maxLength);
            }
        });


    }

    private void calcRemainText(@Nullable CharSequence input, int maxLength) {
        int length = input != null ? input.length() : 0;
        String remainText = length + "/" + maxLength;
        mBinding.remain.setText(remainText);
    }

    public void onFinishClick(View view) {
        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, mBinding.area.getText());
        setResult(RESULT_OK, data);
        finish();
    }
}
