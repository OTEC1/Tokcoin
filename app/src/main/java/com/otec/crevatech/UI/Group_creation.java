package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilJava;

public class Group_creation extends Fragment {


    private Button group_create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group_creation, container, false);
        group_create = view.findViewById(R.id.group_create);

        group_create.setOnClickListener(e->{
            new utilJava().openFrag(new Group_category_creation(), "Group_category_creation", 1, getActivity());
        });
        return view;
    }
}