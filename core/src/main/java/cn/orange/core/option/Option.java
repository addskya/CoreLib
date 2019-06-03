package cn.orange.core.option;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import cn.orange.core.R;


/**
 * Created by Orange on 18-11-22.
 * Email:addskya@163.com
 */
public class Option {

    private int identifier;

    @StringRes
    private int textId;

    @ColorRes
    private int textColor;

    public Option(int identifier, @StringRes int textId) {
        this(identifier, textId, R.color.black);
    }

    public Option(int identifier,
                      @StringRes int textId,
                      @ColorRes int textColor) {
        this.identifier = identifier;
        this.textId = textId;
        this.textColor = textColor;
    }

    public int getIdentifier() {
        return identifier;
    }

    @StringRes
    int getTextId() {
        return textId;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

}
