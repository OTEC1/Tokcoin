package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

public class Group_creation extends Fragment {


    private Button group_create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group_creation, container, false);
        group_create = view.findViewById(R.id.group_create);

        group_create.setOnClickListener(e->{
            if(true)
                new utilKotlin().message2("Pending request ", getContext());
        });
        return view;
    }
}