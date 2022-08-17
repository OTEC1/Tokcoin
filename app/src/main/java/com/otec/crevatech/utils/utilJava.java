package com.otec.crevatech.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otec.crevatech.Adapater.Digits_Call;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.UI.MainActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class utilJava {

    private SharedPreferences sp;


    private  String TAG = "utilJava";

    public SharedPreferences init(Context view) {
        return sp = Objects.requireNonNull(view.getSharedPreferences(view.getString(R.string.app_name), Context.MODE_PRIVATE));
    }






    public Object SET_CACHED_USER(Map<String, Object> obj, String tag, Context view) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", obj.get("email"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("user_id", obj.get("user_id"));
        user.put("UserCategory", obj.get("UserCategory"));
        SET_DATA_TO_CACHE(view,user,tag);
        view.startActivity(new Intent(view, MainActivity.class));
        return user;
    }

    public  String SET_DATA_TO_CACHE(Context view,Object user,String tag){
        SharedPreferences.Editor collection = init(view).edit();
        String area = new Gson().toJson(user);
        collection.putString(tag, area);
        collection.apply();
        return area;
    }

    public Map<String, Object> GET_CACHED_MAP(Context view, String tag) {
        String arrayListString = init(view).getString(tag, null);
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return new Gson().fromJson(arrayListString, type);
    }


    public List<Map<String, Object>> GET_CACHED_LIST(Context view, String tag) {
        String arrayListString = init(view).getString(tag, null);
        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
        return new Gson().fromJson(arrayListString, type);
    }


    //Open Fragment from  Adapter Class
    public void open_Fragment(Fragment fragments, String tag, View view, Bundle bundle, int d) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Fragment myfrag = fragments;
        myfrag.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(d, myfrag, tag).addToBackStack(null).commit();
    }


    //Fragment Open from Activity
    public void openFragment(Fragment fragment, String my_fragment, int a, AppCompatActivity context) {
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        reuse_fragment(fragmentTransaction, fragment, my_fragment, BUNDLE(new Bundle(), null), R.id.frameLayout);
    }


    //Fragment open from fragment
    public void openFrag(Fragment fragment, String my_fragment, Map<String,Object> i, FragmentActivity activity) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        reuse_fragment(fragmentTransaction, fragment, my_fragment, BUNDLE(new Bundle(), i), R.id.frameLayout);
    }



    //Reuse component
    private void reuse_fragment(FragmentTransaction fragmentTransaction, Fragment fragment, String my_fragment, Bundle b, int frameLayout) {
        fragment.setArguments(b);
        fragmentTransaction.replace(frameLayout, fragment, my_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private Bundle BUNDLE(Bundle bundle, Map<String,Object> i) {
        if(bundle != null && i != null) {
        }
        return bundle;
    }



    public  Map<String,Object> _DIGIT(Map<String, Object> user, Boolean isGroup, Boolean isUser, Boolean isBot, List<Double> creator, List<Integer> user_selected, String creator_id, String doc_id){
           Map<String,Object> obj = new HashMap<>();
               obj.put("user_id",user.get("user_id"));
               obj.put("email",user.get("email"));
               obj.put("IMEI",user.get("IMEI"));
               obj.put("isGroup",isGroup);
               obj.put("isUser",isUser);
               obj.put("isBot",isBot);
               obj.put("creator",creator);
               obj.put("user_selected",user_selected);
               obj.put("creator_id",creator_id);
               obj.put("doc_id",doc_id);
           return  Wrap(obj);
    }


    public Map<String, Object> GET_USER(Map<String, Object> obj, String category,List<Map<String,Object>> lists,int call) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("category",category);
        if(lists != null)
             user.put("list",lists);
        return  call == 2 ? Wrap(user) : user;
    }


    public Map<String, Object> GET_GROUP(Map<String, Object> obj) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
      return  Wrap(user);
    }




    public Map<String, Object> GET_GROUP_STATUS(Map<String, Object> obj,String doc_id,String creator_id) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("doc_id", doc_id);
        user.put("creator_id", creator_id);
        return  Wrap(user);
    }





    public   Map<String,Object> RequestSend(Map<String,Object> obj,String doc_id,Object creator){
        Map<String, Object> pack = new HashMap<>();
        pack.put("user_id",obj.get("user_id").toString());
        pack.put("email",obj.get("email").toString());
        pack.put("IMEI",obj.get("IMEI").toString());
        pack.put("doc_id",doc_id);
        pack.put("creator_email",creator.toString().replace("[","").replace("]",""));
        Log.d(TAG, "RequestSend: "+pack);
        return Wrap(pack);
    }



    public Map<String, Object> GET_GROUP(Map<String, Object> obj, EditText groupName, EditText amount, EditText liquidator_stake, EditText miner_stake,Double odd,int avatar) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("groupName", groupName.getText().toString().trim());
        user.put("amount", Long.parseLong(amount.getText().toString()));
        user.put("liquidator_size",Long.parseLong(liquidator_stake.getText().toString()));
        user.put("miner_stake", Long.parseLong(miner_stake.getText().toString()));
        user.put("active", false);
        user.put("odd", odd);
        user.put("avatar", avatar);
        user.put("profit", 0);
        user.put("loss", 0);
        user.put("liquidity", 0);
        return Wrap(user);
    }


    private Map<String, Object> Wrap(Map<String, Object> user) {
        Map<String, Object> pack = new HashMap<>();
        pack.put("User",user);
        Log.d(TAG, "Wrap: "+pack);
        return pack;
    }

    public void LoadInstance(TextView rt, Context e, List<Double> numbers, List<Integer> send_number, Boolean isGroup, Boolean isUser, Boolean isBot, String creator_id,
                             String doc_id, RecyclerView digits_returned, Button button, ProgressBar progress,int i) {
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.DIGIT_BOT_REQUEST(new utilJava()._DIGIT(new utilJava().GET_CACHED_MAP(e, e.getString(R.string.SIGNED_IN_USER)),
                isGroup, isUser, isBot, numbers, send_number, creator_id, doc_id));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String,Object> ms = (Map<String, Object>) response.body().get("message");
                new utilKotlin().message2(ms.get("m1").toString(),e);
                rt.setText("Digit mined was "+new utilKotlin().cast(ms.get("m2").toString()));
                numbers.clear();
                send_number.clear();
                if(i == 1)
                   Request_Digit(e,digits_returned,true,progress,button,rt);
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), e);
            }
        });

    }




    public void Request_Digit(Context cn, RecyclerView digits_returned, boolean loader, ProgressBar progress, Button play, TextView rt) {

        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.DIGIT_BOT_REQUEST(new utilJava()._DIGIT(new utilJava()
                        .GET_CACHED_MAP(cn, cn.getString(R.string.SIGNED_IN_USER)), false, false, true,
                new ArrayList<>(), new ArrayList<>(), null, null));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {

                if (response.body().get("message").toString().contains("error")) {
                    List<Map<String, Object>> o = (List<Map<String, Object>>) response.body().get("message");
                    new utilKotlin().message2(o.get(0).get("error").toString(), cn);
                }else
                if(response.body().get("message").toString().contains("m1")){
                    List<Map<String,Object>>  o = (List<Map<String,Object>>) response.body().get("message");

                    new utilKotlin().message2(o.get(0).get("m1").toString(),cn);
                }else {
                    setlayout((List<Double>) response.body().get("message"), digits_returned, loader, cn, progress, play, rt);
                    rt.setText("");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), cn);
            }
        });

    }

    private void setlayout(List<Double> message, RecyclerView digits_returned, boolean loader, Context cn, ProgressBar progress, Button play, TextView rt) {
         Digits_Call  digits_call = new Digits_Call(message,cn, play, rt,loader,digits_returned,progress);
        GridLayoutManager manager = new GridLayoutManager(cn, 4);
        digits_returned.setLayoutManager(manager);
        digits_returned.setAdapter(digits_call);
        progress.setVisibility(View.INVISIBLE);
    }

    public void routine(String email, String issue, Context c) {
        FirebaseFirestore.getInstance().collection(c.getString(R.string.ISSUE_REPORT)).document(email)
                .collection("ISSUES").document()
                .set(MAP(issue,email))
                .addOnCompleteListener(res->{
                    if(res.isSuccessful())
                        new utilKotlin().message2("We have received your complain" , c);
                    else
                        new utilKotlin().message2(res.getException().getLocalizedMessage(),c);
                });
    }

    private Object MAP(String  issues, String email) {
        Map<String,Object> m = new HashMap<>();
        m.put("email",email);
        m.put("issue",issues);
        return  m;
    }
}
