package com.example.lpeng.tiptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;

public class StartActivity extends AppCompatActivity {

    public void runMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_main);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        /*Timer timer = new Timer();
        timer.schedule();*/


        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    //Log.i("tag", "This'll run 3000 milliseconds later");
                    TextView searchingText = (TextView) findViewById(R.id.searchingText);
                    searchingText.setVisibility(View.VISIBLE);

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    //Log.i("tag", "Again, This'll run 3000 milliseconds later");
                                    runMain();
                                }
                            }, 3000);
                }
            }, 3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
