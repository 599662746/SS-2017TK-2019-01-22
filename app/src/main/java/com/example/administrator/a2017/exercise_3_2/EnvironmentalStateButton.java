package com.example.administrator.a2017.exercise_3_2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.a2017.R;

// 自定义按钮，能够显示环境指标名称和对应的值，正常值显示绿色背景，超出正常范围后显示红色背景；
public class EnvironmentalStateButton extends FrameLayout {

    public EnvironmentalStateButton(Context context) {
        super(context);
    }

    public EnvironmentalStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.button_environmental_state, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EnvironmentalStateButton);
        if (typedArray != null) {
            TextView labelView=findViewById(R.id.label);
            String label = typedArray.getString(R.styleable.EnvironmentalStateButton_label);
            labelView.setText(label);
            typedArray.recycle();
        }
    }

    public void setValue(int value){
        TextView tv=findViewById(R.id.value);
        tv.setText(String.valueOf(value));
    }

    public void setAlertState(boolean state){
        if(state){
            findViewById(R.id.value).setBackgroundResource(R.drawable.button_environmental_state_value_alert);
        }else{
            findViewById(R.id.value).setBackgroundResource(R.drawable.button_environmental_state_value_normal);
        }
    }

}
