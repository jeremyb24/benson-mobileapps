package com.example.bensontodolist;

public class Task {
	// Fields to Model Task Object
	private int taskId;
	private boolean priority;
	private String date;
	private String task;
	private boolean isCompleted;
	
	// Constructor
	public Task() {
		
	}
	
	// Accessors and Modifiers
	public void setTaskId(int id) {
		taskId = id;
	}
	
	public void setPriority(boolean p) {
		priority = p;
	}
	
	public void setDate(String d) {
		date = d;
	}
	
	public void setTask(String t) {
		task = t;
	}
	
	public void setCompleted(boolean c) {
		isCompleted = c;
	}
	
	// Accessors
	public int getId() {
		return taskId;
	}
	
	public boolean getPriority() {
		return priority;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTaskDetails() {
		return task;
	}
	
	public boolean getCompleted() {
		return isCompleted;
	}
	
	// Provides a String of the Class
	public String toString() {
		String result = "";
		
		if (priority) {
			result += "Priority: ";
		}
		
		result += task + " " + date;
		
		return result;
	}

} // end class Task
