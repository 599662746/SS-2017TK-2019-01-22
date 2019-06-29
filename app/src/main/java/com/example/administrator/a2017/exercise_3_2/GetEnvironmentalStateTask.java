package com.example.administrator.a2017.exercise_3_2;

import com.example.administrator.a2017.support.HttpRequest;

import org.json.JSONObject;

import java.util.TimerTask;

// 获取环境指标的任务，应当被 timer 对象定时执行；
public class GetEnvironmentalStateTask extends TimerTask {

    private Exercise_3_2_Activity activity;

    public GetEnvironmentalStateTask(Exercise_3_2_Activity activity){
        this.activity =activity;
    }

    @Override
    public void run() {
        final EnvironmentalStateBean stateBean = new EnvironmentalStateBean();

        Thread t0 = new HttpRequest(){
            @Override
            public void onResponse(JSONObject json) {
                stateBean.temperature = json.optInt("temperature");
                stateBean.humidity = json.optInt("humidity");
                stateBean.lightIntensity = json.optInt("LightIntensity");
                stateBean.co2 = json.optInt("co2");
                stateBean.pm2_5 = json.optInt("pm2.5");
            }
        }.doPostOnNewThread("GetAllSense.do","{\"UserName\":\"user1\"}");

        Thread t1 = new HttpRequest(){
            @Override
            public void onResponse(JSONObject json) {
                stateBean.status = json.optInt("Status");
            }
        }.doPostOnNewThread("GetRoadStatus.do","{\"UserName\":\"user1\",\"RoadId\":1}");

        try {
            t0.join();
            t1.join();
            activity.updateEnvironmentalState(stateBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
