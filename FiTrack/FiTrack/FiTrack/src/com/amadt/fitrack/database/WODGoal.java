package com.amadt.fitrack.database;


import java.text.SimpleDateFormat;
import java.util.Date;

public class WODGoal {
	private long _id;
	private long wid;
	private int goalScore;
	private long startDate;
	private boolean isComplete;
	private long endDate;

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
	 * @return the goalScore
	 */
	public int getGoalScore() {
		return goalScore;
	}

	/**
	 * @param goalScore the goalScore to set
	 */
	public void setGoalScore(int goalScore) {
		this.goalScore = goalScore;
	}

	/**
	 * @return the date
	 */
	public long getStartDate() {
		return startDate;
	}
	
	/**
	 * @return the date as a string
	 */
	public String getStartDateString() {
		return dateToString(this.startDate);
	}

	/**
	 * @param date the date to set
	 */
	public void setStartDate(long date) {
		this.startDate = date;
	}

	/**
	 * @return the isComplete
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * @param isComplete the isComplete to set
	 */
	public void setComplete(int complete) {
		if(complete == 1)
			this.isComplete = true;
		else
			this.isComplete = false;
	}

	/**
	 * @return the endDate
	 */
	public long getEndDate() {
		return endDate;
	}

	/**
	 * @return the date as a string
	 */
	public String getEndDateString() {
		return dateToString(this.endDate);
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(long endDate) {
		this.endDate = endDate;
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
