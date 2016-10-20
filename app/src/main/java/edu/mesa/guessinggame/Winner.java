package edu.mesa.guessinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Winner extends AppCompatActivity {

    int defaultInt = 0;
    int hiScoreWinner;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String dataName = "highscore";
    String intName = "score";

    TextView textHighScore;
    TextView winText;
    TextView youGotNewHighScore;
    ImageView imageView;
    ImageView imageViewLose;
    Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        preferences = getSharedPreferences(dataName, getApplicationContext().MODE_PRIVATE);
        editor = preferences.edit();

        //get previous highscore
        hiScoreWinner = preferences.getInt(intName, defaultInt);


        textHighScore = (TextView) findViewById(R.id.textHighScore);
        winText = (TextView) findViewById(R.id.winText);
        imageView = (ImageView) findViewById(R.id.imageView);
        youGotNewHighScore = (TextView) findViewById(R.id.yougotnewhighscore);
        playAgain = (Button) findViewById(R.id.playAgain);
        imageViewLose = (ImageView) findViewById(R.id.imageViewLose);
        playAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Winner.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(MainActivity.hiScore>hiScoreWinner){
            MediaPlayer mPlayer = MediaPlayer.create(Winner.this, R.raw.win);
            mPlayer.start();
            textHighScore.setText("Score: "+MainActivity.hiScore);
            editor.putInt(intName,MainActivity.hiScore);
            editor.commit();

        }else{
            textHighScore.setText("Score: "+MainActivity.hiScore);
            MediaPlayer mPlayer = MediaPlayer.create(Winner.this, R.raw.lose);
            mPlayer.start();
            winText.setText("You Lose, Try Again!");
            imageView.setVisibility(View.INVISIBLE);
            imageViewLose.setVisibility(View.VISIBLE);
            textHighScore.setVisibility(View.VISIBLE);
            youGotNewHighScore.setVisibility(View.INVISIBLE);

        }
    }
}
