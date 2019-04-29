package com.example.suganya.alarmsystem;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static com.example.suganya.alarmsystem.AudioPlay.stopAudio;


public class AlarmQuiz extends AppCompatActivity {

    private TextView textQuestion;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button btnCheck;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCounterTotal;
    private Question currentQuestion;
    DatabaseHelper db;

    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_quiz);

        textQuestion = findViewById(R.id.question);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_butt1);
        rb2 = findViewById(R.id.radio_butt2);
        rb3 = findViewById(R.id.radio_butt3);
        btnCheck = findViewById(R.id.btnCheckAns);


        QuizDbHelper dbHelper = new QuizDbHelper(this);
        db = new DatabaseHelper(this);
        questionList = dbHelper.getAllQuestion();
        questionCounterTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()){
                    checkAnswer();
                }
                else {
                    Toast.makeText(AlarmQuiz.this, "Please select a answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkAnswer(){
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        final String alaName = getIntent().getExtras().getString("alName");
        final String alaTime = getIntent().getExtras().getString("alTime");
        final String alaTone = getIntent().getExtras().getString("alTone");

        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;
        String ans = String.valueOf(answerNr);

        Log.e("answer number ",ans);

        if(answerNr == currentQuestion.getAnswerNr()){
               db.updateStatus(alaTime,alaName,0 , alaTone);
               Intent mainIntent = new Intent(AlarmQuiz.this,MainActivity.class);
               stopAudio();
//               Intent ringAlarm = new Intent(AlarmQuiz.this,RingAlarm.class);
//               ringAlarm.putExtra("condition","stop");
//               startActivity(ringAlarm);
               startActivity(mainIntent);
               finishQuiz();

        }
        else {
            Toast.makeText(this, "Invalid Answer", Toast.LENGTH_SHORT).show();
            showNextQuestion();
        }
    }

    private void showNextQuestion(){
        rbGroup.clearCheck();

        if(questionCounter< questionCounterTotal){
            currentQuestion = questionList.get(questionCounter);

            textQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            answered = false;
        }else {
            finishQuiz();
        }

    }

    private void finishQuiz(){
        finish();
    }
}
