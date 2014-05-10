package com.conradhaupt.bookmarker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.conradhaupt.bookmarker.BookBookmarksFragment.BookBookmarksInterface;
import com.conradhaupt.bookmarker.BookInformationFragment.BookInformationInterface;
import com.conradhaupt.bookmarker.sqlite.model.Book;
import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

public class BookCreateActivity extends FragmentActivity implements
		BookBookmarksInterface, BookInformationInterface {
	/* Variables */
	// Object Variables
	private Book aBook = null;

	// View Variables
	public int aCurrentThemeResourceID = 0;

	/* Methods */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPreCreate();
		setContentView(R.layout.activity_book_create);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu, this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initPreCreate() {
		// Process theme preference
		boolean inverse = PreferenceManager.getDefaultSharedPreferences(this)
				.getBoolean("preference_theme_colour_inverse", false);
		switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				this).getString("preference_theme_colour", "-100"))) {
		case -100:
			System.out.println("Theme preference not set, defaulting to Red");
		case 0:
			System.out.println("Setting theme as Blue");
			aCurrentThemeResourceID = inverse ? R.style.AppTheme_Blue_Inverse
					: R.style.AppTheme_Blue;
			break;
		case 1:
			System.out.println("Setting theme as Red");
			aCurrentThemeResourceID = inverse ? R.style.AppTheme_Red_Inverse
					: R.style.AppTheme_Red;
			break;
		case 2:
			System.out.println("Setting theme as Green");
			aCurrentThemeResourceID = inverse ? R.style.AppTheme_Green_Inverse
					: R.style.AppTheme_Green;
			break;
		default:
			break;
		}
		// Assign theme
		setTheme(aCurrentThemeResourceID);
	}

	/* Interface Methods and Classes */
	public interface BookCreateInformationInterface {
		public abstract Book getBook();
	}

	public interface BookCreateBookmarksInterface {
		public abstract ArrayList<Bookmark> getBookmarks();
	}

	@Override
	public Book onBookInformationGet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onBookInformationFinished(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Bookmark> onBookBookmarksGet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onBookBookmarksFinished(ArrayList<Bookmark> bookmarks) {
		// TODO Auto-generated method stub
		return false;
	}

}
