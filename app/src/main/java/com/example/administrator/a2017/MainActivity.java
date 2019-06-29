package com.example.administrator.a2017;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.a2017.exercise_3_2.Exercise_3_2_Activity;
import com.example.administrator.a2017.support.NoTitleActivity;

public class MainActivity extends NoTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start_exercise_3_2(View view) {
        startActivity(new Intent(this, Exercise_3_2_Activity.class));
    }
}
