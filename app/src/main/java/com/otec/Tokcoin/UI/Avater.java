package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.otec.Tokcoin.Adapater.Avater_adapter;
import com.otec.Tokcoin.Adapater.Spotcalls;
import com.otec.Tokcoin.R;

import java.util.ArrayList;
import java.util.List;

public class Avater extends Fragment {


    private RecyclerView avater_view;
    private Avater_adapter avater_adapter;
    private ProgressBar spinners;
    private List<Integer> i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avater, container, false);
        avater_view = view.findViewById(R.id.avatar_select);
        spinners = view.findViewById(R.id.spinner);

        i = new ArrayList<>();
        i.add(R.drawable.arsenal);
        i.add(R.drawable.banner);
        i.add(R.drawable.barcelona);
        i.add(R.drawable.barcelonaone);
        i.add(R.drawable.bayenmunchen);
        i.add(R.drawable.borussiadortmund);
        i.add(R.drawable.atleticodemadrid);
        i.add(R.drawable.chelsea);
        i.add(R.drawable.football);
        i.add(R.drawable.footballbadge);
        i.add(R.drawable.footballclub);
        i.add(R.drawable.internazionalemilano);
        i.add(R.drawable.liverpool);
        i.add(R.drawable.manchestercity);
        i.add(R.drawable.manchesterunited);
        i.add(R.drawable.parissaintgermain);
        i.add(R.drawable.realmadrid);
        i.add(R.drawable.roma);
        i.add(R.drawable.roman);
        i.add(R.drawable.villarreal);
        i.add(R.drawable.lazio);
        i.add(R.drawable.napoli);
        i.add(R.drawable.rugby);
        i.add(R.drawable.supporter);
        i.add(R.drawable.valencia);
        i.add(R.drawable.footballc);
        i.add(R.drawable.footballclubig);
        i.add(R.drawable.supporterone);
        i.add(R.drawable.chelseasub);
        i.add(R.drawable.footbalclubyellow);
        i.add(R.drawable.bannerhang);
        i.add(R.drawable.bayernmunchenb);
        setLayout(i);
        return view;
    }

    private void setLayout(List<Integer> avatar) {
        avater_adapter = new Avater_adapter(avatar, getContext(), getArguments().getInt("node"));
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        avater_view.setLayoutManager(manager);
        avater_view.setAdapter(avater_adapter);
        spinners.setVisibility(View.INVISIBLE);
    }
}