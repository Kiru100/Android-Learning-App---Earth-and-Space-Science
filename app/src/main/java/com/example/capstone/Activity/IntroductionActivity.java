package com.example.capstone.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.capstone.R;

public class IntroductionActivity extends AppCompatActivity {
    private TextView tvIntroChapterNumber,tvIntroLessonTitle,tvIntroMessage,tvChapterObjectives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        tvIntroChapterNumber=findViewById(R.id.tvIntroChapterNumber);
        tvIntroLessonTitle=findViewById(R.id.tvIntroLessonTitle);
        tvIntroMessage=findViewById(R.id.tvIntroMessage);
        tvChapterObjectives=findViewById(R.id.tvChapterObjectives);

        Intent intent = getIntent();
        if (null != intent) {
            int chapterNumber= intent.getIntExtra("chapterNumber",0);
            String chapterNum="Chapter "+ chapterNumber;
            tvIntroChapterNumber.setText(chapterNum);

            String lessonName= intent.getStringExtra("lessonName");
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

    }
}