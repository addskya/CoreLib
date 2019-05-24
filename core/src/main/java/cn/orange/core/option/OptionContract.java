package cn.orange.core.option;

/**
 * Created by Orange on 18-11-22.
 * Email:addskya@163.com
 */

public interface OptionContract {

    interface View {

        void onOptionItemClick(OptionItem item);

        void onCancelClick();
    }
}
