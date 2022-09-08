package com.example.mcgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class level extends AppCompatActivity {
    TextView txtlevel;
    String level;
    TextView txtscore;
    TextView txttimer;
    TextView txtqnum;
    CountDownTimer cdTimer;
    TextView txtquestion;
    RadioGroup rg;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    Button btnnext,btnSubmit;
    int score = 0;
    int questionIndex = 1;
    int num1 = 0;
    int num2 = 0;
    int operatorNum = 0;
    String operator;
    int answer = 0;
ArrayList<Integer> optionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        txtlevel = (TextView) findViewById(R.id.idTxtLevel);
        level = getIntent().getStringExtra("Level");
        txtlevel.setText("Level " + level);
        txtscore = (TextView) findViewById(R.id.idtxtscore);
        txttimer = (TextView) findViewById(R.id.idtxttimer);
        txtqnum = (TextView) findViewById(R.id.idtxtqnum);
        txtquestion = (TextView) findViewById(R.id.idtxtquestion);
        rg = (RadioGroup) findViewById(R.id.idrg);
        rb1 = (RadioButton) findViewById(R.id.idopt1);
        rb2 = (RadioButton) findViewById(R.id.idopt2);
        rb3 = (RadioButton) findViewById(R.id.idopt3);
        rb4 = (RadioButton) findViewById(R.id.idopt4);
        btnnext = (Button) findViewById(R.id.idnext);
        btnSubmit=(Button) findViewById(R.id.idsubmit);
        txtscore.setText("Score:"+score);
        btnnext.setVisibility(View.INVISIBLE);


        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionIndex<=10){
                    setUpQuestion();
                }
                else{
                    Toast.makeText(level.this,"Finished!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        if (level.equals("0")) {
            txttimer.setVisibility(View.INVISIBLE);
        }
        setUpQuestion();
    }

    public void Timer() {
        long miLiSec = 0;
        if (level.equals("1")) {
            miLiSec = 21000;
        } else if (level.equals("2")) {
            miLiSec = 11000;
        }
        cdTimer = new CountDownTimer(miLiSec, 1000) {
            @Override
            public void onTick(long l) {
                txttimer.setText("Timer: 00:" + l / 1000);
            }

            @Override
            public void onFinish() {
                if(questionIndex<=10){
                    setUpQuestion();
                }
                else{
                    Toast.makeText(level.this,"Finished!",Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }

    public int randomNumber(int max, int min) {
        int range = max - min + 1;
        int rand = (int) (Math.random() * range) + min;
        return rand;
    }

    public void setUpQuestion() {
        rg.clearCheck();
         if (level.equals("1")) {
            txttimer.setVisibility(View.VISIBLE);
            Timer();


        } else if (level.equals("2")) {
            txttimer.setVisibility(View.VISIBLE);
            Timer();

        }
        num1 = randomNumber(12, 1);
        num2 = randomNumber(12, 1);
        operatorNum = randomNumber(4, 1);

        if (operatorNum == 1) {
            answer = num1 + num2;
            operator = "+";
        } else if (operatorNum == 2) {
            if (num1 > num2) {
                answer = num1 - num2;
                operator = "-";
            } else {
                setUpQuestion();
            }
        } else if (operatorNum == 3) {
            answer = num1 * num2;
            operator = "*";
        } else if (operatorNum == 4) {
            answer = num1 / num2;
            operator = "/";
        }
        txtquestion.setText(num1+""+operator+""+num2+" will be");
        setUpOptions();
        btnSubmit.setVisibility(View.VISIBLE);
        btnnext.setVisibility((View.INVISIBLE));
    }
    public void setUpOptions(){
        optionList=new ArrayList<Integer>();
        optionList.add(answer);
        optionList.add(randomNumber(12,1));
        optionList.add(randomNumber(12,1));
        optionList.add(randomNumber(12,1));
        Collections.shuffle(optionList);
        rb1.setText(String.valueOf(optionList.get(0)));
        rb2.setText(String.valueOf(optionList.get(1)));
        rb3.setText(String.valueOf(optionList.get(2)));
        rb4.setText(String.valueOf(optionList.get(3)));

    }
    public void checkAnswer(){
        if (rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked()){

            btnnext.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility((View.INVISIBLE));
            RadioButton rdcheck =findViewById(rg.getCheckedRadioButtonId());
            if(answer==Integer.parseInt(rdcheck.getText().toString())){
                score++;
                txtscore.setText("Score:"+score);
                Toast.makeText(level.this,"Correct!",Toast.LENGTH_SHORT).show();
                MediaPlayer ring= MediaPlayer.create(level.this,R.raw.correct);
                ring.start();
            }
            else{
                Toast.makeText(level.this,"Incorrect!",Toast.LENGTH_SHORT).show();
                MediaPlayer ring= MediaPlayer.create(level.this,R.raw.wrong);
                ring.start();
            }
            questionIndex++;
            if(!level.equals("0")){
                cdTimer.cancel();
            }

        }
        else{
            Toast.makeText(level.this,"Please choose 1!",Toast.LENGTH_SHORT).show();
        }
    }
}