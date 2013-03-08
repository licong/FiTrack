package com.amadt.fitrack.controller;


import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.EmailUsernameTask;
import com.amadt.fitrack.asynctasks.ResetPasswordTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ResetPasswordActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password_layout);
		
		Button resetButton = (Button)findViewById(R.id.reset_button);
		resetButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				EditText emailField = (EditText)findViewById(R.id.email_field);
				String email = emailField.getText().toString();
				resetPassword(email);
			}
		});
		
		Button sendUsernameButton = (Button)findViewById(R.id.send_username_button);
		sendUsernameButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				EditText emailField = (EditText)findViewById(R.id.email_field);
				String email = emailField.getText().toString();
				sendUsername(email);
			}
		});
	}
	
	private void resetPassword(String email) {
		new ResetPasswordTask(this).execute(email);
	}
	
	private void sendUsername(String email) {		
		new EmailUsernameTask(this).execute(email);
	}
}
