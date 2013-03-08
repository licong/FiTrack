package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.model.FiTrackAPIClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountSettingsActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.account_settings);
		
		setupButtons();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Button editButton = (Button)findViewById(R.id.edit_account_button);
		Button changePasswordButton = (Button)findViewById(R.id.change_password_button);
		
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(this);
		if (apiClient.getSessionToken() == null) {
			editButton.setEnabled(false);
			changePasswordButton.setEnabled(false);
		}
		else {
			editButton.setEnabled(true);
			changePasswordButton.setEnabled(true);
		}
	}

	private void setupButtons() {
		Button changePasswordButton = (Button)findViewById(R.id.change_password_button);
		changePasswordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changePassword();
			}
		});
		
		Button editButton = (Button)findViewById(R.id.edit_account_button);
		editButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				editDetails();
			}
		});
		
		Button forgotButton = (Button)findViewById(R.id.forgot_password_button);
		forgotButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				forgot();
			}
		});
	}

	protected void editDetails() {
		Intent intent = new Intent(AccountSettingsActivity.this, EditAccountDetailsActivity.class);
		startActivity(intent);
	}

	protected void forgot() {
		Intent intent = new Intent(AccountSettingsActivity.this, ResetPasswordActivity.class);
		startActivity(intent);
	}

	protected void changePassword() {
		Intent intent = new Intent(AccountSettingsActivity.this, ChangePasswordActivity.class);
		startActivity(intent);
	}
}
