package com.amadt.fitrack.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TodaysWodActivity extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("\nTODAY'S WORKOUT OF THE DAY IS *******\n" +
        				 "-name\n-details\n-etc. etc");
        setContentView(textview);
    }
}
