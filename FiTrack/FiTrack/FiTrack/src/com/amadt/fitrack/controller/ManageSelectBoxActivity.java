package com.amadt.fitrack.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.amadt.fitrack.R;
import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.Box;
import com.amadt.fitrack.model.BoxArrayAdapter;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;
import com.amadt.fitrack.model.SharedPreferencesManager;

public class ManageSelectBoxActivity extends Activity {
	
	Box [] boxes = {};
	
	ListView boxList;
	BoxArrayAdapter adapter = null;
	
	ProgressDialog progressDiaglog = null;
	
	Context context = ManageSelectBoxActivity.this;
	
	private class Result {
		public boolean successful = false;
		public Box [] boxes = {};
		public String message = "No message";
	}
		
	private class RetrieveBoxListTask extends AsyncTask<Void, Void, Result> {
		protected void onPreExecute() {
	 		progressDiaglog = ProgressDialog.show(context, "", "Retreiving Box List...", true);			
		}
		
	     protected Result doInBackground(Void... params) {	    	 
	    	 Log.i("FiTrack", "Task running...");		
	    	 
	    	 Result result = new Result();

	 		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(context);
	 		try {
	 			result.boxes = apiClient.getOwnedBoxes();
	 			result.successful = true;
	 		}
	 		catch (RESTException e) {
	 			result.successful = false;
	 			result.message = e.toString();
	 		}
	    	
	        return result;
	     }
	     
	     protected void onPostExecute(Result result) {
	    	Log.i("FiTrack", "Task finished");
	    	 
	    	 if (progressDiaglog != null)
	    		 progressDiaglog.dismiss();
	    	
	    	if (result.successful == false) {
	    		AlertManager.alert(context, result.message);
	    	}
	    	
	    	boxes = result.boxes;
	  		adapter = new BoxArrayAdapter(context, boxes);
			boxList.setAdapter(adapter);
	     }
	 }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.manage_box_select);
		boxList = (ListView)findViewById(R.id.box_list);
		
  		adapter = new BoxArrayAdapter(context, boxes);
		boxList.setAdapter(adapter);
		
		boxList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				manageBox(boxes[position]);
			}
	  	});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		new RetrieveBoxListTask().execute();
	}

	protected void manageBox(Box box) {
		Intent intent = new Intent(ManageSelectBoxActivity.this, ManageBoxActivity.class);
		SharedPreferences prefs = SharedPreferencesManager.getBoxPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString("id", box.getId());
		editor.putString("name", box.getName());
		editor.putString("description", box.getDescription());
		editor.commit();
		
		startActivity(intent);
	}
}
