package com.example.capstone;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ManageAccount extends Fragment implements View.OnClickListener{

    public ManageAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manage_account, container, false);

        rootView.findViewById(R.id.btnEditEmail).setOnClickListener(this::onClick);
        rootView.findViewById(R.id.btnEditPassword).setOnClickListener(this::onClick);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        final NavController navController = Navigation.findNavController(view);
        switch (view.getId()) {
            case R.id.btnEditPassword:
                navController.navigate(R.id.action_manageAccount_to_editPassword);
                break;
            case R.id.btnEditEmail:
                navController.navigate(R.id.action_manageAccount_to_editEmail);
                break;
        }

    }
}
