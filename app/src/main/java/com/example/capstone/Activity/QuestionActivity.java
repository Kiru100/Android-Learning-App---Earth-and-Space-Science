package com.example.capstone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        btnA=findViewById(R.id.btnA);
        btnB=findViewById(R.id.btnB);
        btnC=findViewById(R.id.btnC);
        btnD=findViewById(R.id.btnD);

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
        mQuestionList.add(new Question("Question 3","Option ", "Option B", "Option C", "Option D",2));
        mQuestionList.add(new Question("Question 4","Option D", "Option B", "Option C", "Option D",2));

    }

    @Override
    public void onClick(View v) {

    }
}