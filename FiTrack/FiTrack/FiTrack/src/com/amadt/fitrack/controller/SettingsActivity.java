package com.amadt.fitrack.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amadt.fitrack.R;


public class SettingsActivity extends Activity {
	
	private Context myContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
        setContentView(R.layout.settings);
        myContext = this;
        
        setupButtons();
	}
	
	private void setupButtons() {
		Button signInButton = (Button)findViewById(R.id.signin_button);
		Button accountButton = (Button)findViewById(R.id.account_settings_button);
		Button boxButton = (Button)findViewById(R.id.box_settings_button);
		Button resetDataButton = (Button)findViewById(R.id.reset_local_data_button);
		
		signInButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				signIn();
			}
		});
		
		accountButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				accountSettings();
			}
		});
		
		boxButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boxSettings();
			}
		});
		
		resetDataButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				resetLocalData();
			}
		});
	}
	
	private void signIn() {
		Intent intent = new Intent(SettingsActivity.this, SignInActivity.class);
		startActivity(intent);
	}
	
	private void accountSettings() {
		Intent intent = new Intent(SettingsActivity.this, AccountSettingsActivity.class);
		startActivity(intent);
	}
	
	private void boxSettings() {
		Intent intent = new Intent(SettingsActivity.this, BoxSettingsActivity.class);
		startActivity(intent);
	}
	
	private void resetLocalData() {           	 
       confirm();
	}
	
	/**
	 * Confirmation popup to see if user wants to delete local data
	 */
	private void confirm(){
	    AlertDialog alert = new confirmAlert(myContext);
	    alert.show();
	}
	
	/**
	 * Custom AlertDialog to handle confirmation of data deletion
	 * @author Kevin
	 */
	private class confirmAlert extends AlertDialog {

		protected confirmAlert(Context context) {
			super(context);
			setMessage("Are you sure you wish to remove local data?");
			
			//Buttons
			setButton(BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					myContext.deleteDatabase("AMADT");
				}
			});
			setButton(BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
		}
		
	}
}
