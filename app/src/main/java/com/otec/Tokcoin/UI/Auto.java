package com.otec.Tokcoin.UI;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request_class;
import com.otec.Tokcoin.model.models;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Auto extends Fragment {

    private Spinner hours, bots;
    private ProgressBar progress;
    private Button button;
    private TextView output;
    private ArrayAdapter array;
    private List<String> u;


    private List<Map<String, Object>> o, a;
    private List<String> hour, size;
    private String TAG = "Auto";
    private boolean load = false, selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auto, container, false);
        init(v);
        Request(0, null);
        Selects(hours);
        Selects(bots);
        button.setOnClickListener(e -> {

            if (load && selected && u.size() > 1)
                if (!u.get(0).equals("Please select") && !u.get(1).equals("Please select")) {
                    progress.setVisibility(View.VISIBLE);
                      button.setVisibility(View.INVISIBLE);
                        Request(1, new utilJava().MapsValues(u, new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), getArguments()));
                }else
                    new utilKotlin().message2("Invalid request", getContext());
            else
                new utilKotlin().message2("Pls select timeframe and virtual bot", getContext());
        });

        return v;
    }


    private void init(View v) {
        hours = v.findViewById(R.id.hours);
        bots = v.findViewById(R.id.bots);
        output = v.findViewById(R.id.output);
        button = v.findViewById(R.id.place_virtual_stake);
        progress = v.findViewById(R.id.progress);
        button.setEnabled(false);
        hour = new ArrayList<>();
        size = new ArrayList<>();
        u = new ArrayList<>();
    }




    private void Selects(Spinner spin) {
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    u.add(0, parent.getItemAtPosition(position).toString());
                    selected = true;
                    if (spin.getId() == R.id.bots){
                        progress.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);
                        new utilJava().debit_stake(getContext(), output, Integer.parseInt(u.get(0).substring(u.get(0).length() - 1).trim()),button,progress);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


    }


    public void Request(int u, Map<String, Object> obj) {
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<models> isFunded = u == 0 ? config.LIST_OF_VNODES(new utilJava().MAPS()) : config.START_VN(obj);
        isFunded.enqueue(new Callback<models>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                if (response.code() == 200) {
                    load = true;
                    button.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.INVISIBLE);
                    o = response.body().getMessage();
                    if (o.get(0).get("n1").toString().contains("Please select")) {
                        for (int y = 1; y <= 2; y++) {
                            a = (List<Map<String, Object>>) o.get(0).get("n" + y);
                            LoopOut(a, y);
                        }
                        AddToSpinner(size, bots);
                        AddToSpinner(hour, hours);
                    } else
                        new utilKotlin().message2(response.body().getMessage().get(0).get("n1").toString(), getContext());

                }
            }

            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getActivity());
                button.setVisibility(View.VISIBLE);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void LoopOut(List<Map<String, Object>> a, int c) {
        a.forEach((v) -> {
            if (c == 1)
                hour.add(v.get("info") + " " + (v.get("t").toString().trim().length() > 0 ? new utilKotlin().cast(v.get("t")) : ""));
            else
                size.add(v.get("info") + " " + (v.get("t").toString().trim().length() > 0 ? new utilKotlin().cast(v.get("t")) : ""));
        });
    }


    private void AddToSpinner(List<String> body, Spinner spinner) {
        array = new ArrayAdapter(getContext(), R.layout.text_pad, body);
        array.setDropDownViewResource(R.layout.text);
        spinner.setAdapter(array);
    }


}