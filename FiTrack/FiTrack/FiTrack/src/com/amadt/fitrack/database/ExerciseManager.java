package com.amadt.fitrack.database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.amadt.fitrack.model.AlertManager;

public class ExerciseManager {
	
	//The Database and context
	private DatabaseDAO db;
	private Context myContext;
	
	//Indices for Exercise table
	private final static int idPos = 0;
	private final static int namePos = 1;
	private final static int typePos = 2;
	private final static int descriptionPos = 3;
	private final static int videoPos = 4;
	private final static int olymPos = 5;
	
	/**
	 * Constructor
	 * @param context The context for the application environment
	 */
	public ExerciseManager(Context context) {
		db = new DatabaseDAO(context);
		try {
			db.createDataBase();
		} catch (IOException e) {
		}
		db.close();
		myContext = context;
	}
	
	/**
	 * Return an exercise object from the database with the given name
	 * @param eid The Exercise id
	 * @return The exercise object
	 */
	public Exercise getExercise(long eid) {
		db.open();
		Exercise exercise = new Exercise();
		Cursor cursor = db.getExercise(eid);
		
		//Should never happen
		if(cursor.moveToFirst() == false) {
			cursor.close();
			db.close();
			String message = "That exercise doesn't exist!";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		
		exercise = cursorToExercise(cursor);
		cursor.close();
		db.close();
		return exercise;
	}
	
	/**
	 * Return an exercise object from the database with the given name
	 * @param name The name of the exercise
	 * @return The exercise object
	 */
	public Exercise getExercise(String name) {
		db.open();
		Exercise exercise = new Exercise();
		Cursor cursor = db.getExercise(name);
		
		//Check if user input matches an exercise name
		if(cursor.moveToFirst() == false) { 
			cursor.close();
			db.close();
			String message = "Exercise " + name + " doesn't exist!";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		
		exercise = cursorToExercise(cursor);
		cursor.close();
		db.close();
		return exercise;
	}
	
	/**
	 * Gets all exercises
	 * @return A list containing all exercises in the database
	 */
	public List<Exercise> getAllExercises() {
		db.open();
		List<Exercise> exercises = new ArrayList<Exercise>();
		Cursor cursor = db.getAllExercises();
		
		//Should never happen
		if(cursor.moveToFirst() == false) {
			cursor.close();
			db.close();
			String message = "There are no exercises in the database!";
			AlertManager.alert(myContext, message);
			return exercises;
		}
		
		//Populate a List of Exercises
		while (!cursor.isAfterLast()) {
			Exercise exercise = cursorToExercise(cursor);
			exercises.add(exercise);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return exercises;
	}

	/**
	 * Adds a custom exercise to database
	 * @param name Name of the exercise
	 * @param description A description of the exercise
	 * @param video A path to a video
	 * @return The newly created exercise
	 */
	public Exercise createExercise(String name, String description, String video) {
		db.open();
		Exercise exercise = new Exercise();
		long insertId = db.createNewExercise(name, "Custom", description, video); 
		db.close();
		
		//Check ofr user errors
		if(insertId==-1) { 
			db.close();
			String message = "Exercise " + name + " could not be created";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		if(insertId==-2) { 
			db.close();
			String message = "Exercise " + name + " already exists";
			AlertManager.alert(myContext, message);
			return exercise;
		}
		
		//Get the exercise that was just added to database
		Cursor cursor = db.getExercise(insertId);
		if(cursor.moveToFirst() == false) {
			cursor.close();
			db.close();
			return exercise;
		}
		exercise = cursorToExercise(cursor);
		cursor.close();
		return exercise;
	}
	
	/**
	 * Removes a custom exercise from the database.
	 * @param name The name of the exercise to remove.
	 * @return True if one or more rows/exercises were deleted. False if none were or was not a custom workout.
	 */
	public void removeCustomExercise(String name) {
		db.open();
		long remove = db.removeExercise(name);
		db.close();
		
		//Check for user errors
		if(remove==-1){
			String message = "Exercise " + name + " could not be removed";
			AlertManager.alert(myContext, message);
		}	
		if(remove==-2){
			String message = name + " is not a custom exercise";
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
}