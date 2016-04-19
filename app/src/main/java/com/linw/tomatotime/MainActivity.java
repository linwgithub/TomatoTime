package com.linw.tomatotime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.linw.tomatotime.data.Info;
import com.linw.tomatotime.util.SharedPrefUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@EActivity
public class MainActivity extends AppCompatActivity {

    long curTime = 0;
    long workTime;
    long restTime;
    boolean status = true;//true：工作  false:休息
    SharedPrefUtil sharedPrefUtil;

    @ViewById(R.id.tv_click_time)
    TextView tvclicktime;
    @ViewById(R.id.tv_title)
    TextView tvtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(curTime);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        curTime = calendar.getTimeInMillis();

        sharedPrefUtil = SharedPrefUtil.getSingleInstance(this);
        String workTimeStr = sharedPrefUtil.getStringKeyVal(null, Info.StrKeyWorkTime, "");
        if (workTimeStr.isEmpty()) {
//            workTime = 25 * 60 * 1000 + curTime;
            workTime = 25 * 60 * 1000 ;//+ curTime;
        } else {
            workTime = Long.valueOf(workTimeStr);
        }

        String restTimeStr = sharedPrefUtil.getStringKeyVal(null, Info.StrKeyRestTime, "");
        if (restTimeStr.isEmpty()) {
            restTime = 5 * 60 * 1000 ;//+ curTime;
        } else {
            restTime = Long.valueOf(restTimeStr);
        }

        settime();
    }

    @UiThread
    public void settime() {
        long time ;
        if (status) {
            time = workTime;
        } else {
            time = restTime;
        }

        if (time >= 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String timeStr = sdf.format(new Date(time));
            tvclicktime.setText(timeStr);
        } else {
            timer.cancel();
            shwoTimeOverDialog();
        }
    }

    private void shwoTimeOverDialog() {
        String currTimeTitle = "工作";
        String nextTimeTitle = "休息";
        if (!status) {
            currTimeTitle = "休息";
            nextTimeTitle = "工作";
        }
        new AlertDialog.Builder(this)
                .setMessage(currTimeTitle + "时间已结束，是否开启" + nextTimeTitle)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        status = !status;
                        tvtitle.setText(!status ? "休息" : "工作");
                        startTime();
                        dialog.dismiss();
                    }
                }).show();
    }

    Timer timer;

    private void startTime() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (status) {
                    workTime -= 1000;
                } else {
                    restTime -= 1000;
                }

                settime();
            }
        };

        timer.schedule(task, 1000, 1000);
    }


    public void startWorkClick(View view) {
        tvtitle.setText("工作");
        status = true;
        startTime();
    }

    public void startRestClick(View view) {
        tvtitle.setText("休息");
        status = false;
        startTime();
    }

    public void pauseClick(View view) {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}
