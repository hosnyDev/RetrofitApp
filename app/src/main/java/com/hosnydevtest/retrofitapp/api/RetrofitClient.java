package com.hosnydevtest.retrofitapp.api;

import android.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String AUTH = "Basic" + Base64.encodeToString(("hosnydev:password").getBytes(), Base64.NO_WRAP);

    public static final String BASE_URL = "************";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    public RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .addHeader("Authorization", AUTH)
                            .method(original.method(), original.body());
                    Request request = builder.build();
                    return chain.proceed(request);
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
