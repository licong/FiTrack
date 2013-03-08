package com.amadt.fitrack.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.amadt.fitrack.R;
import com.amadt.fitrack.database.Exercise;
import com.amadt.fitrack.database.ExerciseManager;
import com.amadt.fitrack.database.WOD;
import com.amadt.fitrack.database.WODManager;
import com.amadt.fitrack.model.AlertManager;

public class CreateCustomWODActivity extends Activity {
	
	private Context myContext;
	private Activity myActivity;
	private ArrayList<String> exerListItems = new ArrayList<String>();
	private ArrayAdapter<String> listAdapter;
	//Used in WODListAdapter
	private PopupWindow popupWindow = null;
	private TextView pwRepsText = null;
	private TextView pwWeightText = null;
	private TextView pwDistanceText = null;
	List<Exercise> mExercises = new ArrayList<Exercise>();;
	Exercise currentExercise;
	
	/**
	 * Allows use of wod_list_item in a ListView
	 * @author Phillip Loppo
	 *
	 */
	private class WODListAdapter extends ArrayAdapter<String> {		
		private Context context;
		
		public WODListAdapter(Context context, ArrayList<String> values) {
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
		setContentView(R.layout.create_custom_wod);
		this.myContext = this;
		this.myActivity = this;
		

		
		//Buttons
		Button add = (Button)findViewById(R.id.add);
		Button submit = (Button)findViewById(R.id.submitButton);
		 
		//Prompts
		final EditText wodAdd = (EditText)findViewById(R.id.edittext);
		final EditText descAdd = (EditText)findViewById(R.id.description);
		final EditText addition = (EditText) findViewById(R.id.autocomplete_exercises);
		 
		//Add Exercise TextView
		AutoCompleteTextView exerTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_exercises);
		
		//Get and populate a Sting array of the Exercise names
		ExerciseManager em = new ExerciseManager(myContext);
		List<Exercise> exercises = em.getAllExercises();
		Iterator<Exercise> exerIt = exercises.iterator();
		ArrayList<String> exerNames = new ArrayList<String>();
		while(exerIt.hasNext()) {
			Exercise current = exerIt.next();
			exerNames.add(current.getName());
		}
		ArrayAdapter<String> textAdapter = new ArrayAdapter<String>(myContext, R.layout.exercise_list, exerNames);
		exerTextView.setAdapter(textAdapter);
	
		//Added Exercise ListView
		ListView exerListView = (ListView) findViewById(R.id.exerlist);
		//listAdapter = new ArrayAdapter<String>(myContext, R.layout.exercise_list, exerListItems);
		listAdapter = new WODListAdapter(this.getBaseContext(),exerListItems);
		exerListView.setAdapter(listAdapter);		
		
		//Add exercise to list (add button)
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String ename = addition.getText().toString();
				StringTokenizer s = new StringTokenizer(ename, " ");
				if(!(s.hasMoreElements())) {
					//If String is spaces or nothing
					String text = "Must enter an exercise";
		        	AlertManager.alert(myContext, text);
		        	addition.setText("");
		        }
				else {
					ExerciseManager em = new ExerciseManager(myContext);
					//Check if that exercise exists
					if(!(em.getExercise(ename).getName() == null)) {
						addExercise(ename);
	        			addition.setText("");
					}
					else {
						AlertManager.alert(myContext, "Exercise " + ename + " doesn't exist");
					}
        		}
			}
		});
		
		//Set rep,weight and/or distance value for each exercise passed in
		exerListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
		    	   
		    	currentExercise = mExercises.get(position);
		    	showPopup(position);
		     }
		});
		  
		
		//Submit WOD with name, description and exercise list
		submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v)  {
				String wodName = wodAdd.getText().toString();
				String description = descAdd.getText().toString();
				StringTokenizer wodToken = new StringTokenizer(wodName, " ");
        		boolean exerEmpty = true;
            		
        		//Check if there are exercises
        		if(exerListItems.size() > 0)
            		exerEmpty = false;
        		
        		//Error checking and appropriate error messages  TODO: Might need to handle better
        		if(!(wodToken.hasMoreElements()) && exerEmpty) {
        			wodAdd.setText("");
        			AlertManager.alert(myContext, "Need to add custom WOD name and exercises");
        		}
        		else if(!(wodToken.hasMoreElements())) {
            		wodAdd.setText("");
            		AlertManager.alert(myContext, "Need to add custom WOD name");
            	}
            	else if(exerEmpty) {
            		AlertManager.alert(myContext, "No Exercises Added!");
            	}
        		//If tests passed, add to database
            	else {
            		//If no description given, send in the empty string
            		if(description == null) 
            			description = "";
            		
            		WODManager wm = new WODManager(myContext);
            		WOD newWod = wm.createCustomWOD(wodName, description, null);
            		Iterator<String> exNames = exerListItems.iterator();
            		for(int i = 0; exNames.hasNext(); i++) {
            			String currentName = exNames.next();
            			wm.addExerciseToCustomWOD(newWod.getName(), currentName, mExercises.get(i).getReps(), 
            																	 mExercises.get(i).getWeight(), 
            																	 mExercises.get(i).getDistance());
            		}
            		AlertManager.alertAndFinish(myContext, myActivity, "Custom WOD " + wodName + " created");
            	}
	        }
		});
	}    
	
	/**
	 * Add Exercise to list but not database yet
	 */
	public void addExercise(String ename) {
		exerListItems.add(ename);
		ExerciseManager em = new ExerciseManager(myContext);
		Log.d("Name: ", em.getExercise(ename).getName());
		mExercises.add(em.getExercise(ename));
		listAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Remove Exercise from list
	 */
	public void removeExercise(int pos) {
		exerListItems.remove(pos);
		mExercises.remove(pos);
		listAdapter.notifyDataSetChanged();
	}
	
	/**
	 * If an exercise is clicked, opens a popup that modifies the exercise values
	 */
	private void showPopup(final int pos) {
		if (currentExercise == null) {
			Log.e("FiTrack", "currentExercise must not be null");
			return;
		}
		
		//Strength Popup
		if(currentExercise.getType().equals(Exercise.TYPE_STRENGTH)) {
		    try {
		    	Log.i("FiTrack", "launching strength popup");
		        //We need to get the instance of the LayoutInflater, use the context of this activity
		        LayoutInflater inflater = (LayoutInflater) CreateCustomWODActivity.this
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        //Inflate the view from a predefined XML layout
		        View layout = inflater.inflate(R.layout.create_strength_popup,
		                (ViewGroup) findViewById(R.id.create_strength_popup_element));
		        // create a 300px width and 470px height PopupWindow
		        
		        Rect size = new Rect();
		        getWindow().getDecorView().getWindowVisibleDisplayFrame(size);
		        
		        
		        int width = (int)((double)size.width() * 0.9f);
		        int height = (int)((double)size.height() * 0.9f);
		        
		        popupWindow = new PopupWindow(layout, width, height, true);
		        // display the popup in the center
		        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
		        
		        //Set the exercise name and description in the popup
		        TextView nameField = (TextView)layout.findViewById(R.id.exercise_name_field);
		        TextView descriptionField = (TextView)layout.findViewById(R.id.description_textview);
		        nameField.setText(mExercises.get(pos).getName());
		        descriptionField.setText(mExercises.get(pos).getDescription());

		        //Reps and Weight fields
		        pwRepsText = (TextView)layout.findViewById(R.id.reps_field);
		        pwWeightText = (TextView)layout.findViewById(R.id.weight_field);
		        if(mExercises.get(pos).getReps() != 0)
			        pwRepsText.setText(""+mExercises.get(pos).getReps());
		        else
		        	pwRepsText.setText("0");
		        if(mExercises.get(pos).getWeight() != 0)
			        pwWeightText.setText(""+mExercises.get(pos).getWeight());
		        else
		        	pwWeightText.setText("0");
		        
		        //Remove Button - Remove this exercise from the list 
		        Button removeButton = (Button)layout.findViewById(R.id.remove_button);
		        removeButton.setOnClickListener(new View.OnClickListener() {
		        	public void onClick(View v) {
		        		removeExercise(pos);
		        		popupWindow.dismiss();
		        	}
		        });

		        //Save the changes made to reps and weight
		        Button saveButton = (Button)layout.findViewById(R.id.save_button);
		        saveButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						int weight = pwWeightText == null? 0 : Integer.parseInt(pwWeightText.getText().toString());
						int reps = pwRepsText == null? 0 : Integer.parseInt(pwRepsText.getText().toString());
						
						mExercises.get(pos).setWeight(weight);
						mExercises.get(pos).setReps(reps);
						listAdapter.notifyDataSetChanged();
						popupWindow.dismiss();
					}
				});
		 
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		//Cardio prompt
		else if(currentExercise.getType().equals(Exercise.TYPE_CARDIO)) {
		    try {
		    	Log.i("FiTrack", "launching cardio popup");
		        //We need to get the instance of the LayoutInflater, use the context of this activity
		        LayoutInflater inflater = (LayoutInflater) CreateCustomWODActivity.this
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        //Inflate the view from a predefined XML layout
		        View layout = inflater.inflate(R.layout.create_cardio_popup,
		                (ViewGroup) findViewById(R.id.create_cardio_popup_element));
		        // create a 300px width and 470px height PopupWindow
		        
		        Rect size = new Rect();
		        getWindow().getDecorView().getWindowVisibleDisplayFrame(size);
		        
		        
		        int width = (int)((double)size.width() * 0.9f);
		        int height = (int)((double)size.height() * 0.9f);
		        
		        popupWindow = new PopupWindow(layout, width, height, true);
		        // display the popup in the center
		        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
		        
		        //Set the exercise name and description in the popup
		        TextView nameField = (TextView)layout.findViewById(R.id.exercise_name_field);
		        TextView descriptionField = (TextView)layout.findViewById(R.id.description_textview);
		        nameField.setText(mExercises.get(pos).getName());
		        descriptionField.setText(mExercises.get(pos).getDescription());

		        //Distance field
		        pwDistanceText = (TextView)layout.findViewById(R.id.distance_field);
		        if(mExercises.get(pos).getDistance() != 0)
			        pwDistanceText.setText(""+mExercises.get(pos).getDistance());
		        else
		        	pwDistanceText.setText("0");
		        
		        //Remove Button - Remove exercise from list
		        Button removeButton = (Button)layout.findViewById(R.id.remove_button);
		        removeButton.setOnClickListener(new View.OnClickListener() {
		        	public void onClick(View v) {
		        		removeExercise(pos);
		        		popupWindow.dismiss();
		        	}
		        });

		        //Change the distance value of exercise
		        Button saveButton = (Button)layout.findViewById(R.id.save_button);
		        saveButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						int distance = pwDistanceText == null? 0 : Integer.parseInt(pwDistanceText.getText().toString());

						mExercises.get(pos).setDistance(distance);
						listAdapter.notifyDataSetChanged();
						popupWindow.dismiss();
					}
				});
		 
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
}