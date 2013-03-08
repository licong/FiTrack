package com.amadt.fitrack.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amadt.fitrack.R;
import com.amadt.fitrack.database.WODGoal;
import com.amadt.fitrack.database.WODGoalManager;
import com.amadt.fitrack.database.WODManager;

import java.lang.Integer;

public class GoalsActivity extends ListActivity {
  private SimpleAdapter sa;
  ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.goals);
    
    final Button   addButton    = (Button)   findViewById(R.id.addButton);  
    final Button   submitButton = (Button)   findViewById(R.id.submitButton);
    final EditText editName     = (EditText) findViewById(R.id.editName);
    final EditText editScore    = (EditText) findViewById(R.id.editScore);
    //final ListView listView     = (ListView) findViewById(R.id.list);
    
    final WODManager     wm  = new WODManager(this);
    final WODGoalManager wgm = new WODGoalManager(this);
    
    
    editName.setVisibility(View.GONE);
	editScore.setVisibility(View.GONE);
	submitButton.setVisibility(View.GONE);
    
    addButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Toggle visibilities.   
        	addButton.setVisibility(View.GONE);
        	//listView.setVisibility(View.GONE);
        	
        	editName.setVisibility(View.VISIBLE);
        	editScore.setVisibility(View.VISIBLE);
        	submitButton.setVisibility(View.VISIBLE);
        	
        	
        	//wgm.addGoal("Randy", 180, System.currentTimeMillis());
        	//addButton.setVisibility(View.GONE);
        }
    });
    
    submitButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// Get entered info.
			String name = editName.getText().toString();
			String score = editScore.getText().toString();
			
			// !!!!!TODO: Exception if score can't be parsed as an int.
			try {
				int scoreAsInt = Integer.parseInt(score);
				wgm.addGoal(name, scoreAsInt * 1000, System.currentTimeMillis());
			}
			catch (Exception exception){};
			
			// Toggle visibilities.
			addButton.setVisibility(View.VISIBLE);
			//listView.setVisibility(View.VISIBLE);
			
			editName.setVisibility(View.GONE);
			editScore.setVisibility(View.GONE);
			submitButton.setVisibility(View.GONE);
			
			// Refresh. Could not find a better way. TODO: Clean up if there's time.
			List<WODGoal> wodgoals = wgm.getAllWODGoals();
		    int size = wodgoals.size();
		    String[][] goals = new String[size][2];
		    
		    //TODO: Need to have some kind of default message if there are no WOD goals yet
		    for(int i = 0; i < size; i++) {
		    	goals[i][0] = wm.getWOD(wodgoals.get(i).getWid()).getName(); 
		    	if(!wodgoals.get(i).isComplete())
		    		goals[i][1] = "Goal Time: " + (double)(wodgoals.get(i).getGoalScore()/1000);
		    	else
		    		goals[i][1] = "Completed Goal Time: " + (double)(wodgoals.get(i).getGoalScore()/1000);
		    }
		    
		    list = new ArrayList<HashMap<String,String>>();
		    HashMap<String,String> item;
		    for(int i=0;i<goals.length;i++){
		      item = new HashMap<String,String>();
		      item.put( "line1", goals[i][0]);
		      item.put( "line2", goals[i][1]);
		      list.add( item );
		    }
		    
		    sa = new SimpleAdapter(GoalsActivity.this, list,
		      android.R.layout.two_line_list_item ,
		      new String[] { "line1","line2" },
		      new int[] {android.R.id.text1, android.R.id.text2});
		    setListAdapter( sa );
			
		}
	});
    
    //Initialize list.
    List<WODGoal> wodgoals = wgm.getAllWODGoals();
    int size = wodgoals.size();
    String[][] goals = new String[size][2];
    
    //TODO: Need to have some kind of default message if there are no WOD goals yet
    for(int i = 0; i < size; i++) {
    	goals[i][0] = wm.getWOD(wodgoals.get(i).getWid()).getName(); 
    	if(!wodgoals.get(i).isComplete())
    		goals[i][1] = "Goal Time: " + (double)(wodgoals.get(i).getGoalScore()/1000);
    	else
    		goals[i][1] = "Completed Goal Time: " + (double)(wodgoals.get(i).getGoalScore()/1000);
    }

    HashMap<String,String> item;
    for(int i=0;i<goals.length;i++){
      item = new HashMap<String,String>();
      item.put( "line1", goals[i][0]);
      item.put( "line2", goals[i][1]);
      list.add( item );
    }
    
    sa = new SimpleAdapter(this, list,
      android.R.layout.two_line_list_item ,
      new String[] { "line1","line2" },
      new int[] {android.R.id.text1, android.R.id.text2});
    setListAdapter( sa );
  }
  protected void onListItemClick(ListView l, View v, int position, long id)
  {
	  WODGoalManager wgm = new WODGoalManager(this);  
	Intent intent = new Intent(GoalsActivity.this, EditGoalActivity.class);
  	intent.putExtra("WODGOAL", wgm.getAllWODGoals().get(position).getId());
  	startActivity(intent);
  }
  

}