package edu.mesa.guessinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LevelThree extends AppCompatActivity implements View.OnClickListener {

    Animation wobble,rotate;
    int ourRandom;
    int correctAnswerPoints = 10;

    private SoundPool soundPool;
    int sample1 = -1;
    int sample2 = -1;

    TextView highScoreText;
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonFive;
    Button buttonSix;
    Button buttonSeven;
    Button buttonEight;
    Button buttonNine;
    Button buttonTen;
    Button buttonReplayLevelThree;

    Random randInt = new Random();
    String dataName = "highscore";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_three);
        wobble = AnimationUtils.loadAnimation(this, R.anim.wobble);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);


        preferences = getSharedPreferences(dataName, getApplicationContext().MODE_PRIVATE);

        buttonOne = (Button)findViewById(R.id.buttonOne);
        buttonTwo = (Button)findViewById(R.id.buttonTwo);
        buttonThree = (Button)findViewById(R.id.buttonThree);
        buttonFour = (Button)findViewById(R.id.buttonFour);
        buttonFive = (Button)findViewById(R.id.buttonFive);
        buttonSix = (Button) findViewById(R.id.buttonSix);
        buttonSeven = (Button) findViewById(R.id.buttonSeven);
        buttonEight = (Button) findViewById(R.id.buttonEight);
        buttonNine = (Button) findViewById(R.id.buttonNine);
        buttonTen = (Button) findViewById(R.id.buttonTen);

        buttonReplayLevelThree = (Button) findViewById(R.id.buttonReplay);
        highScoreText = (TextView) findViewById(R.id.highScoreText);
        highScoreText.setText("Score: "+MainActivity.hiScore);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);
        buttonTen.setOnClickListener(this);
        buttonReplayLevelThree.setOnClickListener(this);


        ourRandom = randInt.nextInt(10);
        ourRandom++;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            //Create objects of the 2 required classes
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;
            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("One.wav");
            sample1 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("Two.wav");
            sample2 = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            //catch exceptions here

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.buttonOne:
                checkAnswer(R.id.buttonOne,1);
                break;
            case R.id.buttonTwo:
                checkAnswer(R.id.buttonTwo,2);
                break;
            case R.id.buttonThree:
                checkAnswer(R.id.buttonThree,3);
                break;
            case R.id.buttonFour:
                checkAnswer(R.id.buttonFour,4);
                break;
            case R.id.buttonFive:
                checkAnswer(R.id.buttonFive,5);
                break;
            case R.id.buttonSix:
                checkAnswer(R.id.buttonSix,6);
                break;
            case R.id.buttonSeven:
                checkAnswer(R.id.buttonSeven,7);
                break;
            case R.id.buttonEight:
                checkAnswer(R.id.buttonEight,8);
                break;
            case R.id.buttonNine:
                checkAnswer(R.id.buttonNine,9);
                break;
            case R.id.buttonTen:
                checkAnswer(R.id.buttonTen,10);
                break;
            default:
                Intent intent = new Intent(LevelThree.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void checkAnswer(int buttonid,int answerValue) {
        //check answer is correct
        if (answerValue==ourRandom){
            Button b = (Button) findViewById(buttonid);
            b.startAnimation(wobble);
            Toast.makeText(getApplicationContext(),"Correct Guess",Toast.LENGTH_LONG).show();
            soundPool.play(sample1, 1, 1, 0, 0, 1);
            MainActivity.hiScore = correctAnswerPoints + MainActivity.hiScore;
            Intent intent = new Intent(LevelThree.this,Winner.class);
            gotoNextPage(intent);
        }else {
            //Incorrect answer
            Button b = (Button) findViewById(buttonid);
            b.startAnimation(rotate);
            Toast.makeText(getApplicationContext(), "Incorrect Guess", Toast.LENGTH_SHORT).show();
            soundPool.play(sample2, 1, 1, 0, 0, 1);
            correctAnswerPoints = correctAnswerPoints - 1;
            if(correctAnswerPoints < 0){
                correctAnswerPoints = 1;
            }
        }
    }

    public void gotoNextPage(final Intent intent){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },1500);
    }

}
