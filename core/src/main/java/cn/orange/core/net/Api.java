package cn.orange.core.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
interface Api {

    /**
     * 请求网络接口
     *
     * @param api    api name
     * @param params request parameters
     * @return Request Response
     */
    @GET("{api}")
    Observable<String> get(@Path(value = "api", encoded = true) @NonNull String api,
                           @QueryMap Map<String, Object> params);

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api    get_version
     * @param params 请求指定接口时,需要的参数
     * @return Observable包装的响应
     */
    @POST("{api}")
    Observable<String> post(@Path(value = "api", encoded = true) @NonNull String api,
                            @Body @Nullable Map<String, Object> params);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{api}")
    Observable<String> post(@Path(value = "api", encoded = true) @NonNull String api,
                            @Body @NonNull RequestBody body);

    @PUT
    @Multipart
    @POST("{api}")
    Observable<String> upload(@Path(value = "api", encoded = true) @NonNull String api,
                              @PartMap @NonNull Map<String, RequestBody> params);
}
