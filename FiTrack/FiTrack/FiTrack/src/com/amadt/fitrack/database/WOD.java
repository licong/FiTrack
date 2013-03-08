package com.amadt.fitrack.database;


import java.util.*;

public class WOD {
	private long _id;
	private String name;
	private String type;
	private String description;
	private String video;
	private List<Exercise> exer;
	
	/**
	 * Get the id of the workout
	 * @return The id of the workout
	 */
	public long getId() {
		return _id;
	}
	
	/**
	 * Set the id of the workout
	 * @param _id The id we wish to set
	 */
	public void setId(long _id) {
		this._id = _id;
	}
	
	/**
	 * Get the name of the workout
	 * @return The name of the workout
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the workout
	 * @param name The name of the workout
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the type of the workout
	 * @return The type of the workout
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Set the type of the workout
	 * @param type The type of the workout
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * get the workout description
	 * @return The workout description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the workout description
	 * @param description The description of the workout
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get the video path of the workout
	 * @return The video path of the workout
	 */
	public String getVideo() {
		return video;
	}
	
	/**
	 * Set the video path of the workout
	 * @param video The video path of the workout
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	public List<Exercise> getExercises() {
		return exer;
	}

	public void setExercises(List<Exercise> exer) {
		this.exer = exer;
	}
}