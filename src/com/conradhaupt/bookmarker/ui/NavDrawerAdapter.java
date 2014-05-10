package com.conradhaupt.bookmarker.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NavDrawerAdapter<T> extends ArrayAdapter<T> {

	/* Variables */
	// New Variables
	private int currentPosition = 0;

	public NavDrawerAdapter(Context context, int resource, T[] objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View tempView = super.getView(position, convertView, parent);
		if (position == currentPosition) {
			TextView title = (TextView) tempView;
			title.setTypeface(null, Typeface.BOLD);
		} else {
			TextView title = (TextView) tempView;
			title.setTypeface(null, Typeface.NORMAL);
		}
		return tempView;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
		this.notifyDataSetChanged();
	}

}
