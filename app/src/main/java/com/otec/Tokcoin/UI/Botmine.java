package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;

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
            if(play.getText().toString().equals("Play")) {
                progress.setVisibility(View.VISIBLE);
                new utilJava().Request_Digit(getContext(), digits_returned, loader, play, progress, rt);
            }else
                play.setText("Play");

        });
        return v;
    }


}