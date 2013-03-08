package com.amadt.fitrack.database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amadt.fitrack.model.AlertManager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class WODGoalManager {

	//The Database
	private DatabaseDAO db;
	private Context myContext;
	
	//The indices of the WODGoal table
	private final static int idPos = 0; 
	private final static int widPos = 1; 
	private final static int goalPos = 2; 
	private final static int sDatePos = 3; 
	private final static int isComPos = 4; 
	private final static int eDatePos = 5; 
	
	//The indices of the WOD table
	private static final int namePos = 1;
	private static final int typePos = 2;
	private static final int descriptionPos = 3;
	private static final int videoPos = 4;
	
	/**
	 * Constructor
	 * @param context The context for the application environment
	 */
	public WODGoalManager(Context context) {
		db = new DatabaseDAO(context);
		try {
			db.createDataBase();
		} catch (IOException e) {
		}
		myContext = context;
		db.close();
	}
	
	/**
	 * Gets the current goal for a particular WOD
	 * @param name The name of the WOD
	 * @return The WODGoal object that was just added
	 */
	public WODGoal getWODGoal(String name) {
		db.open();
		WODGoal goal = new WODGoal();
		Cursor cursor = db.getWODGoal(name);
		
		if(cursor.moveToFirst() == false) { //There is no current goal for that WOD
			cursor.close();
			db.close();
			return null;
		}
		
		goal = cursorToWODGoal(cursor);
		cursor.close();
		db.close();
		return goal;
	}
	
	/**
	 * Get WOD Goal at the specific id
	 * @param wgid The id of the WOD Goal
	 * @return The WODGoal
	 */
	public WODGoal getWODGoal(long wgid) {
		db.open();
		WODGoal goal = new WODGoal();
		Cursor cursor = db.getWODGoal(wgid);
		
		if(cursor.moveToFirst() == false) { //There is no current goal for that WOD
			cursor.close();
			db.close();
			return null;
		}
		
		goal = cursorToWODGoal(cursor);
		cursor.close();
		db.close();
		return goal;
	}
	
	/**
	 * List of all WOD goals ever made or completed
	 * @return List of all WOD goals
	 */
	public List<WODGoal> getAllWODGoals() {
		db.open();
		List<WODGoal> goals = new ArrayList<WODGoal>();
		Cursor cursor = db.getAllWODGoals();
		
		if(cursor.moveToFirst() == false) { //There are no current WOD goals
			cursor.close();
			db.close();
			return goals;
		}
		
		//Create and populate a List of WODGoals
		while (!cursor.isAfterLast()) {
			WODGoal goal = cursorToWODGoal(cursor);
			goals.add(goal);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return goals;
	}
	
	/**
	 * Gets a list of all incomplete WODGoals
	 * @return A list of all incomplete WODGoals
	 */
	public List<WODGoal> getAllIncomWODGoals() {
		db.open();
		List<WODGoal> goals = new ArrayList<WODGoal>();
		Cursor cursor = db.getAllIncomWODGoals();
		
		if(cursor.moveToFirst() == false) { //There are no current WOD goals
			cursor.close();
			db.close();
			return goals;
		}
		
		//Create and populate a List of WODGoals
		while (!cursor.isAfterLast()) {
			WODGoal goal = cursorToWODGoal(cursor);
			goals.add(goal);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return goals;
	}
	
	/**
	 * Gets a list of all complete WODGoals for a particular WOD
	 * @param name The name of the WOD 
	 * @return List of complete WODGoals for the passed in WOD
	 */
	public List<WODGoal> getComWODGoals(String name) {
		db.open();
		List<WODGoal> goals = new ArrayList<WODGoal>();
		Cursor cursor = db.getComWODGoals(name);
		
		if(cursor.moveToFirst() == false) { //No goals have been completed for that WOD
			cursor.close();
			db.close();
			return null;
		}
		
		while (!cursor.isAfterLast()) {
			WODGoal goal = cursorToWODGoal(cursor);
			goals.add(goal);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return goals;
	}
	
	/**
	 * Gets a list of all WODGoals completed by the user
	 * @return List of all completed WODGoals
	 */
	public List<WODGoal> getAllComWODGoals() {
		db.open();
		List<WODGoal> goals = new ArrayList<WODGoal>();
		Cursor cursor = db.getAllCompletedWODGoals();
		
		if(cursor.moveToFirst() == false) { //No WOD goals have been completed
			cursor.close();
			db.close();
			return null;
		}
		
		while (!cursor.isAfterLast()) {
			WODGoal goal = cursorToWODGoal(cursor);
			goals.add(goal);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return goals;
	}
	
	/**
	 * Adds a new WODGoal
	 * @param name The name of the workout we are adding a goal to
	 * @param goalTime The desired time/score
	 * @param startDate The date the goal was made
	 * @return The WODGoal object just added to the database
	 */
	public WODGoal addGoal(String name, int goalTime, long startDate) {
		db.open();
		long insertId = db.addWODGoal(name, goalTime, startDate); 
		WODGoal newWODGoal = new WODGoal();
		db.close();
		
		if(insertId==-1) { //The WOD goal was not added
			db.close();
			AlertManager.alert(myContext,"Could not add a goal for " + name);
			return newWODGoal;
		}
		if(insertId==-2) {
			db.close();
			AlertManager.alert(myContext, "There is already a goal for " + name);
			return newWODGoal; 
		}
		
		Cursor cursor = db.getWODGoal(name);
		cursor.moveToFirst();
		newWODGoal = cursorToWODGoal(cursor);
		cursor.close();
		return newWODGoal;
	}
	
	/**
	 * Edits an incomplete WODGoal
	 * @param name The name of the workout whose goal we are editing
	 * @param newGoalTime The new desired time/score
	 * @param newStartDate The new date the WODGoal was edited
	 * @return The WODGoal object just editied
	 */
	public WODGoal editGoal(String name, int newGoalTime, long newStartDate) {
		db.open();
		long insertId = db.editWODGoal(name, newGoalTime, newStartDate);
		db.close();
		
		if(insertId==-1) { //The WOD goal could not be edited
			db.close();
			return null;
		}
		
		Cursor cursor = db.getWODGoal(name);
		cursor.moveToFirst();
		WODGoal newWODGoal = cursorToWODGoal(cursor);
		cursor.close();
		return newWODGoal;
	}
	
	/**
	 * Removes an incomplete WODGoal
	 * @param name The name of the workout whose goal we are removing
	 */
	public void deleteWODGoal(String name) {
		db.open();
		boolean remove = db.removeWODGoal(name);
		db.close();
		if(remove==false)
			Log.e("Error","WODGoal was not deleted");
	}
	
	/**
	 * A WODGoal has just been reached
	 * @param name The name of the WOD whose goal was just achieved 
	 */
	public void completeWODGoal(String name, long endDate) {
		db.open();
		long insertId = db.completeWODGoal(name, endDate);
		db.close();
		if(insertId==-1) {
			Log.e("Error","WODGoal was not completed");
		}
	}
	
	/** 
	 * Returns the WOD object associated with a particular WODGoal
	 * @param wgid The WODGoal id
	 * @return The WOD object
	 */
	public WOD WODGoalToWOD(long wgid) {
		db.open();
		WOD workout = new WOD();
		Cursor cursor = db.getWOD(wgid);		
		
		if(cursor.moveToFirst() == false) { //That WOD doesn't exist (should never happen)
			cursor.close();
			db.close();
			return null;
		}
		
		//Get and return the WOD object
		workout = cursorToWOD(cursor);
		cursor.close();
		db.close();
		return workout;
	}
	
	/**
	 * Takes the cursor and returns a WODGoal Object
	 * @param cursor The passed in cursor
	 * @return The WODGoal object
	 */
	private WODGoal cursorToWODGoal(Cursor cursor) {
		WODGoal goal = new WODGoal();
		goal.setId(cursor.getLong(idPos));
		goal.setWid(cursor.getLong(widPos));
		goal.setGoalScore(cursor.getInt(goalPos));
		goal.setStartDate(cursor.getLong(sDatePos));
		goal.setComplete(cursor.getInt(isComPos));
		goal.setEndDate(cursor.getLong(eDatePos));
		return goal;
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
}
