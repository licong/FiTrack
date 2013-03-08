package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ExerciseVideoActivity extends Activity{
	 public void onCreate(Bundle savedInstanceState)
	  {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.exercise_video_view);
	    String path = "http://www.jsharkey.org/downloads/dailytest.3gp";
	    String path2 = "http://www.youtube.com/watch?feature=player_detailpage&v=tzD9BkXGJ1M";
	    if (path == "") {
	        // Tell the user to provide a media file URL/path.
	        Toast.makeText(
	                ExerciseVideoActivity.this,
	                "Please edit VideoViewDemo Activity, and set path"
	                        + " variable to your media file URL/path",
	                Toast.LENGTH_LONG).show();

	         } else {

	     
	        	 VideoView myVideoView = (VideoView)findViewById(R.id.VideoView);
	             myVideoView.setVideoURI(Uri.parse(path2));
	             myVideoView.setMediaController(new MediaController(this));
	             myVideoView.requestFocus();
	             myVideoView.start();
	    }
	  }
}
