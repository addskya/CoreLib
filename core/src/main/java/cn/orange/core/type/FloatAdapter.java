package cn.orange.core.type;

/**
 * Created by Orange on 2019/6/4
 * Email:addskya@163.com
 */
public class FloatAdapter extends TypeAdapter<Float> {

    public FloatAdapter(Float defaultValue) {
        super(defaultValue);
    }

    @Override
    Float convert(String text)  {
        return Float.valueOf(text);
    }
}
