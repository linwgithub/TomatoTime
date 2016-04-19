package com.linw.tomatotime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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

    }

    public void setWorkTime(View view) {
        if (sp.setStringKey(null, Info.StrKeyWorkTime, String.valueOf(workTime))) {
            ToastUtil.showToast(this, "设置成功");
        } else {
            ToastUtil.showToast(this, "设置失败");
        }
    }

    public void setRestTime(View view) {
        if (sp.setStringKey(null, Info.StrKeyRestTime, String.valueOf(restTime))) {
            ToastUtil.showToast(this, "设置成功");
        } else {
            ToastUtil.showToast(this, "设置失败");
        }
    }


}
