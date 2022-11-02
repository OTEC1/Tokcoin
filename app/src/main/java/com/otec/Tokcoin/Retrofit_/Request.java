package com.otec.Tokcoin.Retrofit_;
import android.view.ViewGroup;
import com.otec.Tokcoin.model.models;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Request {



    @GET("https://us-central1-webflystore.cloudfunctions.net/appCat/Banklist")
    Call<List<Map<String,Object>>> getListOfBank();


    @POST("Cravetech/SendPasswordRestLink")
    Call<Object> sendRestLink(@Body Map<String,Object> data);


    @POST("Cravetech/RegisterNewUser")
    Call<Map<String,Object>> postAuthUser(@Body Map<String,Object> data);



    @POST("Cravetech/GenerateRandom")
    Call<models> getPostList(@Body Map<String,Object> data);


    @POST("Cravetech/Userfunds")
    Call<Map<String,Object>> isFunded(@Body Map<String,Object> data);


    @POST("Cravetech/GroupCreation")
    Call<Map<String,Object>> SendGroupRequest(@Body Map<String,Object> data);


    @POST("Cravetech/GetListOfCreatedGroup")
    Call<Map<String,Object>> GT_GROUPS (@Body Map<String,Object> data);



    @POST("Cravetech/LoadActiveGroup")
    Call<models> _REQUEST(@Body Map<String,Object> data);


    @POST("Cravetech/LoadInactiveGroup")
    Call<models> I_REQUEST(@Body Map<String,Object> data);


    @POST("Cravetech/JoinGroupCheck")
    Call<Map<String,Object>> JOIN_REQUEST(@Body Map<String,Object> data);


    @POST("Cravetech/user_mine")
    Call<models>  DIGIT_BOT_REQUEST(@Body Map<String,Object> data);



    @POST("Cravetech/user_mine")
    Call<Map<String,Object>>  DIGIT_BOT_REQUEST2(@Body Map<String,Object> data);


    @POST("Cravetech/VnodeDeal")
    Call<models> SENT_VNODES(@Body Map<String,Object> obj);



    @POST("Cravetech/Voches")
    Call<Map<String,Object>> VOCHERS(@Body Map<String,Object> data);



    @POST("Cravetech/LeaveGroup")
    Call<Map<String,Object>> LEAVE(@Body Map<String,Object> data);




    @POST("Cravetech/creatorcancel")
    Call<Map<String,Object>> CANCEL(@Body Map<String,Object> data);




    @POST("Cravetech/AddHandler")
    Call<models> START_VN(@Body Map<String,Object> data);



    @POST("Cravetech/CA")
    Call<Map<String,Object>> _CA(@Body Map<String,Object> data);


    @POST("Cravetech/VN")
    Call<Map<String,Object>> _VN_(@Body Map<String,Object> data);


    @POST("Cravetech/Updatepics")
    Call<Map<String,Object>> UPDATE_USER(@Body Map<String,Object> data);


    @POST("Cravetech/SignIn")
    Call<Map<String, Object>> SIGN_IN(@Body Map<String, Object> get_user_cred);

    @POST("Cravetech/auth_purchase")
    Call<Map<String, Object>> TRANS(@Body Map<String, Object> i);
}