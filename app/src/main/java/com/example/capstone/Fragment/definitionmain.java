package com.example.capstone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone.R;

public class DefinitionMain extends Fragment {
    private TextView tvDefinitionName2,tvDefinitionDescription;
    private ImageView ivDefinitionImage;
    private String defName,defDescription,defImgURL;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.fragment_definitionmain, container, false);

        tvDefinitionName2=viewroot.findViewById(R.id.tvDefinitionName2);
        tvDefinitionDescription=viewroot.findViewById(R.id.tvDefinitionDescription);

        ivDefinitionImage=viewroot.findViewById(R.id.ivDefinitionImage);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            defName=bundle.getString("DefinitionName");
            defDescription=bundle.getString("DefinitionDescription");
            defImgURL=bundle.getString("DefinitionImageURL");
            tvDefinitionName2.setText(defName);
            tvDefinitionDescription.setText(defDescription);
            Glide.with(getActivity()).load(defImgURL).into(ivDefinitionImage);

        }

        return  viewroot;
    }


}