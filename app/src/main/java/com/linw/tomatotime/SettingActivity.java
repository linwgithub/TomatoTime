package com.linw.tomatotime;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.linw.tomatotime.data.Info;
import com.linw.tomatotime.util.SharedPrefUtil;
import com.linw.tomatotime.util.ToastUtil;

public class SettingActivity extends AppCompatActivity {

    private android.widget.EditText etsettingworktime;
    private android.widget.EditText etsettingresttime;
    SharedPrefUtil sp;
    long workTime;
    long restTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sp = SharedPrefUtil.getSingleInstance(this);
        String workTimeStr = sp.getStringKeyVal(null, Info.StrKeyWorkTime, "");
        if (workTimeStr.isEmpty()) {
            workTime = 25 * 60 * 1000;
        } else {
            workTime = Long.valueOf(workTimeStr);
        }

        String restTimeStr = sp.getStringKeyVal(null, Info.StrKeyRestTime, "");
        if (restTimeStr.isEmpty()) {
            restTime = 5 * 60 * 1000 ;
        } else {
            restTime = Long.valueOf(restTimeStr);
        }
        this.etsettingworktime = (EditText) findViewById(R.id.et_setting_worktime);
        etsettingworktime.setText(workTime + "");

        this.etsettingresttime = (EditText) findViewById(R.id.et_setting_resttime);
        etsettingresttime.setText(restTime + "");

        findViewById(R.id.view_worktime).setOnClickListener(listener);
    }

    boolean whichTime;//设置什么时间：true：工作时间  false：休息时间
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_worktime:
                    whichTime = true;
                    showTimePickerDialog();
                    break;
                case R.id.view_resttime:
                    whichTime = false;
                    showTimePickerDialog();
                    break;
                default:
                    break;
            }
        }
    };

    public void setTime(View view) {
        boolean setWorkTime = sp.setStringKey(null, Info.StrKeyWorkTime, String.valueOf(workTime));
        boolean setRestTime = sp.setStringKey(null, Info.StrKeyRestTime, String.valueOf(restTime));
        if (setWorkTime && setRestTime) {
            ToastUtil.showToast(this, "设置成功");
        } else {
            ToastUtil.showToast(this, "设置失败");
        }
    }

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    };

    private void showTimePickerDialog() {
        new TimePickerDialog(this, timeSetListener, 0, 0, false).show();
    }
}
