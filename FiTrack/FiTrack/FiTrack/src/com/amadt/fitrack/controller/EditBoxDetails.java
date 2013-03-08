package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.EditBoxInfoTask;
import com.amadt.fitrack.asynctasks.Result;
import com.amadt.fitrack.asynctasks.TaskListener;
import com.amadt.fitrack.model.SharedPreferencesManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditBoxDetails extends Activity implements TaskListener<Result> {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.edit_box_details);
		
		setupView();
		setupButtons();
	}

	private void setupButtons() {
		Button saveButton = (Button)findViewById(R.id.save_button);
		final Activity thisActivity = this;
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText nameField = (EditText)findViewById(R.id.box_name_field);
				EditText descriptionField = (EditText)findViewById(R.id.description_field);
				
				SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(thisActivity);
				
				String boxId = prefs.getString("id", "N/A");
				String boxName = nameField.getText().toString();
				String boxDescription = descriptionField.getText().toString();
				
				saveDetails(boxId, boxName, boxDescription);
			}
		});
	}

	protected void saveDetails(String boxId, String boxName, String boxDescription) {
		new EditBoxInfoTask(this, this, boxId, boxName, boxDescription).execute();
	}

	private void setupView() {
		SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
		
		TextView boxIdTextView = (TextView)findViewById(R.id.box_id_textview);
		boxIdTextView.setText(prefs.getString("id", "N/A"));
		
		EditText nameField = (EditText)findViewById(R.id.box_name_field);
		nameField.setText(prefs.getString("name", "N/A"));
		
		EditText descriptionField = (EditText)findViewById(R.id.description_field);
		descriptionField.setText(prefs.getString("description", "N/A"));
	}

	public void onPreExecute() {
		// nothing to do pre-execution
	}

	public void onPostExecute(Result result) {
		if (result.isSuccessful() == false) 
			return;

		EditText nameField = (EditText)findViewById(R.id.box_name_field);
		EditText descriptionField = (EditText)findViewById(R.id.description_field);
		
		SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putString("name", nameField.getText().toString());
		editor.putString("description", descriptionField.getText().toString());
		editor.commit();
	}
}
