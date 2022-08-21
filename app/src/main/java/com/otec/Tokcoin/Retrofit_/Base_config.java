package com.otec.Tokcoin.Retrofit_;

import com.otec.Tokcoin.utils.Constants;

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
