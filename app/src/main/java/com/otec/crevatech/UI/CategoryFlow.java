package com.otec.crevatech.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class CategoryFlow extends AppCompatActivity {

    private Button sports, music, Vehicles, politics, history, science, religion, general, completeReg;
    private ProgressBar spinnerload;


    private List<String> listOfCategory;
    private String TAG = "Category_flowPage";
    private boolean started = false;


    @Override
    public void onBackPressed() {
        if (started)
            new utilKotlin().message2("Pls wait Registration in progress !", getApplicationContext());
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_flow_page);

        sports = findViewById(R.id.sport);
        music = findViewById(R.id.music);
        Vehicles = findViewById(R.id.Vehicles);
        politics = findViewById(R.id.politics);
        history = findViewById(R.id.history);
        science = findViewById(R.id.science);
        religion = findViewById(R.id.religion);
        general = findViewById(R.id.general);
        completeReg = findViewById(R.id.completeReg);
        spinnerload = findViewById(R.id.spinnerload);
        listOfCategory = new ArrayList<>();
        sports.setOnClickListener(e -> {
            if (!listOfCategory.contains(sports.getText().toString())) {
                listOfCategory.add(sports.getText().toString());
                sports.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        music.setOnClickListener(e -> {
            if (!listOfCategory.contains(music.getText().toString())) {
                listOfCategory.add(music.getText().toString());
                music.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        Vehicles.setOnClickListener(e -> {
            if (!listOfCategory.contains(Vehicles.getText().toString())) {
                listOfCategory.add(Vehicles.getText().toString());
                Vehicles.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        politics.setOnClickListener(e -> {
            if (!listOfCategory.contains(politics.getText().toString())) {
                listOfCategory.add(politics.getText().toString());
                politics.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        history.setOnClickListener(e -> {
            if (!listOfCategory.contains(history.getText().toString())) {
                listOfCategory.add(history.getText().toString());
                history.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        science.setOnClickListener(e -> {
            if (!listOfCategory.contains(science.getText().toString())) {
                listOfCategory.add(science.getText().toString());
                science.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        religion.setOnClickListener(e -> {
            if (!listOfCategory.contains(religion.getText().toString())) {
                listOfCategory.add(religion.getText().toString());
                religion.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        general.setOnClickListener(e -> {
            if (!listOfCategory.contains(general.getText().toString())) {
                listOfCategory.add(general.getText().toString());
                general.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        completeReg.setOnClickListener(e -> {
            if (listOfCategory.size() <= 2)
                new utilKotlin().message2("Pls select a least 3 category  ", getApplicationContext());
            else
                Next();
        });
    }

    private void Next() {
        started = true;
        spinnerload.setVisibility(View.VISIBLE);
        completeReg.setEnabled(false);
        Map<String, Object> user, pack, details;
        Bundle bun = getIntent().getBundleExtra("user");
        user = new HashMap<>();
        pack = new HashMap<>();
        details = new HashMap<>();
        details.put("bankAccountNo", bun.getString("bn"));
        details.put("NameOnAccount", bun.getString("ba"));
        details.put("bankSelected", bun.getString("bs"));
        user.put("email", getIntent().getBundleExtra("user").getString("email"));
        user.put("password", getIntent().getBundleExtra("user").getString("password"));
        user.put("avatar", 0);
        details.put("bal", 0);
        details.put("gas", 0);
        user.put("UserCategory", listOfCategory);
        pack.put("User", user);
        pack.put("User_details", details);
        pack.put("User_locations", null);
        Log.d(TAG, "Next: "+pack);
        Request_class base_config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> request = base_config.postAuthUser(pack);
        request.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.d(TAG, "onResponse: "+response.body().get("message"));
                if (response.body().get("message").toString().equals("Account created"))
                    startActivity(new Intent(getApplicationContext(), Login.class));
                else
                    Clear("Error occurred when creating account " + response.body().get("message"));

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Clear("Snap error occurred when creating account " + t.getMessage());
            }
        });
    }

    private void Clear(String s) {
        new utilKotlin().message2(s, getApplicationContext());
        spinnerload.setVisibility(View.INVISIBLE);
        completeReg.setEnabled(true);
    }
}