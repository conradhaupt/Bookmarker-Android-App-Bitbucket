package com.conradhaupt.bookmarker.ui;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class BookFragmentOnItemLongClickListener implements
		OnItemLongClickListener {
	/* Variables */
	// Object Variables
	private Context context;

	// Static Variables

	/* Methods */
	public BookFragmentOnItemLongClickListener(Context context) {
		this.context = context;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> viewGroup, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
