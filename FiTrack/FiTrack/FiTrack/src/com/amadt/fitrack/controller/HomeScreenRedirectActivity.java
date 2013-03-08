package com.amadt.fitrack.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HomeScreenRedirectActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
    	super.onResume();
    	Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}