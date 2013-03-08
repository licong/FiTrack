package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.EditAccountInformationTask;
import com.amadt.fitrack.model.FiTrackAPIClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditAccountDetailsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.edit_account_details);
		
		setupView();
	}

	private void setupView() {
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(this);
		
		TextView usernameTextView = (TextView)findViewById(R.id.username_textview);
		usernameTextView.setText(apiClient.getUsername());
		
		EditText emailField = (EditText)findViewById(R.id.email_field);
		emailField.setText(apiClient.getEmail());
		
		EditText descriptionField = (EditText)findViewById(R.id.description_field);
		descriptionField.setText(apiClient.getDescription());
		
		Button saveButton = (Button)findViewById(R.id.save_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText emailField = (EditText)findViewById(R.id.email_field);
				EditText descriptionField = (EditText)findViewById(R.id.description_field);
				
				saveDetails(emailField.getText().toString(), descriptionField.getText().toString());
			}
		});
	}

	protected void saveDetails(String email, String description) {
		new EditAccountInformationTask(this, email, description).execute();
	}
}
