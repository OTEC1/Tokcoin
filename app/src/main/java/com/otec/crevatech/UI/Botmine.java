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
import android.widget.TextView;

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
    private Digits_Call digits_call;
    private ProgressBar progress;
    private TextView rt;

    private boolean loader = false;
    private String TAG = "Botmine";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_botmine, container, false);
        digits_returned = v.findViewById(R.id.digits_returned);
        progress = v.findViewById(R.id.progress);
        rt = v.findViewById(R.id.rt);
        play = v.findViewById(R.id.play);


        if(play.getText().toString().trim().length()<=0)
            play.setText("Start");

        play.setOnClickListener(e->{
            if(play.getText().toString().equals("Play"))
                Request_Digit();
            else
                play.setText("Play");

        });
        return v;
    }

    private void Request_Digit() {

        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.DIGIT_BOT_REQUEST(new utilJava()._DIGIT(new utilJava()
                        .GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), false, false, true,
                new ArrayList<>(), new ArrayList<>(), null, null));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {

                if (!response.body().get("message").toString().contains("error"))
                    setlayout((List<Double>) response.body().get("message"));
                else {
                    List<Map<String,Object>> o = (List<Map<String, Object>>) response.body().get("message");
                    new utilKotlin().message2(o.get(0).get("error").toString(), getContext());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
            }
        });

    }

    private void setlayout(List<Double> message) {
        digits_call = new Digits_Call(message, getContext(), play, rt);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        digits_returned.setLayoutManager(manager);
        digits_returned.setAdapter(digits_call);
        progress.setVisibility(View.INVISIBLE);
        loader = false;
    }
}