package com.example.pedometerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Pedometer extends Activity {
	
	private Goal goal;
	
	private EditText editGoal;
	private TextView textGoal;
	private Button buttonGoals;
	
	// Display Fields for Accelerometer
	private TextView textViewX;
	private TextView textViewY;
	private TextView textViewZ;
	
	// Display Field for Sensitivity
	private TextView textSensitive;
	
	// Display for Steps
	private TextView textViewSteps;
	
	// Reset Button
	private Button buttonReset;
	
	// Sensor Manager
	private SensorManager sensorManager;
	private float acceleration;
	
	// Values to Calculate Number of Steps
	private float previousY;
	private float currentY;
	private int numSteps;
	
	// SeekBar Fields
	private SeekBar seekBar;
	private int threshold; // Point at which we want to trigger a 'step'

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screenPedometer();
		
		goal = new Goal();
		goal.setReached(false);
		
		// Initialize Values
		previousY = 0;
		currentY = 0;
		numSteps = 0;
		
		// initialize acceleration Values
		acceleration = 0.00f;
		
		// Enable the listener
		enableAccelerometerListening();
		
	} // end Method onCreate()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pedometer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void enableAccelerometerListening() {
		// Initialize the Sensor Manager
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private SensorEventListener sensorEventListener = 
			new SensorEventListener()
	{

		@Override
		public void onSensorChanged(SensorEvent event) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			currentY = y;
			
			if ( Math.abs(currentY - previousY) > threshold ) {
				numSteps++;
				textViewSteps.setText(String.valueOf(numSteps));
			}
			
			textViewX.setText(String.valueOf(x));
			textViewY.setText(String.valueOf(y));
			textViewZ.setText(String.valueOf(z));
			
			previousY = y;
			
			int steps = Integer.valueOf(textViewSteps.getText().toString());
			if (steps == goal.getGoal()) {
				String goals = String.valueOf(goal.getGoal());
				textGoal.setText("Congratulations! You reached your goal of " + goals + " steps!");
				goal.setReached(true);
			}
			
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	
};

	public void resetSteps(View v) {
		numSteps = 0;
		textViewSteps.setText(String.valueOf(numSteps));
		
		if (goal.isReached() == true) {
			textGoal.setVisibility(View.GONE);
			buttonGoals.setVisibility(View.VISIBLE);
			goal.setGoal(99999999);
		}
		goal.setReached(false);
	}
	
	private OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			threshold = seekBar.getProgress();
			textSensitive.setText(String.valueOf(threshold));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public void setGoal(View v) {
		screenGoals();
	}
	
	private void screenGoals() {
		setContentView(R.layout.activity_set_goals);
		
		editGoal = (EditText)findViewById(R.id.editGoal);
	}
	
	public void btnBackClicked(View v) {
		String goals = editGoal.getText().toString();
		int g = Integer.valueOf(goals);
		goal.setGoal(g);
		screenPedometer();
		buttonGoals.setVisibility(View.GONE);
		textGoal.setVisibility(View.VISIBLE);
		textGoal.setText("Current Goal: " + goals);
	}
	
	private void screenPedometer() {
		setContentView(R.layout.activity_pedometer);
		
		// Attach objects to XML View
		textViewX = (TextView)findViewById(R.id.textViewX);
		textViewY = (TextView)findViewById(R.id.textViewY);
		textViewZ = (TextView)findViewById(R.id.textViewZ);
						
		// Attach Step and Sensitive View Objects to XML
		textViewSteps = (TextView)findViewById(R.id.textSteps);
		textSensitive = (TextView)findViewById(R.id.textSensitive);
						
		// Attach the resetButton to XML
		buttonReset = (Button)findViewById(R.id.buttonReset);
						
		// Attach the seekBar to XML
		seekBar = (SeekBar)findViewById(R.id.seekBar);
		
		textGoal = (TextView)findViewById(R.id.textGoal);
		
		buttonGoals = (Button)findViewById(R.id.buttonGoals);
		
		// Set the Values on the seekBar, threshold, and threshold display
		seekBar.setProgress(10);
		seekBar.setMax(20);
		seekBar.setOnSeekBarChangeListener(seekBarListener);
		threshold = 10;
		textSensitive.setText(String.valueOf(threshold));
				
	}

}
