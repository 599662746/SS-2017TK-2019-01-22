package com.example.administrator.a2017.exercise_3_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

// 插入环境指标平均值的任务，应当被 timer 对象定时执行；
public class InsertAvgEnvironmentalStateTask extends TimerTask {

    private Exercise_3_2_Activity activity;

    // 最近（1 分钟内）接收到的环境指标；每过 1 分钟清空一次；
    private List<EnvironmentalStateBean> stateBeans = new ArrayList<>();

    public InsertAvgEnvironmentalStateTask(Exercise_3_2_Activity activity) {
        this.activity = activity;
    }

    public synchronized void addEnvironmentalState(EnvironmentalStateBean stateBean) {
        stateBeans.add(stateBean);
    }

    @Override
    public synchronized void run() {
        // 复制最近接收到的环境指标；
        List<EnvironmentalStateBean> stateBeans = new ArrayList<>(this.stateBeans);
        // 清空最近接收到的环境指标；
        this.stateBeans.clear();
        // 建立平均值对象；
        EnvironmentalStateBean avgStateBean = new EnvironmentalStateBean();
        // 求和；
        for (EnvironmentalStateBean stateBean : stateBeans) {
            avgStateBean.temperature += stateBean.temperature;
            avgStateBean.humidity += stateBean.humidity;
            avgStateBean.lightIntensity += stateBean.lightIntensity;
            avgStateBean.co2 += stateBean.co2;
            avgStateBean.pm2_5 += stateBean.pm2_5;
            avgStateBean.status += stateBean.status;
        }
        // 计算平均值：用求和的结果除以数量；
        if (!stateBeans.isEmpty()) {
            avgStateBean.temperature /= stateBeans.size();
            avgStateBean.humidity /= stateBeans.size();
            avgStateBean.lightIntensity /= stateBeans.size();
            avgStateBean.co2 /= stateBeans.size();
            avgStateBean.pm2_5 /= stateBeans.size();
            avgStateBean.status /= stateBeans.size();

            // 打开或创建（如果不存在）数据库；
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("exercise_3_2.db",null);
            // 创建（如果不存在）表；
            db.execSQL("" //
                    + "CREATE TABLE IF NOT EXISTS environmental_state (               " //
                    + "     id INTEGER PRIMARY KEY AUTOINCREMENT,        " //
                    + "     temperature INTEGER,                            " //
                    + "     humidity INTEGER,                               " //
                    + "     lightIntensity INTEGER,                         " //
                    + "     co2 INTEGER,                                    " //
                    + "     pm2_5 INTEGER,                                  " //
                    + "     status INTEGER,                                 " //
                    + "     created_time INTEGER                            " //
                    + ")");
            // 插入平均值；
            db.execSQL("INSERT INTO environmental_state VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{
                    avgStateBean.temperature,
                    avgStateBean.humidity,
                    avgStateBean.lightIntensity,
                    avgStateBean.co2,
                    avgStateBean.pm2_5,
                    avgStateBean.status,
                    avgStateBean.time
            });
        }
    }

}
