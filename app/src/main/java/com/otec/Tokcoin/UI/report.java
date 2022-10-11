package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.Map;


public class report extends Fragment {

    private EditText email,issue;
    private Button send;
    private ProgressBar spins;
    private String   TAG="report";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_report, container, false);

        issue = v.findViewById(R.id.issue);
        email = v.findViewById(R.id.email);
        send = v.findViewById(R.id.send);
        spins = v.findViewById(R.id.spins);

        Map<String,Object> m = new utilJava().GET_CACHED_MAP(v.getContext(), getString(R.string.SIGNED_IN_USER));
          if(m!= null)
              email.setText(m.get("email").toString());


        send.setOnClickListener(e->{
            if(!email.getText().toString().isEmpty())
                    if(issue.getText().toString().trim().length() > 0) {
                        new utilJava().Change_widget(send, spins,View.INVISIBLE, View.VISIBLE);
                        new utilJava().routine(email.getText().toString(), issue.getText().toString(), v.getContext(),send,spins,issue);
                    }else
                        new utilKotlin().message2("Pls fill out your issues", v.getContext());
            else
                new utilKotlin().message2("Pls enter your registration email", v.getContext());

        });

        return v;
    }
}