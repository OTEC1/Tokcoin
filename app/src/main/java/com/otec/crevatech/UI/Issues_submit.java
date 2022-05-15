package com.otec.crevatech.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilKotlin;

public class Issues_submit extends AppCompatActivity {

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_submit);
        relativeLayout = findViewById(R.id.issue_submit);
        new utilKotlin().Top_status_bar(getWindow(),this, relativeLayout);
    }
}