package com.amadt.fitrack.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

public class CreateBoxTask extends AsyncTask<Void, Void, Result> {
	private ProgressDialog progressDialog = null;
	private Activity activity = null;
	
	private String boxId;
	private String boxName;
	private String boxDescription;
	
	public CreateBoxTask(Activity activity, String boxId, String boxName, String boxDescription) {
		super();
		this.activity = activity;
		
		this.boxId = boxId;
		this.boxName = boxName;
		this.boxDescription = boxDescription;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, "", "Submitting box information...", true);
	}
	
	@Override
	protected void onPostExecute(Result result) {
		if (progressDialog != null)
			progressDialog.dismiss();
		
		if (result.isSuccessful())
			AlertManager.alertAndFinish(activity, activity, result.getMessage());
		else
			AlertManager.alert(activity, result.getMessage());
	}

	@Override
	protected Result doInBackground(Void... params) {
		Result result = new Result();
		
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(activity);
		
		try {
			result.setMessage(apiClient.createBox(boxId, boxName, boxDescription));
			result.setSuccessful(true);
		}
		catch (RESTException e) {
			result.setMessage(e.toString());
			result.setSuccessful(false);
		}

		return result;
	}
}