package com.otec.Tokcoin.UI;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;
import java.util.List;

public class Usermine extends Fragment {


    private TextView n1, n2, n3, n4, n5, n6, rt;
    private Button play;
    private ProgressBar progressCount;


    private List<Integer> send_number;
    private String TAG = "Usermine";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usermine, container, false);

        n1 = v.findViewById(R.id.n1);
        n2 = v.findViewById(R.id.n2);
        n3 = v.findViewById(R.id.n3);
        n4 = v.findViewById(R.id.n4);
        n5 = v.findViewById(R.id.n5);
        n6 = v.findViewById(R.id.n6);
        rt = v.findViewById(R.id.rt);
        play = v.findViewById(R.id.play);
        progressCount = v.findViewById(R.id.progressCount);
        send_number = new ArrayList<>();

        play.setOnClickListener(e -> {

            if (I(n1.getText()))
                send_number.add(Integer.parseInt(n1.getText().toString()));
            if (I(n2.getText()))
                send_number.add(Integer.parseInt(n2.getText().toString()));
            if (I(n3.getText()))
                send_number.add(Integer.parseInt(n3.getText().toString()));
            if (I(n4.getText()))
                send_number.add(Integer.parseInt(n4.getText().toString()));
            if (I(n5.getText()))
                send_number.add(Integer.parseInt(n5.getText().toString()));
            if (I(n6.getText()))
                send_number.add(Integer.parseInt(n6.getText().toString()));

            if (send_number.size() >= 3) {
                rt.setText("");
                 new utilJava().hideKeyboardFrom(v, getContext());
                play.setVisibility(View.INVISIBLE);
                progressCount.setVisibility(View.VISIBLE);
                new utilJava().LoadInstance(rt,play,progressCount, e.getContext(), new ArrayList<>(), send_number, false, true, false, null, null, null, null, null, 2);
            } else
            new utilKotlin().message2("Pls fill out at least 3  nodes", getContext());
        });
        return v;
    }

    private boolean I(CharSequence text) {
        return !text.toString().trim().isEmpty();
    }



}