package cn.orange.core.type;

/**
 * Created by Orange on 2019/6/4
 * Email:addskya@163.com
 */
public class LongAdapter extends TypeAdapter<Long> {

    public LongAdapter(Long defaultValue) {
        super(defaultValue);
    }

    @Override
    Long convert(String text) {
        return Long.valueOf(text);
    }
}
