package com.amadt.fitrack.asynctasks;

public class Result {
	private boolean successful = false;
	private String message = "";

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
