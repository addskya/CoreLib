package cn.orange.core.type;

/**
 * Created by Orange on 2019/6/4
 * Email:addskya@163.com
 */
public class IntegerAdapter extends TypeAdapter<Integer> {

    public IntegerAdapter(Integer defaultValue) {
        super(defaultValue);
    }

    @Override
    Integer convert(String text) {
        return Integer.parseInt(text);
    }
}
