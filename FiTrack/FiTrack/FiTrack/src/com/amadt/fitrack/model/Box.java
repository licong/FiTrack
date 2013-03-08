package com.amadt.fitrack.model;

public class Box {
	private int remoteId;
	private String id;
	private String name;
	private String description;
	private String scheduledWod;
	
	public int getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(int remoteId) {
		this.remoteId = remoteId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getScheduledWod() {
		return scheduledWod;
	}
	public void setScheduledWod(String scheduledWod) {
		this.scheduledWod = scheduledWod;
	}
}
