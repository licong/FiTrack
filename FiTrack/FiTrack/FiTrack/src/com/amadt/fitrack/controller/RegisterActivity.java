package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.CreateAccountTask;
import com.amadt.fitrack.model.AlertManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_layout);
		
		
		Button createAccountButton = (Button)findViewById(R.id.create_account_button);
		createAccountButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				createAccount(v);
			}
		});
	}
	
	private String getUsername() {
		EditText usernameField = (EditText) findViewById(R.id.username_field);
		return usernameField.getText().toString();
	}
	
	private String getEmail() {
		EditText emailField = (EditText) findViewById(R.id.email_field);
		return emailField.getText().toString();
	}
	
	private String getPassword() {
		EditText passwordField = (EditText) findViewById(R.id.password_field);
		return passwordField.getText().toString();
	}
	
	private String getConfirmPassword() {
		EditText passwordConfirmField = (EditText) findViewById(R.id.password_confirm_field);
		return passwordConfirmField.getText().toString();
	}
	
	private void createAccount(View view) {
		String username = getUsername();
		String email = getEmail();
		String password = getPassword();
		String confirmPassword = getConfirmPassword();
		
		if(password.equals(confirmPassword) == false) {
			AlertManager.alert(this, "Passwords do not match!");
			return;
		}
		
		new CreateAccountTask(this, username, email, password).execute();
	}
	
}
