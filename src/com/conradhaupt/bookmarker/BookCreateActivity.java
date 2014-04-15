package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BookCreateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_create);
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

		public BookCreateAdapter(Context context, int resource,
				int textViewResourceId, BookProperty[] objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return super.getView(position, convertView, parent);
		}

	}
}
