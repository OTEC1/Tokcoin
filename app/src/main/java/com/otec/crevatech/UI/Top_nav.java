package com.otec.crevatech.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.otec.crevatech.Adapater.TopNavTabs;
import com.otec.crevatech.utils.utilJava;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Top_nav extends Fragment {

    private RecyclerView recyclerView;
    private TopNavTabs topNavTabs;
    private PopupMenu popup;
    private Button appdrawer;

    private String TAG="Top_nav";
    private  Map<String,Object> listOfCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_nav, container, false);
        Init(view);
        if (new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)) != null)
            CHECK();

        appdrawer.setOnClickListener(v -> {
            popup = new PopupMenu(getContext(), v);
            MenuInflater inflater1 = popup.getMenuInflater();
            inflater1.inflate(R.menu.signuser, popup.getMenu());
            popup.show();
              popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {

                    case R.id.homes:
                        if (FirebaseAuth.getInstance().getUid() != null)
                            startActivity(new Intent(getContext(), Login.class));
                        else
                            new utilKotlin().message2("Pls sign out", getContext());
                        return true;


                    case R.id.sign_out:
                        if (FirebaseAuth.getInstance().getUid() == null)
                            startActivity(new Intent(getContext(), Login.class));
                        else
                            echo(FirebaseAuth.getInstance().getCurrentUser().getEmail() + " already signed out");
                        return true;


                    case R.id.report:
                        if (FirebaseAuth.getInstance().getUid() != null)
                            startActivity(new Intent(getActivity(), Issues_submit.class));
                        else
                            new utilKotlin().message2("Pls sign in", requireActivity());
                        return true;

                }
                return false;
            });
        });
        return  view;
    }


    private void echo(String url) {
        new utilKotlin().message2(url, getContext());
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), Login.class));
    }


    private void Init(View v) {
        recyclerView =  v.findViewById(R.id.tabsRecycler);
         appdrawer  = v.findViewById(R.id.app_drawer);
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