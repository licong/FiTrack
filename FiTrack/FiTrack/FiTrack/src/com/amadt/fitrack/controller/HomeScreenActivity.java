package com.amadt.fitrack.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amadt.fitrack.R;

public class HomeScreenActivity extends Activity {
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        
    	Button wodButton;
    	Button goalsButton;
    	Button logsButton;
    	Button timerButton;
    	
    	Button profileButton;
    	Button settingsButton;
//    	Button syncButton;
    	Button aboutButton;
    	

    wodButton = (Button) findViewById(R.id.wodButton);
    wodButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	Intent intent = new Intent(HomeScreenActivity.this, FiTrackMainMenuActivity.class);
        	FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.WOD_TAB);
        	startActivity(intent);
        }
    });
    
    goalsButton = (Button) findViewById(R.id.goalsButton);
    goalsButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	Intent intent = new Intent(HomeScreenActivity.this, FiTrackMainMenuActivity.class);
        	FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.GOALS_TAB);
        	startActivity(intent);
        }
    });
    
    logsButton = (Button) findViewById(R.id.logsButton);   
    logsButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	Intent intent = new Intent(HomeScreenActivity.this, FiTrackMainMenuActivity.class);
        	FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.LOGS_TAB);
        	startActivity(intent);
        }
    });
    
    timerButton = (Button) findViewById(R.id.timerButton);
    timerButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	Intent intent = new Intent(HomeScreenActivity.this, TimerActivity.class);
        	startActivity(intent);
        }
    });
    
    settingsButton = (Button) findViewById(R.id.settingsButton);
    settingsButton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(HomeScreenActivity.this, SettingsActivity.class);
			startActivity(intent);
		}
	});
    
    profileButton = (Button) findViewById(R.id.profileButton);
    profileButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	Intent intent = new Intent(HomeScreenActivity.this, ProfileActivity.class);
        	//FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.WOD_TAB);
        	startActivity(intent);
        }
    });
    
//    syncButton = (Button) findViewById(R.id.syncButton);
//    syncButton.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View v) {
//            // Perform action on click
//        	Intent intent = new Intent(HomeScreenActivity.this, SyncActivity.class);
//        	//FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.WOD_TAB);
//        	startActivity(intent);
//        }
//    });
    
    aboutButton = (Button) findViewById(R.id.aboutButton);
    aboutButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
        	Intent intent = new Intent(HomeScreenActivity.this, AboutActivity.class);
        	//FiTrackMainMenuActivity.setTabNum(FiTrackMainMenuActivity.WOD_TAB);
        	startActivity(intent);
        }
    });
    
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
}
