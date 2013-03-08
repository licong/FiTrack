package com.amadt.fitrack.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amadt.fitrack.R;
import com.amadt.fitrack.database.ScheduledWODManager;
import com.amadt.fitrack.database.WOD;
import com.amadt.fitrack.database.WODManager;

public class CalendarActivity extends Activity implements OnClickListener {
	private static final String tag = "SimpleCalendarViewActivity";

	private ImageView prevMonth;
	private Button currentMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	
	/*ADDED BY MATT*/
	private ListView listview;
	@SuppressWarnings("rawtypes")
	private ArrayAdapter listadapter;
	private ImageView removeItem;
	private Button dayView;
	private ImageView addItem;
	private ArrayList<String> dayList;
	private final int MONTH_CHANGE = 0;
	private final int DAY_CHANGE = 1;
	private String selectedDate = "";
	private boolean onStart = true;
	
	private Date DBDate;
	/*-----------*/
	
	private Calendar calendar;
	private int month, year;
	private final DateFormat dateFormatter = new DateFormat();
	private static final String monthYearTemplate = "MMMM yyyy";
	@SuppressWarnings("unused")
	private static final String monthTemplate = "MMMM";

	/** Called when the activity is first created. */
	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_calendar_view);
		
		calendar = Calendar.getInstance(Locale.getDefault());
		month = calendar.get(Calendar.MONTH) + 1;
		year = calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: " + year);

		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (Button) this.findViewById(R.id.currentMonth);
		currentMonth.setText(dateFormatter.format(monthYearTemplate, calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);
		
		/*ADDED BY MATT*/
		adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		
		removeItem = (ImageView) this.findViewById(R.id.removeItem);
		removeItem.setOnClickListener(this);
		
		dayView = (Button) this.findViewById(R.id.dayView);
		dayView.setText("Scheduled items for " + selectedDate + ", " + year);
		
		addItem = (ImageView) this.findViewById(R.id.addItem);
		addItem.setOnClickListener(this);

		dayList = new ArrayList<String>();
		
		listview = (ListView) this.findViewById(R.id.task_list);
		listadapter = new ArrayAdapter<String>(this, R.layout.calendar_events_list, dayList);
		listview.setAdapter(listadapter);
		listview.setTextFilterEnabled(true);
		
        
        ColorDrawable gold = new ColorDrawable(this.getResources().getColor(R.drawable.gold));
        listview.setDivider(gold);
        listview.setDividerHeight(2);
        
		listview.setOnItemClickListener(new OnItemClickListener() {
	         public void onItemClick(AdapterView<?> parent, View view,
	             int position, long id) {
	           // When clicked, show a toast with the TextView text
	        	 view.setSelected(true);
	           Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	               Toast.LENGTH_SHORT).show();
	         }
	       });
		/*-------------------*/

		calendarView.setAdapter(adapter);
	}

	/**
	 * 
	 * @param month
	 * @param year
	 */
	@SuppressWarnings("static-access")
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
		calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(dateFormatter.format(monthYearTemplate, calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	public void onClick(View v) {
		if (v == prevMonth) {
			if (month <= 1) {
				
				month = 12;
				year--;
			}
			
			else {
				month--;
			}
			
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
			updateListHeader(MONTH_CHANGE);
		}
		
		if (v == nextMonth) {
			
			if (month > 11) {
				month = 1;
				year++;
			}
			
			else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
			updateListHeader(MONTH_CHANGE);
		}
		
		/*ADDED BY MATT */
		
		if (v == addItem) {
			listPrompt('a');
		}
		
		if (v == removeItem) {
			listPrompt('r');
		}
		
		/*---------------*/
		
	}

	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View ...");
		super.onDestroy();
	}
	
	/*ADDED BY MATT*/
	public void updateListHeader(int key) {
		if (key == MONTH_CHANGE) {
			dayView.setText("No day selected");
		}
		
		if (key == DAY_CHANGE) {
			dayView.setText("Scheduled items for " + selectedDate + ", " + year);
			ScheduledWODManager swm = new ScheduledWODManager(this);
			List<WOD> wodList = swm.getWODsForADay(DBDate.getTime());
			   Iterator<WOD> wodIt = wodList.iterator();
			   ArrayList<String> wodNames = new ArrayList<String>();
			   
			   
			   while(wodIt.hasNext()) {
				   WOD current = wodIt.next();
				   wodNames.add(current.getName());
			   }
			   
			   listadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.calendar_events_list, wodNames);
			   listview.setAdapter(listadapter);
		}
	}
	
	private void listPrompt(char field) {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		
		/*REMOVE ROUTINE*/
		if (field == 'r') {
			
			final ScheduledWODManager swm = new ScheduledWODManager(this);
			
			List<WOD> wodList = swm.getWODsForADay(DBDate.getTime());
			   Iterator<WOD> wodIt = wodList.iterator();
			   ArrayList<String> wodNames = new ArrayList<String>();
			   while(wodIt.hasNext()) {
				   WOD current = wodIt.next();
				   wodNames.add(current.getName());
			   }
			   
			final String[] templistitems = new String[wodNames.size()];
			final ArrayList<Boolean> isChecked = new ArrayList<Boolean>(templistitems.length);
			
			
			for (int i = 0; i < templistitems.length; i++) {
				templistitems[i] = wodNames.get(i);
				Log.d(tag, templistitems[i]);
				isChecked.add(i, false);
			}
			
			
			alt_bld.setIcon(R.drawable.remove)
				   .setTitle("Select items to remove from: " + selectedDate)
				   .setMultiChoiceItems(templistitems, null, new DialogInterface.OnMultiChoiceClickListener() {
					   public void onClick(DialogInterface dialog, int item, boolean arg2) {
						   // TODO Auto-generated method stub
						   
						   while (true) {
							   if (isChecked.get(item) == false) {
								   isChecked.set(item, true);
								   Log.d(tag, "Success1");
								   break;
							   }
							   
							   if (isChecked.get(item) == true) {
								   isChecked.set(item, false);
								   Log.d(tag, "Success2");
								   break;
							   }
						   }
					   }
				   })
				   .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int id) {
						   //COMPLETELY REMOVE ITEMS FROM THE LIST
						   List<WOD> wodList = swm.getWODsForADay(DBDate.getTime());
						   Iterator<WOD> wodIt = wodList.iterator();
						   ArrayList<String> wodNames = new ArrayList<String>();
						   while(wodIt.hasNext()) {
							   WOD current = wodIt.next();
							   wodNames.add(current.getName());
						   }
						   
						   for (int i = 0; i < isChecked.size(); i++) {
							   while (isChecked.get(i) == true) {
								   swm.cancelWOD(wodNames.get(i), DBDate.getTime());
								   wodNames.remove(i);
								   isChecked.remove(i);
								   wodNames.trimToSize();
								   isChecked.trimToSize();
								   
								   if (i >= isChecked.size()) {
									   break;
								   }	   
							   }  
						   }
						   
						   /*wodList = swm.getWODsForADay(DBDate.getTime());
						   wodIt = wodList.iterator();
						   //wodNames = new ArrayList<String>();
						   for(int i = 0; wodIt.hasNext(); i++) {
							   WOD current = wodIt.next();
							   wodNames.add(current.getName());
						   }*/
						   
						   listadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.calendar_events_list, wodNames);
						   listview.setAdapter(listadapter);
					   }
				   })
				   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int id) {
						   //GET RID OF THE WINDOW
						   dialog.cancel();
					   }
				   });
		} 
		
		/*ADD ROUTINE*/
		if (field == 'a') {
			
			WODManager WOD = new WODManager(this);
			List<WOD> WODList = WOD.getAllWODs();
			Iterator<WOD> WODIt = WODList.iterator();
			final ScheduledWODManager swm = new ScheduledWODManager(this);
			final String[] templistitems = new String[WODList.size()];
			final ArrayList<Boolean> isChecked = new ArrayList<Boolean>(templistitems.length);
			
			for (int i = 0; WODIt.hasNext(); i++) {
				WOD current = WODIt.next();
				templistitems[i] = current.getName();
				isChecked.add(i, false);
			}
			
			alt_bld.setIcon(R.drawable.add)
			   .setTitle("Select items to add to: " + selectedDate)
			   .setMultiChoiceItems(templistitems, null, new DialogInterface.OnMultiChoiceClickListener() {
				   public void onClick(DialogInterface dialog, int item, boolean arg2) {
					   // TODO Auto-generated method stub
					   
					   while (true) {
						   if (isChecked.get(item) == false) {
							   isChecked.set(item, true);
							   Log.d(tag, "Success1");
							   break;
						   }
						   
						   if (isChecked.get(item) == true) {
							   isChecked.set(item, false);
							   Log.d(tag, "Success2");
							   break;
						   }
					   }
					   
				   }
			   })
			   .setPositiveButton("Add", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   //ADD THINGS TO THE LIST
					   for (int i = 0; i < isChecked.size(); i++) {
						   if (isChecked.get(i) == true) {
							   //dayList.add(templistitems[i]); //TODO: maybe remove
						   	   swm.scheduleWOD(templistitems[i], DBDate.getTime());
						   }
					   }
					   List<WOD> wodList = swm.getWODsForADay(DBDate.getTime());
					   Iterator<WOD> wodIt = wodList.iterator();
					   ArrayList<String> wodNames = new ArrayList<String>();
					   
					   while(wodIt.hasNext()) {
						   WOD current = wodIt.next();
						   wodNames.add(current.getName());
					   }
					   
					   listadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.calendar_events_list, wodNames);
					   listview.setAdapter(listadapter);
				   }
			   })
			   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   //GET RID OF THE WINDOW
					   dialog.cancel();
				   }
			   });
		}
		
		AlertDialog alert = alt_bld.create();
		alert.show();
	}
	/*--------------*/

	// ///////////////////////////////////////////////////////////////////////////////////////
	// Inner Class
	public class GridCellAdapter extends BaseAdapter implements OnClickListener {
		private static final String tag = "GridCellAdapter";
		private final Context _context;
		private final List<String> list;
		private static final int DAY_OFFSET = 1;
		private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		@SuppressWarnings("unused")
		private final int month, year;
		@SuppressWarnings("unused")
		private int daysInMonth, prevMonthDays;
		private int currentDayOfMonth;
		private int currentWeekDay;
		private Button gridcell;
		private TextView num_events_per_day;
		private final HashMap<String, Integer> eventsPerMonthMap;
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		// Days in Current Month
		public GridCellAdapter(Context context, int textViewResourceId, int month, int year) {
			super();
			this._context = context;
			this.list = new ArrayList<String>();
			this.month = month;
			this.year = year;

			Log.d(tag, "==> Passed in Date FOR Month: " + month + " " + "Year: " + year);
			Calendar calendar = Calendar.getInstance();
			setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
			Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
			Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
			Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

			// Print Month
			printMonth(month, year);

			// Find Number of Events
			eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
		}
		
		private String getMonthAsString(int i) {
			return months[i];
		}

		private String getWeekDayAsString(int i) {
			return weekdays[i];
		}

		private int getNumberOfDaysOfMonth(int i) {
			return daysOfMonth[i];
		}

		public String getItem(int position) {
			return list.get(position);
		}

		public int getCount() {
			return list.size();
		}

		/**
		 * Prints Month
		 * 
		 * @param mm
		 * @param yy
		 */
		private void printMonth(int mm, int yy) {
			Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
			// The number of days to leave blank at
			// the start of this month.
			int trailingSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			int currentMonth = mm - 1;
			String currentMonthName = getMonthAsString(currentMonth);
			daysInMonth = getNumberOfDaysOfMonth(currentMonth);

			Log.d(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");

			// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
			GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
			Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

			if (currentMonth == 11) {
				prevMonth = currentMonth - 1;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
				Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
			}
			
			else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
				Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
			}
			else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
			}

			// Compute how much to leave before before the first day of the
			// month.
			// getDay() returns 0 for Sunday.
			int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;

			Log.d(tag, "Week Day:" + currentWeekDay + " is " + getWeekDayAsString(currentWeekDay));
			Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
			Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

			if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
				++daysInMonth;
			}

			// Trailing Month days
			for (int i = 0; i < trailingSpaces; i++) {
				Log.d(tag, "PREV MONTH:= " + prevMonth + " => " + getMonthAsString(prevMonth) + " " + String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i));
				list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {
				Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
				
				if (i == getCurrentDayOfMonth()) {
					list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
				}
				
				else {
					list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
				}
			}

			// Leading Month days
			for (int i = 0; i < list.size() % 7; i++) {
				Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
				list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
			}
		}

		/**
		 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
		 * ALL entries from a SQLite database for that month. Iterate over the
		 * List of All entries, and get the dateCreated, which is converted into
		 * day.
		 * 
		 * @param year
		 * @param month
		 * @return
		 */
		private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month) {
			return  new HashMap<String, Integer>();
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);
			}

			// Get a reference to the Day gridcell
			gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
			gridcell.setOnClickListener(this);

			// ACCOUNT FOR SPACING

			Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
			String[] day_color = list.get(position).split("-");
			String theday = day_color[0];
			String themonth = day_color[2];
			String theyear = day_color[3];
			if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
				if (eventsPerMonthMap.containsKey(theday)) {
					num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
					Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
					num_events_per_day.setText(numEvents.toString());
				}
			}

			// Set the Day GridCell
			gridcell.setText(theday);
			gridcell.setTag(theday + "-" + themonth + "-" + theyear);
			Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-" + theyear);

			if (day_color[1].equals("GREY")) {
				gridcell.setTextColor(Color.LTGRAY);
			}
			
			if (day_color[1].equals("WHITE")) {
				gridcell.setTextColor(Color.WHITE);
			}
			
			if (day_color[1].equals("BLUE")) {
				gridcell.setTextColor(getResources().getColor(R.color.static_text_color));
				if (onStart) {
					gridcell.performClick();
					onStart = false;
				}
			}
			
			return row;
		}
		
		public void onClick(View view) {
			String date_month_year = (String) view.getTag();
			
			Log.d(tag, date_month_year);
			
						
			/*Calendar day button implementation*/
			try {
				Date parsedDate = dateFormatter.parse(date_month_year);
				DBDate = parsedDate;
				Log.d(tag, "Parsed Date: " + parsedDate.toString());
				
				selectedDate = getShortDayInfo(parsedDate);
				
			} catch (ParseException e) {	
				e.printStackTrace();
			}
			
			updateListHeader(DAY_CHANGE);
			
		}

		public int getCurrentDayOfMonth() {
			return currentDayOfMonth;
		}

		private void setCurrentDayOfMonth(int currentDayOfMonth) {
			this.currentDayOfMonth = currentDayOfMonth;
		}
		
		public void setCurrentWeekDay(int currentWeekDay) {
			this.currentWeekDay = currentWeekDay;
		}
		
		public int getCurrentWeekDay() {
			return currentWeekDay;
		}
		
		/* ADDED BY MATT */
		public String getShortDayInfo(Date d) {
			//Only read the first 3 tokens, cut the rest out
			String[] longDate = d.toString().split("\\s");
			String shortDate = "";
			for (int i = 0; i <= 2; i++) {
				if (i < 2)
					shortDate += longDate[i] + " ";
				else
					shortDate += longDate[i];
			}
			return shortDate;
		}
		/*----------------*/
	}	
}
