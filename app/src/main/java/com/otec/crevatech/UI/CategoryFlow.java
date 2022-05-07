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
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFlow extends AppCompatActivity {

    private Button sports,music,Vehicles,politics,history,science,religion,general,completeReg;
    private ProgressBar spinnerload;


    private List<String> listOfCategory;
    private String TAG = "Category_flowPage";


    @Override
    public void onBackPressed() {
        new utilKotlin().message2("Pls complete account Registration !", getApplicationContext());
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
            sports.setOnClickListener(e->{
                if(!listOfCategory.contains(sports.getText().toString())) {
                    listOfCategory.add(sports.getText().toString());
                    sports.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            music.setOnClickListener(e->{
                if(!listOfCategory.contains(music.getText().toString())) {
                    listOfCategory.add(music.getText().toString());
                    music.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            Vehicles.setOnClickListener(e->{
                if(!listOfCategory.contains(Vehicles.getText().toString())) {
                    listOfCategory.add(Vehicles.getText().toString());
                     Vehicles.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            politics.setOnClickListener(e->{
                if(!listOfCategory.contains(politics.getText().toString())) {
                    listOfCategory.add(politics.getText().toString());
                    politics.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            history.setOnClickListener(e->{
                if(!listOfCategory.contains(history.getText().toString())) {
                    listOfCategory.add(history.getText().toString());
                    history.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            science.setOnClickListener(e->{
                if(!listOfCategory.contains(science.getText().toString())) {
                    listOfCategory.add(science.getText().toString());
                    science.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            religion.setOnClickListener(e->{
                if(!listOfCategory.contains(religion.getText().toString())) {
                    listOfCategory.add(religion.getText().toString());
                    religion.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            general.setOnClickListener(e->{
                if(!listOfCategory.contains(general.getText().toString())) {
                    listOfCategory.add(general.getText().toString());
                    general.setBackgroundResource(R.drawable.inputlines1);
                }
            });
            completeReg.setOnClickListener(e->{
                if(listOfCategory.size()<=2)
                    new utilKotlin().message2("Pls select a least 3 category  " , getApplicationContext());
                else
                    //Check for null in documents call
                    Next();
            });
    }

    private void Next() {
        spinnerload.setVisibility(View.VISIBLE);
        completeReg.setEnabled(false);
        Map<String,Object> user,pack,details;
        user = new HashMap<>();
        pack = new HashMap<>();
        details = new HashMap<>();
        details.put("bal", 0);
        details.put("bankAccount_No", getIntent().getBundleExtra("user").getString("bankAccount_No"));
        details.put("NameOnAccount", getIntent().getBundleExtra("user").getString("NameOnAccount"));
        details.put("bank_selected", getIntent().getBundleExtra("user").getString("bank_selected"));
        user.put("user_id", getIntent().getBundleExtra("user").getString("user_id"));
        user.put("email", getIntent().getBundleExtra("user").getString("email"));
        user.put("IMEI", getIntent().getBundleExtra("user").getString("IMEI"));
        user.put("UserCategory", listOfCategory);
        pack.put("User",user);
        pack.put("User_details",details);
        FirebaseFirestore.getInstance().collection(getString(R.string.REGISTER_USER)).document(FirebaseAuth.getInstance().getUid())
                .set(pack).addOnCompleteListener(evt -> {
            if (evt.isSuccessful())
                startActivity(new Intent(getApplicationContext(), Login.class));
            else
                new utilKotlin().message2("Error occurred while adding user  " + evt.getException(), getApplicationContext());
            spinnerload.setVisibility(View.VISIBLE);
        });

    }
}