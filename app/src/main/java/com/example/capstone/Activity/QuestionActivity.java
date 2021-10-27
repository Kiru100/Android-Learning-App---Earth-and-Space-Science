package com.example.capstone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstone.Model.Question;
import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvQuizChapterNumber,tvQuizChapterTitle,tvType,tvQuestion,tvTimer,tvCount;
    private Button btnA,btnB,btnC,btnD;
    private List<Question> mQuestionList;
    private int questionNumber;
    private CountDownTimer countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //textbox
        tvQuizChapterNumber=findViewById(R.id.tvQuizChapterNumber);
        tvQuizChapterTitle=findViewById(R.id.tvQuizChapterTitle);
        tvType=findViewById(R.id.tvType);
        tvQuestion=findViewById(R.id.tvQuestion);
        tvTimer=findViewById(R.id.tvTimer);
        tvCount=findViewById(R.id.tvCount);

        //buttons
        btnA=(Button)findViewById(R.id.btnA);
        btnB=(Button)findViewById(R.id.btnB);
        btnC=(Button)findViewById(R.id.btnC);
        btnD=(Button)findViewById(R.id.btnD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


        getQuestionList();
    }

    private void getQuestionList() {
        mQuestionList=new ArrayList();
        mQuestionList.add(new Question("Question 1","Option A", "Option B", "Option C", "Option D",2));
        mQuestionList.add(new Question("Question 2","Option B", "Option B", "Option C", "Option D",2));
        mQuestionList.add(new Question("Question 3","Option C", "Option D", "Option C", "Option B",2));
        mQuestionList.add(new Question("Question 4","Option D", "Option D", "Option C", "Option B",2));

        setQuestion();

    }

    private void setQuestion() {
        tvTimer.setText(String.valueOf(10));
        tvQuestion.setText(mQuestionList.get(0).getQuestion());
        btnA.setText(mQuestionList.get(0).getOptionA());
        btnB.setText(mQuestionList.get(0).getOptionB());
        btnC.setText(mQuestionList.get(0).getOptionC());
        btnD.setText(mQuestionList.get(0).getOptionD());

        tvCount.setText(String.valueOf(1)+"/"+String.valueOf(mQuestionList.size()));
    
        startTimer();

        questionNumber=0;
    }

    private void startTimer() {
        countdown=new CountDownTimer(12000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished<10000){
                    tvTimer.setText(String.valueOf(millisUntilFinished/1000));
                }
            }
            @Override
            public void onFinish() {

               changeQuestion();
            }
        };

        countdown.start();

    }

    @Override
    public void onClick(View v) {
        int selectedButton=0;

        switch(v.getId()){
            case R.id.btnA:
                selectedButton=1;
                break;
            case R.id.btnB:
                selectedButton=2;
                break;
            case R.id.btnC:
                selectedButton=3;
                break;
            case R.id.btnD:
                selectedButton=4;
                break;

            default:
        }
        countdown.cancel();
        checkAnswer(selectedButton,v);

    }

    private void checkAnswer(int selectedButton, View view) {
        if(selectedButton==mQuestionList.get(questionNumber).getCorrectAnswer()){
            //answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        }else{
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch(mQuestionList.get(questionNumber).getCorrectAnswer()){
                case 1:
                    btnA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    btnB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    btnC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    btnD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;

                default:
            }

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },2000);


    }

    private void changeQuestion() {
        if (questionNumber<mQuestionList.size()-1){
            //play animation to change the question

            questionNumber++;

            playAnim(tvQuestion,0,0);
            playAnim(btnA,0,1);
            playAnim(btnB,0,2);
            playAnim(btnC,0,3);
            playAnim(btnD,0,4);

            tvCount.setText(String.valueOf(questionNumber+1)+"/"+String.valueOf(mQuestionList.size()));

            tvTimer.setText(String.valueOf(10));
            startTimer();


        }else{
            //go to score act
             Intent i= new Intent(QuestionActivity.this,ScoreActivity.class);
             startActivity(i);
             QuestionActivity.this.finish();
        }

    }

    private void playAnim(View view, final int value,int viewNumber) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value==0){
                            switch(viewNumber){
                                case 0:
                                    ((TextView)view).setText(mQuestionList.get(questionNumber).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(mQuestionList.get(questionNumber).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(mQuestionList.get(questionNumber).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(mQuestionList.get(questionNumber).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(mQuestionList.get(questionNumber).getOptionD());
                                    break;
                            }
                            if (viewNumber!=0){
                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                                //   ((Button))view.setBackgroundTintList(ColorStateList.valueOf()); TODO:reset color of selected button
                            }

                            playAnim(view,1,viewNumber);
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }
}