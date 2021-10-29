package com.example.capstone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditPasswordFragment extends Fragment {
    private Button btnresetPW;
    private FirebaseAuth auth;
    public EditPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_password, container, false);

        //TODO:Restrict user from spamming the button to prevent multiple emails
        //TODO:Add Text telling users to click reset password then check email
        //TODO: Show no internet connection toast/message


        btnresetPW=view.findViewById(R.id.btnResetPW);
        auth=FirebaseAuth.getInstance();

        btnresetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(getContext(), "Please check your email to reset your password.", Toast.LENGTH_LONG).show();
                           final NavController navController = Navigation.findNavController(view);
                           navController.navigate(R.id.action_editPassword_to_manageAccount);
                       }
                }
            });

            }
        });

        return view;
    }
}
