package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookCreateActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.book_preferences);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_book_create, menu);
		return true;
	}

	/* Classes */
	public class BookProperty {
		/* Variables */
		private int type = 0;
		private Object value;
		private String name = "";

		// Static Variables
		public static final int TYPE_SECTION = -2;
		public static final int TYPE_HEADING = -1;
		public static final int TYPE_VALUE = 0;

		public BookProperty() {
		}
	}

	public class BookCreateAdapter extends ArrayAdapter<BookProperty> {

		/* Variables */
		public BookProperty[] listElements;

		public BookCreateAdapter(Context context, int resource,
				int textViewResourceId, BookProperty[] objects) {
			super(context, resource, textViewResourceId, objects);
			listElements = objects;
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Check if the view needs to be instantiated
			if (convertView == null) {

			}
			return super.getView(position, convertView, parent);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
}
