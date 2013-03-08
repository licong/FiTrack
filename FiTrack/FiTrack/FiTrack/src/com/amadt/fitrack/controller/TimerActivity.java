package com.amadt.fitrack.controller;


import com.amadt.fitrack.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.ToneGenerator;
 
public class TimerActivity extends Activity {
    Button start, pause, reset, setT;
    TextView sec, min, msec;
    long m = 0;
    long s = 0;
    long ms = 0;
    long t = 100;
    MyCounter timer = new MyCounter(1000 *(60*(t/100) + (t%100)),1);
    
   // Context context = getApplicationContext();
    int duration = Toast.LENGTH_SHORT;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
 
        s = t % 100;
        m = t/100;
        
        start = (Button)findViewById(R.id.start);
        pause = (Button)findViewById(R.id.pause);
        reset = (Button)findViewById(R.id.reset);
        min  = (TextView)findViewById(R.id.min);
        msec  = (TextView)findViewById(R.id.msec);
        msec.setText("0"+ ms);
        min.setText(m+""); // starting from 1 minute.
        sec  = (TextView)findViewById(R.id.sec);
        
        if (s < 10)
        	sec.setText("0" + s);
        else
        	sec.setText(s+""); // starting from 0 seconds
  
        
        final long timeT = (1000 *(60*m + s));
        
        timer = new MyCounter(timeT,1);
     //   timeT = timer.saved;
        
        start.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if (timer.saved != 0)
            		newt(timer.saved);
                timer.start();
            }
        });
        pause.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	timer.pause();
            }
        });
        reset.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	timer.pause();
            	msec.setText("0"+ ms);
                min.setText(m+"");
                if (s < 10)
                	sec.setText("0" + s);
                else
                	sec.setText(s+"");
                timer.reset();
            	
            }
        });
        setT = (Button)findViewById(R.id.setTime);
        setT.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	timer.pause();
            	TextView time = (TextView)findViewById(R.id.editTime);
            	CharSequence i = time.getText();
            	t = Integer.parseInt(i.toString());
            	s = t % 100;
                m = t/100;
                ms = 0;
                msec.setText("0"+ ms);
                min.setText(m+"");
                if (s < 10)
                	sec.setText("0" + s);
                else
                	sec.setText(s+"");
                long timet = (1000 *(60*m + s));
                timer = new MyCounter(timet,1);
                timer.reset(); 
               
            }
        });
        Button stopwatch = (Button) findViewById(R.id.stopwatch);
        stopwatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), StopwatchActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });
    }
    public void newt(long s) {
    	timer = new MyCounter(s,1);
    }
   
 
    public class MyCounter extends CountDownTimer{
        
    	long saved; 
    	
        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
           // System.out.println("Timer Completed.");
            sec.setText("00");
            min.setText("0"); 
            msec.setText("00");
         //   Toolkit.getDefaultToolkit().beep();
            android.media.ToneGenerator tg=new ToneGenerator(AudioManager.STREAM_ALARM, 100);
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
        }
        
        @Override
        public void onTick(long millisUntilFinished) {
        	saved = millisUntilFinished;
        	long msecUntilFinished = (millisUntilFinished % 1000) / 10;
        	long minUntilFinished = (millisUntilFinished / 60000);
        	long secUntilFinished = (millisUntilFinished % 60000) / 1000;
        	if (secUntilFinished < 10)
        		sec.setText("0" + secUntilFinished);
        	else
        		sec.setText(secUntilFinished+"");
        	if (msecUntilFinished < 10)
            	msec.setText("0" + msecUntilFinished);
        	else
        		msec.setText(msecUntilFinished+"");
            min.setText(minUntilFinished+"");
        	}
        public void pause(){
        	cancel();
        	}
       public void reset(){
    	   saved = 0;
    	   start();
    	   cancel();
    	   }
        
        }

    } 