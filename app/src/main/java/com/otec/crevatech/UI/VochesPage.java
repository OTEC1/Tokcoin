package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.otec.crevatech.Adapater.Vouches;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VochesPage extends Fragment {




    private ProgressBar spinner;
    private RecyclerView LoadVouchers;
    private Vouches vouches_;


    private List<Map<String,Object>> vouches;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_voches_page, container, false);
        LoadVouchers = v.findViewById(R.id.LoadVouchers);
        spinner = v.findViewById(R.id.spinner);

        //Request_Vouchers();
        //Temp
         Mapping();
        return v;
    }




    private void Mapping() {

        vouches = new ArrayList<>();
        Map<String,Object> obj = new HashMap<>();
        obj.put("serial",95456789);
        obj.put("amount",10);
        obj.put("mode","medium");

        Map<String,Object> obj1 = new HashMap<>();
        obj1.put("serial",95456789);
        obj1.put("amount",20);
        obj1.put("mode","chief whip");


        Map<String,Object> obj2 = new HashMap<>();
        obj2.put("serial",95456789);
        obj2.put("amount",30);
        obj2.put("mode","Jack");

        vouches.add(obj);
        vouches.add(obj1);
        vouches.add(obj2);

        setLayout(vouches);
    }

    private void Request_Vouchers() {
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.VOCHERS(new utilJava()
                .GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.SIGNED_IN_USER))));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                //setLayout((List<Map<String, Object>>)response.body().get("message"));
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
            }
        });
    }

    private void setLayout(List<Map<String, Object>> vouches) {

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        vouches_ = new Vouches((vouches),getContext());
        LoadVouchers.setAdapter(vouches_);
        LoadVouchers.setLayoutManager(manager);
        spinner.setVisibility(View.INVISIBLE);
    }
}