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

import com.example.capstone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
       // zoomControls2=findViewById(R.id.zoomControls2);
     //   btnIntroductionNextLesson=findViewById(R.id.btnIntroductionNextLesson);

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
//
//        float[] textSize = {17.5f};
//        zoomControls2.setOnZoomInClickListener(v -> {
//            textSize[0] += 1f;
//            tvIntroMessage.setTextSize(textSize[0]);
//            tvChapterObjectives.setTextSize(textSize[0]);
//
//            zoomandoutdisable(textSize);
//        });
//        zoomControls2.setOnZoomOutClickListener(v->{
//            textSize[0] -= 1f;
//            tvIntroMessage.setTextSize(textSize[0]);
//            tvChapterObjectives.setTextSize(textSize[0]);
//            zoomandoutdisable(textSize);
//        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                markAsDone(chapterNumber,lessonName);
                isdone=true;
            }
        },60000);


        btnDone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//            btnIntroductionNextLesson.setOnClickListener(v -> {
//                if(isDoneReading){
//                    //send to next activity
//                    System.out.println("line 1 is clicked");
//                }else{
//                    Toast.makeText(getApplication(), "You have to read all content first before you can proceed", Toast.LENGTH_SHORT).show();
//                    System.out.println("line is clicked");
//                }
//            });

    }


//    public void zoomandoutdisable(float[] textSize){
//        if(textSize[0]<=17.5){
//            zoomControls2.setIsZoomOutEnabled(false);
//        }else{
//            zoomControls2.setIsZoomOutEnabled(true);
//        }
//        if(textSize[0]>=25){
//            zoomControls2.setIsZoomInEnabled(false);
//        }else{
//            zoomControls2.setIsZoomInEnabled(true);
//        }
//    }

    public void markAsDone(int ChapterNumber,String LessonName){
        String userID= mAuth.getCurrentUser().getUid();
        reference.child(userID).child("Chapter_"+ChapterNumber+"_Mark_as_Done").child(LessonName).setValue(true);
    }

}