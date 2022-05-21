package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Group_creation extends Fragment {


    private Button group_create;
    private EditText groupName,Amount,Liquidator_size,miner_stake;
    private ProgressBar  spinners;

    private FirebaseFirestore firestore;
    private String TAG = "Group_creation";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group_creation, container, false);
        group_create = view.findViewById(R.id.group_create);
        groupName = view.findViewById(R.id.groupNames);
        Amount = view.findViewById(R.id.Amount);
        Liquidator_size = view.findViewById(R.id.Liquidator_size);
        miner_stake = view.findViewById(R.id.miner_stake);
        spinners = view.findViewById(R.id.spinners);
        firestore = FirebaseFirestore.getInstance();


        group_create.setOnClickListener(e->{
            if(Val(groupName,Amount,Liquidator_size,miner_stake))
                   Check();
            else
                    new utilKotlin().message2("Pls fill out all fields !", getContext());
        });
        return view;
    }




    private boolean Val(EditText groupName, EditText threshold, EditText liquidator_stake, EditText miner_stake) {
        return groupName.getText().toString().trim().length() > 0 && threshold.getText().toString().trim().length() > 0 && liquidator_stake.getText().toString().trim().length() > 0 && miner_stake.getText().toString().trim().length() > 0;
    }


    private void Check() {
        spinners.setVisibility(View.VISIBLE);
        Request_class request_class = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String,Object>> isFunded = request_class.SendGroupRequest(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.SIGNED_IN_USER)),groupName,Amount,Liquidator_size,miner_stake));
        isFunded.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                 new utilKotlin().message2(response.body().get("message").toString(),getContext());
                spinners.setVisibility(View.INVISIBLE);
                groupName.setText(""); Amount.setText(""); Liquidator_size.setText(""); miner_stake.setText("");
            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                Log.d(TAG, "onFailure:  " + t.getMessage());
                spinners.setVisibility(View.INVISIBLE);
            }
        });

    }
}