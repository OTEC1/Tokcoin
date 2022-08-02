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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otec.crevatech.Adapater.Digits_Call;
import com.otec.crevatech.Adapater.JoinGroupCall;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.Repo;
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
                new utilJava().Request_Digit(getContext(),digits_returned,loader,progress,play,rt);
            else
                play.setText("Play");

        });
        return v;
    }


}