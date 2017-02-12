package com.example.mathalarm.Alarms.MathAlarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathalarm.R;
import com.example.mathalarm.firstsScreens.MainActivity;

import java.util.Random;

public class DisplayAlarmActivity extends AppCompatActivity
{
    private static final String TAG = "AlarmProcess";
    private Boolean OnOffRemember = false;

    private TextView tvNumber1,tvNumber2, tvMathSign,tvNumber3;
    private EditText etAnswer;
    private int iAnswer = 0;
    private int iFalseAnswerCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"DisplayAlarmActivity "+"onCreate");
        setContentView(R.layout.activity_display_alarm);

        final Window window = getWindow();
        /**@FLAG_DISMISS_KEYGUARD
         * Window flag: when set the window will cause the keyguard to be dismissed, only if it is
         * not a secure lock keyguard. Because such a keyguard is not needed for security, it will
         * never re-appear if the user navigates to another window (in contrast to FLAG_SHOW_WHEN_LOCKED,
         * which will only temporarily hide both secure and non-secure keyguards but ensure they reappear
         * when the user moves to another UI that doesn't hide them). If the keyguard is currently active
         * and is secure (requires an unlock pattern) than the user will still need to confirm it before
         * seeing this window, unless FLAG_SHOW_WHEN_LOCKED has also been set.
         */
        /*flags to show activity if user device have keyguard*/
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        /*flags to turn screen on */
        |WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        |WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
        tvMathSign = (TextView) findViewById(R.id.tvMathSign);
        tvNumber2 = (TextView) findViewById(R.id.tvNumber2);
        tvNumber3 = (TextView) findViewById(R.id.tvNumber3);
        etAnswer = (EditText) findViewById(R.id.etAnswer);

        tvAlarmMessageText_EditorMethod();
        getAlarmComplexityLevel_Method();
        //stop WakeLock Service
        //this stops the service no matter how many times it was started.
        stopService (new Intent(this,WakeLockService.class));
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"DisplayAlarmActivity "+"onResume");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopServices();
        Log.i(TAG,"DisplayAlarmActivity "+"onDestroy");
    }

    private void getAlarmComplexityLevel_Method() {
        Intent intent = getIntent();
        int alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel",0);
        //0-easy,1-medium
        switch (alarmComplexityLevel) {
            case 0:
                //for Easy complexity 4 sign (1)
                CreateMathEasyTask_Method(1);
                Log.i(TAG,"DisplayAlarmActivity "+"Easy:" + iAnswer);
                break;
            case 1:
                CreateMathMediumTask_Method();
                Log.i(TAG,"DisplayAlarmActivity "+"Medium:" + iAnswer);
                break;
            default:
                Log.i(TAG,"DisplayAlarmActivity "+"default (complexity error):" + iAnswer);
                break;
        }
    }

    private void CreateMathEasyTask_Method(int complexityLevel) {
        Random random = new Random();
        int iNumber1 = random.nextInt(100 - 1) + 1;
        int iNumber2 = random.nextInt(100 - 1) + 1;
        tvNumber1.setText("("+iNumber1);
        tvNumber2.setText(iNumber2 + ")");

        int[] randomCharacterEasy = new int[2];
        //for medium complexity only 2 sign
         randomCharacterEasy[0] = random.nextInt(3 - 1) + 1;
        //for Easy complexity 4 sign
         randomCharacterEasy[1] = random.nextInt(5 - 1) + 1;

        switch (randomCharacterEasy[complexityLevel]) {
                case 1: { // /
                    iAnswer = iNumber1 / iNumber2;
                    tvMathSign.setText("/");
                }break;
                case 2: { // *
                    iAnswer = iNumber1 * iNumber2;
                    tvMathSign.setText("*");
                }break;
                case 3: { // +
                    iAnswer = iNumber1 + iNumber2;
                    tvMathSign.setText("+");
                }break;
                case 4: { // -
                    iAnswer = iNumber1 - iNumber2;
                    tvMathSign.setText("-");
                }break;
            }
    }

    private void CreateMathMediumTask_Method() {
        //establish first to numbers of the task
        //for medium complexity only 2 sign (0)
        CreateMathEasyTask_Method(0);
        tvNumber3.setVisibility(View.VISIBLE);

        Random random = new Random();
        int randomCharacterMedium = random.nextInt(3 - 1) + 1;
        int iNumber3 = random.nextInt(100 - 1) + 1;

        switch (randomCharacterMedium){
            case 1: { // +
                iAnswer = iAnswer + iNumber3;
                tvNumber3.setText("+" + iNumber3);
            }break;
            case 2: { // -
                iAnswer = iAnswer - iNumber3;
                tvNumber3.setText("-" + iNumber3);
            }break;
        }
    }

    public void bCheckAnswerInClickListener(View v) {
            String getUserAnswer = etAnswer.getText().toString();
            if(!getUserAnswer.equals("")) {
                int iGetUserAnswer = Integer.parseInt(getUserAnswer);

                if (iGetUserAnswer == iAnswer) {
                    iFalseAnswerCounter = 0;
                    Log.i(TAG,"DisplayAlarmActivity bCheckAnswerInClickListener "+"iGetUserAnswer==iAnswer" + iGetUserAnswer + " == " + iAnswer);
                    checkAlarmState_Method();

                } else if (iGetUserAnswer != iAnswer) {
                    Log.i(TAG,"DisplayAlarmActivity bCheckAnswerInClickListener "+ "iGetUserAnswer!=iAnswer" + iGetUserAnswer + " != " + iAnswer);
                    iFalseAnswerCounter += 1;

                    if (iFalseAnswerCounter > 2) {
                        iFalseAnswerCounter = 0;
                        getAlarmComplexityLevel_Method();
                    }
                }
            }
        }

        private void checkAlarmState_Method() {
        Intent intent = getIntent();
        OnOffRemember = intent.getBooleanExtra("AlarmCreatedKey",false);
        //cancel the alarm  Only if Alarm was set (button On pressed)
        if (OnOffRemember)
            stopServices();
        else{
            Log.i(TAG,"DisplayAlarmActivity checkAlarmState_Method "+" Alarm wasn't set");
            Toast.makeText(this, "Alarm wasn't set", Toast.LENGTH_SHORT).show();
        }
    }
    private void stopServices() {
        ////stop MathService and playing music
        stopService(new Intent(DisplayAlarmActivity.this,MathAlarmService.class));
        Log.i(TAG,"DisplayAlarmActivity checkAlarmState_Method "+"stopService");

        Intent intent_startMainActivity= new Intent(DisplayAlarmActivity.this,MainActivity.class);
        startActivity(intent_startMainActivity);
        OnOffRemember = false;
    }

    private void tvAlarmMessageText_EditorMethod() {
        Intent intent = getIntent();
        String defaultAlarmMessageText = "\"" +"Good morning sir" +"\"";
        String alarmMessageText = intent.getExtras().getString("alarmMessageText",defaultAlarmMessageText);

        TextView tvStartedAlarmMessageText = (TextView) findViewById(R.id.tvStartedAlarmMessageText);
        tvStartedAlarmMessageText.setText(alarmMessageText);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //impossible to overwrite (protected from this , built into system to prevent malicious apps that cannot be exited)
            case KeyEvent.KEYCODE_HOME: {
                return true;
            }
            case KeyEvent.KEYCODE_BACK: {
                return true;
            }
            case KeyEvent.KEYCODE_VOLUME_UP: {
                return true;
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN: {
                return true;
            }
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}




