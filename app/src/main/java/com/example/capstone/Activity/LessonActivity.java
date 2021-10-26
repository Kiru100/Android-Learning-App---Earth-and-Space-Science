package com.example.capstone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstone.Model.YoutubeConfig;
import com.example.capstone.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class LessonActivity extends YouTubeBaseActivity {
    private TextView tvLessonType, tvLessonNumber;
    private Button btnPlayYoutubeVideo;
    private YouTubePlayer.OnInitializedListener youtubeListener;
    private YouTubePlayerView viewYoutubePlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        tvLessonType=findViewById(R.id.tvLessonType);
        tvLessonNumber=findViewById(R.id.tvLnum);
        btnPlayYoutubeVideo=findViewById(R.id.btnPlayYoutubeVideo);
        viewYoutubePlayer=findViewById(R.id.viewYoutubePlayer);

        Intent intent = getIntent();
        if (null != intent) {
            String Ltype= intent.getStringExtra("lessonType");
            String CNumber= String.valueOf(intent.getIntExtra("lessonNumber",0));
            tvLessonType.setText(Ltype);
            tvLessonNumber.setText(CNumber);
        }

        youtubeListener= new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Intent intent = getIntent();
                String YTURL= intent.getStringExtra("YoutubeURL");
                youTubePlayer.loadVideo(YTURL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        btnPlayYoutubeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewYoutubePlayer.initialize(YoutubeConfig.getApiKey(),youtubeListener);
            }
        });








    }

}
