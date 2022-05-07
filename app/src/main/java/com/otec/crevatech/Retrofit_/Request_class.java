package com.otec.crevatech.Retrofit_;

import com.otec.crevatech.model.models;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Request_class {

    @GET("https://us-central1-grelots-ad690.cloudfunctions.net/Noman/getTimeStamp") //Temp url
    Call<Map<String,Object>> getDate();


    @POST("Cravetech/SendPasswordRestLink")
    Call<Object> sendRestLink(@Body Map<String,Object> data);


    @POST("Cravetech/RegisterNewUser")
    Call<Map<String,Object>> postAuthUser(@Body Object data);


    @GET("https://us-central1-webflystore.cloudfunctions.net/appCat/Banklist")
    Call<List<Map<String,Object>>> getListOfBank();


    @POST("https://us-central1-grelots-ad690.cloudfunctions.net/Cravetech/AuthUserSession")
    Call<models> getPostList(@Body Map<String,Object> data);

}
