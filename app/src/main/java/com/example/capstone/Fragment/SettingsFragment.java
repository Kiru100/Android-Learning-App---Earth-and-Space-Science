package com.example.capstone.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.Activity.LoginActivity;
import com.example.capstone.Model.Student;
import com.example.capstone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private FirebaseUser student;
    private DatabaseReference reference;
    private String userID;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        rootView.findViewById(R.id.btnAbout).setOnClickListener(this);
        rootView.findViewById(R.id.btnLogout).setOnClickListener(this);
        rootView.findViewById(R.id.btnEditEmail).setOnClickListener(this);
        rootView.findViewById(R.id.btnChangePassword).setOnClickListener(this);
        rootView.findViewById(R.id.btnChangePersonalDetails).setOnClickListener(this);


        student = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Students");
        userID=student.getUid();


        final TextView tvFirstLastName =rootView.findViewById(R.id.tvFirstLastName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile= snapshot.getValue(Student.class);
                String sfname=studentProfile.getSFname();
                String slname=studentProfile.getSLname();
                String FullName=sfname+" "+slname;
                tvFirstLastName.setText(FullName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        return rootView;
    }


    @Override
    public void onClick(View view) {
        final NavController navController = Navigation.findNavController(view);
        switch (view.getId()) {
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.btnEditEmail:
                navController.navigate(R.id.action_settingsFragment_to_editEmail);
                break;
            case R.id.btnAbout:
                navController.navigate(R.id.action_settingsFragment_to_fragmentAbout);
                break;
            case R.id.btnChangePassword:
                navController.navigate(R.id.action_settingsFragment_to_editPassword);
                break;
            case R.id.btnChangePersonalDetails:
                navController.navigate(R.id.action_settingsFragment_to_changePersonalDetails);
                break;
            default:

        }

    }
}
