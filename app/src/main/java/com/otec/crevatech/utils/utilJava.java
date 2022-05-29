package com.otec.crevatech.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otec.crevatech.R;
import com.otec.crevatech.UI.MainActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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



    public Map<String, Object> GET_USER(Map<String, Object> obj, String category,List<Map<String,Object>> lists,int id,int call) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("category",category);
        if(lists != null)
             user.put("list",lists);
        user.put("id",id);
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
        pack.put("creator",creator.toString().replace("[","").replace("]",""));
        return Wrap(pack);
    }



    public Map<String, Object> GET_GROUP(Map<String, Object> obj, EditText groupName, EditText amount, EditText liquidator_stake, EditText miner_stake,Double odd,int avatar) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("groupName", groupName.getText().toString());
        user.put("amount", Long.parseLong(amount.getText().toString()));
        user.put("liquidator_size",Long.parseLong(liquidator_stake.getText().toString()));
        user.put("miner_stake", Long.parseLong(miner_stake.getText().toString()));
        user.put("active", false);
        user.put("odd", odd);
        user.put("avatar", avatar);
        return Wrap(user);
    }


    private Map<String, Object> Wrap(Map<String, Object> user) {
        Map<String, Object> pack = new HashMap<>();
        pack.put("User",user);
        Log.d(TAG, "Wrap: "+pack);
        return pack;
    }

}
