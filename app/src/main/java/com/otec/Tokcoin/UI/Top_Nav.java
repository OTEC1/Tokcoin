package com.otec.Tokcoin.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.otec.Tokcoin.Adapater.TopNavTabs;
import com.otec.Tokcoin.utils.utilJava;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;


public class Top_Nav extends Fragment {

    private RecyclerView recyclerView;
    private TopNavTabs topNavTabs;
    private PopupMenu popup;
    private Button appdrawer;

    private String TAG="Top_nav";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_nav, container, false);
        Init(view);
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
                            echo(FirebaseAuth.getInstance().getCurrentUser().getEmail() + " already signed out",view.getContext());
                        return true;


                    case R.id.report:
                            startActivity(new Intent(getActivity(), Report_issues.class));
                        return true;

                }
                return false;
            });
        });
        return  view;
    }


    private void echo(String url, Context c) {
        new utilKotlin().message2(url, getContext());
        FirebaseAuth.getInstance().signOut();
        new utilJava().SET_DATA_TO_CACHE( c,null, c.getString(R.string.SIGNED_IN_USER));
        startActivity(new Intent(getActivity(), Login.class));
    }


    private void Init(View v) {
        recyclerView =  v.findViewById(R.id.tabsRecycler);
         appdrawer  = v.findViewById(R.id.app_drawer);
    }


    private void CHECK() {
             LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
             topNavTabs = new TopNavTabs(new ArrayList<>(),getContext());
             recyclerView.setLayoutManager(manager);
             recyclerView.setAdapter(topNavTabs);
    }




}