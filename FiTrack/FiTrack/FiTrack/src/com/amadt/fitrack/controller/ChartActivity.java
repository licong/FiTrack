package com.amadt.fitrack.controller;



import com.amadt.fitrack.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChartActivity extends Activity {
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.graph);
        Button graphButton;
	    graphButton = (Button) findViewById(R.id.graphButton);
	    graphButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Perform action on click
	        lineGraphHandler(v);
	        }
	    });
  }
  public void lineGraphHandler(View view){
	  GraphLine line = new GraphLine("Fran");
	  Intent lineIntent = line.getWODHistoryIntent(this, "Fran");
	 startActivity(lineIntent);
	  
	 
  }
  
}