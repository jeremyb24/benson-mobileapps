package com.example.pedometerapp;

public class Goal {
	
	private int goal;
	private boolean isReached;
	
	public Goal() {
		
	}
	
	public void setGoal(int g) {
		goal = g;
	}
	
	public void setReached(boolean r) {
		isReached = r;
	}
	
	public int getGoal() {
		return goal;
	}
	
	public boolean isReached() {
		return isReached;
	}

}
