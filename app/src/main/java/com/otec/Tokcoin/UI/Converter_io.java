package com.otec.Tokcoin.UI;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.model.models;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Converter_io extends Fragment {

    private Button proceed;
    private TextView converted;
    private ProgressBar spin;
    private EditText edit;


    private String TAG = "Converter_io";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_converter_io, container, false);
        init_ui(view);
        request_conversation(1);


        proceed.setOnClickListener(e -> {
            request_conversation(2);
        });


        if (getArguments().getInt("node_") == 1) {
            edit.setVisibility(View.VISIBLE);
            converted.setTextSize(20);
        }else
            edit.setVisibility(View.INVISIBLE);

        return view;
    }




    private void init_ui(View view) {
        proceed = view.findViewById(R.id.proceed);
        converted = view.findViewById(R.id.converted);
        spin = view.findViewById(R.id.spinners);
        edit = view.findViewById(R.id.edit);
    }




    private void request_conversation(int a) {
        Request config = Base_config.getRetrofit().create(Request.class);
        Call<Map<String, Object>> isFunded = a == 1 ? config._CA(new utilJava().Maps(2, getArguments().getInt("payload"), getContext())) : config.TRANS(new utilJava().Maps(getArguments().getInt("serial"), getArguments().getInt("payload"), getContext()));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.body() != null) {
                    if (!response.body().get("message").toString().contains("error")) {
                        if (a != 2)
                            converted.setText(!response.body().get("message").toString().equals("2.5")  ? "$" + getArguments().getInt("payload") + " = " + response.body().get("message") + " Gas" : "Enter amount to process");
                        else
                            new utilKotlin().Message(requireActivity(), response.body().get("message").toString());
                        spin.setVisibility(View.INVISIBLE);
                    } else {
                        new utilKotlin().message2("Unauthorized Request !", getContext());
                        spin.setVisibility(View.INVISIBLE);
                    }
                } else {
                    new utilKotlin().message2("Error occurred while retrieving odds", getContext());
                    spin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                spin.setVisibility(View.INVISIBLE);

            }
        });
    }

}