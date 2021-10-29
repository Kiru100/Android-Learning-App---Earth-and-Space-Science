package com.example.capstone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Model.Student;
import com.example.capstone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    //TODO: implement Show Password button
    private boolean connected;

    private FirebaseAuth mAuth;
    private  TextView tvHaveAccount,btnRegister;
    private EditText edtFirstName, edtLastName, edtEmail, edtPassword;

    private CheckBox cbRegistrationShowPassword;

    private Spinner spinnerSection;

    private ProgressBar progressBar;

    String [] StudentSections={"Section 1","Section 2","Section 3","Section 4","Section 5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Spinner
        spinnerSection =findViewById(R.id.spinnerSection);
        spinnerSection.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        tvHaveAccount = findViewById(R.id.tvHaveAccount);
        tvHaveAccount.setOnClickListener(this);


        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        edtFirstName =  findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        progressBar= findViewById(R.id.loginprogressBar);

        cbRegistrationShowPassword=findViewById(R.id.cbRegistrationShowPassword);


        //Spinner Array Adapter
        ArrayAdapter aa= new ArrayAdapter(RegistrationActivity.this,android.R.layout.simple_spinner_item,StudentSections);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(aa);

        //checkbox show password
        cbRegistrationShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                edtPassword.setSelection(edtPassword.length());
            }else{
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                edtPassword.setSelection(edtPassword.length());
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvHaveAccount:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fname = edtFirstName.getText().toString().trim();
        String lname = edtLastName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String section =  spinnerSection.getSelectedItem().toString();

        if (fname.isEmpty()) {
            edtFirstName.setError("First name is required.");
            edtFirstName.requestFocus();
            return;
        }
        if (lname.isEmpty()) {
            edtLastName.setError("Last name is required.");
            edtLastName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please provide valid email.");
            edtEmail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtPassword.setError("Minimum password length should be 6 characters!");
            edtPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //return internet connection status
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Student student=new Student(fname,lname,email,section,0);

                            FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistrationActivity.this, "Register successfully. \n Please check your email address \n to verify email. ", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(RegistrationActivity.this, "Failed to register, Try again", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });
                       }else {
                            //To show user if connected to internet or not
                            if(!connected){
                                Toast.makeText(RegistrationActivity.this, "Failed to register, \n Please check your internet connection.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }else{
                                //TODO: show user if the email already exist
                                Toast.makeText(RegistrationActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    }
                });
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
