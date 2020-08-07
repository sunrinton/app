package com.example.sunrinton;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mContext = this;

        TextView indexTV = findViewById(R.id.quiz);
        TextView quizTV = findViewById(R.id.ment);

        indexTV.setText("QUIZ " + (MainActivity.quizIndex+1));
        quizTV.setText("'"+MainActivity.quizlist.get(MainActivity.quizIndex).getWord() +"' 의 뜻은?");

        Button quiz1Btn = findViewById(R.id.quizBtn_1);
        Button quiz2Btn = findViewById(R.id.quizBtn_2);
        Drawable correct = getDrawable( R.drawable.ic_correct );
        int h = correct.getIntrinsicHeight();
        int w = correct.getIntrinsicWidth();
        correct.setBounds(-20,0,w,h);
        Drawable incorrect = getDrawable( R.drawable.ic_nocorrect );
        h = incorrect.getIntrinsicHeight();
        w = incorrect.getIntrinsicWidth();
        incorrect.setBounds(-20,0,w,h);

        if(new Random().nextInt(2) == 1) {
            quiz1Btn.setText(MainActivity.quizlist.get(MainActivity.quizIndex).getMean());
            int a;
            while((a = new Random().nextInt(MainActivity.quizlist.size())) != MainActivity.quizIndex) {
                quiz2Btn.setText(MainActivity.quizlist.get(a).getMean());
            }
            quiz1Btn.setOnClickListener((view) -> {
                MainActivity.CorrectCount++;
                quiz1Btn.setBackgroundResource(R.drawable.correctbutton);
                quiz1Btn.setCompoundDrawables(null, null, correct, null);
                quiz1Btn.setTextColor(getColor(R.color.white));
                quiz1Btn.setClickable(false);
                quiz2Btn.setClickable(false);
                nextPage();

            });
            quiz2Btn.setOnClickListener((view) -> {
                quiz2Btn.setBackgroundResource(R.drawable.incorrectbutton);
                quiz2Btn.setTextColor(getColor(R.color.white));
                quiz2Btn.setCompoundDrawables(null, null, incorrect, null);
                quiz1Btn.setClickable(false);
                quiz2Btn.setClickable(false);
                nextPage();
            });
        } else {
            quiz2Btn.setText(MainActivity.quizlist.get(MainActivity.quizIndex).getMean());
            int a;
            while((a = new Random().nextInt(MainActivity.quizlist.size())) != MainActivity.quizIndex) {
                quiz1Btn.setText(MainActivity.quizlist.get(a).getMean());
            }
            quiz2Btn.setOnClickListener((view) -> {
                MainActivity.CorrectCount++;
                quiz2Btn.setBackgroundResource(R.drawable.correctbutton);
                quiz2Btn.setTextColor(getColor(R.color.white));
                quiz2Btn.setCompoundDrawables(null, null, correct, null);
                quiz1Btn.setClickable(false);
                quiz2Btn.setClickable(false);
                nextPage();
            });
            quiz1Btn.setOnClickListener((view) -> {
                quiz1Btn.setBackgroundResource(R.drawable.incorrectbutton);
                quiz1Btn.setTextColor(getColor(R.color.white));
                quiz1Btn.setCompoundDrawables(null, null, incorrect, null);
                quiz1Btn.setClickable(false);
                quiz2Btn.setClickable(false);
                nextPage();
            });
        }
    }

    private void nextPage() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(mContext, QuizAnswerActivity.class));
                finish();
            }
        },1000L);
    }
}
