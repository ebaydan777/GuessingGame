package edu.mesa.guessinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int defaultInt = 0;
    private SoundPool soundPool;
    int sample1 = -1;

    SharedPreferences preferences;
    String dataName = "highscore";
    String intName = "score";


    public static int hiScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize our two SharedPreferences objects
        preferences = getSharedPreferences(dataName, getApplicationContext().MODE_PRIVATE);

        hiScore = preferences.getInt(intName, defaultInt);
        //Make a reference to the Hiscore textview in layout
        TextView textHiScore = (TextView) findViewById(R.id.textHiScore);

        //Display the high score
        textHiScore.setText("High Score: " + hiScore);

        Button buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            //Create objects of the 2 required classes
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;
            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("Play.wav");
            sample1 = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            //catch exceptions here
        }

    }


    @Override
    public void onClick(View view) {

        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.buttonclick);
        soundPool.play(sample1, 1, 1, 0, 0, 1);
        mPlayer.start();
        Intent i;
        i = new Intent(this, LevelOne.class);
        startActivity(i);
    }
}
