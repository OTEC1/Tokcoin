package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.Map;


public class report extends Fragment {

    private EditText email,issue;
    private Button send;

    private String   TAG="report";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_report, container, false);

        issue = v.findViewById(R.id.issue);
        email = v.findViewById(R.id.email);
        send = v.findViewById(R.id.send);

        Map<String,Object> m = new utilJava().GET_CACHED_MAP(v.getContext(), getString(R.string.SIGNED_IN_USER));
        Log.d(TAG, "onCreateView: "+m);
          if(m!= null)
              email.setText(m.get("email").toString());


        send.setOnClickListener(e->{
            if(!email.getText().toString().isEmpty())
                    if(issue.getText().toString().trim().length() > 0)
                        new utilJava().routine(email.getText().toString(),issue.getText().toString(),v.getContext());
                    else
                        new utilKotlin().message2("Pls fill out your issues", v.getContext());
            else
                new utilKotlin().message2("Pls enter your registration email", v.getContext());

        });

        return v;
    }
}