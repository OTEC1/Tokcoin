package com.otec.Tokcoin.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

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