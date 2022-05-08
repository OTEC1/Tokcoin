package com.otec.crevatech.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otec.crevatech.Adapater.TopNavTabs;
import com.otec.crevatech.utils.utilJava;

import com.otec.crevatech.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Top_nav extends Fragment {

    private RecyclerView recyclerView;
    private TopNavTabs topNavTabs;

    private String TAG="Top_nav";
    private  Map<String,Object> listOfCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_top_nav, container, false);
         Init(view);
           if(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.SIGNED_IN_USER))!=null)
              CHECK();
        return  view;
    }

    private void Init(View v) {
        recyclerView =  v.findViewById(R.id.tabsRecycler);
    }

    private void CHECK() {
             listOfCategory = new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER));
             LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
             List<String> l = (List<String>) listOfCategory.get("UserCategory");
             topNavTabs = new TopNavTabs(l,getContext());
             recyclerView.setLayoutManager(manager);
             recyclerView.setAdapter(topNavTabs);
        Log.d(TAG, "CHECK: ");
    }
}