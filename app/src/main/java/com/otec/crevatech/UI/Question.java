package com.otec.crevatech.UI;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Question extends Fragment {


    private String TAG = "Question";
    private List<Integer> list = new ArrayList<>();


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        Bundle b = getArguments();
        CheckLoad(b);
        return v;
    }


    private void CheckLoad(Bundle b) {
        if (Check(getString(R.string.QuestionID), b.get("category").toString()))
            LoadQuestion(getString(R.string.QuestionID), b.get("category").toString(),UUID.randomUUID().toString());
        else
            InitQuestion(b, UUID.randomUUID().toString(), getString(R.string.QuestionID));
    }



    private boolean Check(String tag, String category) {
        List<String> categories = new ArrayList<>();
        List<Map<String,Object>> array =  new utilJava().GET_CACHED_LIST(getContext(),tag);
        if (array != null) {
            for (int y = 0; y < array.size(); y++)
                for (Map.Entry<String, Object> obj : array.get(y).entrySet())
                         categories.add(obj.getKey());
            if (categories.contains(category))
                return true;
            else
                return false;
        }else
            return false;

    }


    private void InitQuestion(Bundle b, String uuid, String tag) {
        GenerateRandomValue(b, uuid, tag);
    }


    private void GenerateRandomValue(Bundle b, String uuid, String tag) {
        Map<String, Object> obj = new HashMap<>();
        obj.put(b.getString("category"), psvm(0, 100));
        List<Map<String, Object>> arrayList = new utilJava().GET_CACHED_LIST(getContext(),tag);
        arrayList.add(obj);
        SharedPreferences.Editor collection = new utilJava().init(getActivity()).edit();
        String area = new Gson().toJson(arrayList);
        collection.putString(tag, area);
        collection.apply();
        Log.d(TAG, "GenerateRandomValue: " + area);
    }


    private void LoadQuestion(String tag, String category,String uuid) {
        List<Map<String, Object>> array = new utilJava().GET_CACHED_LIST(getContext(),tag);
        if (array != null)
             for (int y = 0; y < array.size(); y++)
                for (Map.Entry<String, Object> obj : array.get(y).entrySet()) {
                    if(obj.getKey().equals(category))
                        RequestQeustion(C(obj.getValue()).get(C(obj.getValue()).size()-1),category);
                }
    }


    public  List<?> C(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }



    private void RequestQeustion(Object o, String obj2) {
        Map<String,Object> user  = new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.SIGNED_IN_USER)),obj2,"",(int)Double.parseDouble(o.toString()));
        Log.d(TAG, "RequestQeustion: "+user);
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<models> list = request.getPostList(user);
        list.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                Log.d(TAG, "onResponse: "+response.body().getMessage());


            }

            @Override
            public void onFailure(Call<models> call, Throwable t) {

            }
        });
    }


    public List<Integer> psvm(int min, int max) {
        int x;
        while (true) {
                x = min + (int) (Math.random() * ((max - min) + 1));
                list.add(x == 0 ? x + 1 : x);
                List<Integer> deDupStringList = new ArrayList<>(new HashSet<>(list));
                  if (deDupStringList.size() >= max)
                    return  deDupStringList;
        }
    }

}