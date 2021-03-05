package com.demonative;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CustomView extends LinearLayout {

    private Context context;
    private TextView txt;
    private Button btn;

    public CustomView(Context context) {
        super(context);//ADD THIS
        this.context = context;
        init();
    }

    public void init() {
        inflate(context, R.layout.test, this);
        txt = findViewById(R.id.txt1);
        btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

}