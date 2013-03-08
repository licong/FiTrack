package com.amadt.fitrack.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amadt.fitrack.R;


public class ViewExerciseActivity extends Activity{
	//String path = "android.resource://com.amadt.fitrack/raw/pull_ups";
	String path = "http://www.jsharkey.org/downloads/dailytest.3gp";
	//private VideoView mVideoView;
  TextView exerciseTextView;
  public void onCreate(Bundle saveInstanceState){
    super.onCreate(saveInstanceState);
    setContentView(R.layout.exercise_view);
    /*Intent intent = getIntent();
    intent.getStringExtra("EXERCISE_NAME");
    exerciseTextView = (TextView)findViewById(R.id.exercise_view_name);
	exerciseTextView.setText(intent.getStringExtra("EXERCISE_NAME"));
    ExerciseManager em = new ExerciseManager(this);
    Exercise exercise = em.getExercise(intent.getStringExtra("EXERCISE_NAME"));
    TextView text = (TextView)findViewById(R.id.description);
    text.setText(exercise.getDescription());*/
    
    //String url = "http://media.crossfit.com/cf-video/CrossFit_AdrianKippingPullUps1.wmv";
    TextView x = (TextView)findViewById(R.id.description);
    x.setText(Html.fromHtml("This is a "+"<a href=\"http://media.crossfit.com/cf-video/CrossFit_AdrianKippingPullUps1.wmv\">link</a>"+" to CrossFit"));
    x.setMovementMethod(LinkMovementMethod.getInstance());
    Button viewYoutube;
    viewYoutube = (Button) findViewById(R.id.button1);
    viewYoutube.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=tzD9BkXGJ1M&feature=player_detailpage")));          
        	   
        	
        }
    });
    Button video;
    video = (Button) findViewById(R.id.button2);
    video.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	startActivity(new Intent(ViewExerciseActivity.this, ExerciseVideoActivity.class));          
        	   
        	
        }
    });
   
  
    //VideoView mVideoView = (VideoView) findViewById(R.id.VideoView);
    //the VideoView will hold the video
    //mVideoView = new VideoView(this); 
   
  }
}
