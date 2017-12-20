package com.mrkostua.mathalarm.firstsScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.mrkostua.mathalarm.Alarms.MathAlarm.MainAlarmActivity;
import com.mrkostua.mathalarm.Alarms.MathAlarm.MainMathAlarm;
import com.mrkostua.mathalarm.Alarms.MathAlarm.SetAlarmFromHistory;
import com.mrkostua.mathalarm.R;
import com.mrkostua.mathalarm.ShowLogs;

public class MainActivity extends AppCompatActivity {
    private Button bMathAlarm;
    public static final String TAG = MainActivity.class.getSimpleName();
    Animation animationShake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bMathAlarm = (Button) findViewById(R.id.bMathAlarm);

        animationShake = AnimationUtils.loadAnimation(this, R.anim.incorrect_button_shake);
        animationShake.setRepeatCount(3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ShowLogs.LOG_STATUS) ShowLogs.i("MainActivity onResume");
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

    public void bSetFromHistoryMethod(View view) {
        if (ShowLogs.LOG_STATUS) ShowLogs.i("MainActivity bSetFromHistoryMethod");
        startActivity(new Intent(this, SetAlarmFromHistory.class));
    }

    public void bMainSettings_OnClickMethod(View view) {
        if (ShowLogs.LOG_STATUS) ShowLogs.i("MainActivity bMainSettings_OnClickMethod");
        startActivity(new Intent(this, MainAlarmActivity.class));
    }

}
