package com.example.capstone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Model.Student;
import com.example.capstone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {
    private Button btnLogout;

//TODO: Subject to remove
    private FirebaseUser student;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnLogout= findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Menu.this, LoginActivity.class));
        });

        student =FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Students");
        userID=student.getUid();

        final TextView emptytvGreetings =(TextView)findViewById(R.id.emptytvGreetings);
        final TextView emptytvFirstName =(TextView)findViewById(R.id.emptytvFirstName);
        final TextView emptytvLastName =(TextView)findViewById(R.id.emptytvLastName);
        final TextView emptytvEmail=(TextView)findViewById(R.id.emptytvEmail);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile= snapshot.getValue(Student.class);
                if(studentProfile!=null){
                    String sfname=studentProfile.getSFname();
                    String slname=studentProfile.getSLname();
                    String semail=studentProfile.getSEmail();

                    emptytvGreetings.setText("Welcome "+sfname+"!");
                    emptytvFirstName.setText(sfname);
                    emptytvLastName.setText(slname);
                    emptytvEmail.setText(semail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Menu.this, "Something wrong happen!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
