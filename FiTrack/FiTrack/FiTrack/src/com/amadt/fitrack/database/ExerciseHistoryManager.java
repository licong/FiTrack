package com.amadt.fitrack.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ExerciseHistoryManager {

	//The Database
	private DatabaseDAO db;
	
	//Indices for the ExerciseHistory table
	private static final int idPos = 0;
	private static final int eidPos = 1;
	private static final int whidPos = 2;
	private static final int repsPos = 3;
	private static final int weightPos = 4;
	private static final int distPos = 5;
	private static final int durPos = 6;
	
	/**
	 * Constructor
	 * @param context The context for the application environment
	 */
	public ExerciseHistoryManager(Context context) {
		db = new DatabaseDAO(context);
		try {
			db.createDataBase();
		} catch (IOException e) {
		}
		db.close();
	}
	
	/**
	 * Completes an exercise as part of a WOD
	 * @param name The name of the exercise
	 * @param whid The completed WOD it is associated with
	 * @param reps The number of reps completed
	 * @param weight The weight completed
	 * @param dist The distance ran/rowed/etc.
	 * @param dur The time that distance was covered in
	 * @return
	 */
	public ExerciseHistory addExerciseToHistory(String name, long whid, long reps, long weight, long dist, long dur) {
		db.open();
		ExerciseHistory hist = new ExerciseHistory();
		long insertId = db.addExerciseToHistory(name, whid, reps, weight, dist, dur);
		db.close();
		if(insertId==-1) { //Could not add Exercise to history
			Log.e("Error","Exercise not entered into History");
			return null;
		}
		
		//Get and return WorkoutHistory object just added to Database
		Cursor cursor = db.getExerciseHistoryById(insertId);
		if(cursor.moveToFirst() == false) {
			db.close();
			return null;
		}
		hist = cursorToExerciseHistory(cursor);
		cursor.close();
		return hist;
	}
	
	/**
	 * Gets the entire history of a particular exercise
	 * @param ename The name of the exercise
	 * @return A List of ExerciseHistory object
	 */
	public List<ExerciseHistory> getAllHistory(String ename) {
		db.open();
		Cursor cursor = db.getHistoryOfExercise(ename);
		ArrayList<ExerciseHistory> hist = new ArrayList<ExerciseHistory>();
		
		if(cursor.moveToFirst()==false) { //That exercise has never been completed before
			db.close();
			return null;
			
		}
		
		//Populate List of ExerciseHistory objects
		while (!cursor.isAfterLast()) {
			ExerciseHistory hi = cursorToExerciseHistory(cursor);
			hist.add(hi);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
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
		hist.setDur(cursor.getLong(durPos));
		return hist;
	}
}
