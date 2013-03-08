package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.Result;
import com.amadt.fitrack.asynctasks.TaskListener;
import com.amadt.fitrack.asynctasks.UnfollowBoxTask;
import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.Box;
import com.amadt.fitrack.model.BoxArrayAdapter;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StopFollowingBoxActivity extends Activity implements TaskListener<Result> {
	
	Box [] boxes = {};
	
	ListView boxList;
	BoxArrayAdapter adapter = null;
	
	ProgressDialog progressDiaglog = null;
	
	Context context = StopFollowingBoxActivity.this;
	
	private class BoxListResult {
		public boolean successful = false;
		public Box [] boxes = {};
		public String message = "No message";
	}
		
	private class RetrieveBoxListTask extends AsyncTask<Void, Void, BoxListResult> {
		protected void onPreExecute() {
	 		progressDiaglog = ProgressDialog.show(context, "", "Retreiving Box List...", true);			
		}
		
	     protected BoxListResult doInBackground(Void... params) {	    	 
	    	 Log.i("FiTrack", "Task running...");		
	    	 
	    	 BoxListResult result = new BoxListResult();

	 		FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(context);
	 		try {
	 			result.boxes = apiClient.getFollowedBoxes();
	 			result.successful = true;
	 		}
	 		catch (RESTException e) {
	 			result.successful = false;
	 			result.message = e.toString();
	 		}
	    	
	        return result;
	     }
	     
	     protected void onPostExecute(BoxListResult result) {
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
		
		setContentView(R.layout.remove_box);
		
		boxList = (ListView)findViewById(R.id.box_list);
		boxList.setTextFilterEnabled(true);
				
		boxList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				verifyRemoval(boxes[position]);
			}
	  	});

		new RetrieveBoxListTask().execute();
	}
	
	private void verifyRemoval(final Box box) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to stop following '" + box.getName() + "'?")
               .setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					removeBox(box);
				}
			}).setNegativeButton("Cancel", null);
        AlertDialog alert = builder.create();
        alert.show();
	}
	
	private void removeBox(Box box) {      
		new UnfollowBoxTask(this, this, box.getId()).execute();
	}

	public void onPreExecute() {
		
	}

	public void onPostExecute(Result result) {
		new RetrieveBoxListTask().execute();
	}
}
