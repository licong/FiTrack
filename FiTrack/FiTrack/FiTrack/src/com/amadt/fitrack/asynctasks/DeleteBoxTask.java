package com.amadt.fitrack.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

public class DeleteBoxTask extends AsyncTask<Void, Void, Result> {
	private ProgressDialog progressDialog = null;
	private Activity activity = null;
	
	private String boxId;
	
	private TaskListener<Result> listener;
		
	public DeleteBoxTask(Activity activity, TaskListener<Result> listener, String boxId) {
		super();
		
		this.activity = activity;
		this.boxId = boxId;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {		
		if (listener != null)
			listener.onPreExecute();
		
		progressDialog = ProgressDialog.show(activity, "", "Deleting box...", true);
	}
	
	@Override
	protected void onPostExecute(Result result) {
		if (progressDialog != null)
			progressDialog.dismiss();		

		if (result.isSuccessful()) {
			if (listener != null)
				listener.onPostExecute(result);
		}
		else {
			AlertManager.alert(activity, result.getMessage());
		}			
	}

	@Override
	protected Result doInBackground(Void... params) {
		Result result = new Result();
		
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(activity);
		
		try {
			result.setMessage(apiClient.deleteBox(boxId));
			result.setSuccessful(true);
		}
		catch (RESTException e) {
			result.setMessage(e.toString());
			result.setSuccessful(false);
		}

		return result;
	}
}
