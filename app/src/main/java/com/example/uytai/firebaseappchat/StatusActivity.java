package com.example.uytai.firebaseappchat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button mChangeBtn;
    private TextInputLayout mStatus;
    private ProgressDialog mProgress;

    //FireBase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        AnhXa();
        ActionToolBar();
        ChangeStatus();
        //FireBase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
    }

    private void ChangeStatus() {
        String status_value = getIntent().getStringExtra("status_value");
        mStatus.getEditText().setText(status_value);
        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();
                String status = mStatus.getEditText().getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgress.dismiss();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "There were some error in saving Changes", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AnhXa() {
        mToolbar = findViewById(R.id.status_toolbar);
        mStatus = findViewById(R.id.status_your);
        mChangeBtn = findViewById(R.id.status_change_btn);
        mProgress = new ProgressDialog(StatusActivity.this);
    }
}
