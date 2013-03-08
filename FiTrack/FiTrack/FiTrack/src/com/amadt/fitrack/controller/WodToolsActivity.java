package com.amadt.fitrack.controller;

import java.util.ArrayList;

import com.amadt.fitrack.R;
import com.amadt.fitrack.asynctasks.Result;
import com.amadt.fitrack.model.Box;
import com.amadt.fitrack.model.FiTrackAPIClient;
import com.amadt.fitrack.model.RestClient.RESTException;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.amadt.fitrack.database.ScheduledWODManager;
import com.amadt.fitrack.database.WOD;



public class WodToolsActivity extends ListActivity {
    private String listItems[] = {"Select a WOD", 
    					  		  "My Scheduled",
    					  		  "CrossFit Official RSS",
    					  		  "Create Custom WOD"};
    
    private class GetBoxWodsResult extends Result {
    	Box [] boxSchedule = {};
    }

    Box [] wodSchedule = {};
	
	private class GetBoxWodsTask extends AsyncTask<Void, Void, GetBoxWodsResult> {		
		private WodToolsActivity activity;
		
		GetBoxWodsTask(WodToolsActivity activity) {
			this.activity = activity;
		}
		
		@Override
		protected void onPreExecute() {
			Toast toast = Toast.makeText(activity, "Retrieving todays WODs...", Toast.LENGTH_SHORT);
			toast.show();
			
		}
	    
		@Override
	    protected void onPostExecute(GetBoxWodsResult result) {
			wodSchedule = result.boxSchedule;
			
			ArrayList<String> list = new ArrayList<String>();
			
			for (int i=0; i < listItems.length; ++i)
				list.add(listItems[i]);
			
			for (Box box : wodSchedule) {
				list.add(box.getName());
			}

	        activity.setListAdapter(new ArrayAdapter<String>(activity, R.layout.list_layout, list));
	    }

		@Override
		protected GetBoxWodsResult doInBackground(Void... arg0) {
			GetBoxWodsResult result = new GetBoxWodsResult();	
			
			try {				
				FiTrackAPIClient apiClient = FiTrackAPIClient.getInstance(activity);	
				result.boxSchedule = apiClient.getTodaysWODs();
			} catch (RESTException e) {
				e.printStackTrace();
			}
			
			return result;
		}
	 }
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_layout, listItems));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        ColorDrawable gold = new ColorDrawable(this.getResources().getColor(R.drawable.gold));
        lv.setDivider(gold);
        lv.setDividerHeight(2);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	  
        	  if (position == 0) { //FIND A WOD       		  
        		  Intent intent = new Intent(WodToolsActivity.this, FindAWodActivity.class);
        		  startActivity(intent);
        		  
        		  return;
        	  }
        	  
        	  if (position == 1) { //SCHEDULED WORKOUT
        		  
        		  AlertDialog.Builder alt_bld = new AlertDialog.Builder(WodToolsActivity.this);
         		  

        		  try {
        			  SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        			  Date todaysDate = new Date();
        			  String formattedDate = dateFormatter.format(todaysDate);
        			  Date parsedDate = dateFormatter.parse(formattedDate);
       			  
        			  final ScheduledWODManager swm = new ScheduledWODManager(WodToolsActivity.this);
            		  List<WOD> wodList = swm.getWODsForADay(parsedDate.getTime());
       			   	  Iterator<WOD> wodIt = wodList.iterator();
       			   	  ArrayList<String> wodNames = new ArrayList<String>();

       			   	  

       			   	  while(wodIt.hasNext()) {
       			   		  WOD current = wodIt.next();
       			   		  wodNames.add(current.getName());
       			   	  }

       			   	  final String[] templistitems = new String[wodNames.size()];     	
       			   	  
       			   	  for (int i = 0; i < templistitems.length; i++) {
       			   		  templistitems[i] = wodNames.get(i);
       			   	  }   
       			   	  
       			   	  alt_bld.setIcon(R.drawable.dumbells_gold);
       			   	  
       			   	  //NO WODS ARE SCHEDULED -- DATABASE IS EMPTY FOR TODAY'S DATE
       			   	  if (wodNames.size() == 0) {
       			   		  alt_bld.setTitle("No WODs are scheduled for today!")
       			   		  .setPositiveButton("Go to Calendar", new DialogInterface.OnClickListener() {
       			   			  public void onClick(DialogInterface dialog, int id) {
       			   				  //REDIRECT TO THE CALENDAR
       			   				  dialog.cancel();
       			   				  Intent intent = new Intent(WodToolsActivity.this, FiTrackMainMenuActivity.class);
       			   				  FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.LOGS_TAB);
       			   				  startActivity(intent);
       			   				  
       			   			  }
       			   		  })
       			   		  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
       			   			  public void onClick(DialogInterface dialog, int id) {
       			   				  //GET RID OF THE WINDOW
       			   				  dialog.cancel();
       			   			  }
       			   		  });
       			   	  }
       			   	  
       			   	  //ONLY 1 WOD IS SCHEDULED -- GO STRAIGHT TO IT, NO NEED FOR DIALOG
       			   	  if (wodNames.size() == 1) {
       			   		  Intent intent = new Intent(WodToolsActivity.this, WODViewActivity.class);
		   				  intent.putExtra("WOD_NAME", templistitems[0]);
		   				  startActivity(intent);
       			   	  }
       			   	  
       			   	  //MORE THAN 1 WOD IS SCHEDULED -- SHOW A LIST OF EACH ONE, REDIRECT TO
       			   	  //THE USER SELECTS
       			   	  if (wodNames.size() > 1) {
       			   		  alt_bld.setTitle("Today's scheduled WODs: ")
       			   		  .setItems(templistitems, new DialogInterface.OnClickListener() {
       			   			  public void onClick(DialogInterface dialog, int item) {
       			   				  // Do something with the selection
       			   				  Intent intent = new Intent(WodToolsActivity.this, WODViewActivity.class);
       			   				  intent.putExtra("WOD_NAME", templistitems[item]);
       			   				  startActivity(intent);
       			   			  }
       			   		  });
       			   	  }
       			   	  
        		  } catch (ParseException e) {	
      				e.printStackTrace();
      				}
        		  
        		  
        		  AlertDialog alert = alt_bld.create();
    			  alert.show();
    			  
        		  return;
        	  }
        	  
              // When clicked, show a toast with the TextView text
        	  if(position == 2) // CrossFit RSS 
        	  {
        		  Intent intent = new Intent(WodToolsActivity.this, CrossFitRSSActivity.class);
        		  startActivity(intent);
        		  return;
        	  }
        	  
        	  if(position == 3) //CREATE CUSTOM WOD
        	  {
        		  Intent intent = new Intent(WodToolsActivity.this, CreateCustomWODActivity.class);
        		  startActivity(intent);        		  
        		  return;
        	  }
        	  
        	  if (position > 3) { // scheduled wods from boxes go here
        		  Intent intent = new Intent(WodToolsActivity.this, WODViewActivity.class);
        		  intent.putExtra("WOD_NAME", wodSchedule[position - listItems.length].getScheduledWod());
        		  startActivity(intent);
        	  }
          }
        });
        
        new GetBoxWodsTask(this).execute();
    }
}
