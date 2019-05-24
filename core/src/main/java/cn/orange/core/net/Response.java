package cn.orange.core.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class Response<T> {
    private static final int INVALID_CODE = -1;
    private int code;
    private String desc;
    private T data;

    Response(int code, String desc) {
        this(code, desc, null);
    }

    Response(int code, String desc, T value) {
        this.code = code;
        this.desc = desc;
        this.data = value;
    }

    public static <T> Response<T> success(T value) {
        return new Response<>(200, null, value);
    }

    /**
     * get the response code
     *
     * @param response the response from Web server
     * @return the response code or {@code INVALID_CODE}
     */
    public static int getResponseCode(@Nullable Response response) {
        return response != null ? response.getCode() : INVALID_CODE;
    }

    /**
     * 解析服务器返回的Code
     *
     * @param responseJson 服务器返回的json数据
     * @return the code.
     */
    static int getResponseCode(@NonNull String responseJson) {
        final int invalidCode = INVALID_CODE;
        if (TextUtils.isEmpty(responseJson)) {
            return invalidCode;
        }
        final String KEY = "code";
        JSONObject json = toJsonObject(responseJson);
        return json != null ? json.optInt(KEY, invalidCode) : invalidCode;
    }

    /**
     * 解析服务器返回的msg
     *
     * @param responseJson 服务器返回的json数据
     * @return the meg
     */
    static String getResponseMsg(@NonNull String responseJson) {
        if (TextUtils.isEmpty(responseJson)) {
            return null;
        }

        final String KEY = "desc";
        JSONObject json = toJsonObject(responseJson);
        return json != null ? json.optString(KEY, null) : null;
    }

    /**
     * 解析服务器返回的value
     *
     * @param responseJson 服务器返回的json数据
     * @param responseType 需要转换的类型
     * @param <T>          需要转换的类型
     * @return 转换后的数据
     */
    static <T> T getResponseValue(@NonNull Gson gson,
                                  @NonNull String responseJson,
                                  @NonNull Type responseType) {
        if (TextUtils.isEmpty(responseJson)) {
            return null;
        }

        final String KEY = "data";

        JSONObject json = toJsonObject(responseJson);
        String value = json != null ? json.optString(KEY, null) : null;
        // msg maybe null or "null"

        if (TextUtils.isEmpty(value) || "null".equalsIgnoreCase(value)) {
            return null;
        }

        return gson.fromJson(value, responseType);
    }

    /**
     * 将字符串转换为JsonObject对象
     *
     * @param jsonString 字符串
     * @return JsonObject对象
     */
    private static JSONObject toJsonObject(@NonNull String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Whether or NOT the response is SUCCESS
     *
     * @return true if response code is 200
     */
    public boolean isSuccess() {
        return code == 200;
    }

    /**
     * 获取响应码,请使用 {@link Response#getResponseCode(Response)}
     *
     * @return 响应码
     */
    private int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public T getData() {
        return data;
    }

    @Override
    @NonNull
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
