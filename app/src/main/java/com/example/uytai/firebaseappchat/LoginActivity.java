package com.example.uytai.firebaseappchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextInputLayout mLoginEmail, mLoginPass;
    private Button mLogin_btn;
    private ProgressDialog mLoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        AnhXa();
        ThucThi();
        ActionToolBar();
    }

    private void ActionToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ThucThi() {
        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLoginEmail.getEditText().getText().toString();
                String pass = mLoginPass.getEditText().getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){
                    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please wait while we check your credentials.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email, pass);
                }
            }
        });
    }

    private void loginUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mLoginProgress.dismiss();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Can't Sign in. Please check the form and try angain!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void AnhXa() {
        mLoginEmail = findViewById(R.id.login_email);
        mLoginPass = findViewById(R.id.login_password);
        mToolbar = findViewById(R.id.login_toolbar);
        mLogin_btn = findViewById(R.id.login_btn);
        mLoginProgress = new ProgressDialog(this);
    }
}
