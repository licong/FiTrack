package com.amadt.fitrack.asynctasks;

import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

public class ScheduleWODTask extends AsyncTask<Void, Void, Result> {
	
	private ProgressDialog progressDialog = null;	
	
	private Activity activity;
	private String boxId;
	private String wodName;
	private Date date;
	
	public ScheduleWODTask(Activity activity, String boxId, String wodName, Date date) {
		this.activity = activity;
		this.boxId = boxId;
		this.wodName = wodName;
		this.date = date;
	}
	
	@Override
	protected void onPreExecute() {		
		progressDialog = ProgressDialog.show(activity, "", "Sending request...", true);
	}
	
	@Override
	protected Result doInBackground(Void... params) {
		Result result = new Result();
		
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(activity);
		
		try {
			result.setMessage(apiClient.scheduleWOD(boxId, date, wodName));	
			result.setSuccessful(true);
		}
		catch (RESTException e) {
			e.printStackTrace();
			result.setMessage(e.getMessage());
			result.setSuccessful(false);
		}
		
		// TODO Auto-generated method stub
		return result;
	}
    
	@Override
	protected void onPostExecute(Result result) {
		if (progressDialog != null)
			progressDialog.dismiss();
		
		AlertManager.alert(activity, result.getMessage());
	}
}
