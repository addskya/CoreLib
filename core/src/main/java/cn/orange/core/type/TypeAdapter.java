package cn.orange.core.type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

/**
 * Created by Orange on 2019/6/4
 * Email:addskya@163.com
 */
abstract class TypeAdapter<T> implements JsonDeserializer<T> {
    private T mDefaultValue;

    TypeAdapter(T defaultValue) {
        mDefaultValue = defaultValue;
    }

    @Override
    public T deserialize(JsonElement json,
                         Type typeOfT,
                         JsonDeserializationContext context) {
        try {
            String text = json.getAsString(); json.getAsBoolean();
            return convert(text);
        } catch (Throwable e) {
            return mDefaultValue;
        }
    }

    abstract T convert(String text) throws Throwable;
}
