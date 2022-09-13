package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.otec.Tokcoin.Adapater.Vouches;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request_class;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class vouche_page extends Fragment {

    private ProgressBar spinner;
    private RecyclerView LoadVouchers;
    private Vouches vouches_;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_vouche_page, container, false);
        LoadVouchers = view.findViewById(R.id.LoadVouchers);
         spinner = view.findViewById(R.id.spinner);
           Request_Vouchers();
        return view;
    }

    private void Request_Vouchers() {
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.VOCHERS(new utilJava()
                .GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.SIGNED_IN_USER))));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                setLayout((List<Map<String, Object>>)response.body().get("message"));
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