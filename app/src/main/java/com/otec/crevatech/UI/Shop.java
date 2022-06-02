package com.otec.crevatech.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private RecyclerView LoadVouchers;


    private List<Map<String,Object>> vouches;
    private String TAG = "Shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        relativeLayout = findViewById(R.id.shopPage);
        LoadVouchers = findViewById(R.id.LoadVouchers);
        new utilKotlin().Top_status_bar(getWindow(), this, relativeLayout);
        Request_Vouchers();
    }

    private void Request_Vouchers() {
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.VOCHERS(new utilJava()
                    .GET_GROUP(new utilJava().GET_CACHED_MAP(getApplicationContext(),getString(R.string.SIGNED_IN_USER))));
                    isFunded.enqueue(new Callback<Map<String, Object>>() {
                        @Override
                        public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                            setLayout((List<Map<String, Object>>)response.body().get("message"));
                        }

                        @Override
                        public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                            new utilKotlin().message2(t.getMessage(), getApplicationContext());
                        }
                });
    }

    private void setLayout(List<Map<String, Object>> vouches) {
        
    }
}