package com.example.capstone.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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


public class LessonActivity extends YouTubeBaseActivity {
    private YouTubePlayer.OnInitializedListener youtubeListener;
    private YouTubePlayerView viewYoutubePlayer;

    private String lessonFirstLineString,firstLessonImageURL,lessonSecondLineString,secondLessonImageURL,
                    firstFigureNumber,youtubeVideoTitleString,secondFigureNumber,lessonThirdLineString;

    private TextView tvLessonTitle,tvFirstLine,tvSecondLine,tvThirdLine,tvFirstFigureNumber,tvYoutubeTitle,tvSecondFigureNumber;

    private ImageView ivFirstLessonImage,ivSecondLessonImage;

    private ZoomControls zoomControls;

    private boolean isDoneWatching;

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

        viewYoutubePlayer=findViewById(R.id.viewYoutubePlayer);

        zoomControls=findViewById(R.id.zoomControls);


        Intent intent = getIntent();
        if (null != intent) {
            String LessonName= intent.getStringExtra("lessonName");
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


            firstLessonImageURL=intent.getStringExtra("firstLessonImage");
            Glide.with(this).load(firstLessonImageURL).into(ivFirstLessonImage);
            secondLessonImageURL=intent.getStringExtra("secondLessonImage");
            Glide.with(this).load(secondLessonImageURL).into(ivSecondLessonImage);
        }
        playYoutube();

        float[] textSize = {17.5f};
        zoomControls.setOnZoomInClickListener(v -> {
            textSize[0] += 1f;
            tvFirstLine.setTextSize(textSize[0]);
            tvSecondLine.setTextSize(textSize[0]);
            tvThirdLine.setTextSize(textSize[0]);
            zoomandoutdisable(textSize);
        });
        zoomControls.setOnZoomOutClickListener(v->{
            textSize[0] -= 1f;
            tvFirstLine.setTextSize(textSize[0]);
            tvSecondLine.setTextSize(textSize[0]);
            tvThirdLine.setTextSize(textSize[0]);
            zoomandoutdisable(textSize);
        });
    }

    public void zoomandoutdisable(float[] textSize){
        if(textSize[0]<=17.5){
            zoomControls.setIsZoomOutEnabled(false);
        }else{
            zoomControls.setIsZoomOutEnabled(true);
        }
        if(textSize[0]>=25){
            zoomControls.setIsZoomInEnabled(false);
        }else{
            zoomControls.setIsZoomInEnabled(true);
        }
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
                        isDoneWatching=true;
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

//    private class scrollviewend extends ScrollView {
//
//        public scrollviewend(Context context) {
//            super(context);
//        }
//
//        @Override
//        public void setOnScrollChangeListener(OnScrollChangeListener l) {
//            super.setOnScrollChangeListener(l);
//        }
//
//        @Override
//        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//            View view = (View) getChildAt(getChildCount()-1);
//            int diff = (view.getBottom()-(getHeight()+getScrollY()));
//
//            if (diff == 0 && mListener != null) {
//                mListener.onBottomReached();
//            }
//
//            super.onScrollChanged(l, t, oldl, oldt);
//        }
    }


}
