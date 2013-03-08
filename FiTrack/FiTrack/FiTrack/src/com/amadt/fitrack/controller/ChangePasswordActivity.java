package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.ChangePasswordTask;
import com.amadt.fitrack.model.AlertManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ChangePasswordActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		
		Button changePasswordButton = (Button)findViewById(R.id.change_password_button);
		changePasswordButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				EditText currentField = (EditText)findViewById(R.id.current_password_field);
				EditText newField = (EditText)findViewById(R.id.new_password_field);
				EditText confirmField = (EditText)findViewById(R.id.confirm_password_field);

				String currentPassword = currentField.getText().toString();
				String newPassword = newField.getText().toString();
				String confirmPassword = confirmField.getText().toString();
				
				changePassword(currentPassword, newPassword, confirmPassword);
			}
		});
	}
	
	private void changePassword(String currentPassword, String newPassword, String confirmPassword) {
		if (newPassword.equals(confirmPassword) == false) {
			AlertManager.alert(this, "Passwords do not match");
			return;
		}
		
		new ChangePasswordTask(this, currentPassword, newPassword).execute();
	}
}
