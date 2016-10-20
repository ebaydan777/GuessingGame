package edu.mesa.guessinggame;

import android.content.Intent;
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

public class LevelOne extends AppCompatActivity implements View.OnClickListener{


    int ourRandom;
    int correctAnswerPoints = 3;
    Random randInt = new Random();

    private SoundPool soundPool;
    int sample1 = -1;
    int sample2 = -1;

    TextView levelOneHighScore;
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonReplayLevelOne;

    Animation wobble;
    Animation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);
        wobble = AnimationUtils.loadAnimation(this, R.anim.wobble);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);


        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonReplayLevelOne = (Button) findViewById(R.id.buttonReplayLevelOne);
        levelOneHighScore = (TextView) findViewById(R.id.levelOneHighScore);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonReplayLevelOne.setOnClickListener(this);

        //Generate a random number on start of the Level
        ourRandom = randInt.nextInt(3);
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
            default:
                Intent intent = new Intent(LevelOne.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    private void checkAnswer(int buttonid, int answerValue) {

        //check answer is correct
        if (answerValue==ourRandom){
            Button b = (Button) findViewById(buttonid);
            b.startAnimation(wobble);
            Toast.makeText(getApplicationContext(),"Correct Guess!",Toast.LENGTH_LONG).show();
            soundPool.play(sample1, 1, 1, 0, 0, 1);
            MainActivity.hiScore = correctAnswerPoints;
            Intent intent = new Intent(LevelOne.this,LevelTwo.class);
            //Two second delay to go to next level
            gotoNextPage(intent);
            levelOneHighScore.setText("Score: "+correctAnswerPoints);

        }else {

            //Incorrect answer
            Toast.makeText(getApplicationContext(),"Incorrect Guess",Toast.LENGTH_SHORT).show();
            soundPool.play(sample2, 1, 1, 0, 0, 1);
            Button b = (Button) findViewById(buttonid);
            b.startAnimation(rotate);
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
