package com.amadt.fitrack.database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.amadt.fitrack.model.AlertManager;

public class WODManager {

	//The Database and context
	private DatabaseDAO db;
	private Context myContext;
	
	//Indices for the WOD table
	private static final int idPos = 0;
	private static final int namePos = 1;
	private static final int typePos = 2;
	private static final int descriptionPos = 3;
	private static final int videoPos = 4;
	
	private final static int eidPos = 2;
	private final static int repPos = 3;
	private final static int weightPos = 4;
	private final static int distPos = 5;
		
	//Indices for Exercise table
	private final static int olymPos = 5;
	
	/**
	 * Constructor
	 * @param context The context for the application environment
	 */
	public WODManager(Context context) {
		db = new DatabaseDAO(context);
		try {
			db.createDataBase();
		} catch (IOException e) {
		}
		db.close();
		this.myContext = context;
	}
	
	public WOD getWOD(long id) {
		db.open();
		WOD workout = new WOD();
		Cursor cursor = db.getWOD(id);
		
		//Check if that WOD exists
		if(cursor.moveToFirst() == false) {  
			cursor.close();
			db.close();
			String message = "That WOD doesn't exist";
			AlertManager.alert(myContext, message);
			return workout;
		}
		
		//Get and return the WOD queried for
		workout = cursorToWOD(cursor);
		workout.setExercises(getAllExercises(workout.getName()));
		cursor.close();
		db.close();
		return workout;
	}
	
	/**
	 * Return a WOD object from the database with the given name
	 * @param name The name of the WOD
	 * @return The WOD object
	 */
	public WOD getWOD(String name) {
		db.open();
		WOD workout = new WOD();
		Cursor cursor = db.getWOD(name);
		
		//Check if that WOD exists
		if(cursor.moveToFirst() == false) {  
			cursor.close();
			db.close();
			String message = "No WOD with the name " + name + " exists!";
			AlertManager.alert(myContext, message);
			return workout;
		}
		
		//Get and return the WOD queried for
		workout = cursorToWOD(cursor);
		workout.setExercises(getAllExercises(name));
		cursor.close();
		db.close();
		return workout;
	}
	
	/**
	 * Gets all WODs
	 * @return A list containing all WODs in the database
	 */
	public List<WOD> getAllWODs() {
		db.open();
		List<WOD> workouts = new ArrayList<WOD>();
		Cursor cursor = db.getAllWODs();
		
		if(!cursor.moveToFirst()){ 
			cursor.close();
			db.close();
			String message = "There are no WODs in the database!";
			AlertManager.alert(myContext, message);
			return workouts;
		}
		
		//Get and return all the WODs that exist
		while ( !cursor.isAfterLast()) {
			WOD workout = cursorToWOD(cursor);
			workouts.add(workout);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return workouts;
	}
	
	/**
	 * Get a list of WODs by their type
	 * @param type The type of WODs we want to get
	 * @return A list of WODs of the specified type
	 */
	public List<WOD> getWODsByType(String type) {
		db.open();
		List<WOD> workouts = new ArrayList<WOD>();
		Cursor cursor = db.getWODsByType(type);
		
		//Check if that type of WOD exists
		if(cursor.moveToFirst() == false && !type.equals("Custom")) { 
			db.close();
			cursor.close();
			String message = "No WOD of type " + type + " exists!" ;
			AlertManager.alert(myContext, message);
			return workouts;
		}
		
		//Get and return all of the WODs of that type
		while (!cursor.isAfterLast()) {
			WOD workout = cursorToWOD(cursor);
			workouts.add(workout);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return workouts;
	}
	
	/**
	 * Add a new custom WOD into the database
	 * @param name The name of the new WOD
	 * @param description The desired user description of the new WOD
	 * @param video The path to a video
	 * @return The WOD class with the passed in information
	 */
	public WOD createCustomWOD(String name, String description, String video) {
		db.open();
		long insertId = db.createCustomWOD(name, description, video); 
		WOD newWorkout = new WOD();
		db.close();
		
		//Check for various user errors
		if(insertId==-2) { 
			db.close();
			String message = "That WOD name already exists!";
			AlertManager.alert(myContext, message);
			return newWorkout;
		}	
		if(insertId==-1) { 
			db.close();
			String message = "Could not add that WOD!";
			AlertManager.alert(myContext, message);
			return newWorkout;
		}
		
		//Get and return the WOD that was just created
		Cursor cursor = db.getWOD(insertId);
		cursor.moveToFirst();
		newWorkout = cursorToWOD(cursor);
		cursor.close();
		return newWorkout;
	}

	/**
	 * Removes a custom WOD from the database.
	 * @param name The name of the custom WOD to remove.
	 */
	public void removeCustomWOD(String name) {
		db.open();
		long remove = db.removeCustomWOD(name);
		db.close();
	
		//Check for various user errors 
		if(remove==-1) {
			String message = "That WOD could not be removed!";
			AlertManager.alert(myContext, message);
		}
		if(remove==-2) {
			String message = "That is not a Custom WOD!";
			AlertManager.alert(myContext, message);
		}		
	}
	
	/**
	 * Gets all exercises associated with a particular WOD
	 * @param workoutName The name of the WOD whose exercises we are looking up
	 * @return A list containing all exercises associated with a particular WOD
	 */
	public List<Exercise> getAllExercises(String workoutName) {
		db.open();
		List<Exercise> exercises = new ArrayList<Exercise>();
		Cursor cursor = db.allExercisesForWOD(workoutName);
		
		if(!cursor.moveToFirst()){ 
			/*String message = "WOD " + workoutName + " doesn't have any exercises!";
			AlertManager.alert(myContext, message);*/
			cursor.close();
			db.close();
			return exercises;
		}
		
		//Populate list of exercises
		while (!cursor.isAfterLast()) {
			Cursor cur = db.getExercise(cursor.getLong(eidPos));
			if(cur.moveToFirst() == false) {
				String message = "Could not get that exercise!";
				AlertManager.alert(myContext, message);
				cur.close();
				db.close();
				return exercises;
			}
			Exercise exercise = cursorToExercise(cur); 
			exercise.setReps(cursor.getInt(repPos));       //Extra steps to get specific data for this exercise
			exercise.setWeight(cursor.getInt(weightPos));
			exercise.setDistance(cursor.getInt(distPos));
			exercises.add(exercise);
			cursor.moveToNext();
			cur.close();
		}
		cursor.close();
		db.close();
		return exercises;
	}
	
	/**
	 * Adds an exercise to a custom WOD
	 * @param wName Name of the WOD
	 * @param eName Name of the exercise
	 * @return The exercise added to the WOD
	 */
	public Exercise addExerciseToCustomWOD(String wName, String eName, int reps, int weight, int distance) {
		db.open();
		Exercise exercise = new Exercise();
		long insertId = db.addExerciseToCustomWorkout(wName, eName, reps, weight, distance); 
		db.close();
		
		//Check for various user errors
		if(insertId==-1) { 
			db.close();
			String message = "Could not get that WOD!";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		if(insertId==-2) { 
			db.close();
			String message = "Could not get that Exercise!";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		if(insertId==-3) { 
			db.close();
			String message = wName + " is not a Custom WOD!";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		if(insertId==-4) { 
			db.close();
			String message = "Could not add " + eName + " to " + wName + "!";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		
		//Get and return the exercise just added
		Cursor cursor = db.getExercise(eName);
		if(cursor.moveToFirst() == false) {
			return exercise;
		}
		exercise = cursorToExercise(cursor);
		cursor.close();
		return exercise;
	}
	
	/**
	 * Removes an exercise from a custom WOD
	 * @param wname The WOD we are removing an exercise from
	 * @param ename The exercise we are removing 
	 */
	public void removeExerciseFromCustomWOD(String wname, String ename) {
		db.open();
		long remove = db.removeExerciseFromCustomWorkout(wname, ename);
		db.close();
		
		//Check for various user errors
		if(remove==-1) { 
			String message = "Could not get that WOD!";
			AlertManager.alert(myContext, message);
		}
		if(remove==-2) { 
			String message = "Could not get that Exercise!";
			AlertManager.alert(myContext, message);
		}
		if(remove==-3) { 
			String message = wname + " is not a Custom WOD!";
			AlertManager.alert(myContext, message);
		}
		if(remove==-4) { 
			String message = "Could not remove " + ename + " from " + wname + "!";
			AlertManager.alert(myContext, message);
		}
	}
	
	/**
	 * Takes the cursor and returns an Exercise Object
	 * @param cursor
	 * @return
	 */
	private Exercise cursorToExercise(Cursor cursor) {
		Exercise exercise = new Exercise();
		exercise.setId(cursor.getLong(idPos));
		exercise.setName(cursor.getString(namePos));
		exercise.setType(cursor.getString(typePos));
		exercise.setDescription(cursor.getString(descriptionPos));
		exercise.setVideo(cursor.getString(videoPos));
		exercise.setOlympic(cursor.getInt(olymPos));
		return exercise;
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