package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText edtResetPasswordEmail;
    private Button btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtResetPasswordEmail=findViewById(R.id.edtResetPasswordEmail);
        btnResetPassword=findViewById(R.id.btnResetPassword);
        progressBar=findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        }

        public void resetPassword(){
        String email=edtResetPasswordEmail.getText().toString().trim();

            if(email.isEmpty()){
                edtResetPasswordEmail.setError("Email is required.");
                edtResetPasswordEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtResetPasswordEmail.setError("Please provide valid email.");
                edtResetPasswordEmail.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this, "Check your email to reset password.", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(ForgotPassword.this, "Try again, something wrong happen.", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });


    }
}
