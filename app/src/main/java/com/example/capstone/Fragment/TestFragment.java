package com.example.capstone.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.capstone.Activity.QuestionActivity;
import com.example.capstone.R;


public class TestFragment extends Fragment {
    private TextView lessonTitle,tvTestType,tvChapterNumber;
    private Button btnAttempt;
    private String LessonName,LessonType,ChapterNumber,TestNode;

    public TestFragment() {
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
        btnAttempt=rootView.findViewById(R.id.btnAttempt);

        Bundle bundle = getArguments();
        bundle.getString("lessonType");
        TestNode=bundle.getString("TestNode");

        LessonName=bundle.getString("lessonName");
        LessonType=bundle.getString("lessonType");
        ChapterNumber=String.valueOf(bundle.getInt("ChapterNumber"));

        lessonTitle.setText(LessonName);
        tvTestType.setText(LessonType);
        tvChapterNumber.setText(ChapterNumber);

        btnAttempt.setOnClickListener(v -> {

            Intent i = new Intent(getContext(), QuestionActivity.class);
            i.putExtra("LessonName", LessonName);
            i.putExtra("LessonType", LessonType);
            i.putExtra("ChapterNumber",ChapterNumber);
            i.putExtra("TestNode",TestNode);


            startActivity(i);
            getActivity().onBackPressed();
        });



       // String.valueOf(bundle.getInt("chapnum")

        return rootView;
    }
}
