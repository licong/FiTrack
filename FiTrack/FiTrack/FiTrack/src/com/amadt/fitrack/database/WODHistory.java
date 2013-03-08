package com.amadt.fitrack.database;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WODHistory {
	private long _id;
	private long wid;
	private long date;
	private long duration;
	private String usernotes;
	private List<ExerciseHistory> exerHist;
	
	/**
	 * @return the _id
	 */
	public long getId() {
		return _id;
	}
	
	/**
	 * @param _id the _id to set
	 */
	public void setId(long _id) {
		this._id = _id;
	}

	/**
	 * @return the wid
	 */
	public long getWid() {
		return wid;
	}

	/**
	 * @param wid the wid to set
	 */
	public void setWid(long wid) {
		this.wid = wid;
	}

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}
	
	/**
	 * @return the date as a string
	 */
	public String getDateString() {
		return dateToString(this.date);
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param time the time to set
	 */
	public void setDuration(long time) {
		this.duration = time;
	}

	/**
	 * @return the usernotes
	 */
	public String getUsernotes() {
		return usernotes;
	}

	/**
	 * @param usernotes the usernotes to set
	 */
	public void setUsernotes(String usernotes) {
		this.usernotes = usernotes;
	}
	
	public List<ExerciseHistory> getExerciseHistories() {
		return exerHist;
	}

	public void setExerciseHistories(List<ExerciseHistory> exerHist) {
		this.exerHist = exerHist;
	}
	
	/**
	 * Returns the date as a string
	 * @param date The date
	 * @return The date
	 */
    private String dateToString(long date) {
    	SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy");
    	Date d = new Date(date);
    	return dateformat.format(d);
    }
}