package com.example.capstone.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TestFragment extends Fragment {
    private TextView lessonTitle,tvTestType,tvChapterNumber,tvItemNumber;
    private Button btnAttempt;
    private String LessonName,LessonType,ChapterNumber,TestNode,ImageURl,TestName;
    private ImageView ivChapterImage2;
    private int testNumber;
    private FirebaseUser student;

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
        TestName=bundle.getString("testName");
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


        student=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Students");
        String userID = student.getUid();
        btnAttempt.setVisibility(View.VISIBLE);
        //Know if student already took the exam TODO:get this thing back

        reference.child(userID).child("Chapter_"+ChapterNumber+"_Mark_as_Done").child(LessonName).child("lessonScore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    tvItemNumber.setText("Score : "+snapshot.getValue());
                    btnAttempt.setVisibility(View.GONE);
                }else{
                    btnAttempt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        i.putExtra("testName",TestName);
                        startActivity(i);
                        requireActivity().onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Do you want to attempt "+bundle.getInt("testNumber")+" items assessment?\nMake sure your internet connection is stable.").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });

        return rootView;
    }

}
