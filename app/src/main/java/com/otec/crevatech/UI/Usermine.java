package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.List;

public class Usermine extends Fragment {


    private TextView n1, n2, n3, n4, n5, n6, rt;
    private Button play;


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

            if (send_number.size() > 0)
                new utilJava().LoadInstance(rt, e.getContext(), new ArrayList<>(), send_number, false, true, false, null, null);
            else
                new utilKotlin().message2("Pls fill out some nodes", getContext());
        });
        return v;
    }

    private boolean I(CharSequence text) {
        return !text.toString().trim().isEmpty();
    }
}