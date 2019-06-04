package cn.orange.core.type;

/**
 * Created by Orange on 2019/6/4
 * Email:addskya@163.com
 */
public class DoubleAdapter extends TypeAdapter<Double> {

    public DoubleAdapter(Double defaultValue) {
        super(defaultValue);
    }

    @Override
    Double convert(String text) {
        return Double.valueOf(text);
    }
}
