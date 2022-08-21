package com.otec.Tokcoin.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;
import  com.otec.Tokcoin.utils.utilKotlin;

import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText  emailF,passwordf;
    private FloatingActionButton login_btn;
    private TextView register, report;
    private ProgressBar spin;
    private Fragment fr;
    
    
    private  boolean decide = false;
    private long backPressed = 0;
    private int TimeLapsed = 2000;
    private String TAG ="Login";


    @Override
    protected void onResume() {
        super.onResume();
        decide = true;
    }

    @Override
    public void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.fragment_login);

        emailF =  findViewById(R.id.emailf);
        passwordf =  findViewById(R.id.passwordf);
        login_btn =  findViewById(R.id.login_btn);
        register =  findViewById(R.id.register);
        report =  findViewById(R.id.report);
        spin =  findViewById(R.id.spin);



        register.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),Register.class));
        });

        login_btn.setOnClickListener(v->{
          if(new utilKotlin().verfiy(emailF,passwordf)) {
              spin.setVisibility(View.VISIBLE);
              FirebaseAuth.getInstance().signInWithEmailAndPassword(emailF.getText().toString(), passwordf.getText().toString())
                      .addOnCompleteListener(e -> {
                          if (e.isSuccessful())
                              LoadUserDetails();
                          else {
                              new utilKotlin().message2("Error occurred " + e.getException(), getApplicationContext());
                              spin.setVisibility(View.INVISIBLE);
                          }
                          });
          }
          else
              new utilKotlin().message2("Pls fill out all fields ! ", getApplicationContext());
        });


        report.setOnClickListener( e-> {
                startActivity(new Intent(getApplicationContext(),Report_issues.class));
        });

    }

    private void LoadUserDetails() {
        FirebaseFirestore.getInstance().collection(getString(R.string.REGISTER_USER)).document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get()
                .addOnCompleteListener(evt -> {
                        if(evt.isSuccessful())
                            new utilJava().SET_CACHED_USER((Map<String, Object>) evt.getResult().get("User"),getString(R.string.SIGNED_IN_USER),this);
                        else
                            new utilKotlin().message2("Sign in failed !", getApplicationContext());
                });
        }


    public void onBackPressed() {
            if (decide) {
                new utilKotlin().message2("Press twice to exit", getApplicationContext());
                    if (backPressed + TimeLapsed > System.currentTimeMillis()) {
                        Intent intent = new  Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                backPressed = System.currentTimeMillis();
            }
        }
}