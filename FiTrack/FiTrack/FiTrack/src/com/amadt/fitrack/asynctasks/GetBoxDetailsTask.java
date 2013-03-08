package com.amadt.fitrack.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.Box;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

public class GetBoxDetailsTask extends AsyncTask<Void, Void, GetBoxDetailsTask.BoxDetailsResult> {
	
	public static class BoxDetailsResult extends Result{
		Box box = new Box();
	};
	
	private ProgressDialog progressDialog = null;
	private Activity activity = null;
	
	private String boxId;
	
	private TaskListener<BoxDetailsResult> listener;
	
	public GetBoxDetailsTask(Activity activity, TaskListener<BoxDetailsResult> listener, String boxId) {
		super();
		
		this.activity = activity;
		this.boxId = boxId;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {		
		if (listener != null)
			listener.onPreExecute();
		
		progressDialog = ProgressDialog.show(activity, "", "Getting box details...", true);
	}
	
	@Override
	protected void onPostExecute(GetBoxDetailsTask.BoxDetailsResult result) {
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
	protected BoxDetailsResult doInBackground(Void... params) {
		BoxDetailsResult result = new BoxDetailsResult();
		
		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(activity);
		
		try {
			result.box = apiClient.getBox(boxId);
			if (result.box == null)
				result.setSuccessful(false);
			else
				result.setSuccessful(true);
		}
		catch (RESTException e) {
			result.setMessage(e.toString());
			result.setSuccessful(false);
		}

		return result;
	}
}
