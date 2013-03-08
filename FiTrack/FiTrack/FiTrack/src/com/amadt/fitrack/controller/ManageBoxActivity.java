package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.DeleteBoxTask;
import com.amadt.fitrack.asynctasks.Result;
import com.amadt.fitrack.asynctasks.TaskListener;
import com.amadt.fitrack.model.SharedPreferencesManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManageBoxActivity extends Activity implements TaskListener<Result> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.manage_box);
		
		setupView();
		setupButtons();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setupView();
	}

	private void setupView() {
		SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
		
		TextView nameField = (TextView)findViewById(R.id.box_name_text);
		nameField.setText(prefs.getString("name", "N/A"));
		
		TextView idField = (TextView)findViewById(R.id.box_id_text);
		idField.setText(prefs.getString("id", "N/A"));
	}

	private void setupButtons() {
		Button scheduleButton = (Button)findViewById(R.id.schedule_wod_button);
		scheduleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				schedule();
			}
		});

		Button editDetailsButton = (Button)findViewById(R.id.edit_details_button);
		editDetailsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				editDetails();
			}
		});
		
		Button deleteButton = (Button)findViewById(R.id.delete_button);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				verifyDelete();
			}
		});
	}
	
	protected void verifyDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
        
        builder.setMessage("Are you certain that you wish to delete this box?")
        		.setTitle("Confirm Deletion of " + prefs.getString("id", null))
               .setCancelable(true)
               .setNegativeButton("Cancel", null)
               .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {				        
						dialog.dismiss();
						deleteBox();
					}
			});
        AlertDialog alert = builder.create();
        alert.show();
	}

	protected void deleteBox() {
		Log.i("FiTrack", "Deleting box...");
        SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
        new DeleteBoxTask(this, this, prefs.getString("id", "N/A")).execute();
	}

	protected void editDetails() {
		Intent intent = new Intent(ManageBoxActivity.this, EditBoxDetails.class);
		startActivity(intent);
	}

	private void schedule() {
		Intent intent = new Intent(ManageBoxActivity.this, ScheduleBoxWODActivity.class);
		startActivity(intent);
	}

	public void onPreExecute() {
		// nothing to do here.
	}

	public void onPostExecute(Result result) {
		if (result.isSuccessful())
			finish();
	}
}
