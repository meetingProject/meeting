package com.example.admin.projectmeeting;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by user on 2015-02-05.
 */
public class onami_setting extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("세팅설정");
        setContentView(tv);
    }
}
