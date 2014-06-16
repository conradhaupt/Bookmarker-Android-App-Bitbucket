package com.conradhaupt.bookmarker;

import com.conradhaupt.bookmarker.ui.ActivityThemeSetter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * A simple {@link FragmentActivity} subclass. Use the {@link #MODE} tag in the
 * intent. Refer to {@link #BOOK_ID} for information on editing a book.
 * 
 */
public class BookCreateActivity extends FragmentActivity {
	/* Variables */
	// Object Variables
	/**
	 * This is the mode of the activity. The possible values are
	 * {@link #MODE_CREATE} to create an entry, {@link #MODE_EDIT} to edit a
	 * current entry or {@link #MODE_VIEW} to view a current entry. If no value
	 * is supplied in the {@link Intent} then {@link #MODE_VIEW} is assumed to
	 * be the value.
	 */
	private int mode = MODE_VIEW;

	/**
	 * This is the instance of {@link ActivityThemeSetter} that helps with
	 * setting the activities theme based off of the apps global settings.
	 */
	public ActivityThemeSetter aActivityThemeSetter = new ActivityThemeSetter();

	/**
	 * This is the book id to be used only if {@link #BOOK_ID} is defined in the
	 * {@link Intent} and {@link #mode} is set to either {@link #MODE_EDIT} or
	 * {@link #MODE_VIEW}. If the value is not changed from -1 then the activity
	 * will treat {@link #mode} as if set to {@link #MODE_CREATE}.
	 */
	private long bookID = -1;

	/**
	 * This is whether the current activity is doublePaned, and has both
	 * fragments open at the same time, or is singlePaned, and so would have the
	 * ViewPager active.
	 */
	private boolean isDoublePaned = false;

	/**
	 * This is the activitys instance of
	 * {@link com.conradhaupt.bookmarker.BookInformationFragment}
	 */
	private BookInformationFragment aBookInformationFragment;

	// Static Variables
	/**
	 * This is the activities tag for logs.
	 */
	public static final String TAG = "com.conradhaupt.bookmarker.BookCreateActivity.LOG";

	/**
	 * This is the tag to be used to supply the {@link #mode} in the
	 * {@link Intent}
	 */
	public static final String MODE = "com.conradhaupt.bookmarker.BookCreateActivity.MODE";

	/**
	 * The constant value to define the activity as creating an entry. Used with
	 * {@link #onCreate} with the tag {@link #mode} in the {@link Intent}.
	 */
	public static final int MODE_CREATE = 0;

	/**
	 * The constant value to define the activity as editing an entry. Used with
	 * {@link #onCreate} with the tag {@link #MODE} in the {@link Intent}.
	 */
	public static final int MODE_EDIT = 1;

	/**
	 * The constant value to define the activity as viewing an entry. Used with
	 * {@link #onCreate} with the tag {@link #MODE} in the {@link Intent}.
	 */
	public static final int MODE_VIEW = 2;

	/**
	 * This is the tag to be used to supply the {@link #bookID} in the
	 * {@link Intent}. This will only be used IF {@link #mode} is set to
	 * {@link #MODE_EDIT} and thus is to edit the entry. This will be ignored if
	 * it is set to {@link #MODE_CREATE}.
	 */
	public static final String BOOK_ID = "com.conradhaupt.bookmarker.BookCreateActivity.BOOK_ID";

	/* Methods */
	/**
	 * This method is called when the activity is first created. It handles the
	 * bundle assigned to it which should have a value with the tag
	 * {@link #MODE}. Refer to {@link #mode} for more. If the activity has been
	 * restarted, due to a configuration change etc..., then
	 * {@link #handleSavedInstance} will be called to process the saved
	 * information.<br>
	 * <br>
	 * Calls {@link #initPreCreate}, {@link #handleFragments}.<br>
	 * Calls {@link #handleSavedInstance} only if the activity has been
	 * restarted.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call super method
		super.onCreate(savedInstanceState);

		// Initialize anything that needs to be created before the layout is
		// inflated
		this.initPreCreate();

		// Inflate layout

		// Check for doublePane and handle fragments
		this.handleFragments();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Is called by {@link #onCreate} and calls {@link #handleBundle} and
	 * {@link #handleTheme}
	 */
	private void initPreCreate() {
		// Handle bundle
		this.handleBundle();

		// Handle theme
		this.handleTheme();
	}

	/**
	 * This method is called by {@link #onCreate}. It process the activity's
	 * theme and assigns it based off of the {@link SharedPreferences} for the
	 * app. It uses the {@link #aActivityThemeSetter} variable to assign the
	 * theme.
	 */
	private void handleTheme() {
		// Assign theme
		aActivityThemeSetter.updateTheme(this);
	}

	/**
	 * This method is called by {@link #onCreate}. It processes the activities
	 * intent to determine the layout and functionality. This method could
	 * change the value of {@link #mode} depending on the intent given. It will
	 * also change the value of {@link #mode} if no value is assigned to
	 * {@link #BOOK_ID}.
	 */
	private void handleBundle() {
		// Get the intent for the activity
		Intent intent = getIntent();

		// Retrieve values from intent
		try {
			mode = intent.getIntExtra(MODE, MODE_VIEW);

			// Handle no bookID if MODE = MODE_EDIT or MODE_VIEW
			if (mode == MODE_EDIT || mode == MODE_VIEW) {
				bookID = intent.getLongExtra(BOOK_ID, bookID);

				// Handle no value given
				if (bookID == -1) {
					mode = MODE_CREATE;
					Log.e(TAG,
							"Mode was set to MODE_CREATE as no value was given for bookID");
				} else {
					Log.d(TAG, "Mode was set to "
							+ (mode == MODE_VIEW ? "MODE_VIEW"
									: (mode == MODE_EDIT ? "MODE_EDIT"
											: "uh... nothing?"))
							+ " and a bookID was set to " + bookID);
				}
			} else {
				Log.d(TAG, "Mode was set to MODE_CREATE");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error during intent and bundle processing:\n" + e);
		}

		// If the activity was rotated or the configuration changed then
		// retrieve the previous values
		// TODO add in code
	}

	/**
	 * 
	 */
	private void handleFragments() {
		// TODO Auto-generate method stub
	}

	/**
	 * This is called by {@link #setMode} only if the mode value changed. The
	 * contentView is changed here and all processes are altered to incorporate
	 * the new functionality.
	 */
	private void onModeChanged() {
		// TODO Auto-generated method stub
	}

	/**
	 * Change the current mode of the activity. This will call
	 * {@link #onModeChanged} if the mode has changed from its original value.
	 * If the original value was {@link MODE_CREATE} then the mode cannot be
	 * changed as the book has not been "created" yet.
	 * 
	 * @param mode
	 *            Can be either {@link #MODE_VIEW}, {@link #MODE_CREATE} or
	 *            {@link #MODE_EDIT}. If the value is neither of the three then
	 *            {@link #MODE_VIEW} will be set.
	 */
	public void setMode(int mode) {
		// Check if mode has changed
		if (this.mode != mode && this.mode != MODE_CREATE) {
			// Set new mode
			if (mode == MODE_EDIT) {
				Log.d(TAG, "Mode was set to MODE_EDIT");
			} else if (mode == MODE_VIEW) {
				Log.d(TAG, "Mode was set to MODE_VIEW");
			}
			// Call onModeChanged
			this.onModeChanged();
		}
	}
	/* Classes and Interfaces */

}
