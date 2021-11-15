package com.example.capstone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone.Model.Question;
import com.example.capstone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvQuizChapterNumber,tvQuizChapterTitle,tvType,tvQuestion,tvTimer,tvCount;
    private Button btnA,btnB,btnC,btnD;
    private List<Question> mQuestionList;
    private CountDownTimer countdown;
    private int right,
            questionNumber;
    private String LessonName,LessonType,ChapterNumber,TestNode,imageURL;
    private ImageView ivChapterImage3;



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
        ivChapterImage3=findViewById(R.id.ivChapterImage3);

        //buttons
        btnA=findViewById(R.id.btnA);
        btnB=findViewById(R.id.btnB);
        btnC=findViewById(R.id.btnC);
        btnD=findViewById(R.id.btnD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


        Intent intent = getIntent();
        if (null != intent) {

            LessonName= intent.getStringExtra("LessonName");
            LessonType= intent.getStringExtra("LessonType");
            ChapterNumber= intent.getStringExtra("ChapterNumber");
            TestNode=intent.getStringExtra("TestNode");
           imageURL=intent.getStringExtra("imageURl");

            tvQuizChapterNumber.setText("Chapter "+ChapterNumber);
            tvQuizChapterTitle.setText(LessonName);
            tvType.setText(LessonType);
            Glide.with(this).load(imageURL).into(ivChapterImage3);
        }



        getQuestionList();
    }

    private void getQuestionList() {
        mQuestionList=new ArrayList();


        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://capstoneproject-4b898-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.child("Question").child(TestNode).child("Items").getChildren()){

                    final String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    final String getOption1= dataSnapshot.child("optionA").getValue(String.class);
                    final String getOption2= dataSnapshot.child("optionB").getValue(String.class);
                    final String getOption3= dataSnapshot.child("optionC").getValue(String.class);
                    final String getOption4= dataSnapshot.child("optionD").getValue(String.class);
                    final int getCorrectAnswer= dataSnapshot.child("answer").getValue(int.class);

                    Question question=new Question(getQuestion,getOption1,getOption2,getOption3,getOption4,getCorrectAnswer);
                    mQuestionList.add(question);

                }
                setQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setQuestion() {
        tvTimer.setText(String.valueOf(200));
        tvQuestion.setText(mQuestionList.get(0).getQuestion());
        btnA.setText(mQuestionList.get(0).getOptionA());
        btnB.setText(mQuestionList.get(0).getOptionB());
        btnC.setText(mQuestionList.get(0).getOptionC());
        btnD.setText(mQuestionList.get(0).getOptionD());

        String totalScore=String.valueOf(1)+ "/"+String.valueOf(mQuestionList.size());
        tvCount.setText(totalScore);
        startTimer();

        questionNumber=0;
    }

    private void startTimer() {
        countdown=new CountDownTimer(202000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished<200000){
                    tvTimer.setText(String.valueOf(millisUntilFinished/1000));
                }
                if(millisUntilFinished<200000){
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        countdown.cancel();
        checkAnswer(selectedButton,v);

    }

    private void checkAnswer(int selectedButton, View view) {
        if(selectedButton==mQuestionList.get(questionNumber).getCorrectAnswer()){
            //answer
            view.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            right++;
        }else{
            view.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

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

            tvTimer.setText(String.valueOf(200));
            startTimer();


        }else{
            //go to score act
             Intent i= new Intent(QuestionActivity.this,ScoreActivity.class);
             i.putExtra("Score",right);
             i.putExtra("Total Items",mQuestionList.size());
             i.putExtra("Wrong answers",(mQuestionList.size() - right));
             i.putExtra("Lesson Name",LessonName);
             i.putExtra("Lesson Type",LessonType);
             i.putExtra("Chapter Number",ChapterNumber);

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
                                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55616A")));
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