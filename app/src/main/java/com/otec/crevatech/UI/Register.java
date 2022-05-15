package com.otec.crevatech.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText email, password, confirmPass, bankAccount_No, NameOnAccount;
    private FloatingActionButton register;
    private Spinner bankName;
    private ProgressBar spin, loadingBanks;
    private ArrayAdapter arrayAdapter;


    private String bank_selected="";
    private String TAG = "Register";
    private List<Map<String, Object>> banks;
    private List<String> banksArrays;
    private boolean started_reg_process = false;


    @Override
    public void onBackPressed() {
        if(!started_reg_process)
            super.onBackPressed();
        else
            new utilKotlin().message2("Pls wait account Registration in progress", getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inits();
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
            if(!started_reg_process)
                  if (!new utilKotlin().check(email) && !new utilKotlin().check(password) && !new utilKotlin().check(confirmPass) && !new utilKotlin().check(NameOnAccount) && !new utilKotlin().check(bankAccount_No) && !bank_selected.equals("Select Bank"))
                    AuthNewUser();
                  else
                      if (bank_selected.equals("Select Bank") || bank_selected.trim().length()<=0)
                            new utilKotlin().message2("Pls select your bank", getApplicationContext());
                     else
                       new utilKotlin().message2("Pls fill out all fields ! ", getApplicationContext());
            else
                new utilKotlin().message2("Registration in Progress ! ", getApplicationContext());

        });


    }

    private void inits() {
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
        Request_class base_config = Base_config.getRetrofit().create(Request_class.class);
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
        arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.text_pad, body);
        arrayAdapter.setDropDownViewResource(R.layout.text_pad);
        bankName.setAdapter(arrayAdapter);
        loadingBanks.setVisibility(View.GONE);
    }

    private void AuthNewUser() {
        started_reg_process = true;
        spin.setVisibility(View.VISIBLE);
        if (confirmPass.getText().toString().equals(password.getText().toString()))
            PostToEndPoint();
        else {
            new utilKotlin().message2("Password doesn't match ", getApplicationContext());
            started_reg_process = false;
            spin.setVisibility(View.INVISIBLE);
        }
    }

    private void PostToEndPoint() {
        AddToDB();
    }

    private void AddToDB() {
        Bundle bundle = new Bundle();
        bundle.putString("email", email.getText().toString());
        bundle.putString("password", password.getText().toString());
        bundle.putString("IMEI", GET_IMEI().toString());
        bundle.putString("bankAccountNo", bankAccount_No.getText().toString());
        bundle.putString("NameOnAccount", NameOnAccount.getText().toString());
        bundle.putString("bankSelected", bank_selected);
        startActivity(new Intent(getApplicationContext(), CategoryFlow.class).putExtra("user",bundle));
        started_reg_process = false;
        spin.setVisibility(View.INVISIBLE);
    }

    private Object GET_IMEI() {
        return UUID.randomUUID().toString()+"+_"+FirebaseAuth.getInstance().getUid();

    }
}