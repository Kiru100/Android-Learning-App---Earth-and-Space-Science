package com.example.capstone.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.example.capstone.Model.LessonDoneInfo;
import com.example.capstone.Model.MarkAsDoneInfo;
import com.example.capstone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class IntroductionActivity extends AppCompatActivity {
    private TextView tvIntroChapterNumber,tvIntroLessonTitle,tvIntroMessage,tvChapterObjectives;
    private int chapterNumber;
    private String lessonName;

    private boolean isdone;

    private Button btnDone2;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference reference= FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        tvIntroChapterNumber=findViewById(R.id.tvIntroChapterNumber);
        tvIntroLessonTitle=findViewById(R.id.tvIntroLessonTitle);
        tvIntroMessage=findViewById(R.id.tvIntroMessage);
        tvChapterObjectives=findViewById(R.id.tvChapterObjectives);
        btnDone2=findViewById(R.id.btnDone2);


        Intent intent = getIntent();
        if (null != intent) {
             chapterNumber= intent.getIntExtra("chapterNumber",0);
            String chapterNum="Chapter "+ chapterNumber;
            tvIntroChapterNumber.setText(chapterNum);

             lessonName= intent.getStringExtra("lessonName");
            tvIntroLessonTitle.setText(lessonName);

            String introMessage= intent.getStringExtra("introMessage");
            tvIntroMessage.setText(introMessage);

            String chapterObjectives= intent.getStringExtra("chapterObjectives");
                    tvChapterObjectives.setText(chapterObjectives);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvIntroMessage.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            tvChapterObjectives.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        new Handler().postDelayed(() -> {
            markAsDone(chapterNumber,lessonName);
            isdone=true;
        },60000);


        btnDone2.setOnClickListener(v -> {
            if(!isdone){
                Toast.makeText(getApplication(), "You have to read all content first before you can proceed", Toast.LENGTH_SHORT).show();
            }else{
                onBackPressed();
            }
        });

    }

    public void markAsDone(int ChapterNumber,String LessonName){
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String userID= mAuth.getCurrentUser().getUid();
        MarkAsDoneInfo lessonDoneInfo = new MarkAsDoneInfo(mydate,LessonName,true,"lecture");
        reference.child(userID).child("Chapter_"+ChapterNumber+"_Mark_as_Done").child(LessonName).setValue(lessonDoneInfo);
    }

}