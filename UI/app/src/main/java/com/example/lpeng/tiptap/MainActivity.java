package com.example.lpeng.tiptap;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i("MainActivity: ", "starting MainActivity");

        Log.v("Sound: ", "Initializing sounds...");

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.mariocoin);

        Button play_button = (Button)this.findViewById(R.id.tipButton);
        play_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("Sound: ", "Playing sound...");
                mp.start();

                Log.i("tipButton: ", "clickEvent");
                EditText enterTip = (EditText) findViewById(R.id.enterTip);
                enterTip.setVisibility(View.INVISIBLE);

                ImageView blueCoin = (ImageView) findViewById(R.id.blueCoin);
                blueCoin.setVisibility(View.INVISIBLE);

                ImageView greenCoin = (ImageView) findViewById(R.id.greenCoin);
                greenCoin.setVisibility(View.INVISIBLE);

                ImageView arrow = (ImageView) findViewById(R.id.arrow);
                arrow.setVisibility(View.INVISIBLE);

                Button tipButton = (Button) findViewById(R.id.tipButton);
                tipButton.setVisibility(View.INVISIBLE);

                TextView thanksText = (TextView) findViewById(R.id.thanksText);
                thanksText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void giveTip(View view) {
        //EditText tipAmount = (EditText) findViewById(R.id.editText)



    }


}
