package com.otec.crevatech.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

public class Shop extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private ProgressBar spinner;
    private RecyclerView LoadVouchers;
    private Vouches vouches_;


    private List<Map<String,Object>> vouches;
    private String TAG = "Shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        relativeLayout = findViewById(R.id.shopPage);
        LoadVouchers = findViewById(R.id.LoadVouchers);
        spinner = findViewById(R.id.spinner);
        new utilKotlin().Top_status_bar(getWindow(), this, relativeLayout);
        //Request_Vouchers();
        //Temp
        Mapping();
    }

    private void Mapping() {

//        [{Voches:{serial:987654569,amount:10}},{Voches:{serial:43212567,amount:25}},{Voches:{serial:765436789,amount:35}},
//        {Voches:{serial:87654569,amount:45}},{Voches:{serial:54309823,amount:55}},{Voches:{serial:09812575,amount:75}}]

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
                    .GET_GROUP(new utilJava().GET_CACHED_MAP(getApplicationContext(),getString(R.string.SIGNED_IN_USER))));
                    isFunded.enqueue(new Callback<Map<String, Object>>() {
                        @Override
                        public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                            //setLayout((List<Map<String, Object>>)response.body().get("message"));
                        }

                        @Override
                        public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                            new utilKotlin().message2(t.getMessage(), getApplicationContext());
                        }
                });
    }

    private void setLayout(List<Map<String, Object>> vouches) {

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        vouches_ = new Vouches((vouches),getApplicationContext());
        LoadVouchers.setAdapter(vouches_);
        LoadVouchers.setLayoutManager(manager);
        spinner.setVisibility(View.INVISIBLE);
    }
}