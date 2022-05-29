package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

import com.otec.crevatech.Adapater.JoinGroupCall;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {


    private RecyclerView recyclerView;
    private JoinGroupCall joinGroupCall;
    private ProgressBar spinners;

    private String TAG = "Home";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.Stakes);
        spinners = v.findViewById(R.id.spinners);
        Request();
        return v;
    }



    private  void Request(){
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<models> isFunded = config._REQUEST(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER))));
        isFunded.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                setLayout(response.body().getMessage());
            }
            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(),getContext());
            }
        });
    }




    private void setLayout(List<Map<String, Object>> message) {
        joinGroupCall = new JoinGroupCall(message,getContext(),1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(joinGroupCall);
        ScaleInAnimationAdapter adapter = new ScaleInAnimationAdapter(joinGroupCall);
        adapter.setDuration(3000);
        adapter.setInterpolator(new AccelerateDecelerateInterpolator());
        adapter.setFirstOnly(false);
        spinners.setVisibility(View.INVISIBLE);

    }
}