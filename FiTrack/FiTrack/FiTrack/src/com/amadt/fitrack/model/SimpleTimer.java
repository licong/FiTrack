package com.amadt.fitrack.model;

public class SimpleTimer {
	long lastTick = 0;
	long elapsed = 0;
	
	boolean stopped = true;
	
	public void start(long elapsed) {
		lastTick = System.currentTimeMillis();
		this.elapsed = elapsed;
		stopped = false;
	}
	
	public void start() {
		start(0);
	}
	public void resume() {
		start(elapsed);
	}
	
	public void stop() {
		stopped = true;
	}
	
	public void update() {
		if (stopped == false) {
			long tick = System.currentTimeMillis();
			elapsed += tick - lastTick;
			lastTick = tick;
		}
	}
	
	public void reset() {
		elapsed = 0;
		stopped = true;
	}
	
	public long getElapsed() {
		return elapsed;
	}
	
	public long getMilli() {
		return elapsed % 1000;
	}
	
	public long getSeconds() {
		return (elapsed / 1000) % 60;
	}
	
	public long getMinutes() {
		return (elapsed / 1000 / 60) % 100;
	}
	
	public String toString() {
		return String.format("%02d:%02d.%03d", getMinutes(), getSeconds(), getMilli());
	}
}
