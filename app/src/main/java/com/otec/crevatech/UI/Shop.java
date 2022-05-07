package com.otec.crevatech.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.otec.crevatech.R;
import  com.otec.crevatech.utils.utilKotlin;
public class Shop extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        relativeLayout = findViewById(R.id.shopPage);
        new  utilKotlin().Top_status_bar(getWindow(),this, relativeLayout);

    }
}