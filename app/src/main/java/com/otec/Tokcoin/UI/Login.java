package com.otec.Tokcoin.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText emailF, passwordf;
    private FloatingActionButton login_btn;
    private TextView register, report;
    private ProgressBar spin;
    private Fragment fr;


    private boolean decide = false;
    private long backPressed = 0;
    private int TimeLapsed = 2000;
    private String TAG = "Login";


    @Override
    protected void onResume() {
        super.onResume();
        decide = true;
    }

    @Override
    public void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.fragment_login);

        emailF = findViewById(R.id.emailf);
        passwordf = findViewById(R.id.passwordf);
        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);
        report = findViewById(R.id.report);
        spin = findViewById(R.id.spin);


        register.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Register.class));
        });

        login_btn.setOnClickListener(v -> {
            if (new utilKotlin().verfiy(emailF, passwordf)) {
                spin.setVisibility(View.VISIBLE);
                request();
            } else
                new utilKotlin().message2("Pls fill out all fields ! ", getApplicationContext());
        });
        report.setOnClickListener(e -> {
            startActivity(new Intent(getApplicationContext(), Report_issues.class));
        });
    }


    private void request() {
        Request request = Base_config.getRetrofit().create(Request.class);
        Call<Map<String, Object>> list = request.SIGN_IN(GET_USER_CRED(emailF.getText().toString(), passwordf.getText().toString(), new utilJava().init(getApplicationContext()).getString(getString(R.string.DEVICE_TOKEN), "")));
        list.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String,Object> obj = (Map<String, Object>) response.body().get("message");
                Map<String,Object> u = (Map<String, Object>) obj.get("User");
                if (response.body().get("message").toString().contains("No user associated with this account !"))
                    new utilKotlin().Message((Activity) getApplicationContext(), "No user associated with this account !");
                else if (response.body().get("message").toString().contains("Email or Password Doesn't match !"))
                    new utilKotlin().Message((Activity) getApplicationContext(), "No user associated with this account !");
                else
                    new utilJava().SET_CACHED_USER(u, getString(R.string.SIGNED_IN_USER), getApplicationContext());
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getApplicationContext());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private Map<String, Object> GET_USER_CRED(String mail, String pass, String device_token) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("mail", mail);
        obj.put("pass", pass);
        obj.put("dv", device_token);
        return new utilJava().Wrap(obj);
    }


    public void onBackPressed() {
        if (decide) {
            new utilKotlin().message2("Press twice to exit", getApplicationContext());
            if (backPressed + TimeLapsed > System.currentTimeMillis()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            backPressed = System.currentTimeMillis();
        }
    }
}