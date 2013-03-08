package com.amadt.fitrack.model;

import com.amadt.fitrack.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BoxArrayAdapter extends ArrayAdapter<Box> {		
	private final Context context;
	private final Box[] values;
	
	public BoxArrayAdapter(Context context, Box[] values) {
		super(context, R.layout.box_list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.box_list_item, parent, false);

		TextView boxNameField = (TextView) rowView.findViewById(R.id.box_name_field);
		boxNameField.setText(values[position].getName());
		
		TextView boxDescriptionField = (TextView) rowView.findViewById(R.id.box_description_field);
		boxDescriptionField.setText(values[position].getDescription());

		return rowView;
	}
};
