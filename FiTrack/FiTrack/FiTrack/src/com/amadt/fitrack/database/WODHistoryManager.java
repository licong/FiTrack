package com.amadt.fitrack.database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amadt.fitrack.model.AlertManager;

import android.content.Context;
import android.database.Cursor;

public class WODHistoryManager {

	//The Database
	private DatabaseDAO db;
	private final Context myContext;
	
	//Indices for the WODHistory table
	private static final int idPos = 0;
	private static final int widPos = 1;
	private static final int datePos = 2;
	private static final int wodDurPos = 3;
	private static final int notesPos = 4;
	
	//Indices for the ExerciseHistory table
	private static final int eidPos = 1;
	private static final int whidPos = 2;
	private static final int repsPos = 3;
	private static final int weightPos = 4;
	private static final int distPos = 5;
	private static final int exDurPos = 6;
	
	/**
	 * Constructor
	 * @param context The context for the application environment
	 */
	public WODHistoryManager(Context context) {
		db = new DatabaseDAO(context);
		try {
			db.createDataBase();
		} catch (IOException e) {
		}
		this.myContext = context;
		db.close();
	}
	
	public WODHistory getWODHistory(long whid) {
		db.open();
		Cursor cursor = db.getWODHistoryById(whid);
		WODHistory wodHist = new WODHistory();
		
		if(cursor.moveToFirst()==false) { //That WOD History doesn't exist (should not happen)
			cursor.close();
			db.close();
			return wodHist;
		}
		
		wodHist = cursorToWODHistory(cursor);
		List<ExerciseHistory> exerHist = getAllExerciseHistory(whid);
		wodHist.setExerciseHistories(exerHist);
		cursor.close();
		db.close();
		return wodHist;
	}
	
	/**
	 * Get the entire history of a workout
	 * @param wname The name of the workout
	 * @return A list with the history of a workout
	 */
	public List<WODHistory> getAllHistory(String wname) {
		db.open();
		Cursor cursor = db.getWODHistory(wname);
		ArrayList<WODHistory> hist = new ArrayList<WODHistory>();
		
		if(cursor.moveToFirst()==false) { //That WOD has never been completed before
			db.close();
			return hist;
			
		}
		
		//Populate List of WorkoutHistory objects
		while (!cursor.isAfterLast()) {
			WODHistory hi = cursorToWODHistory(cursor);
			List<ExerciseHistory> exerHist = getAllExerciseHistory(hi.getId());
			hi.setExerciseHistories(exerHist);
			hist.add(hi);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return hist;
	}
	
	/**
	 * Get all ExerciseHistory objects associated with this WODHistory Object
	 * @param whid The id of the WOD History
	 * @return A List of ExerciseHistories
	 */
	public List<ExerciseHistory> getAllExerciseHistory(long whid) {
		db.open();
		List<ExerciseHistory> exerHist = new ArrayList<ExerciseHistory>();
		Cursor cursor = db.getAllExerciseHistories(whid);
		
		if(cursor.moveToFirst()==false) { //That WOD History doesn't exist (should never happen)
			cursor.close();
			db.close();
			return exerHist;
		}
		
		while(!cursor.isAfterLast()) {
			ExerciseHistory exer = cursorToExerciseHistory(cursor);
			exerHist.add(exer);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return exerHist;
	}
	
	/**
	 * Get the number of times a workout has been completed
	 * @param name The name of the workout
	 * @return The number of times a workout has been completed
	 */
	public int getWODFrequency(String wname) { 
		return getAllHistory(wname).size();
	}
	
	/**
	 * Get the best time a particular workout was completed
	 * @param name The name of the workout
	 * @return The number value of a workout's best personal time
	 */
	public int getWODBestTime(String name) {
		db.open();
		Cursor c = db.getWODHistoryByTime(name);
		
		if(c.moveToFirst()==false) {
			db.close();
			c.close();
			return -1;
		}
		
		return c.getInt(3);
	}
	
	/**
	 * Adds an exercise to a custom workout
	 * @param name The workout we are adding an exercise to
	 * @param date The exercise we are adding
	 * @param time The tme to complete workout
	 * @param notes Extra notes the user might want to store
	 * @return The WorkoutHistory object just added
	 */
	public WODHistory addWODToHistory(String name, long date, long time, String notes) {
		db.open();
		WODHistory hist = new WODHistory();
		long insertId = db.addWODToHistory(name, date, time, notes);
		db.close();
		if(insertId==-1) { //Could not add WOD to history
			db.close();
			return null;
		}
		
		//Check if completed WOD matches or beats current WOD goal. If so, update.
		WODGoalManager wgm = new WODGoalManager(myContext);
		WODGoal goal = wgm.getWODGoal(name);
		if(goal != null && time <= goal.getGoalScore()) {
			wgm.completeWODGoal(name, date);
			AlertManager.alert(myContext, "Congratulations! You have just beaten your goal score of " + 
			                               goal.getGoalScore()/1000 + " !");
		}
		
		//Get and return WorkoutHistory object just added to Database
		Cursor cursor = db.getWODHistoryById(insertId);
		if(cursor.moveToFirst() == false) {
			db.close();
			return null;
		}
		hist = cursorToWODHistory(cursor);
		cursor.close();
		return hist;
		
	}
	
	/**
	 * Takes the cursor and returns a WorkoutHistory Object
	 * @param cursor
	 * @return
	 */
	private WODHistory cursorToWODHistory(Cursor cursor) {
		WODHistory hist = new WODHistory();
		hist.setId(cursor.getLong(idPos));
		hist.setWid(cursor.getLong(widPos));
		hist.setDate(cursor.getLong(datePos));
		hist.setDuration(cursor.getLong(wodDurPos));
		hist.setUsernotes(cursor.getString(notesPos));;
		return hist;
	}
	
	/**
	 * 
	 * @param cursor
	 * @return
	 */
	private ExerciseHistory cursorToExerciseHistory(Cursor cursor){
		ExerciseHistory hist = new ExerciseHistory();
		hist.setId(cursor.getLong(idPos));
		hist.setEid(cursor.getLong(eidPos));
		hist.setWhid(cursor.getLong(whidPos));
		hist.setReps(cursor.getLong(repsPos));
		hist.setWeight(cursor.getLong(weightPos));
		hist.setDist(cursor.getLong(distPos));
		hist.setDur(cursor.getLong(exDurPos));
		return hist;
	}
}
