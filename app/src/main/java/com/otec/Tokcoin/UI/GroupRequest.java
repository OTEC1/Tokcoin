package com.otec.Tokcoin.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.otec.Tokcoin.Adapater.JoinGroupCall;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.model.models;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupRequest extends Fragment {


    private RecyclerView recyclerView;
    private JoinGroupCall joinGroupCall;
    private ProgressBar spinners;

    private String TAG = "GroupRequest";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_request, container, false);
        recyclerView = view.findViewById(R.id.loadGroups);
        spinners = view.findViewById(R.id.spinners);
        Request();
        return view;
    }

    private void Request() {
        Request config = Base_config.getRetrofit().create(Request.class);
        Call<models> isFunded = config.I_REQUEST(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER))));
        isFunded.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                Log.d(TAG, "onResponse: "+response.body().getMessage());
                if (response.body().getMessage() != null)
                    setLayout(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
            }
        });
    }


    private void setLayout(List<Map<String, Object>> message) {
        joinGroupCall = new JoinGroupCall(message, getContext(), 2);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(joinGroupCall);
        spinners.setVisibility(View.INVISIBLE);

    }
}