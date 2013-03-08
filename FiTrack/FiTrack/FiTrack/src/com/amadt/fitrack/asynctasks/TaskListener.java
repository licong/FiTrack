package com.amadt.fitrack.asynctasks;

public interface TaskListener<T> {
	public void onPreExecute();
	
	public void onPostExecute(T result);
}
