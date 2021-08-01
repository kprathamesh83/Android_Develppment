package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText mloginId, mloginPassword;
    private RelativeLayout mloginButton, msignupButton;
    private TextView mforgotPassword;

    ProgressBar mprogressbar;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mloginId = findViewById(R.id.login_id);
        mloginPassword = findViewById(R.id.login_password);
        mloginButton = findViewById(R.id.login_button);
        msignupButton = findViewById(R.id.signup_button);
        mforgotPassword = findViewById(R.id.forgot_password);
        mprogressbar = findViewById(R.id.progressbarofmainactivity);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
          finish();
          startActivity(new Intent(MainActivity.this, home.class));
        }

        mforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, forgotPassword.class);
                startActivity(intent);
            }
        });

        msignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mloginId.getText().toString().trim();
                String password = mloginPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are compulsory", Toast.LENGTH_SHORT).show();
                }
                else {

                    mprogressbar.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkEmailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Email doesn't exist", Toast.LENGTH_SHORT).show();
                                mprogressbar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            Toast.makeText(getApplicationContext(), "Signing in", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, home.class));
        }
        else{
            mprogressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Verify the user first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}