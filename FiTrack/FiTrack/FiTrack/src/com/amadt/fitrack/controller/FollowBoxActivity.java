package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.FollowBoxTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FollowBoxActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_box);
		
		setupButtons();
	}

	private void setupButtons() {
		Button followButton = (Button)findViewById(R.id.follow_button);
		followButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText boxIdField = (EditText)findViewById(R.id.box_id_field);
				follow(boxIdField.getText().toString());
			}
		});
	}
	
	private void follow(String boxId) {
		new FollowBoxTask(this, boxId).execute();
	}
}
