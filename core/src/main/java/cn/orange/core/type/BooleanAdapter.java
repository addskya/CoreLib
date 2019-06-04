package cn.orange.core.type;

/**
 * Created by Orange on 2019/6/4
 * Email:addskya@163.com
 */
public class BooleanAdapter extends TypeAdapter<Boolean> {

    public BooleanAdapter(Boolean defaultValue) {
        super(defaultValue);
    }

    @Override
    Boolean convert(String text) {
        return Boolean.valueOf(text);
    }
}
