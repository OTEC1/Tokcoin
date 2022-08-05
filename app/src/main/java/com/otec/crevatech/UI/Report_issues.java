package com.otec.crevatech.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.HashMap;
import java.util.Map;

public class Report_issues extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private Fragment fr = null;

    private   long back_pressed = 0;
    private   int  Time_lapsed = 2000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issues);
        relativeLayout = findViewById(R.id.issue_submit);
        new utilKotlin().Top_status_bar(getWindow(),this, relativeLayout);
        new utilJava().openFragment(new report(),"report",0,this);
    }


    @Override
    public void onBackPressed() {
                new utilKotlin().message2("Press twice to exit", getApplicationContext());
                    if (back_pressed + Time_lapsed > System.currentTimeMillis()) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                back_pressed = System.currentTimeMillis();
    }

}