package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.protobuf.Any;
import com.otec.crevatech.Adapater.UserGroupslist;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsLists extends Fragment {

    private ImageView createGroup;
    private RecyclerView recyclerView;
    private ProgressBar progress;



    private String TAG = "GroupsLists";
    private UserGroupslist user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups_lists, container, false);
        createGroup = view.findViewById(R.id.createGroup);
        recyclerView = view.findViewById(R.id.joinedGroup);
        progress = view.findViewById(R.id.progress);
        RequestGroups();
        createGroup.setOnClickListener(e -> {
            new utilJava().openFrag(new Group_creation(), "Group_creation", null, getActivity());
        });
        return view;
    }

    private void RequestGroups() {
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String,Object>> list = request.GT_GROUPS(new utilJava().GET_GROUP( new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER))));
        list.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> l1 = (Map<String, Object>) response.body().get("message");
                Map<String,Object> l2 = (Map<String, Object>) l1.get("listB");
                BuildView(new Question().C(l2.get("raw2")));

            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void BuildView(List<?> raw2) {
        user = new UserGroupslist((List<Map<String, Object>>) raw2,getContext(),2);
        GridLayoutManager manager = new  GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(user);
        progress.setVisibility(View.GONE);
    }
}