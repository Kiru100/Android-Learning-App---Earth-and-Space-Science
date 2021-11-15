package com.example.capstone.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone.Activity.QuestionActivity;
import com.example.capstone.R;

import java.util.Objects;


public class TestFragment extends Fragment {
    private TextView lessonTitle,tvTestType,tvChapterNumber,tvItemNumber;
    private Button btnAttempt;
    private String LessonName,LessonType,ChapterNumber,TestNode,ImageURl;
    private ImageView ivChapterImage2;
    private int testNumber;

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
        ivChapterImage2=rootView.findViewById(R.id.ivChapterImage2);
        tvItemNumber=rootView.findViewById(R.id.tvItemNumber);

        Bundle bundle = getArguments();
        assert bundle != null;
        bundle.getString("lessonType");
        TestNode=bundle.getString("TestNode");

        LessonName=bundle.getString("lessonName");
        LessonType=bundle.getString("lessonType");
        ImageURl=bundle.getString("chapterImageURL");
        testNumber=bundle.getInt("testNumber");

        ChapterNumber=String.valueOf(bundle.getInt("ChapterNumber"));

        lessonTitle.setText(LessonName);
        tvTestType.setText(LessonType);
        tvChapterNumber.setText(ChapterNumber);
        Glide.with(this).load(ImageURl).into(ivChapterImage2);

        String tvItemNumberMessage="Item number: "+testNumber;
        tvItemNumber.setText(tvItemNumberMessage);


        btnAttempt.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent i = new Intent(getContext(), QuestionActivity.class);
                        i.putExtra("LessonName", LessonName);
                        i.putExtra("LessonType", LessonType);
                        i.putExtra("ChapterNumber",ChapterNumber);
                        i.putExtra("TestNode",TestNode);
                        i.putExtra("imageURl",ImageURl);
                        startActivity(i);
                        requireActivity().onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Do you want to attempt "+bundle.getInt("testNumber")+" items assessment?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });



       // String.valueOf(bundle.getInt("chapnum")

        return rootView;
    }
}
