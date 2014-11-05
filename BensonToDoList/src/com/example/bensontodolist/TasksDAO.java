package com.example.bensontodolist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TasksDAO {
	// Fields
	private SQLiteDatabase database;
	private TasksSQLiteHelper dbHelper;
	private String [] allColumns = {
			TasksSQLiteHelper.COLUMN_ID,
			TasksSQLiteHelper.COLUMN_PRIORITY,
			TasksSQLiteHelper.COLUMN_DATE,
			TasksSQLiteHelper.COLUMN_TASK,
			TasksSQLiteHelper.COLUMN_COMPLETED};
	
	// Constructor
	public TasksDAO(Context context) {
		dbHelper = new TasksSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Task createTask(Task t) {
		ContentValues values = new ContentValues();
		// Place t data into values object
		// Handle boolean values
		int priority = 0;
		if (t.getPriority()) {
			priority = 1;
		}
		
		int done = 0;
		if (t.getCompleted()) {
			done = 1;
		}
		
		values.put(TasksSQLiteHelper.COLUMN_PRIORITY, priority);
		values.put(TasksSQLiteHelper.COLUMN_DATE, t.getDate());
		values.put(TasksSQLiteHelper.COLUMN_TASK, t.getTaskDetails());
		values.put(TasksSQLiteHelper.COLUMN_COMPLETED, done);
		
		long insertId = database.insert(TasksSQLiteHelper.TABLE_TASKS, null, values);
		
		Cursor cursor = database.query(TasksSQLiteHelper.TABLE_TASKS, allColumns,
				TasksSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		
		cursor.moveToLast();
		Task newTask = cursorToTask(cursor);
		cursor.close();
		
		return newTask;
	}
	
	public void deleteTask(Task t) {
		int id = t.getId();
		database.delete(TasksSQLiteHelper.TABLE_TASKS,
				TasksSQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public void editTask(Task t) {
		ContentValues values = new ContentValues();
		int id = t.getId();
		
		int priority = 0;
		if (t.getPriority()) {
			priority = 1;
		}
		
		int done = 0;
		if (t.getCompleted()) {
			done = 1;
		}
		
		values.put(TasksSQLiteHelper.COLUMN_PRIORITY, priority);
		values.put(TasksSQLiteHelper.COLUMN_DATE, t.getDate());
		values.put(TasksSQLiteHelper.COLUMN_TASK, t.getTaskDetails());
		values.put(TasksSQLiteHelper.COLUMN_COMPLETED, done);
		
		database.update(TasksSQLiteHelper.TABLE_TASKS, values, TasksSQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Task> getAllTasks() {
		List <Task> taskList = new ArrayList<Task>(0);
		
		Cursor cursor = database.query(TasksSQLiteHelper.TABLE_TASKS, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			Task task = cursorToTask(cursor);
			taskList.add(task);
			cursor.moveToNext();
		}
		
		return taskList;
		
	}
	
	private Task cursorToTask(Cursor cursor) {
		
		int id = cursor.getInt(0);
		int priority = cursor.getInt(1);
		String date = cursor.getString(2);
		String taskString = cursor.getString(3);
		int complete = cursor.getInt(4);
		
		Task t = new Task();
		
		t.setTaskId(id);
		t.setPriority(priority == 1);
		t.setDate(date);
		t.setTask(taskString);
		t.setCompleted(complete == 1);
		
		return t;
		
	}
	
	public Task getTaskById(int id) {
		// Create a cursor
		Cursor cursor = database.query(TasksSQLiteHelper.TABLE_TASKS, allColumns,
				TasksSQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
		return (cursor.moveToFirst()) ? cursorToTask(cursor) :null;
	}
	
} // end class TasksDAO

