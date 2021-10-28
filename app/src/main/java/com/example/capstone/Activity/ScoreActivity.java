package com.example.capstone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.Fragment.LessonFragment;
import com.example.capstone.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvIncorrectScore = findViewById(R.id.tvIncorrectScore);
        TextView tvScoreSummany = findViewById(R.id.tvScoreSummany);
        TextView tvScoreChapterNumber = findViewById(R.id.tvScoreChapterNumber);
        TextView tvScoreTestType = findViewById(R.id.tvScoreTestType);
        TextView tvScoreChapterTitle = findViewById(R.id.tvScoreChapterTitle);
        TextView tvAttemptNumber=findViewById(R.id.tvAttemptNumber);

        Intent intent = getIntent();
        if (null != intent) {

            String score= String.valueOf(intent.getIntExtra("Score",0));
            String total= String.valueOf(intent.getIntExtra("Total Items",0));
            String incorrect= String.valueOf(intent.getIntExtra("Wrong answers",0));
            String LessonTitle= intent.getStringExtra("Lesson Name");
            String LessonType= intent.getStringExtra("Lesson Type");
            String ChapterNumber= intent.getStringExtra("Chapter Number");

            if(LessonType.equals("Pre-Assessment")){
                tvAttemptNumber.setVisibility(View.GONE);
            }

            tvScoreChapterNumber.setText("Chapter "+ChapterNumber);
            tvScoreTestType.setText(LessonType);
            tvScoreChapterTitle.setText(LessonTitle);
                     tvScore.setText("Correct Answer   : "+score);
            tvIncorrectScore.setText("Incorrest Answer : "+incorrect);
              tvScoreSummany.setText("Summary          : "+score+"/"+total);
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}