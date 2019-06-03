package cn.orange.core.option;

/**
 * Created by Orange on 18-11-22.
 * Email:addskya@163.com
 */
public interface OptionCallBack {

    /**
     * when the option item clicked
     *
     * @param item the click option item
     */
    void onCallBack(Option item);

    /**
     * when the Cancel clicked
     */
    void onCancel();
}
