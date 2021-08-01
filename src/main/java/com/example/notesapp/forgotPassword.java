package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewOnReceiveContentListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class forgotPassword extends AppCompatActivity {

    private EditText mregisteredEmail;
    private RelativeLayout mforgotButton;
    private TextView mgotoLogin;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Objects.requireNonNull(getSupportActionBar()).hide();
        mregisteredEmail = findViewById(R.id.registered_emil);
        mforgotButton = findViewById(R.id.forgot_button);
        mgotoLogin = findViewById(R.id.goto_login);

        firebaseAuth = FirebaseAuth.getInstance();
        mgotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mforgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mregisteredEmail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email not entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    // need to write later for sending verification email
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password reset email sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotPassword.this, MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Oops, something went wrong!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
}