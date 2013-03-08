package com.amadt.fitrack.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.amadt.fitrack.R;
import com.amadt.fitrack.database.Exercise;
import com.amadt.fitrack.database.ExerciseHistory;
import com.amadt.fitrack.database.ExerciseHistoryManager;
import com.amadt.fitrack.database.ExerciseManager;
import com.amadt.fitrack.database.WOD;
import com.amadt.fitrack.database.WODHistory;
import com.amadt.fitrack.database.WODHistoryManager;
import com.amadt.fitrack.database.WODManager;
import com.amadt.fitrack.model.AlertManager;
import com.amadt.fitrack.model.SimpleTimer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WODViewActivity extends Activity {
	private Context myContext;
	List<Exercise> mExercises;
	ListView mListView;
	TextView mWodNameTextView;
	TextView mTimerTextView;
	
	String mUserNotes = "";

	private static final int TIMER_STARTED = 1;
	private static final int TIMER_STOPPED = 0;

	ArrayList<ExerciseHistory> mExerciseHistory = new ArrayList<ExerciseHistory>();
	Exercise currentExercise = null;
	
	private int timerState = TIMER_STOPPED;
	
	WOD mWod = null;
	
	WodTimer wodTimerTask = new WodTimer();
	SimpleTimer wodTimer = new SimpleTimer();
	
	private PopupWindow popupWindow = null;
	private TextView pwRepsText = null;
	private TextView pwWeightText = null;
	private TextView pwDistanceText = null;
	
	private final BroadcastReceiver onBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
        	wodTimer.update();
        	mTimerTextView.setText(wodTimer.toString());
        }
    };
	
	private class WodTimer extends TimerTask {
		@Override
		public void run() {
			getApplicationContext().sendBroadcast(new Intent("Update UI"));
		}	
	}
	
	private class WODListAdapter extends ArrayAdapter<String> {		
		private final Context context;
		public WODListAdapter(Context context, String[] values) {
			super(context, R.layout.wod_list_item, values);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.wod_list_item, parent, false);
			
			Exercise exercise = mExercises.get(position);
			
			if(exercise == null)
				return rowView;
			
			TextView exerciseNameTextView = (TextView)rowView.findViewById(R.id.wod_listitem_name);
			exerciseNameTextView.setText(exercise.getName());
			
			TextView exerciseDescriptionTextView = (TextView)rowView.findViewById(R.id.wod_listitem_description);
			exerciseDescriptionTextView.setText(exercise.getDescription());
			
			TextView infoTextView = (TextView)rowView.findViewById(R.id.wod_listitem_info);
			String info = "";
			
			if (position % 2 == 0)
				rowView.setBackgroundColor(Color.LTGRAY);
			
			if (exercise.getType().equals(Exercise.TYPE_STRENGTH)) {
				Log.i("FiTrack", exercise.getType());
				info = String.format("weight: %d\n", exercise.getWeight());
				info += String.format("reps: %d", exercise.getReps());
			}
			else if (exercise.getType().equals(Exercise.TYPE_CARDIO)) {
				info = String.format("Dist: %d", exercise.getDistance());
			}
			
			infoTextView.setText(info);
	
			return rowView;
		}
	};
	
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.wod_view_layout2);
	  myContext = this;
	  
	  WODManager wm = new WODManager(this);
	  Intent intent = getIntent();
	  mWod = wm.getWOD(intent.getStringExtra("WOD_NAME"));
	  mExercises = wm.getAllExercises(intent.getStringExtra("WOD_NAME"));
	  
	  setupViews();
	  setupButtons();

	  registerReceiver(onBroadcast, new IntentFilter("Update UI"));
	  Timer timer = new Timer(false);
	  timer.schedule(wodTimerTask, 0, 50);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		unregisterReceiver(onBroadcast);
	}

	private void setupViews() {
		TextView descriptionTextVIew = (TextView)findViewById(R.id.description_textview);
		descriptionTextVIew.setText(mWod.getDescription());

		mTimerTextView = (TextView)findViewById(R.id.timer_textview);
		  
		 mListView = (ListView) findViewById(R.id.wod_listview);
		 mListView.setTextFilterEnabled(true);

		 Object[] exer = mExercises.toArray();
		 String[] exercises = new String[exer.length];
		 for(int i = 0; i < exer.length; i++)
			  exercises[i] = exer[i].toString();
		  
		 mWodNameTextView = (TextView)findViewById(R.id.wodview_name);
		 mWodNameTextView.setText(getIntent().getStringExtra("WOD_NAME"));
		  
		 mListView.setAdapter(new WODListAdapter(this.getBaseContext(), exercises));
	}

	private void setupButtons() {
		  mListView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {		    	  
		    	currentExercise = mExercises.get(position);
		    	showPopup(position);
		      }
		  	});
		  
		  Button timerButton = (Button)findViewById(R.id.timer_button);
		  timerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				timerButtonPressed((Button)v);
			}
		});
		  
		  Button saveButton = (Button)findViewById(R.id.save_button);
		  saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveWod();
			}
		});
	}
	
	protected void saveWod() {
		if (timerState == TIMER_STARTED) {
			// Stop the timer
			Button timerButton = (Button)findViewById(R.id.timer_button);
			timerButtonPressed(timerButton);
		}
		
		WODHistoryManager wodHistoryManager = new WODHistoryManager(this);
		WODHistory history = wodHistoryManager.addWODToHistory(mWod.getName(), 
				   											   System.currentTimeMillis(), 
															   wodTimer.getElapsed(), 
															   mUserNotes);
		
		ExerciseHistoryManager exerciseHistoryManager = new ExerciseHistoryManager(this);
		for (ExerciseHistory ex : mExerciseHistory) {
			exerciseHistoryManager.addExerciseToHistory(ex.getWodName(), history.getId(), ex.getReps(), ex.getWeight(), ex.getDis(), 0 /*unused*/);
		}

		AlertManager.alertAndFinish(myContext, WODViewActivity.this, mWod.getName() + " has been completed with a time of "
				                                          + ((double)(history.getDuration()/1000))%60 + " seconds");
	}

	private void showStrengthPopup(final int position) {
	    try {
	    	Log.i("FiTrack", "launching strength popup");
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) WODViewActivity.this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.strength_popup,
	                (ViewGroup) findViewById(R.id.strength_popup_element));
	        // create a 300px width and 470px height PopupWindow
	        
	        Rect size = new Rect();
	        getWindow().getDecorView().getWindowVisibleDisplayFrame(size);
	        
	        
	        int width = (int)((double)size.width() * 0.9f);
	        int height = (int)((double)size.height() * 0.667f);
	        
	        popupWindow = new PopupWindow(layout, width, height, true);
	        // display the popup in the center
	        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

	        pwRepsText = (TextView)layout.findViewById(R.id.reps_field);
	        pwWeightText = (TextView)layout.findViewById(R.id.weight_field);

	        pwRepsText.setText("" + currentExercise.getReps());
	        pwWeightText.setText("" + currentExercise.getWeight());
	        
	        TextView nameTextView = (TextView)layout.findViewById(R.id.exercise_name_field);
	        nameTextView.setText(currentExercise.getName());
	        TextView descriptionTextView = (TextView)layout.findViewById(R.id.description_textview);
	        descriptionTextView.setText(currentExercise.getDescription());
	        
	        Button cancelButton = (Button)layout.findViewById(R.id.cancel_button);
	        cancelButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					cancelPopup(position);
				}
			});

	        Button saveButton = (Button)layout.findViewById(R.id.save_button);
	        saveButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					saveExercise(position);
				}
			});
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void showCardioPopup(final int position) {
	    try {
	    	Log.i("FiTrack", "launching cardio popup");
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) WODViewActivity.this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.cardio_popup,
	        (ViewGroup) findViewById(R.id.cardio_popup_element));
	        
	        Rect size = new Rect();
	        getWindow().getDecorView().getWindowVisibleDisplayFrame(size);
	        
	        int width = (int)((double)size.width() * 0.9f);
	        int height = (int)((double)size.height() * 0.667f);
	        
	        popupWindow = new PopupWindow(layout, width, height, true);
	        // display the popup in the center
	        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

	        pwDistanceText = (TextView)layout.findViewById(R.id.distance_field);

	        pwDistanceText.setText("" + currentExercise.getDistance());
	        
	        TextView nameTextView = (TextView)layout.findViewById(R.id.exercise_name_field);
	        nameTextView.setText(currentExercise.getName());
	        
	        TextView descriptionTextView = (TextView)layout.findViewById(R.id.description_textview);
	        descriptionTextView.setText(currentExercise.getDescription());
	        
	        Button cancelButton = (Button)layout.findViewById(R.id.cancel_button);
	        cancelButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					cancelPopup(position);
				}
			});

	        Button saveButton = (Button)layout.findViewById(R.id.save_button);
	        saveButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					saveExercise(position);
				}
			});
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void showPopup(final int position) {
		if (currentExercise == null) {
			Log.e("FiTrack", "currentExercise must not be null");
			return;
		}
		
		if (currentExercise.getType().equals(Exercise.TYPE_STRENGTH)) {
			showStrengthPopup(position);
		}
		else if (currentExercise.getType().equals(Exercise.TYPE_CARDIO)) {
			showCardioPopup(position);
		}
	}
	
	private void cancelPopup(int position) {
		if (popupWindow == null)
			return;
		
		popupWindow.dismiss();
	}
	
	private void saveExercise(int position) {
		if (popupWindow == null)
			return;
		
		if (currentExercise == null)
			return;

		int distance = pwDistanceText == null? 0 : Integer.parseInt(pwDistanceText.getText().toString());
		int weight = pwWeightText == null? 0 : Integer.parseInt(pwWeightText.getText().toString());
		int reps = pwRepsText == null? 0 : Integer.parseInt(pwRepsText.getText().toString());
		
		ExerciseHistory ex = new ExerciseHistory();
		ex.setDist(distance);
		ex.setReps(reps);
		ex.setWeight(weight);
		ex.setEid(currentExercise.getId());
		ex.setWodName(currentExercise.getName());
		mExerciseHistory.add(ex);

		removeExerciseAtPosition(position);
		updatePorgressText();

		popupWindow.dismiss();
	}
	
	private void removeExerciseAtPosition(int position) {
		 if (position >= mExercises.size()) {
			 Log.e("FiTrack", "position out of bounds");
			 return;
		 }
		 
		 mExercises.remove(position);
		 
		 Object[] exer = mExercises.toArray();
		 String[] exercises = new String[exer.length];
		 for(int i = 0; i < exer.length; i++)
			  exercises[i] = exer[i].toString();
		  
		 mWodNameTextView = (TextView)findViewById(R.id.wodview_name);
		 mWodNameTextView.setText(getIntent().getStringExtra("WOD_NAME"));
		  
		 mListView.setAdapter(new WODListAdapter(this.getBaseContext(), exercises));
	}
	
	private void updatePorgressText() {
		ExerciseManager manager = new ExerciseManager(this);
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		for (ExerciseHistory exh : mExerciseHistory) {
			Exercise ex = manager.getExercise(exh.getEid());
			if (ex == null)
				continue;
			
			String name = ex.getName();
			
			if ( map.get(name) == null ) {
				map.put(name, "");
			}
			
			String progressText = map.get(name);
			
			if (ex.getType().equals(Exercise.TYPE_STRENGTH)) {
				progressText += " " + exh.getWeight() + "x" + exh.getReps();
			}
			else if (ex.getType().equals(Exercise.TYPE_CARDIO)) {
				progressText += " " + exh.getDis();
			}
			
			map.put(name, progressText);
		}
		
		String progressText = "Progress:\n";
		
		for (String key : map.keySet()) {
			progressText += key + map.get(key) + "\n";
		}
		
		TextView progressTextView = (TextView)findViewById(R.id.progress_textview);
		progressTextView.setText(progressText);
	}
	
	private void timerButtonPressed(Button button) {
		final String restartText = "Restart";
		final String stopText = "Stop";
		
		if (timerState == TIMER_STARTED) {
			Log.i("FiTrack",  button.getText().toString() + " Stop button pressed");
			timerState = TIMER_STOPPED;
			button.setText(restartText);
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
			wodTimer.stop();
		}
		else {
			Log.i("FiTrack",  button.getText().toString() + "Start button pressed");
			timerState = TIMER_STARTED;
			button.setText(stopText);
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_button));
			wodTimer.start();
		}
	}
}
