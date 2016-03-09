package com.valyakinaleksey.amocrm.domain;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;
import com.valyakinaleksey.amocrm.models.AuthResponse;
import com.valyakinaleksey.amocrm.models.Response;

import java.io.IOException;
import java.util.HashMap;

import retrofit.Call;
import retrofit.Converter;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Ashiq Uz Zoha on 9/13/15.
 * Dhrubok Infotech Services Ltd.
 * ashiq.ayon@gmail.com
 */
public class RestClient {
    public static final String USER_LOGIN_P = "USER_LOGIN";
    public static final String USER_PASSWORD_P = "USER_PASSWORD";
    public static final String USER_HASH_P = "USER_HASH";
    public static final String USER_LOGIN = "xzaleksey@gmail.com";
    public static final String USER_HASH = "29dd9a8473c46f508776336045486e7e";
    private static AmoCrmApiInterface amoCrmApiInterface;
    private static final String BASE_URL = "https://andxzalekseygmailcomibqb.amocrm.ru";
    public static HashMap<String, String> userLoginMap = new HashMap<>();

    public static AmoCrmApiInterface getClient(Converter.Factory factory) {
        OkHttpClient okClient = new OkHttpClient();
        okClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        });
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .client(okClient);
        if (factory != null) {
            builder.addConverterFactory(factory);
        }
        amoCrmApiInterface = builder.build().create(AmoCrmApiInterface.class);
        return amoCrmApiInterface;
    }

    public interface AmoCrmApiInterface {

        //        @GET("/search/users")
//        Call<GitResult> getUsersNamedTom(@Query("q") String name);
        @FormUrlEncoded
        @POST("/private/api/auth.php?type=json")
        Call<Response<AuthResponse>> apiLogin(@Field(USER_LOGIN_P) String login, @Field(USER_PASSWORD_P) String password);

        @GET("/private/api/v2/json/leads/list")
        Call<ResponseBody> getLeads(@Header("session_id") String sessionId);
//        @PUT("/user/{id}/update")
//        Call<Response> updateUser(@Path("id") String id, @Body Response user);
    }

}