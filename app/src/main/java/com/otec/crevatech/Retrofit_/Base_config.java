package com.otec.crevatech.Retrofit_;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.otec.crevatech.utils.Constants;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Base_config {


    static Retrofit retrofit;

    //Issues with timeout
    public  static  Retrofit getRetrofit(){
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(20, TimeUnit.MINUTES)
//                .readTimeout(20,TimeUnit.MINUTES)
//                .writeTimeout(20,TimeUnit.MINUTES)
//                .build();
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.FIREBASE_CLOUD_FUNCTION_URL)
                    //.client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return  retrofit;
    }
}
