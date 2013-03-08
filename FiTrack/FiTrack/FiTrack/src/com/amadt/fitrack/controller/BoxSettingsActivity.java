package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BoxSettingsActivity extends Activity {
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
     
		setContentView(R.layout.box_settings);
		
		setupButtons();
	}
	
	private void setupButtons() {
		Button followBoxButton = (Button)findViewById(R.id.follow_box_button);
		Button stopFollowingBoxButton = (Button)findViewById(R.id.stop_following_box_button);
		Button createBoxButton = (Button)findViewById(R.id.create_box_button);
		Button manageBoxButton = (Button)findViewById(R.id.manage_box_button);

		followBoxButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				followBox();
			}
		});
		
		stopFollowingBoxButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				stopFollowingBox();
			}
		});
		
		createBoxButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				createBox();
			}
		});
		
		manageBoxButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				manageBox();
			}
		});
	}
	
	private void followBox() {
		Intent intent = new Intent(BoxSettingsActivity.this, FollowBoxActivity.class);
		startActivity(intent);
	}
	
	private void stopFollowingBox() {
		Intent intent = new Intent(BoxSettingsActivity.this, StopFollowingBoxActivity.class);
		startActivity(intent);
	}
	
	private void createBox() {
		Intent intent = new Intent(BoxSettingsActivity.this, CreateBoxActivity.class);
		startActivity(intent);		
	}
	
	private void manageBox() {
		Intent intent = new Intent(BoxSettingsActivity.this, ManageSelectBoxActivity.class);
		startActivity(intent);		
	}
}
