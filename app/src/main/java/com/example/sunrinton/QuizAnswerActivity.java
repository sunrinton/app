package com.example.sunrinton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class QuizAnswerActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);

        TextView answerTV = findViewById(R.id.ment);
        answerTV.setText("'"+MainActivity.quizlist.get(MainActivity.quizIndex).getMean() + "' 입니다!");

        TextView answerSentence = findViewById(R.id.mean);
        answerSentence.setText(MainActivity.quizlist.get(MainActivity.quizIndex).getSentence());

        Button nextbtn = findViewById(R.id.nextQuiz);
        nextbtn.setOnClickListener(view -> {

            if(MainActivity.quizIndex == MainActivity.quizlist.size()-1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("결과")
                        .setMessage("맞힌 문제 : " + MainActivity.CorrectCount  + " / " + MainActivity.quizlist.size())
                        .setPositiveButton("확인", (dialogInterface, i) -> {

                            SharedPreferences sf = getSharedPreferences("Correct", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sf.edit();
                            edit.putInt("correct", MainActivity.CorrectCount);
                            edit.commit();


                            startActivity(new Intent(this, MainFormActivity.class));
                            finish();
                        });
                builder.show();
                return;
            }

            MainActivity.quizIndex++;
            startActivity(new Intent(this, QuizActivity.class));
            finish();

        });
    }
}
