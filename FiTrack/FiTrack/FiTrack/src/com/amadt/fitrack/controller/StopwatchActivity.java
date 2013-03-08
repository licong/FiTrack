package com.amadt.fitrack.controller;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import java.util.Timer;
import java.util.TimerTask;

import com.amadt.fitrack.R;
import com.amadt.fitrack.model.SimpleTimer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends Activity {
	private static final int TIMER_RESET = 2;
	private static final int TIMER_STARTED = 1;
	private static final int TIMER_STOPPED = 0;
	SimpleTimer wodTimer = new SimpleTimer();
	WodTimer wodTimerTask = new WodTimer();
	private int timerState = TIMER_STOPPED;
	TextView mTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stopwatch);
        
        mTimerTextView = (TextView)findViewById(R.id.timer_textview);
        
        registerReceiver(onBroadcast, new IntentFilter("Update UI"));
        Timer timer = new Timer(false);
        timer.schedule(wodTimerTask, 0, 50);
        
  	  Button timerButton = (Button)findViewById(R.id.timer_button);
	  timerButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			timerButtonPressed((Button)v);
		}
	});
	  Button timerButton1 = (Button)findViewById(R.id.timer_button1);
	  timerButton1.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			timerState = TIMER_RESET;
			timerButtonPressed((Button)v);
		}
	});
	  Button stopwatch = (Button) findViewById(R.id.timer);
      stopwatch.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
              Intent myIntent = new Intent(view.getContext(), TimerActivity.class);
              startActivityForResult(myIntent, 0);
              finish();
          }

      });
    }

    private void timerButtonPressed(Button button) {
    	final String resumeText = "Resume";
    	final String pauseText = "Pause";
		if (timerState == TIMER_STARTED) {
			Log.i("FiTrack",  button.getText().toString() + " Stop button pressed");
			timerState = TIMER_STOPPED;
			button.setText(resumeText);
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button));
			wodTimer.stop();
		}
		else {
		if (timerState == TIMER_STOPPED) {
			timerState = TIMER_STARTED;
			button.setText(pauseText);
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
			wodTimer.resume();
		}
		else {
			timerState = TIMER_STOPPED;
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_button));
			wodTimer.reset();
			Button timerButton = (Button)findViewById(R.id.timer_button);
			timerButton.setText("Start");
			timerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button));
		}
		}
	}
	private class WodTimer extends TimerTask {
		@Override
		public void run() {
			getApplicationContext().sendBroadcast(new Intent("Update UI"));
		}	
	}
	private final BroadcastReceiver onBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
        	wodTimer.update();
        	mTimerTextView.setText(wodTimer.toString());
        }
    };
    public void onStop() {
		super.onStop();
		
		unregisterReceiver(onBroadcast);
	}
}