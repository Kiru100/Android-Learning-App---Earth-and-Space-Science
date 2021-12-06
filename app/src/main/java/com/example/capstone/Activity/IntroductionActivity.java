package com.example.capstone.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.example.capstone.R;

public class IntroductionActivity extends AppCompatActivity {
    private TextView tvIntroChapterNumber,tvIntroLessonTitle,tvIntroMessage,tvChapterObjectives;
    private ZoomControls zoomControls2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        tvIntroChapterNumber=findViewById(R.id.tvIntroChapterNumber);
        tvIntroLessonTitle=findViewById(R.id.tvIntroLessonTitle);
        tvIntroMessage=findViewById(R.id.tvIntroMessage);
        tvChapterObjectives=findViewById(R.id.tvChapterObjectives);
        zoomControls2=findViewById(R.id.zoomControls2);

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






        float[] textSize = {17.5f};
        zoomControls2.setOnZoomInClickListener(v -> {
            textSize[0] += 1f;
            tvIntroMessage.setTextSize(textSize[0]);
            tvChapterObjectives.setTextSize(textSize[0]);

            zoomandoutdisable(textSize);
        });
        zoomControls2.setOnZoomOutClickListener(v->{
            textSize[0] -= 1f;
            tvIntroMessage.setTextSize(textSize[0]);
            tvChapterObjectives.setTextSize(textSize[0]);
            zoomandoutdisable(textSize);
        });


    }




    public void zoomandoutdisable(float[] textSize){
        if(textSize[0]<=17.5){
            zoomControls2.setIsZoomOutEnabled(false);
        }else{
            zoomControls2.setIsZoomOutEnabled(true);
        }
        if(textSize[0]>=25){
            zoomControls2.setIsZoomInEnabled(false);
        }else{
            zoomControls2.setIsZoomInEnabled(true);
        }
    }
}