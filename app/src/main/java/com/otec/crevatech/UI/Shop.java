package com.otec.crevatech.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilKotlin;

public class Shop extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private String TAG = "Shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        relativeLayout = findViewById(R.id.shopPage);
        new utilKotlin().Top_status_bar(getWindow(), this, relativeLayout);
    }
}