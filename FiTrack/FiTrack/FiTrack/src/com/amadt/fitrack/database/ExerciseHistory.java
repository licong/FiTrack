package com.amadt.fitrack.database;

public class ExerciseHistory {
	private long _id;
	private long eid;
	private long whid;
	private long reps;
	private long weight;
	private long cardiodistance;
	private long cardioduration;
	private String wodName;
	
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
	 * @return the eid
	 */
	public long getEid() {
		return eid;
	}

	/**
	 * @param eid the eid to set
	 */
	public void setEid(long eid) {
		this.eid = eid;
	}

	/**
	 * @return the whid
	 */
	public long getWhid() {
		return whid;
	}

	/**
	 * @param whid the whid to set
	 */
	public void setWhid(long whid) {
		this.whid = whid;
	}

	/**
	 * @return the reps
	 */
	public long getReps() {
		return reps;
	}

	/**
	 * @param reps the reps to set
	 */
	public void setReps(long reps) {
		this.reps = reps;
	}

	/**
	 * @return the weight
	 */
	public long getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(long weight) {
		this.weight = weight;
	}

	/**
	 * @return the cardiodistance
	 */
	public long getDis() {
		return cardiodistance;
	}

	/**
	 * @param cardiodistance the cardiodistance to set
	 */
	public void setDist(long cardiodistance) {
		this.cardiodistance = cardiodistance;
	}

	/**
	 * @return the cardioduration
	 */
	public long getur() {
		return cardioduration;
	}

	/**
	 * @param cardioduration the cardioduration to set
	 */
	public void setDur(long cardioduration) {
		this.cardioduration = cardioduration;
	}

	public String getWodName() {
		return wodName;
	}

	public void setWodName(String wodName) {
		this.wodName = wodName;
	}
	
}
