package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.otec.Tokcoin.Adapater.JoinGroupCall;
import com.otec.Tokcoin.Adapater.Spotcalls;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.model.models;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {


    private RecyclerView stake, spot;
    private JoinGroupCall joinGroupCall;
    private Spotcalls sportCalls;
    private ProgressBar spinners;

    private Map<String, RecyclerView> m, p;
    private List<Map<String, Object>> l;
    private String TAG = "Home";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        stake = v.findViewById(R.id.Stakes);
        spot = v.findViewById(R.id.spot);
        spinners = v.findViewById(R.id.spinners);
        Request(1);
        Request(2);
        return v;
    }





    private void Request(int q) {
        Request config = Base_config.getRetrofit().create(Request.class);
        Call<models> isFunded = config._REQUEST(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER))));
        isFunded.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                if (response.body() != null)
                    if (!response.body().getMessage().toString().contains("error")) {
                        setLayout(response.body().getMessage());
                        setLayout2(response.body().getMessage());
                    } else
                        new utilKotlin().message2("Unauthorized Request !", getContext());
                else
                    new utilKotlin().message2("Error occurred while retrieving odds", getContext());
            }

            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
            }
        });
    }


    private void setLayout(List<Map<String, Object>> message) {
        if (message != null) {
            joinGroupCall = new JoinGroupCall(message, getContext(), 1);
            stake.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            stake.setAdapter(joinGroupCall);
        } else
            new utilKotlin().message2("Error occurred " + message.size(), getContext());
        spinners.setVisibility(View.INVISIBLE);
    }



    private void setLayout2(List<Map<String, Object>> message) {
        if (message != null) {
                sportCalls = new Spotcalls(message, getContext());
                spot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                spot.setAdapter(sportCalls);
        } else
            new utilKotlin().message2("Error occurred " + message.size(), getContext());
        spinners.setVisibility(View.INVISIBLE);
    }
}