package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilJava;

import java.util.HashMap;
import java.util.Map;

public class GroupsLists extends Fragment {

    private ImageView createGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups_lists, container, false);
        createGroup = view.findViewById(R.id.createGroup);

        createGroup.setOnClickListener(e -> {
            Map<String,Object> o = new HashMap<>();
            o.put("view_caller",1);
            new utilJava().openFrag(new Group_creation(), "Group_creation", o, getActivity());
        });
        return view;
    }
}