package com.example.capstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PreTest extends Fragment {
    private TextView lessonTitle,tvTestType,tvChapterNumber;


    public PreTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_pre_test, container, false);

        tvTestType=rootView.findViewById(R.id.tvTestType);
        lessonTitle=rootView.findViewById(R.id.tvTestTitle);
        tvChapterNumber=rootView.findViewById(R.id.tvChapterNumber);

        Bundle bundle = getArguments();
        bundle.getString("lessonType");

        lessonTitle.setText(bundle.getString("lessonName"));
        tvTestType.setText(bundle.getString("lessonType"));
        tvChapterNumber.setText(String.valueOf(bundle.getInt("ChapterNumber")));




       // String.valueOf(bundle.getInt("chapnum")

        return rootView;
    }
}
