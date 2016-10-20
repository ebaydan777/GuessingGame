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

public class LevelTwo extends AppCompatActivity implements View.OnClickListener{

    int ourRandom;
    int correctAnswerPoints = 5;
    Random randInt = new Random();

    private SoundPool soundPool;
    int sample1 = -1;
    int sample2 = -1;

    TextView levelTwoHighScore;
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonFive;
    Button buttonReplayLevelTwo;
    Animation wobble;
    Animation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two);
        wobble = AnimationUtils.loadAnimation(this, R.anim.wobble);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);


        buttonOne = (Button)findViewById(R.id.buttonOneLevelTwo);
        buttonTwo = (Button)findViewById(R.id.buttonTwoLevelTwo);
        buttonThree = (Button)findViewById(R.id.buttonThreeLevelTwo);
        buttonFour = (Button)findViewById(R.id.buttonFour);
        buttonFive = (Button)findViewById(R.id.buttonFive);
        buttonReplayLevelTwo = (Button) findViewById(R.id.buttonReplayLevelTwo);
        levelTwoHighScore = (TextView) findViewById(R.id.levelTwoHighScore);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonReplayLevelTwo.setOnClickListener(this);

        //set current high score
        levelTwoHighScore.setText("Score: "+MainActivity.hiScore);


        ourRandom = randInt.nextInt(5);
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

            case R.id.buttonOneLevelTwo:
                checkAnswer(R.id.buttonOneLevelTwo,1);
                break;
            case R.id.buttonTwoLevelTwo:
                checkAnswer(R.id.buttonTwoLevelTwo,2);
                break;
            case R.id.buttonThreeLevelTwo:
                checkAnswer(R.id.buttonThreeLevelTwo,3);
                break;
            case R.id.buttonFour:
                checkAnswer(R.id.buttonFour,4);
                break;
            case R.id.buttonFive:
                checkAnswer(R.id.buttonFive,5);
                break;
            default:
                Intent intent = new Intent(LevelTwo.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void checkAnswer(int buttonid,int answer) {

        //check answer is correct
        if (answer==ourRandom){
            Button b = (Button) findViewById(buttonid);
            b.startAnimation(wobble);
            Toast.makeText(getApplicationContext(),"Correct Guess!",Toast.LENGTH_LONG).show();
            soundPool.play(sample1, 1, 1, 0, 0, 1);
            MainActivity.hiScore = correctAnswerPoints + MainActivity.hiScore;
            Intent intent = new Intent(LevelTwo.this,LevelThree.class);
            gotoNextPage(intent);

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
