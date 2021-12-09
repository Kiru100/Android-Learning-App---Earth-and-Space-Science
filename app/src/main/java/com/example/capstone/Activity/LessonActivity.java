package com.example.capstone.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.bumptech.glide.Glide;
import com.example.capstone.Model.YoutubeConfig;
import com.example.capstone.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LessonActivity extends YouTubeBaseActivity {
    private YouTubePlayer.OnInitializedListener youtubeListener;
    private YouTubePlayerView viewYoutubePlayer;

    private String lessonFirstLineString,firstLessonImageURL,lessonSecondLineString,secondLessonImageURL,
                    firstFigureNumber,youtubeVideoTitleString,secondFigureNumber,lessonThirdLineString,LessonName,lessonReference;

    private TextView tvLessonTitle,tvFirstLine,tvSecondLine,tvThirdLine,tvFirstFigureNumber,tvYoutubeTitle,tvSecondFigureNumber,tvLessonReference;

    private ImageView ivFirstLessonImage,ivSecondLessonImage;

    private int ChapterNumber;

    private ScrollView scrollView2;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference reference= FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        tvLessonTitle=findViewById(R.id.tvDefinitionName);
        tvFirstLine=findViewById(R.id.tvFirstLine);
        ivFirstLessonImage=findViewById(R.id.ivFirstLessonImage);
        tvSecondLine=findViewById(R.id.tvSecondLine);
        tvFirstFigureNumber=findViewById(R.id.tvFirstFigureNumber);
        tvYoutubeTitle=findViewById(R.id.tvYoutubeTitle);
        ivSecondLessonImage=findViewById(R.id.ivSecondLessonImage);
        tvSecondFigureNumber=findViewById(R.id.tvSecondFigureNumber);
        tvThirdLine=findViewById(R.id.tvThirdLine);
        tvLessonReference=findViewById(R.id.tvLessonReference);

        viewYoutubePlayer=findViewById(R.id.viewYoutubePlayer);

        scrollView2=findViewById(R.id.scrollView2);

        Intent intent = getIntent();
        if (null != intent) {
            LessonName= intent.getStringExtra("lessonName");
            tvLessonTitle.setText(LessonName);

            lessonFirstLineString=intent.getStringExtra("lessonFirstLineString");
            tvFirstLine.setText(lessonFirstLineString);
            lessonSecondLineString=intent.getStringExtra("secondLineLessonLecture");
            tvSecondLine.setText(lessonSecondLineString);
            lessonThirdLineString=intent.getStringExtra("lessonThirdLineString");
            tvThirdLine.setText(lessonThirdLineString);

            firstFigureNumber=intent.getStringExtra("firstFigureNumber");
            tvFirstFigureNumber.setText(firstFigureNumber);
            secondFigureNumber=intent.getStringExtra("secondFigureNumber");
            tvSecondFigureNumber.setText(secondFigureNumber);

            youtubeVideoTitleString=intent.getStringExtra("youtubeTitle");
            tvYoutubeTitle.setText(youtubeVideoTitleString);

            lessonReference=intent.getStringExtra("LessonReference");
            tvLessonReference.setText(lessonReference);

            ChapterNumber=intent.getIntExtra("ChapterNumber",0);

            firstLessonImageURL=intent.getStringExtra("firstLessonImage");
            Glide.with(this).load(firstLessonImageURL).into(ivFirstLessonImage);
            secondLessonImageURL=intent.getStringExtra("secondLessonImage");
            Glide.with(this).load(secondLessonImageURL).into(ivSecondLessonImage);

        }
        playYoutube();

    }

    public void playYoutube(){

        youtubeListener= new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Intent intent = getIntent();
                String YTURL= intent.getStringExtra("YoutubeURL");
                youTubePlayer.loadVideo(YTURL);

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                    }
                    @Override
                    public void onLoaded(String s) {
                        youTubePlayer.pause();
                        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
                    }
                    @Override
                    public void onAdStarted() {

                    }
                    @Override
                    public void onVideoStarted() {
                    }
                    @Override
                    public void onVideoEnded() {
                        scrollView2.getViewTreeObserver().addOnScrollChangedListener(() -> {
                            if (! scrollView2.canScrollVertically(1)) {
                                markAsDone(ChapterNumber,LessonName);
                            }
                            if (! scrollView2.canScrollVertically(-1)) {
                                // top of scroll view
                            }
                        });
                    }
                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                        Toast.makeText(getApplicationContext(), errorReason.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        viewYoutubePlayer.initialize(YoutubeConfig.getApiKey(),youtubeListener);
    }

    public void markAsDone(int ChapterNumber,String LessonName){
        String userID= mAuth.getCurrentUser().getUid();
        reference.child(userID).child("Chapter_"+ChapterNumber+"_Mark_as_Done").child(LessonName).setValue(true);
    }


}
