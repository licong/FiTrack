package com.amadt.fitrack.database;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class ScheduledWODManager {

	//The Database
	private DatabaseDAO db;
	
	//Indices for the ScheduledWOD table
	private static final int widPos = 1;
	private static final int datePos = 2;
	
	//Indices for the WOD table
	private static final int idPos = 0;
	private static final int namePos = 1;
	private static final int typePos = 2;
	private static final int descriptionPos = 3;
	private static final int videoPos = 4;
	
	/**
	 * Constructor
	 * @param context The context for the application environment
	 */
	public ScheduledWODManager(Context context) {
		db = new DatabaseDAO(context);
		try {
			db.createDataBase();
		} catch (IOException e) {
		}
		db.close();
	}
	
	/**
	 * Schedule a WOD for a particular date
	 * @param name The name of the WOD
	 * @param date The date to schedule the WOD for
	 * @return True if the WOD was successfully scheduled, False otherwise
	 */
	public boolean scheduleWOD(String name, long date) {
		db.open();
		long insertId = db.scheduleAWOD(name, date); 
		db.close();
		
		if(insertId==-1) { //WOD could not be scheduled
			return false;
		}
		return true;
	}
	
	/**
	 * Cancels a scheduled WOD 
	 * @param name The name of the WOD to cancel
	 * @param date The date to cancel the WOD
	 * @return True if the WOD was canceled, False otherwise
	 */
	public boolean cancelWOD(String name, long date) {
		db.open();
		boolean ret = db.cancelAWOD(name, date);
		db.close();
		return ret;
	}
	
	/**
	 * Gets all the WODs that are scheduled for a certain day (usually just one)
	 * @param date The date 
	 * @return A list of WOD objects
	 */
	public List<WOD> getWODsForADay(long date) {
		db.open();
		List<WOD> wods = new ArrayList<WOD>();
		Cursor cursor = db.getWODsForADay(date);
		
		if(cursor.moveToFirst() == false) { //There are no WODs for that day
			cursor.close();
			db.close();
			return wods;
		}
		
		//Populate a list of WODs scheduled for that date
		while (!cursor.isAfterLast()) {
			Cursor workout = db.getWOD(cursor.getLong(widPos));
			if(workout.moveToFirst() == false) { //That WOD doesn't exist (should never happen)
				workout.close();
				db.close();
				return wods;
			}
			WOD wod = cursorToWOD(workout);
			wods.add(wod);
			workout.close();
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return wods;
	}
	
	/**
	 * Gets all the scheduled future dates for a particular WOD
	 * @param name The name of the WOD
	 * @return A list of dates formatted as strings
	 */
	public List<String> getWODSchedule(String name) {
		db.open();
		List<String> dates = new ArrayList<String>();
		Cursor cursor = db.getWODSchedule(name);
		
		if(cursor.moveToFirst() == false) { //This WOD has not been scheduled for the future
			cursor.close();
			db.close();
			return dates;
		}
		
		//Populate the String list
		while (!cursor.isAfterLast()) {
			String date = dateToString(cursor.getLong(datePos));
			dates.add(date);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return dates;
	}
	
	/**
	 * Deletes all previously scheduled workout (before the current date)
	 * @return True if one or more were deleted, False otherwise
	 */
	public boolean clearPast() {
		db.open();
		boolean ret = db.clearPast();
		db.close();
		return ret;
	}
	
	/**
	 * Takes the Cursor and returns a Workout object
	 * @param cursor The passed in Cursor
	 * @return
	 */
	private WOD cursorToWOD(Cursor cursor) {
		WOD workout = new WOD();
		workout.setId(cursor.getLong(idPos));
		workout.setName(cursor.getString(namePos));
		workout.setType(cursor.getString(typePos));
		workout.setDescription(cursor.getString(descriptionPos));
		workout.setVideo(cursor.getString(videoPos));
		return workout;
	}
	
	/**
	 * Returns the date as a string
	 * @param date The date
	 * @return The date
	 */
    public String dateToString(long date) {
    	SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    	Date d = new Date(date);
    	return dateformat.format(d);
    }
}
