package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.otec.Tokcoin.Adapater.Group_cover;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;

public class GroupsLists extends Fragment {

    private RelativeLayout createGroup;
    private TabLayout joinedGroup;
    private ViewPager viewPager;
    private Group_cover cover;


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
        cover =  new Group_cover(getChildFragmentManager() ,getContext(),joinedGroup.getTabCount(),1);
        viewPager.setAdapter(cover);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(joinedGroup));

        joinedGroup.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

