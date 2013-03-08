package com.amadt.fitrack.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.amadt.fitrack.R;

public class FiTrackMainMenuActivity extends TabActivity {
	
	public TabHost myTabHost;
	public final static int HOME_TAB = 0;
	public final static int WOD_TAB = 1;
	public final static int GOALS_TAB = 2;
	public final static int LOGS_TAB = 3;
	
	static int tabnum;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        myTabHost = getTabHost();
        myTabHost.getTabWidget().setBackgroundColor(Color.BLACK);
        myTabHost.setBackgroundColor(Color.WHITE);

        TabHost.TabSpec spec;
        Intent intent;
        
        Resources res = getResources();
        
        // Home tab
        intent = new Intent(this, HomeScreenRedirectActivity.class);
        
        spec = myTabHost.newTabSpec("home")
        		.setIndicator("Home", res.getDrawable(R.drawable.home_tab_selector))
        		.setContent(intent);
        myTabHost.addTab(spec);
        
        // WOD tab
        intent = new Intent(this, WodToolsActivity.class);
        
        spec = myTabHost.newTabSpec("wod")
        		.setIndicator("WOD", res.getDrawable(R.drawable.wod_tab_selector))
        		.setContent(intent);
        myTabHost.addTab(spec);
        
        // Goals tab
        intent = new Intent(this, GoalsActivity.class);
        
        spec = myTabHost.newTabSpec("goals")
        		.setIndicator("Goals", res.getDrawable(R.drawable.goals_tab_selector))
        		.setContent(intent);
        myTabHost.addTab(spec);
        
        // Logs Tab
        intent = new Intent(this, CalendarActivity.class);
        
        spec = myTabHost.newTabSpec("logs")
        		.setIndicator("Logs", res.getDrawable(R.drawable.logs_tab_selector))
        		.setContent(intent);
        myTabHost.addTab(spec);
        
        //myTabHost.setCurrentTab(2);
        //myTabHost.getTabWidget().getChildAt(myTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#CC0033"));
        //setTabColor(myTabHost);
        
        setTabColor(myTabHost);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	myTabHost.setCurrentTab(getTabNum());
    	//myTabHost.getTabWidget().getChildAt(myTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0066CC"));
    }
    
    @Override
    public void onResume() {
        super.onResume();
        //myTabHost.setCurrentTab(2);
    	//myTabHost.getTabWidget().getChildAt(myTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0066CC"));
    }
    
    public static void setTabColor(TabHost tabhost) {
    	//TextView tv;
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#0066CC")); //unselected
            TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
        }
        
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0066CC")); // selected
        //TextView tv = (TextView) tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).findViewById(android.R.id.title); //for Selected Tab
        //tv.setTextColor(Color.parseColor("#CCCC00"));

    }
  
    public static void setTabNum(int num) {
    	tabnum = num;
    }
    
    public static int getTabNum() {
    	return tabnum;
    }
}