package com.amadt.fitrack.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseDAO extends SQLiteOpenHelper{

	//Database fields
    private SQLiteDatabase myDataBase;
    private final Context myContext; // TODO Don't really need. Will just need the application context.
	
	//Path to database
    private static String DB_PATH = "/data/data/com.amadt.fitrack/databases/";
    private static String DB_NAME = "AMADT";
	
    //Table and Column Names
    private static final String id = "_id";
    
    private static final String WOD_TABLE = "wod";
    private static final String wod_name = "name";
    private static final String wod_type = "type";
    private static final String wod_description = "description";
    private static final String wod_video = "video";
    private static final String[] wodcols = {id,wod_name,wod_type,wod_description,wod_video};
    
    private static final String EXERCISE_TABLE = "exercise";
    private static final String ecol_name = "name";
    private static final String ecol_type = "type";
    private static final String ecol_description = "description";
    private static final String ecol_video = "video";
    private static final String ecol_isOlym = "isolympic";
    private static final String[] ecols = {id,ecol_name,ecol_type,ecol_description,ecol_video,ecol_isOlym };
    
    private static final String WORKOUT_TABLE = "workout";
    private static final String wcol_wid = "w_id";
    private static final String wcol_eid = "e_id";
    private static final String wcol_reps = "reps";
    private static final String wcol_weight = "weight";
    private static final String wcol_dist = "distance";
    private static final String[] wcols = {id,wcol_wid,wcol_eid,wcol_reps,wcol_weight,wcol_dist};
    
    private static final String EHISTORY_TABLE = "exercisehistory";
    private static final String ehcol_eid = "e_id";
    private static final String ehcol_whid = "wh_id";
    private static final String ehcol_reps = "reps";
    private static final String ehcol_weight = "weight";
    private static final String ehcol_dist = "cardiodistance";
    private static final String ehcol_dur = "cardioduration";
    private static final String[] ehcols = {id,ehcol_eid,ehcol_whid,ehcol_reps,ehcol_weight,ehcol_dist,ehcol_dur};
    
    private static final String WHISTORY_TABLE = "wodhistory";
    private static final String whcol_wid = "w_id";
    private static final String whcol_date = "date";
    private static final String whcol_duration = "time";
    private static final String whcol_usernotes = "usernotes";
    private static final String[] whcols = {id,whcol_wid,whcol_date,whcol_duration,whcol_usernotes};
    
    private static final String SWOD_TABLE = "scheduledwod";
    private static final String swcol_wid = "w_id";
    private static final String swcol_date = "date";
    private static final String[] swcols = {id,swcol_wid,swcol_date};
    
    private static final String WGOAL_TABLE = "wodgoal";
    private static final String wgcol_wid = "w_id";
    private static final String wgcol_goaltime = "goaltime";
    private static final String wgcol_sdate = "startdate";
    private static final String wgcol_isCom = "iscomplete";
    private static final String wgcol_edate = "enddate";
    private static final String[] wgcols = {id,wgcol_wid,wgcol_goaltime,wgcol_sdate,wgcol_isCom,wgcol_edate};
    private static final int mFalse = 0;
    private static final int mTrue = 1;
    
    /**
     * Constructor
     * @param context The context the constructor was passed in
     */
	public DatabaseDAO(Context context){
		super(context, DB_NAME, null, 1);
        this.myContext = context;
	}
	
	/**
     * Creates a empty database on the system and rewrites it with your own preset database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDBExists();
 
    	if(dbExist == false){
        	this.getWritableDatabase();
        	this.close();
    		copyDataBase();
    	}
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return True if it exists, False if it doesn't.
     */
    //Rename to make it clear (to checkExists())
    private boolean checkDBExists(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e) {
    		return false;
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
    	
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies the preset database from the assets folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open the preset database in assets as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty database
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty database as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    /**
     * Opens the system database for reading and writing.
     * @throws SQLException
     */
    public void open() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
        if(myDataBase == null)
        	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    /**
     * Closes the system database
     */
    @Override
	public synchronized void close() {
 
    	if(myDataBase != null)
    		myDataBase.close();
 
    	super.close();
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
/***********************************************************************************************************
 * WOD Table                                                                                           *
 ***********************************************************************************************************/
	
	/**
	 * Gets a specific workout.
	 * @param name The name of the workout we want to get.
	 * @return A cursor positioned before the first entry. 
	 */
	public Cursor getWOD(String name) {
		
		String where = wod_name + "=?";
		String[] args = {name};
		return DBQuery(true, WOD_TABLE, wodcols, where, args, null, null, null, null);	
	}
	
	/**
	 * Get a specific workout
	 * @param Id The id of the workout we want to get
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getWOD(long Id) {
		
		String where = id + "=?";
		String[] args = {""+Id};
		return DBQuery(true, WOD_TABLE, wodcols, where, args, null, null, null, null);		
	}
	
	/**
	 * Get WODs by type
	 * @param type The type of workouts we want to get
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getWODsByType(String type) {
		
		String where = wod_type + "=?";
		String[] args = {type};
		return DBQuery(true, WOD_TABLE, wodcols, where, args, null, null, null, null);
		
	}
	
	/**
	 * Gets all workouts in the database
	 * @return Returns all workouts in the database within a Cursor
	 */
	public Cursor getAllWODs() {
		return DBQuery(true, WOD_TABLE, wodcols, null, null, null, null, null, null);
	}
	
	/**
	 * Add a new custom workout into the database
	 * @param name The name of the new workout
	 * @param description The desired user descriptin of the new workout
	 * @param video The path to a video
	 * @return The id of the new workout 
	 * 		   -1 if the WOD could not be inserted
	 *         -2 if there already exists a WOD with that name
	 *         
	 */
	public long createCustomWOD(String name, String description, String video) {
		if(WODExists(name)) {
			return -2;
		}
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(wod_name, name);
		values.put(wod_type, "Custom");
		if(description != null)
			values.put(wod_description, description);
		if(video != null) 
			values.put(wod_video, video);

		//Return new WOD id if successful or -1 if not
		long ret = db.insert(WOD_TABLE, null, values);	
		if(ret != -1) {
			return getWODId(name);
		}
		return ret;
	}
	
	/**
	 * Removes a custom workout from the database.
	 * @param name The name of the custom workout to remove.
	 * @return 1 if one or more rows were deleted
	 * 		  -1 if the WOD couldn't be removed/doesn't exist
	 * 	      -2 if it wasn't a custom WOD
	 */
	public long removeCustomWOD(String name) {		
		if (!isCustom(name))
			return -2;
		
		String where = wod_name + "=?";
		String[] args = {name};
		boolean remove = DBDelete(WOD_TABLE, where, args);
		if(!remove) {
			return -1;
		}
		return 1;
	} 
	
	/**
	 * Test if the passed in workout is custom. Used as helper function in removeCustomWorkout().
	 * @param name The name of the workout 
	 * @return True if the workout is a custom one, false otherwise
	 */
	private boolean isCustom(String name) {
		Cursor c = getWOD(name);
		c.moveToFirst();
		String type = c.getString(2);
		
		return type.equals("Custom");
	}
	
	/**
	 * Get a specific workout id.
	 * @param name The name of the workout whose id we want to get
	 * @return The id of the workout we passed in or -1 if ther is no WOD with that name
	 */
	private int getWODId(String name) {
		Cursor c = getWOD(name);
		if(c.moveToFirst() == false)
			return -1;
		
		return c.getInt(0);
	}
	
	/**
	 * Checks if a WOD with that name already exists
	 * @param name The name of the WOD
	 * @return True if that WOD already exists, false otherwise
	 */
	private boolean WODExists(String name) {
		return getWOD(name).getCount() > 0;
	}
/**********************************************************************************************************
 * Exercise Table                                                                                         *
 **********************************************************************************************************/
	
	/**
	 * Gets a specific exercise
	 * @param name The name of the exercise
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getExercise(String name) {
		
		String where = ecol_name + "=?";
		String[] args = {name};
		
		return DBQuery(true,EXERCISE_TABLE,ecols,where,args,null,null,null,null);
	}
	
	/**
	 * Gets a specific exercise
	 * @param Id The _id of the exercise
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getExercise(long Id) {

		String where = id + "=?";
		String[] args = {""+Id};
		
		return DBQuery(true,EXERCISE_TABLE,ecols,where,args,null,null,null,null);
	}
	
	/**
	 * Gets all workouts in the database
	 * @return Returns all workouts in the database within a Cursor
	 */
	public Cursor getAllExercises() {
		return DBQuery(true, EXERCISE_TABLE, ecols, null, null, null, null, null, null);
	}
	
	/**
	 * Add a new exercise into the databse
	 * @param name The name of the exercise
	 * @param type The type of exercise it is (lift or cardio)
	 * @param description A description of the exercise
	 * @param video The path to a video
	 * @return The id of the new exercise if added
	 * 		   -1 if Exercise couldn't be added
	 * 		   -2 if Exercise already exists
	 */
	public long createNewExercise(String name, String type, String description, String video) {	
		if(exerciseExists(name))
			return -2;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(ecol_name, name);
		values.put(ecol_type, type);
		if(description != null)
			values.put(ecol_description, description);
		if(video != null) 
			values.put(ecol_video, video);
		
		long ret = db.insert(EXERCISE_TABLE, null, values);	
		if(ret != -1)
			return getExerciseId(name);
		return ret;
	}
	
	/**
	 * Remove a custom exercise from the database
	 * @param name The name of the exercise we wish to remove
	 * @return 1 if the exercise was removed
	 * 		  -1 if the exercise could not be removed
	 *        -2 if the exercise is not a custom one
	 */
	public long removeExercise(String name) {
		if (!isExerciseCustom(name))
			return -2;
		
		String where = ecol_name + "=?";
		String[] args = {name};
		
		boolean remove = DBDelete(EXERCISE_TABLE, where, args);
		if(remove==true)
			return 1;
		return -1;
	} 
	
	/**
	 * Test if the passed in exercise is custom. Used as helper function in removeExercise().
	 * @param name The name of the exercise 
	 * @return True if the exercise is a custom one, false otherwise
	 */
	private boolean isExerciseCustom(String name) {
		Cursor c = getExercise(name);
		c.moveToFirst();
		String type = c.getString(2);
		
		return type.equals("Custom");
	}
	
	/**
	 * Get a specific exercise id
	 * @param name The name of the exercise whose id we want to get
	 * @return The id of the passed in exercise or -1 if an error occurred
	 */
	private int getExerciseId(String name){
		Cursor c = getExercise(name);
		if(c.moveToFirst()==false)
			return -1;
		
		return c.getInt(0);
	}
	
	/**
	 * Check if an Exercise with that name already exists
	 * @param name The name of the exercise
	 * @return True if it exists, false if it doesn't
	 */
	private boolean exerciseExists(String name) {
		return getExercise(name).getCount() > 0;
	}
	
/**********************************************************************************************************
 * Workout Table                                                                                 *
 **********************************************************************************************************/
	
	/**
	 * Returns a Cursor with all exercises associated with a particular workout
	 * @param name The name of the workout
	 * @return A Cursor with all exercises in a particular workout
	 */
	public Cursor allExercisesForWOD(String name) {
		int wid = getWODId(name);
		
		String where = wcol_wid + "=?";
		String[] args = {""+wid};
		return DBQuery(true, WORKOUT_TABLE, wcols, where, args, null, null, null, null);
	}
	
	/**
	 * Adds an exercise to a custom workout
	 * @param workoutname The workout we are adding an exercise to
	 * @param exercisename The exercise we are adding
	 * @return 1 if the exercise was correctly added to the custom workout
	 * 		  -1 if that WOD doesn't exist
	 * 		  -2 if that Exercise doesn't exist
	 * 		  -3 if that WOD is not a custom one
	 * 		  -4 if there was some other error
	 */
	public long addExerciseToCustomWorkout(String workoutname, String exercisename, int reps, int weight, int distance) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		int wid = getWODId(workoutname);
		int eid = getExerciseId(exercisename);
		
		if(wid == -1 )
			return -1;
		if(eid == -1)
			return -2;
		if(!isCustom(workoutname))
			return -3;
		
		ContentValues values = new ContentValues();
		values.put(wcol_wid, getWODId(workoutname));
		values.put(wcol_eid, getExerciseId(exercisename));
		values.put(wcol_reps, reps);
		values.put(wcol_weight, weight);
		values.put(wcol_dist, distance);
		
		long ret = db.insert(WORKOUT_TABLE, null, values);
		if(ret != -1) {
			return 1;
		}
		return -4; 
	}
	
	/**
	 * Removes an exercise from a custom workout
	 * @param workoutname The workout we are removing from
	 * @param exercisename The exercise we are removing
	 * @return 1 if exercise was removed from custom WOD
	 * 		  -1 if WOD could not be found
	 * 	 	  -2 if Exercise could not be found
	 * 		  -3 if WOD is not custom
	 * 		  -4 if removed failed for another reason 
	 */
	public long removeExerciseFromCustomWorkout(String workoutname, String exercisename) {		
		int wid = getWODId(workoutname);
		int eid = getExerciseId(exercisename);
		
		if (wid == -1 )
			return -1;
		if(eid ==-1)
			return -2;
		if(!isCustom(workoutname))
			return -3;
		
		String where = wcol_wid + "=?" + " and " + wcol_eid + "=?";
		String[] args = {""+wid, ""+eid};
		boolean remove = DBDelete(WORKOUT_TABLE, where, args);
		if(remove==true)
			return 1;
		return -4;
		
	}
	
/***********************************************************************************************************
 * WOD History Table                                                                                   *
 * *********************************************************************************************************/

	/** 
	 * Get the user history of a specific workout
	 * @param wid The id of the specific workout
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getWODHistory(int wid) {
		String where = whcol_wid + "=?";
		String[] args = {""+wid};
	
		return DBQuery(true, WHISTORY_TABLE, whcols, where, args, null, null, null, null);
	}

	/** 
	 * Get the user history of 
	 * @param wid The id of the workout history
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getWODHistoryById(long _id) {
		String where = id + "=?";
		String[] args = {""+_id};
	
		return DBQuery(true, WHISTORY_TABLE, whcols, where, args, null, null, null, null);
	}

	/** 
	 * Get the user history of a specific workout
	 * @param name The name of the workout
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getWODHistory(String name) {
		return getWODHistory(getWODId(name));
	}

	/** 
	 * Get the user workout history for a specific day 
	 * @param date The date we wish to look up
	 * @return A cursor positioned before the first entry
	 */
	public Cursor getWODHistory(long date) {
		String where = whcol_date + "=?";
		String[] args = {""+date};
	
		return DBQuery(true, WHISTORY_TABLE, whcols, where, args, null, null, null, null);
	}

	/**
	 * Gets the workout history in order of best to worst time
	 * @param name Name of the workout whose best time we are looking for
	 * @return The best time or -1 if workout never completed before
	 */
	public Cursor getWODHistoryByTime(String name) {
		String where = whcol_wid +"=?";
		String[] args = {""+getWODId(name)};
		String orderBy = whcol_duration + " ASC";
		
		return DBQuery( true, WHISTORY_TABLE, whcols, where, args, null, null, orderBy, null);
	}

	/**
	 * After WOD has been completed, adds it to history. Defaults to current date.
	 * @param name The name of the WOD completed
	 * @return The row id or -1 if an error occurred
	 */
	public long addWODToHistory(String name, long date, long time, String usernotes) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(whcol_wid, getWODId(name));
		values.put(whcol_date, date);
		values.put(whcol_duration, time);
		if(usernotes != null)
			values.put(whcol_usernotes, usernotes);
		return db.insert(WHISTORY_TABLE, null, values);
	}
	
	/**
	 * Gets all Exercise Histories associated with a particular Workout History
	 * @param whid The id of the Workout History
	 * @return A cursor pointed before the first entry
	 */
	public Cursor getAllExerciseHistories(long whid) {
		String where = ehcol_whid + "=?";
		String[] args = {""+whid};
		
		return DBQuery(true, EHISTORY_TABLE, ehcols, where, args, null, null, null, null);
	}

/*********************************************************************************************************
 * ExerciseHistory Table                                                                                 *
 *********************************************************************************************************/

	/**
	 * Complete an exercise
	 * @param name The name of the exercise that was completed
	 * @param whid The id of the completed WOD the exercise is associated with
	 * @param reps The number of reps completed
	 * @param weight The amount of weight
	 * @param dist The distance ran/rowed/etc.
	 * @param dur The time it took to cover that distance
	 * @return The row id or -1 if an error ocurred
	 */
	public long addExerciseToHistory(String name, long whid, long reps, long weight, long dist, long dur) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(ehcol_eid, getExerciseId(name));
		values.put(ehcol_whid, whid);
		if(reps != 0)
			values.put(ehcol_reps, reps);
		if(weight != 0)
			values.put(ehcol_weight, weight);
		if(dist != 0)
			values.put(ehcol_dist, dist);
		if(dur != 0)
			values.put(ehcol_dur, dur);

		return db.insert(EHISTORY_TABLE, null, values);
	}

	/**
	 * Get the entire history of a particular exercise (regardless of WOD association)
	 * @param name The name of the exercise
	 * @return A cursor pointed before the first entry
	 */
	public Cursor getHistoryOfExercise(String name) {
		long eid = getExerciseId(name);
		if(eid == -1) //Exercise doesn't exist
			return null;
		String where = ehcol_eid + "=?";
		String[] args = {""+eid};
		
		return DBQuery(true, EHISTORY_TABLE, ehcols, where, args, null, null, null, null);
	}
	
	/**
	 * Get a particular exercise history (should only return one)
	 * @param _id The id of the Exercise History
	 * @return A cursor pointed before the first entry
	 */
	public Cursor getExerciseHistoryById(long _id) {
		String where = id + "=?";
		String[] args = {""+_id};
	
		return DBQuery(true, EHISTORY_TABLE, ehcols, where, args, null, null, null, null);
	}

/**********************************************************************************************************
 * WODGoal Table                                                                                      *
 **********************************************************************************************************/
	
	/**
	 * Gets all WOD goals
	 * @return A Cursor pointing before the first entry
	 */
	public Cursor getAllWODGoals() {
		String orderBy = wgcol_isCom + " ASC";
		return DBQuery(true, WGOAL_TABLE, wgcols, null, null, null, null, orderBy, null);
	}
	
	/**
	 * Gets all workout goals that are not yet completed
	 * @return A Cursor pointing before the first entry
	 */
	public Cursor getAllIncomWODGoals() {
		String where = wgcol_isCom + "=?";
		String[] args = {""+mFalse};
		
		return DBQuery(true, WGOAL_TABLE, wgcols, where, args, null, null, null, null);
	}
	
	/**
	 * Gets all workout goals that have been completed
	 * @return A Cursor pointing before the first entry
	 */
	public Cursor getAllCompletedWODGoals() {
		String where = wgcol_isCom + "=?";
		String[] args = {""+mTrue};
		
		return DBQuery(true, WGOAL_TABLE, wgcols, where, args, null, null, null, null);
	}
	
	/**
	 * Gets the incomplete workout goal for a particular workout
	 * @param name The name of the workout whose goal we are viewing 
	 * @return A cursor pointing before the first entry
	 */
	public Cursor getWODGoal(String name) {
		String where = wgcol_wid + "=?" + " AND " + wgcol_isCom + "=?";
		String[] args = {""+getWODId(name), ""+mFalse};
		
		return DBQuery(true, WGOAL_TABLE, wgcols, where, args, null, null, null, null);
	}
	
	/**
	 * Gets the WOD goal at the specific WODGoal id
	 * @param wgid The id of theWODGoal
	 * @return A cursor pointing before the first entry
	 */
	public Cursor getWODGoal(long wgid) {
		String where = id + "=?";
		String[] args = {""+wgid};
		
		return DBQuery(true, WGOAL_TABLE, wgcols, where, args, null, null, null, null);
	}
	
	/**
	 * Gets all completed workout goals for a particular workout
	 * @param name The name of the workout whose goals we are viewing 
	 * @return A cursor pointing before the first entry
	 */
	public Cursor getComWODGoals(String name) {
		String where = wgcol_wid + "=?" + " AND " + wgcol_isCom + "=?";
		String[] args = {""+getWODId(name), ""+mTrue};
		
		return DBQuery(true, WGOAL_TABLE, wgcols, where, args, null, null, null, null);
	}
	
	/**
	 * Adds a new goal for a workout (default to incomplete)
	 * @param name The name of the workout we are setting a new goal for
	 * @param time The user goal time
	 * @return The row id or -1 if there was an error
	 */
	public long addWODGoal(String name, int time, long startDate) {
		SQLiteDatabase db = this.getWritableDatabase();	
		int wid = getWODId(name);
		
		if(wid==-1) //That WOD does not exist
			return -1;
		if(checkGoalExist(name)) //That WOD already has a goal
			return -2;
		
		ContentValues values = new ContentValues();
		values.put(wgcol_wid, wid);
		values.put(wgcol_goaltime, time);
		values.put(wgcol_sdate, startDate);
		
		return db.insert(WGOAL_TABLE, null, values);
	}
	
	/**
	 * Removes the incomplete goal for a workout
	 * @param name The workout whose goal we want to remove
	 * @return True if one or more rows were deleted, false otherwise
	 */
	public boolean removeWODGoal(String name) {
		int wid = getWODId(name);
		
		if (wid == -1)
			return false;
		
		String where = wgcol_wid + "=?";
		String[] args = {""+wid};
		return DBDelete(WGOAL_TABLE, where, args);
	}
	
	/**
	 * Changes the goal time for a workout
	 * @param name The name of the workout
	 * @return The number of rows changed
	 */
	public int editWODGoal(String name, int newTime, long newDate) {
		int wid = getWODId(name);
		
		if(wid==-1 || !checkGoalExist(name)) // That workout doesn't exist or there is no incomplete goal currently
			return -1;
		
		String where = wgcol_wid + "=?" + " AND " + wgcol_isCom + "=?";
		String[] args = {""+wid, ""+mFalse};
		
		ContentValues values = new ContentValues();
		values.put(wgcol_goaltime, newTime);
		values.put(wgcol_sdate, newDate);
		return DBUpdate(WGOAL_TABLE,values,where,args);
	}
	
	/**
	 * User achieves the goal time for a particular WOD.
	 * @param name The name of the WOD
	 * @param endDate The date it was completed
	 * @return The number of rows changed
	 */
	public int completeWODGoal(String name, long endDate) {
		int wid = getWODId(name);
		
		if(wid==-1 || !checkGoalExist(name)) // That workout doesn't exist or there is not incomplete goal currently
			return -1;
		
		String where = wgcol_wid + "=?" + " AND " + wgcol_isCom + "=?";
		String[] args = {""+getWODId(name), ""+mFalse};
		
		ContentValues values = new ContentValues();
		values.put(wgcol_isCom, mTrue);
		values.put(wgcol_edate, endDate);
		return DBUpdate(WGOAL_TABLE,values,where,args);
	}
	
	/**
	 * Checks if the workout has an incomplete goal
	 * @param name The name of the workout
	 * @return True if the workout already has a goal, false otherwise
	 */
	public boolean checkGoalExist(String name) {
		Cursor goal = getWODGoal(name);
		
		return goal.getCount() != 0;
	}

/**********************************************************************************************************
 * ScheduledWOD Table                                                                                 *
 **********************************************************************************************************/
	
	/**
	 * Add a workout to the schedule
	 * @param name The name of the workout
	 * @param date The date the workout is scheduled for
	 * @return The row id of the newly scheduled workout or -1 if there was an error
	 */
	public long scheduleAWOD(String name, long date) {
		SQLiteDatabase db = this.getWritableDatabase();
		int wid = getWODId(name);
		
		if(wid==-1) 
			return -1;
		
		ContentValues values = new ContentValues();
		values.put(swcol_wid, wid);
		values.put(swcol_date, date);
		
		return db.insert(SWOD_TABLE, null, values);
	}
	
	/**
	 * Cancels a workout scheduled for a specific date
	 * @param name The name of the workout
	 * @param date The date it is scheduled for
	 * @return True if one or more rows were removed, false otherwise
	 */
	public boolean cancelAWOD(String name, long date) {
		int wid = getWODId(name);
		
		if (wid == -1)
			return false;
		
		String where = swcol_wid + "=?" + " AND " + swcol_date + "=?";
		String[] args = {""+getWODId(name), ""+date};
		return DBDelete(SWOD_TABLE, where, args);
	}
	
	/**
	 * Gets all workouts for that day
	 * @param date The date
	 * @return A cursor before the first entry
	 */
	public Cursor getWODsForADay(long date) {
		String where = swcol_date + "=?";
		String[] args = {""+date};
		
		return DBQuery(true, SWOD_TABLE, swcols, where, args, null, null, null, null);
	}
	
	/**
	 * Get the future schedule of a workout
	 * @param name The name of the workout
	 * @return A cursor pointed before the first entry
	 */
	public Cursor getWODSchedule(String name) { //Only get future workouts (WHERE date >= currentTimeMillis()) 
		String where = swcol_wid + "=?" + " AND " + swcol_date + ">=?";
		String[] args = {""+getWODId(name), ""+System.currentTimeMillis()};
		
		return DBQuery(true, SWOD_TABLE, swcols, where, args, null, null, null, null);
	}
	
	/**
	 * Deletes all previous scheduled WODs from the calendar (they remain in history)
	 * @return True if one or more rows were deleted, false otherwise
	 */
	public boolean clearPast() {
		String where = swcol_date + "<=?";
		String[] args = {""+System.currentTimeMillis()};
		
		return DBDelete(SWOD_TABLE, where, args);
		
	}
	
/**********************************************************************************************************
 * Helper functions used for various tables	                                                              *
 **********************************************************************************************************/
	
	/**
	 * A general delete function used to delete from the Database
	 * @param table The table name to delte from
	 * @param whereClause Optional Where caluse to apply (passing Null will delete all rows)
	 * @param whereArgs Replaces ?s found in whereClause
	 * @return
	 */
	private boolean DBDelete(String table, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = this.getWritableDatabase();		
		return db.delete(table, whereClause, whereArgs) > 0;
	}
	
	/**
	 * A general query function used to query the Database
	 * @param distinct Whether each row is to be unique
	 * @param table The table name to compile the query against
	 * @param columns A list of which columns to return. Null returns all columns.
	 * @param selection Filter declaring which rows to return 
	 * @param selectionArgs Replaces ?s found in selection
	 * @param groupBy Filter declaring how to group rows
	 * @param having Filter declaring which row groups to include in the cursor
	 * @param orderBy How to order the rows
	 * @param limit Limit the number of rows returned 
	 * @return A cursor positioned before the first entry.
	 */
	private Cursor DBQuery(boolean distinct, String table, String[] columns, String selection,
						   String[] selectionArgs, String groupBy, String having, String orderBy,
						   String limit) {
		SQLiteDatabase db = this.getReadableDatabase();		
		return db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	
	/**
	 * A general update function used to update the Database
	 * @param table The table to update
	 * @param values A map from column names to new column values
	 * @param whereClause Optional WHERE clause
	 * @param whereArgs Arguments for the WHERE clause
	 * @return
	 */
	private int DBUpdate(String table, ContentValues values, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = this.getWritableDatabase();	
		return db.update(table, values, whereClause, whereArgs);
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
    
    /**
     * Returns the date as a long
     * @param date The date
     * @return The date
     * @throws ParseException
     */
    public long stringToDate(String date) throws ParseException {
    	Date d = new Date();
    	try { 
        	SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    		d = dateformat.parse(date);
    	} catch (ParseException e) {
    		Log.e("stringToDate", "Parse Exception at" + e.getErrorOffset());
    		return 0;
    	}
        return d.getTime();
    }
}