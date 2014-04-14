package com.conradhaupt.bookmarker;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;

public class BookCreateActivity extends PreferenceActivity {

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

}
