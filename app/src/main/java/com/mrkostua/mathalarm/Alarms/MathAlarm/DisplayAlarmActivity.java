package com.mrkostua.mathalarm.Alarms.MathAlarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mrkostua.mathalarm.ShowLogsOld;
import com.mrkostua.mathalarm.Tools.ConstantValues;
import com.mrkostua.mathalarm.MathAlarmTaskGenerator;
import com.mrkostua.mathalarm.R;
import com.mrkostua.mathalarm.firstsScreens.MainActivity;


public class DisplayAlarmActivity extends AppCompatActivity {
    private Boolean onOffMusicPlayingRemember;
    private TextView tvTaskToSolve,tvNumber3;
    private int iAnswer = 0;
    private int iFalseAnswerCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_alarm);
        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity "+"onCreate");

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
        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity "+"onDestroy");
    }

    private void getAlarmComplexityLevel_Method() {
        MathAlarmTaskGenerator mathAlarmTaskGenerator = new MathAlarmTaskGenerator();
        Intent intent = getIntent();
        int alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel",0);
        //0-easy,1-medium, 2-hard, 3-unbelievably hard
        switch (alarmComplexityLevel) {
            case 0:
                mathAlarmTaskGenerator.GeneratorEquation(0);
                iAnswer = mathAlarmTaskGenerator.ResultGetter();
                tvTaskToSolve.setText("("+String.valueOf(mathAlarmTaskGenerator.Number1Getter())+" " +
                        mathAlarmTaskGenerator.SymbolGetter() + " " + String.valueOf(mathAlarmTaskGenerator.Number2Getter())+")");

                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity "+"Easy:" + String.valueOf(iAnswer));
                break;

            case 1:
                mathAlarmTaskGenerator.GeneratorEquation(1);
                iAnswer = mathAlarmTaskGenerator.ResultGetter();
                tvTaskToSolve.setText("("+String.valueOf(mathAlarmTaskGenerator.Number1Getter())+" " +
                        mathAlarmTaskGenerator.SymbolGetter() + " " + String.valueOf(mathAlarmTaskGenerator.Number2Getter())+")");
                tvNumber3.setText(mathAlarmTaskGenerator.Symbol2Getter() + " "
                        + String.valueOf(mathAlarmTaskGenerator.Number3Getter()));

                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity "+"Medium:" + String.valueOf(iAnswer));
                break;
            case 2:
                mathAlarmTaskGenerator.GeneratorEquation(2);
                iAnswer = mathAlarmTaskGenerator.ResultGetter();
                tvTaskToSolve.setText("("+String.valueOf(mathAlarmTaskGenerator.Number1Getter())+" " +
                        mathAlarmTaskGenerator.SymbolGetter() + " "
                        + String.valueOf(mathAlarmTaskGenerator.Number2Getter())+")");

                tvNumber3.setText(mathAlarmTaskGenerator.Symbol2Getter() + " ("
                        + String.valueOf(mathAlarmTaskGenerator.Number3Getter()) + ")^" + mathAlarmTaskGenerator.PowerNumberGetter());

                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity "+"Hard:" + String.valueOf(iAnswer));
                break;

            case 3:

                break;
            default:
                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity "+"default (complexity error):" + iAnswer);
                break;
        }
    }


    public void bStopAlarm_OnClickListener(View view) {
        final EditText etAnswer = new EditText(DisplayAlarmActivity.this);
        etAnswer.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayAlarmActivity.this,R.style.AlertDialogCustomStyle);
        builder.setTitle(R.string.displayAlarmActivity_bStopAlarmAlertDialog)
                .setMessage(tvTaskToSolve.getText().toString() +" "+ tvNumber3.getText().toString() + "=")
                .setView(etAnswer)
                .setPositiveButton(R.string.DAA_stopAlarmPositiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CheckAnswer_Method(etAnswer.getText().toString());
                    }
                })
                .setNegativeButton(R.string.DAA_stopAlarmNegativeButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    private void CheckAnswer_Method(String answer) {
            if( answer !=null && !answer.equals("")) {
                int iGetUserAnswer = Integer.parseInt(answer);

                if (iGetUserAnswer == iAnswer) {
                    iFalseAnswerCounter = 0;
                    if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity CheckAnswer_Method "+"iGetUserAnswer==iAnswer " + iGetUserAnswer + " == " + iAnswer);
                    checkAlarmState_Method();
                    onOffMusicPlayingRemember =false;
                }
                else {
                    if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity CheckAnswer_Method "+ "iGetUserAnswer!=iAnswer " + iGetUserAnswer + " != " + iAnswer);
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
            if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity checkAlarmState_Method "+" Alarm wasn't set");
        }
    }

    private void stopServices() {
        ////stop MathService and playing music
        stopService(new Intent(DisplayAlarmActivity.this,MathAlarmService.class));
        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("DisplayAlarmActivity checkAlarmState_Method "+"stopService");

        Intent intent_startMainActivity= new Intent(DisplayAlarmActivity.this,MainActivity.class);
        intent_startMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_startMainActivity);
        finish();
    }

    private void tvAlarmMessageText_EditorMethod() {
        Intent intent = getIntent();
        String alarmMessageText = intent.getExtras().getString("alarmMessageText",getString(R.string.edHintGood_morning_sir));

        TextView tvStartedAlarmMessageText = (TextView) findViewById(R.id.tvStartedAlarmMessageText);
        tvStartedAlarmMessageText.setText("\"" + alarmMessageText +"\"");
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
        Intent snoozeIntent = new Intent(ConstantValues.INSTANCE.getSNOOZE_ACTION());
        sendBroadcast(snoozeIntent);
    }

}




