package com.amadt.fitrack.controller;

//import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.amadt.fitrack.R;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.Gravatar;

public class ProfileActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
        setupView();
    }

	private void setupView() {
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(this);
		Gravatar gravatar = new Gravatar(apiClient.getEmail());
		
		Log.i("FiTrack", gravatar.getUrl());
		
		WebView avatarView = (WebView)findViewById(R.id.avatar_webview);
		avatarView.loadUrl(gravatar.getUrl());
		
		TextView usernameView = (TextView)findViewById(R.id.username_textview);
		usernameView.setText(apiClient.getUsername());
		
		TextView emailView = (TextView)findViewById(R.id.email_textview);
		emailView.setText(apiClient.getEmail());
		
		TextView descriptionView = (TextView)findViewById(R.id.description_textview);
		descriptionView.setText(apiClient.getDescription());
	}
}
