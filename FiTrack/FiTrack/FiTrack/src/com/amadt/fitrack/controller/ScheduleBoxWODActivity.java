package com.amadt.fitrack.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.Result;
import com.amadt.fitrack.asynctasks.ScheduleWODTask;
import com.amadt.fitrack.database.WOD;
import com.amadt.fitrack.database.WODManager;
import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.SharedPreferencesManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class ScheduleBoxWODActivity extends Activity {
	
	WODManager wodManager = null;
	
	List<WOD> wods = null;
	String [] names = {};
	
	private class RetrieveWodResult extends Result {
		public List<WOD> wods = null;
		public String [] names;
	}
	
	private class RetrieveWodsTask extends AsyncTask<Void, Void, RetrieveWodResult> {
		private ProgressDialog progressDialog = null;
		private Activity activity;
		
		RetrieveWodsTask(Activity activity) {
			this.activity = activity;
		}
		
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(activity, "", "Loading WODs...", true);			
		}
		
	     protected RetrieveWodResult doInBackground(Void... params) {	
	    	RetrieveWodResult result = new RetrieveWodResult();

	    	ArrayList<String> namesList= new ArrayList<String>();
	    	
			result.wods = wodManager.getAllWODs();
			for (WOD wod : result.wods) {
				namesList.add(wod.getName());
			}
			
			result.names = namesList.toArray(new String[namesList.size()]);
			result.setSuccessful(true);
	    	 
	        return result;
	     }
	     
	     protected void onPostExecute(RetrieveWodResult result) {
			Spinner wodSpinner = (Spinner)findViewById(R.id.wod_spinner);
	    	 
	    	 if (progressDialog != null)
	    		 progressDialog.dismiss();
	    	 
	    	 wods = result.wods;
	    	 names = result.names;
	    	 
			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, names);
			 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 wodSpinner.setAdapter(adapter);
	    	
	    	if (result.isSuccessful() == false) {
	    		AlertManager.alert(activity, result.getMessage());
	    	}
	     }
	 }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.box_schedule_layout);
		
		this.wodManager = new WODManager(this);
		
		Spinner wodSpinner = (Spinner)findViewById(R.id.wod_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		wodSpinner.setAdapter(adapter);
		
		setupView();
		setupButtons();
		
		new RetrieveWodsTask(this).execute();
	}

	private void setupView() {
		SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
		
		TextView boxIdTextView = (TextView)findViewById(R.id.box_id_textview);
		boxIdTextView.setText(prefs.getString("id", "N/A"));
	}

	private void setupButtons() {
		Button scheduleButton = (Button)findViewById(R.id.schedule_button);
		scheduleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {		
				Spinner wodSpinner = (Spinner)findViewById(R.id.wod_spinner);
				DatePicker datePicker = (DatePicker)findViewById(R.id.date_picker);
				
				WOD selectedWod = wods.get(wodSpinner.getSelectedItemPosition());
				
				Date date = new Date();
				date.setDate(datePicker.getDayOfMonth());
				date.setMonth(datePicker.getMonth() + 1);
				date.setYear(datePicker.getYear());
			
				scheduleWOD(selectedWod.getName(), date);
			}
		});
	}
	
	private void scheduleWOD(String wodName, Date date) {
		SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
		String boxId = prefs.getString("id", "N/A");
		
		new ScheduleWODTask(this, boxId, wodName, date).execute();
	}
}
