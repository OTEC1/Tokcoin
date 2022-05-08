package com.otec.crevatech.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otec.crevatech.R;
import com.otec.crevatech.UI.MainActivity;

import java.lang.reflect.Type;
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
        SharedPreferences.Editor collection = init(view).edit();
        String area = new Gson().toJson(user);
        collection.putString(tag, area);
        collection.apply();
        view.startActivity(new Intent(view, MainActivity.class));
        return user;
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
        reuse_fragment(fragmentTransaction, fragment, my_fragment, BUNDLE(new Bundle(), a), R.id.frameLayout);
    }


    //Fragment open from fragment
    public void openFrag(Fragment fragment, String my_fragment, int i, FragmentActivity activity) {
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

    private Bundle BUNDLE(Bundle bundle, int i) {
        return bundle;
    }

    public Map<String, Object> GET_USER(Map<String, Object> obj, String category,String uuid,int id) {

        Map<String, Object> user = new HashMap<>();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("category", category.trim());
        user.put("sessionID", uuid);
        user.put("section",1);
        user.put("Id",id);
        Log.d(TAG, "GET_USER: "+user);
        return user;
    }
}
