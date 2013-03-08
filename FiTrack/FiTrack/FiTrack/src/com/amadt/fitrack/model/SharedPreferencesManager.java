package com.amadt.fitrack.model;

import android.app.Activity;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
	public static final String BOX_PREFS = "BoxPrefs";
	
	public static SharedPreferences getSharedPreferences(Activity activity, String preferences) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(preferences, 0);
		
		return sharedPreferences;
	}
	
	public static SharedPreferences getBoxPreferences(Activity activity) {
		return getSharedPreferences(activity, BOX_PREFS);
	}
}
