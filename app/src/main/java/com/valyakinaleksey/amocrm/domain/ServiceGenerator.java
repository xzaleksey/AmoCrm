package com.valyakinaleksey.amocrm.domain;

import android.text.TextUtils;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.valyakinaleksey.amocrm.models.api.AuthResponse;
import com.valyakinaleksey.amocrm.models.api.LeadsResponse;
import com.valyakinaleksey.amocrm.models.api.LeadsStatusesResponse;
import com.valyakinaleksey.amocrm.models.api.Response;
import com.valyakinaleksey.amocrm.util.Logger;
import com.valyakinaleksey.amocrm.util.Session;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public class ServiceGenerator {
    public static final String USER_LOGIN_P = "USER_LOGIN";
    public static final String USER_PASSWORD_P = "USER_PASSWORD";
    private static final String BASE_URL = "https://andxzalekseygmailcomibqb.amocrm.ru";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient okClient = new OkHttpClient();
        okClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                if (!TextUtils.isEmpty(Session.SESSION_ID)) {
                    Logger.d(Session.SESSION_ID);
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Cookie", Session.SESSION_ID)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
                return chain.proceed(chain.request());
            }
        });
        Retrofit retrofit = builder.client(okClient).build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit retrofit() {
        return builder.build();
    }

    public interface AmoCrmApiInterface {

        @FormUrlEncoded
        @POST("/private/api/auth.php?type=json")
        Call<Response<AuthResponse>> apiLogin(@Field(USER_LOGIN_P) String login, @Field(USER_PASSWORD_P) String password);

        @GET("/private/api/v2/json/leads/list")
        Call<Response<LeadsResponse>> getLeads();

        @GET("/private/api/v2/json/accounts/current")
        Call<Response<LeadsStatusesResponse>> getLeadStatuses();
//        @PUT("/user/{id}/update")
//        Call<Response> updateUser(@Path("id") String id, @Body Response user);
    }

}