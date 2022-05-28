package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.protobuf.Any;
import com.otec.crevatech.Adapater.UserGroupslist;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsLists extends Fragment {

    private RelativeLayout createGroup;
    private TabLayout joinedGroup;
    private ViewPager viewPager;



    private String TAG = "GroupsLists";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups_lists, container, false);
        createGroup = view.findViewById(R.id.createGroup_1);
        joinedGroup = view.findViewById(R.id.joinedGroup);
        viewPager= view.findViewById(R.id.viewPager);
        Populate();
        RequestGroups();
        createGroup.setOnClickListener(e -> {
            new utilJava().openFrag(new Group_creation(), "Group_creation", null, getActivity());
        });
        return view;
    }

    private void Populate() {
        joinedGroup.addTab(joinedGroup.newTab().setText("Group joined"));
        joinedGroup.addTab(joinedGroup.newTab().setText("Group request"));
        joinedGroup.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void RequestGroups() {
    }
}

