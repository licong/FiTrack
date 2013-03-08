package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.UserSignInTask;
import com.amadt.fitrack.model.FiTrackAPIClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		setupView();
		setupButtons();
	}
	
	private void setupView() {
		EditText usernameField = (EditText)findViewById(R.id.username_field);
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(this);
		
		if (apiClient.getUsername() != null) {
			usernameField.setText(apiClient.getUsername());
		}
	}
	
	private void setupButtons() {
		Button resetPasswordButton = (Button)findViewById(R.id.reset_password_button);
		resetPasswordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
				startActivity(intent);
			}
		});
		
		Button registerButton = (Button)findViewById(R.id.register_button);
		registerButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		Button signInButton = (Button)findViewById(R.id.signin_button);
		signInButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText usernameField = (EditText)findViewById(R.id.username_field);
				EditText passwordField = (EditText)findViewById(R.id.password_field);
				
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				
				signIn(username, password);
			}
		});
	}
	
	private void signIn(String username, String password) {
		new UserSignInTask(this, username, password).execute();
	}
}
