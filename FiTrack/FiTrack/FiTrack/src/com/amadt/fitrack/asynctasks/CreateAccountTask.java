package com.amadt.fitrack.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

public class CreateAccountTask extends AsyncTask<Void, Void, Result> {
	private ProgressDialog progressDialog = null;
	private Activity activity = null;
	
	private String username;
	private String password;
	private String email;
	
	public CreateAccountTask(Activity activity, String username, String email, String password) {
		super();
		this.activity = activity;
		
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, "", "Creating Account...", true);
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
			result.setMessage(apiClient.createUser(username, email, password));
			result.setSuccessful(true);
		}
		catch (RESTException e) {
			result.setMessage(e.toString());
			result.setSuccessful(false);
		}

		return result;
	}
}

