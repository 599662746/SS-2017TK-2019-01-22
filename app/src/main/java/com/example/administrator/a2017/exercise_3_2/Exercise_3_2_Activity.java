package com.example.administrator.a2017.exercise_3_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.a2017.R;
import com.example.administrator.a2017.SingleSensorActivity;
import com.example.administrator.a2017.support.NoTitleActivity;

import java.util.Timer;

// 第三部分 -> 编程题 2 -> 主界面；
public class Exercise_3_2_Activity extends NoTitleActivity{

    // 获取环境指标任务；
    private GetEnvironmentalStateTask getEnvironmentalStateTask = new GetEnvironmentalStateTask(this);
    // 插入环境指标平均值任务；
    private InsertAvgEnvironmentalStateTask insertAvgEnvironmentalStateTask = new InsertAvgEnvironmentalStateTask(this);

    // 显示环境指标的 6 个按钮；
    private EnvironmentalStateButton temperatureButton;
    private EnvironmentalStateButton humidityButton;
    private EnvironmentalStateButton lightIntensityButton;
    private EnvironmentalStateButton co2Button;
    private EnvironmentalStateButton pm2_5_Button;
    private EnvironmentalStateButton statusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_3_2);

        // 找到并保存有关控件；
        temperatureButton = findViewById(R.id.temperature);
        humidityButton = findViewById(R.id.humidity);
        lightIntensityButton = findViewById(R.id.lightIntensity);
        co2Button = findViewById(R.id.co2);
        pm2_5_Button = findViewById(R.id.pm2_5);
        statusButton = findViewById(R.id.status);

        Timer timer = new Timer();
        // 定时执行获取环境指标任务；
        timer.scheduleAtFixedRate(getEnvironmentalStateTask, 0, 5000);
        // 定时执行插入环境指标平均值任务；
        timer.scheduleAtFixedRate(insertAvgEnvironmentalStateTask, 0, 60 * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭定时任务；
        getEnvironmentalStateTask.cancel();
        insertAvgEnvironmentalStateTask.cancel();
    }

    // 当获得最新的环境指标时，通过此方法更新 UI 并保存到数据库中（委托给：插入环境指标平均值任务）；
    public void updateEnvironmentalState(final EnvironmentalStateBean stateBean) {
        insertAvgEnvironmentalStateTask.addEnvironmentalState(stateBean);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                temperatureButton.setValue(stateBean.temperature);
                temperatureButton.setAlertState(stateBean.temperature > 20);
                humidityButton.setValue(stateBean.humidity);
                lightIntensityButton.setValue(stateBean.lightIntensity);
                co2Button.setValue(stateBean.co2);
                pm2_5_Button.setValue(stateBean.pm2_5);
                statusButton.setValue(stateBean.status);
            }
        });
    }

    public void startTemperatureActivity(View view) {
        startActivity(new Intent(this, SingleSensorActivity.class));
    }

}
