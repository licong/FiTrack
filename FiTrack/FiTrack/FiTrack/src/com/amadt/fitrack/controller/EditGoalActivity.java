package com.amadt.fitrack.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.amadt.fitrack.R;
import com.amadt.fitrack.database.WODGoal;
import com.amadt.fitrack.database.WODGoalManager;
import com.amadt.fitrack.database.WODHistory;
import com.amadt.fitrack.database.WODHistoryManager;
import com.amadt.fitrack.database.WODManager;


public class EditGoalActivity extends Activity {
	private static final String NAME = "child";
    private static final String GROUP= "group";
    private final int EDIT = 1;
    private final int VIEW = 0;
    
    
	Button editButton;
	TextView name;
	TextView time;
	TextView bestTime;
	TextView status;
	EditText timeEdit;
	
	WODGoalManager wgm;
	WODManager wm;
	WODHistoryManager whm;
	String goalString;
	Button remove;
	int mode;
	
	public void onCreate(Bundle savedInstanceState) {
		mode = VIEW;
		wgm = new WODGoalManager(this);
		wm = new WODManager(this);
		whm = new WODHistoryManager(this);
		
		 List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	     List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
		
	    
	    
		super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_goal);
        
     // set certain fields to be invisible
         timeEdit = (EditText)findViewById(R.id.editgoal_time);
     	 timeEdit.setVisibility(View.GONE);
     	 remove = (Button)findViewById(R.id.remove_button);
     	 remove.setVisibility(View.GONE);
     	
     	 //sets up the pull downlist
        ExpandableListView pastList = (ExpandableListView)findViewById(R.id.performance_list);
        long goalId = getIntent().getLongExtra("WODGOAL", 0);
        String goalName = wm.getWOD(wgm.getWODGoal(goalId).getWid()).getName();
        goalString = goalName;
        WODGoal goal = wgm.getWODGoal(goalId);
        List<WODHistory> history = whm.getAllHistory(goalName);
        
        Map<String, String> curGroupMap = new HashMap<String, String>();
        groupData.add(curGroupMap);
        curGroupMap.put(GROUP, "Previous Times");
        
        List<Map<String, String>> children = new ArrayList<Map<String, String>>();
        for (int j = 0; j < history.size(); j++) {
            Map<String, String> curChildMap = new HashMap<String, String>();
            curChildMap.put(NAME, history.get(j).getDateString() + ": " +(double)(history.get(j).getDuration()/1000));
            children.add(curChildMap);        
            //curChildMap.put()
        }
        childData.add(children);
        
        ExpandableListAdapter mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { GROUP, NAME },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { GROUP, NAME },
                new int[] { android.R.id.text1, android.R.id.text2 }
                );
        pastList.setAdapter(mAdapter);
        
        
        //edits text fields to be what they should display	
        name = (TextView)findViewById(R.id.editgoal_title);
		time = (TextView) findViewById(R.id.editgoal_goal );
		
		
		name.setText("Goal for " + goalName);
		Log.d("Goal Date :", goal.getStartDateString());
		Log.d("Goal ID :", ""+goal.getWid());
		time.setText("Goal: " + (double)(goal.getGoalScore()/1000));
		setBestTimeAndStatus(goalName, goal.getGoalScore());
		setUpButtons();
		
	}
	
	private void setUpButtons()
	{
		remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wgm.deleteWODGoal(goalString);
                Intent intent = new Intent(EditGoalActivity.this, GoalsActivity.class);
                startActivity(intent);
            }
        });
		Button edit = (Button)findViewById(R.id.edit_Button);
		edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(mode == VIEW){
            		time.setText("Goal: ");
            		timeEdit.setVisibility(View.VISIBLE);
            		remove.setVisibility(View.VISIBLE);
            		mode = EDIT;
            	}
            	else
            	{
            		mode = VIEW;
            		String score = timeEdit.getText().toString();
            		if(score.equals(""))
            		{
            			time.setText("Goal: " + (double)(wgm.getWODGoal(goalString).getGoalScore()/1000));
        				timeEdit.setVisibility(View.GONE);
                		remove.setVisibility(View.GONE);
                		return;
            		}
            		int scoreAsInt = 0;
            		try {
        				scoreAsInt = Integer.parseInt(score);
        			}
        			catch (Exception exception){
        				time.setText("Goal: " + wgm.getWODGoal(goalString).getGoalScore()/1000);
        				timeEdit.setVisibility(View.GONE);
                		remove.setVisibility(View.GONE);
                		return;
        			};
        			scoreAsInt = Integer.parseInt(score);
        			wgm.editGoal(goalString, scoreAsInt, System.currentTimeMillis());
        			
        			time.setText("Goal: " + wgm.getWODGoal(goalString).getGoalScore()/1000);
        			timeEdit.setVisibility(View.GONE);
            		remove.setVisibility(View.GONE);
            	}
                
            }
        });
	}
	private void setBestTimeAndStatus(String goalName, int goalScore)
	{
		bestTime = (TextView)findViewById(R.id.editgoal_performance);
		status = (TextView)findViewById(R.id.editgoal_status);
		
		long best = whm.getWODBestTime(goalName);
		if(best == -1)
		{
			bestTime.setText("Best Time: N/A");
			status.setText("Status: " + "Not Started");
		}
		else{
			bestTime.setText("Best Time: " + best/1000 );
			if(best <= goalScore)
				status.setText("Status: " + "Completed");
			else
				status.setText("Status: " + "In Progress");
		}
	}

}
