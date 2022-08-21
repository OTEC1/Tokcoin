package com.otec.Tokcoin.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

public class Shop extends AppCompatActivity {

    private RelativeLayout relativeLayout;

    private String TAG = "Shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        relativeLayout = findViewById(R.id.shopPage);
        new utilKotlin().Top_status_bar(getWindow(), this, relativeLayout);
         new utilJava().openFragment(new VochesPage(),"VochesPage",1,this);
    }

}