package com.example.mathalarm.Alarms.MathAlarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
    private Boolean onOffMusicPlayingRemember;
    private TextView tvTaskToSolve,tvNumber3;
    private int iAnswer = 0;
    private int iFalseAnswerCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity "+"onCreate");
        setContentView(R.layout.activity_display_alarm);

        onOffMusicPlayingRemember = true;
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

        tvTaskToSolve = (TextView) findViewById(R.id.tvTaskToSolve);
        tvNumber3 = (TextView) findViewById(R.id.tvNumber3);

        tvAlarmMessageText_EditorMethod();
        getAlarmComplexityLevel_Method();
        //stop WakeLock Service
        //this stops the service no matter how many times it was started.
        stopService (new Intent(this,WakeLockService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkAlarmState_Method();
        Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity "+"onDestroy");
    }

    private void getAlarmComplexityLevel_Method() {
        Intent intent = getIntent();
        int alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel",0);
        //0-easy,1-medium
        switch (alarmComplexityLevel) {
            case 0:
                //for Easy complexity 4 sign (1)
                CreateMathEasyTask_Method(1);
                Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity "+"Easy:" + iAnswer);
                break;
            case 1:
                CreateMathMediumTask_Method();
                Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity "+"Medium:" + iAnswer);
                break;
            default:
                Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity "+"default (complexity error):" + iAnswer);
                break;
        }
    }

    private void CreateMathEasyTask_Method(int complexityLevel) {
        Random random = new Random();
        int iNumber1 = random.nextInt(100 - 1) + 1;
        int iNumber2 = random.nextInt(10) + 1;


        int[] randomCharacterEasy = new int[2];
        //for medium complexity only 2 sign
         randomCharacterEasy[0] = random.nextInt(3 - 1) + 1;
        //for Easy complexity 4 sign
         randomCharacterEasy[1] = random.nextInt(5 - 1) + 1;

        switch (randomCharacterEasy[complexityLevel]) {
                case 1: { // /
                    iAnswer = iNumber1 / iNumber2;
                    tvTaskToSolve.setText("("+iNumber1 +" / "+ iNumber2+")");
                }break;
                case 2: { // *
                    iAnswer = iNumber1 * iNumber2;
                    tvTaskToSolve.setText("("+iNumber1 +" * "+ iNumber2+")");
                }break;
                case 3: { // +
                    iAnswer = iNumber1 + iNumber2;
                    tvTaskToSolve.setText("("+iNumber1 +" + "+ iNumber2+")");
                }break;
                case 4: { // -
                    iAnswer = iNumber1 - iNumber2;
                    tvTaskToSolve.setText("("+iNumber1 +" - "+ iNumber2+")");
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
                tvNumber3.setText("+ " + iNumber3);
            }break;
            case 2: { // -
                iAnswer = iAnswer - iNumber3;
                tvNumber3.setText("- " + iNumber3);
            }break;
        }
    }


    public void bStopAlarm_OnClickListener(View view) {
        final EditText etAnswer = new EditText(this);
        etAnswer.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("your answer is ? ")
                .setView(etAnswer)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckAnswer_Method(etAnswer.getText().toString());
                    }
                })
                .setNegativeButton("back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();


    }

    private void CheckAnswer_Method(String answer) {

            if( answer !=null && !answer.equals("")) {
                int iGetUserAnswer = Integer.parseInt(answer);

                if (iGetUserAnswer == iAnswer) {
                    iFalseAnswerCounter = 0;
                    Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity CheckAnswer_Method "+"iGetUserAnswer==iAnswer" + iGetUserAnswer + " == " + iAnswer);
                    checkAlarmState_Method();
                    onOffMusicPlayingRemember =false;
                } else if (iGetUserAnswer != iAnswer) {
                    Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity CheckAnswer_Method "+ "iGetUserAnswer!=iAnswer" + iGetUserAnswer + " != " + iAnswer);
                    iFalseAnswerCounter += 1;

                    if (iFalseAnswerCounter > 2) {
                        iFalseAnswerCounter = 0;
                        getAlarmComplexityLevel_Method();
                    }
                }
            }
        }




        private void checkAlarmState_Method() {
        //cancel the alarm  Only if Alarm was set (button On pressed)
        if (onOffMusicPlayingRemember)
            stopServices();
        //probably it is unreachable statement , because onOffMusicPlayingRemember can be false only (if user gave right answer for task) after what the activity will be finished
        else{
            Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity checkAlarmState_Method "+" Alarm wasn't set");
            Toast.makeText(this, "Alarm wasn't set", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopServices() {
        ////stop MathService and playing music
        stopService(new Intent(DisplayAlarmActivity.this,MathAlarmService.class));
        Log.i(MainMathAlarm.TAG,"DisplayAlarmActivity checkAlarmState_Method "+"stopService");

        Intent intent_startMainActivity= new Intent(DisplayAlarmActivity.this,MainActivity.class);
        intent_startMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_startMainActivity);
        finish();
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

    public void bSnoozeAlarm_OnClickListener(View view){
        Intent snoozeIntent = new Intent(MainMathAlarm.ALARM_SNOOZE_ACTION);
        sendBroadcast(snoozeIntent);
    }
}




