package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.otec.Tokcoin.Adapater.Group_cover;
import com.otec.Tokcoin.R;

public class Digits extends Fragment {


    private TabLayout joinedGroup;
    private ViewPager viewPager;
    private Group_cover cover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digits, container, false);
        joinedGroup = view.findViewById(R.id.digit_mine);
        viewPager= view.findViewById(R.id.viewPager);
        Populate();
        RequestGroups();
        return view;
    }

    private void Populate() {
        joinedGroup.addTab(joinedGroup.newTab().setText("User miner"));
        joinedGroup.addTab(joinedGroup.newTab().setText("Bot miner"));
        joinedGroup.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void RequestGroups() {
        cover =  new Group_cover(getChildFragmentManager() ,getContext(),joinedGroup.getTabCount(),2);
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