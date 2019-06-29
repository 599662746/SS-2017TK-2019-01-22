package com.example.administrator.a2017.support;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

// 去除 Activity 自带的标题栏，项目中其它 Activity 从此类继承；
public class NoTitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
