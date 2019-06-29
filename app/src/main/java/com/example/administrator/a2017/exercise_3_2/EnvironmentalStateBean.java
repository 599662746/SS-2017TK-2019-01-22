package com.example.administrator.a2017.exercise_3_2;

// 封装服务端反馈的 6 个环境指标数据；
public class EnvironmentalStateBean {

    public final long time = System.currentTimeMillis();

    public int temperature;
    public int humidity;
    public int lightIntensity;
    public int co2;
    public int pm2_5;

    public int status;

}
