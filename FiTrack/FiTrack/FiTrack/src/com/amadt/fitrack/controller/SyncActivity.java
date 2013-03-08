package com.amadt.fitrack.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SyncActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the SSync Window.");
        setContentView(textview);
    }
}
