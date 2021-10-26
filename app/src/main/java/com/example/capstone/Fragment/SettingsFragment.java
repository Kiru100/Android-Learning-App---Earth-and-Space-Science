package com.example.capstone.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstone.Activity.LoginActivity;
import com.example.capstone.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        rootView.findViewById(R.id.btnAbout).setOnClickListener(this::onClick);
        rootView.findViewById(R.id.btnLogout).setOnClickListener(this::onClick);
        rootView.findViewById(R.id.btnManageAccount).setOnClickListener(this::onClick);


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
            case R.id.btnManageAccount:
                navController.navigate(R.id.action_settingsFragment_to_manageAccount);
                break;
            case R.id.btnAbout:
                navController.navigate(R.id.action_settingsFragment_to_fragmentAbout);
                break;
        }

    }
}
