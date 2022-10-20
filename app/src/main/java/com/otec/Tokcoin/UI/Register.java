package com.otec.Tokcoin.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText email, password, confirmPass, bankAccount_No, NameOnAccount;
    private FloatingActionButton register;
    private Spinner bankName;
    private ProgressBar spin, loadingBanks;
    private ArrayAdapter<?> arrayAdapter;


    private String bank_selected="";
    private String TAG = "Register";
    private List<Map<String, Object>> banks;
    private List<String> banksArrays;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        LoadListOfBanks();

        bankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank_selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(e -> {
                  if (new utilKotlin().check(email) | new utilKotlin().check(password) | new utilKotlin().check(confirmPass) | new utilKotlin().check(NameOnAccount) | new utilKotlin().check(bankAccount_No)  | bank_selected.equals("Select Bank"))
                        new utilKotlin().message2("Pls fill out all fields ! ", getApplicationContext());
                  else
                      if (bank_selected.equals("Select Bank") || bank_selected.trim().length()<=0)
                            new utilKotlin().message2("Pls select your bank", getApplicationContext());
                     else
                          AuthNewUser(e);



        });


    }

    private void init() {
        register = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmpassword);
        NameOnAccount = findViewById(R.id.NameOnAccount);
        bankAccount_No = findViewById(R.id.Bank_AccountNo);
        bankName = findViewById(R.id.bank_name);
        register = findViewById(R.id.register);
        loadingBanks = findViewById(R.id.loadingBanks);
        spin = findViewById(R.id.spin);
        banks = new ArrayList<>();
        banksArrays = new ArrayList<>();
        spin.setVisibility(View.INVISIBLE);
    }

    private void LoadListOfBanks() {
        Request base_config = Base_config.getRetrofit().create(Request.class);
        Call<List<Map<String, Object>>> request = base_config.getListOfBank();
        request.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                banks = response.body();
                for (Map<String,Object> bank : banks)
                      banksArrays.add(bank.get("name").toString());
                    if(banksArrays != null)
                           PopulateSpinner(banksArrays);
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                new utilKotlin().message2("Error occurred couldn't load list of banks " + t.getMessage(), getApplicationContext());
            }
        });
    }



    private void PopulateSpinner(List<String> body) {
        arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.text_pad2, body);
        arrayAdapter.setDropDownViewResource(R.layout.text_pad2);
        bankName.setAdapter(arrayAdapter);
        loadingBanks.setVisibility(View.GONE);
    }



    private void AuthNewUser(View e) {
        spin.setVisibility(View.VISIBLE);
        new utilJava().hideKeyboardFrom(e, getApplicationContext());
        if (confirmPass.getText().toString().equals(password.getText().toString()))
            AddToDB();
        else {
            new utilKotlin().message2("Password doesn't match ", getApplicationContext());
            spin.setVisibility(View.INVISIBLE);
        }
    }



    private void AddToDB() {
    Map<String, Object> user, pack, details;
    user = new HashMap<>();
    pack = new HashMap<>();
    details = new HashMap<>();
    user.put("email", email.getText().toString());
    user.put("password", password.getText().toString());
    user.put("avatar", 0);
    details.put("bankaccountno", bankAccount_No.getText().toString());
    details.put("Nameonaccount", NameOnAccount.getText().toString());
    details.put("bankselected", bank_selected);
    details.put("bal", 0);
    details.put("gas", 0);
    pack.put("User", user);
    pack.put("User_details", details);
    pack.put("User_locations", null);

        Request base_config = Base_config.getRetrofit().create(Request.class);
        Call<Map<String, Object>> request = base_config.postAuthUser(pack);
            request.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.body().get("message").toString().equals("Account created")) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    spin.setVisibility(View.INVISIBLE);
                }
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
        spin.setVisibility(View.INVISIBLE);
    }


}