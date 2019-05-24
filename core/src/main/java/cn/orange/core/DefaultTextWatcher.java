package cn.orange.core;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class DefaultTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s,
                                  int start,
                                  int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s,
                              int start,
                              int before,
                              int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
