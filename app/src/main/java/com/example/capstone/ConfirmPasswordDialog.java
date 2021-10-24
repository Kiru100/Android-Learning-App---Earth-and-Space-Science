package com.example.capstone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmPasswordDialog extends DialogFragment {
    private EditText edtConfirmPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_password,null);

        builder.setView(view).setTitle("Confirm Password")
                .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    String password = edtConfirmPassword.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("1",password);
                    getParentFragmentManager().setFragmentResult("1",bundle);
                });
        edtConfirmPassword=view.findViewById(R.id.edtConfirmPassword);
        return builder.create();
    }


}