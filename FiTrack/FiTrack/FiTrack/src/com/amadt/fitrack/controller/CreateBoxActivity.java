package com.amadt.fitrack.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.CreateBoxTask;


public class CreateBoxActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.create_box);
		
		setupButtons();
	}
	
	private void setupButtons() {
		Button createButton = (Button)findViewById(R.id.create_button);
		
		createButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				EditText idField = (EditText)findViewById(R.id.id_field);
				EditText nameField = (EditText)findViewById(R.id.full_name_field);
				EditText descriptionField = (EditText)findViewById(R.id.description_field);

				String id = idField.getText().toString();
				String name = nameField.getText().toString();
				String description = descriptionField.getText().toString();
				
				createBox(id, name, description);
			}
		});
	}
	
	protected void createBox(String boxId, String boxName, String boxDescription) {
		new CreateBoxTask(this, boxId, boxName, boxDescription).execute();
	}
}
