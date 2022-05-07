package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Question extends Fragment {

    private String TAG = "Question";


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_question, container, false);
             Bundle b = getArguments();
              InitQuestion(b);
            return v;
        }

    private void InitQuestion(Bundle b) {
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<models> list =  request.getPostList(new utilJava().GET_USER(new utilJava().GET_CACHED_USER(getActivity(), getString(R.string.SIGNED_IN_USER)),b.get("category").toString()));
        list.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                Log.d(TAG, "onResponse: "+response.body().getMessage());
            }
            @Override
            public void onFailure(Call<models> call, Throwable t) {
                    new utilKotlin().message2("Error occurred "+t.getMessage(), getContext());
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}