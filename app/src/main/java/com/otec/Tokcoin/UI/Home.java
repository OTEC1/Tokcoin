package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.otec.Tokcoin.Adapater.JoinGroupCall;
import com.otec.Tokcoin.Adapater.SportCalls;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request_class;
import com.otec.Tokcoin.model.models;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {


    private RecyclerView stake,spot;
    private JoinGroupCall joinGroupCall;
    private SportCalls sportCalls;
    private ProgressBar spinners;

    private String TAG = "Home";
;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        stake = v.findViewById(R.id.Stakes);
        spot = v.findViewById(R.id.spot);
        spinners = v.findViewById(R.id.spinners);

        for(int y = 0; y< 2; y++)
           Request(y);

        return v;
    }




    private  void Request(int n){
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<models> isFunded = n == 1 ? config._REQUEST(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)))) :  config._REQUEST(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER))));
        isFunded.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                if(response.body() != null)
                    if(!response.body().getMessage().toString().contains("error")) {
                        setLayout(response.body().getMessage(),stake,0);
                        setLayout(response.body().getMessage(),spot,1);
                    }else
                        new utilKotlin().message2("Unauthorized Request !", getContext());
                else
                    new utilKotlin().message2("Error occurred while retrieving odds", getContext());
            }
            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(),getContext());
            }
        });
    }




    private void setLayout(List<Map<String, Object>> message, RecyclerView o,int b) {

        if(message!=null) {
            if(b == 0)
               joinGroupCall = new JoinGroupCall(message, getContext(), 1);
            else
                sportCalls = new SportCalls(message, getContext());

            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            o.setLayoutManager(manager);
            o.setAdapter(b == 0 ? joinGroupCall : sportCalls);
            spinners.setVisibility(View.INVISIBLE);

        }
        else
                new utilKotlin().message2("Error occurred "+message.size(),getContext());

    }
}