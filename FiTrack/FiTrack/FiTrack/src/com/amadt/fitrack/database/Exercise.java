package com.amadt.fitrack.database;

public class Exercise {
	private long _id;
	private String name;
	private String type;
	private String description;
	private String video;
	private boolean isOlympic;
	private int reps = 0;
	private int weight = 0;
	private int distance = 0;

	public static final String TYPE_CARDIO = "Cardio";
	public static final String TYPE_STRENGTH = "Strength";
	public static final String TYPE_CUSTOM = "Custom";
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the video path
	 */
	public String getVideo() {
		return video;
	}
	/**
	 * @param video the video path to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}
	/**
	 * @return the isOlympic
	 */
	public boolean isOlympic() {
		return isOlympic;
	}
	/**
	 * @param isOlympic the isOlympic to set
	 */
	public void setOlympic(int olympic) {
		if(olympic == 1)
			this.isOlympic = true;
		else
			this.isOlympic = false;
	}
	
	/**
	 * @return The number of reps if this exercise is associated with a WOD
	 */
	public int getReps() {
		return reps;
	}
	
	/**
	 * Set the number of reps for this exercise
	 * @param reps The number of reps
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}
	
	/**
	 * @return The proposed weight for this exercise when associated with a WOD
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Set the amount of weight for this exercise
	 * @param weight The weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * @return The proposed distance for this exercise when associated with a WOD
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Set the distance for this exercise
	 * @param distance The distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Used to pass between intents
	 */
	public String toString() {
		return name;
	}
}
