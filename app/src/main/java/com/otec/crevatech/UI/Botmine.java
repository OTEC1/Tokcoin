package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.otec.crevatech.Adapater.Digits_Call;
import com.otec.crevatech.Adapater.JoinGroupCall;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Botmine extends Fragment {

    private RecyclerView digits_returned;
    private Button play;
    private Digits_Call  digits_call;
    private ProgressBar progress;


    private String TAG = "Botmine";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_botmine, container, false);
        digits_returned = v.findViewById(R.id.digits_returned);
        progress = v.findViewById(R.id.progress);
        play = v.findViewById(R.id.play);
        Request_Digit();
        return v;
    }

    private void Request_Digit() {

        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String,Object>> isFunded = config.DIGIT_BOT_REQUEST(new utilJava()._DIGIT(new utilJava()
                .GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), false,false,true,
                new ArrayList<>(),new ArrayList<>(),null,null));
        isFunded.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                List<Double> numbers = (List<Double>) response.body().get("message");
                if(numbers.size() > 0)
                    setlayout((List<Double>)response.body().get("message"));

            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(),getContext());
            }
        });

    }

    private void setlayout(List<Double> message) {
        digits_call = new Digits_Call(message,getContext(),play);
        GridLayoutManager manager = new  GridLayoutManager(getContext(),4);
        digits_returned.setLayoutManager(manager);
        digits_returned.setAdapter(digits_call);
        progress.setVisibility(View.INVISIBLE);
    }
}