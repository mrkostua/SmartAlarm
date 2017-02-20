package com.example.mathalarm.firstsScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.mathalarm.Alarms.MathAlarm.MainMathAlarm;
import com.example.mathalarm.R;
import com.example.mathalarm.Settings_Preference;

public class MainActivity extends AppCompatActivity
{
    private Button bMathAlarm;
    Animation animationShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bMathAlarm = (Button) findViewById(R.id.bMathAlarm);

        animationShake = AnimationUtils.loadAnimation(this,R.anim.incorrect_button_shake);
        animationShake.setRepeatCount(3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MainMathAlarm.TAG, "MainActivity onResume");
    }

    @Override
    public void onBackPressed() {
        //doing nothing
    }

    public void bMathAlarmOnClickMethod(View view) {
        bMathAlarm.startAnimation(animationShake);

        Intent iMathAlarm;
        iMathAlarm = new Intent(this, MainMathAlarm.class);
        startActivity(iMathAlarm);
    }
    public void bMainSettings_OnClickMethod(View view) {
        Log.i(MainMathAlarm.TAG,"MainActivity bMainSettings_OnClickMethod");
        try {
            Intent intent = new Intent(MainActivity.this, Settings_Preference.class);
            startActivity(intent);
        }catch (Exception e) {
            Log.i(MainMathAlarm.TAG," bMainSettings_OnClickMethod" + e.getMessage());

        }
    }

}
