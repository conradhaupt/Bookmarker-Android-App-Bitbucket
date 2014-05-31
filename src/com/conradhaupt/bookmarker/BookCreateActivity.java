package com.conradhaupt.bookmarker;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.conradhaupt.bookmarker.sqlite.helper.BookDatabaseHelper;
import com.conradhaupt.bookmarker.sqlite.model.Book;
import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

public class BookCreateActivity extends FragmentActivity {
	/* Variables */
	// Object Variables
	private Book aBook = null;
	private Bookmark aBookmark = null;
	private int mode = -1;

	// View Variables
	public int aCurrentThemeResourceID = 0;
	private Menu actionBarMenu = null;
	private ProgressDialog aProgressDialog;

	// Fragment Variables
	private BookInformationFragment fBookInformationFragment;
	private BookCreateInformationInterface fBookInformationInterface;
	private BookBookmarksFragment fBookBookmarksFragment;
	private BookCreateBookmarksInterface fBookBookmarksInterface;

	// Create Variables
	private BookCreateFragmentInterface fBookCreateInformationInterface;
	private BookCreateFragmentInterface fBookCreateBookmarksInterface;

	// Static Variables
	public static String MODE = "com.conradhaupt.bookmarker.MODE";
	public static String BOOK_ID = "com.conradhaupt.bookmarker.BOOK_ID";
	public static int MODE_CREATE = 0;
	public static int MODE_EDIT = 1;
	public static int MODE_OTHER = 2;
	public static final int CHANGE_ERROR = 0;
	public static final int CHANGE_UNKNOWN = 1;
	public static final int CHANGE_OK = -1;

	/* Methods */
	public static BookCreateActivity newInstance(Book book, Bookmark bookmark) {
		// Create and Initialize a new activity
		BookCreateActivity bookCreateActivity = new BookCreateActivity();

		// Assign values
		bookCreateActivity.setValues(book == null ? null : book,
				bookmark == null ? null : bookmark);

		// Return final activity
		return bookCreateActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPreCreate();
		setContentView(R.layout.activity_book_create);

		// Handle Home As Up
		// getActionBar().setDisplayHomeAsUpEnabled(false);
		// getActionBar().setHomeButtonEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		System.out.println("OnCreateOptionsMenu Run");
		// Inflate the menu, this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_book_create, menu);

		// Store menu
		this.actionBarMenu = menu;

		// Process mode
		processMode();

		// Return true
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Handle bundle
		handleBundle();

		// Initialize Fragments
		initFragments();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle back button
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}

		// Handle fragment menus
		if (fBookInformationFragment.onOptionsItemSelected(item)) {
			return true;
		} else if (fBookBookmarksFragment.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle activity menu items
		switch (item.getItemId()) {
		case R.id.activity_book_create_save:
		case R.id.activity_book_create_create:
			// Handle new book
			int result = confirmChange();
			switch (result) {
			case CHANGE_UNKNOWN:
				Toast.makeText(
						this,
						getResources().getString(
								R.string.activity_book_create_error_unknown),
						Toast.LENGTH_LONG).show();
				break;
			case CHANGE_ERROR:
				Toast.makeText(
						this,
						getResources().getString(
								R.string.activity_book_create_error),
						Toast.LENGTH_LONG).show();
				break;
			case CHANGE_OK:

				break;
			default:
				break;
			}
			break;
		case R.id.activity_book_create_cancel:
			this.finish();
			break;
		default:
			// Do nothing
		}
		return super.onOptionsItemSelected(item);
	}

	protected void setValues(Book book, Bookmark bookmark) {
		// Handle null values
		if (book == null) {
			System.out.println("Book is null");
			aBook = new Book();
		} else {
			aBook = book;
		}
		if (bookmark == null) {
			System.out.println("Bookmark is null");
			aBookmark = new Bookmark();
		} else {
			aBookmark = bookmark;
		}
	}

	private void handleBundle() {
		// Handle mode
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			this.mode = bundle.getInt(BookCreateActivity.MODE,
					BookCreateActivity.MODE_OTHER);
			if (bundle.getInt(BookCreateActivity.BOOK_ID, -1) != -1) {
				System.out.println("Retrieving book");
				// Get Book ID
				int bookId = bundle.getInt(BookCreateActivity.BOOK_ID, -1);

				// Initialize variables
				BookDatabaseHelper bookDatabaseHelper = new BookDatabaseHelper(
						getBaseContext());

				// Get book
				Book book = bookDatabaseHelper.getBook(bookId);

				// Assign bookmarks
				book.bookmarks = bookDatabaseHelper.getBookmarksForBook(bookId);

				// Set values
				setValues(book, book.bookmarks.get(0));
			} else {
				if (aBook == null || aBookmark == null) {
					setValues(null, null);
				}
			}
		}
	}

	private void processMode() {
		System.out.println("ActionBarMenu is "
				+ (actionBarMenu == null ? "NULL" : "NOT NULL"));
		// Process mode
		if (this.mode == BookCreateActivity.MODE_EDIT) {
			// Display the save actionBar menu item
			actionBarMenu.findItem(R.id.activity_book_create_save).setVisible(
					true);
			actionBarMenu.findItem(R.id.activity_book_create_cancel)
					.setVisible(true);
		} else if (this.mode == BookCreateActivity.MODE_CREATE) {
			// Display the confirm actionBar menu item
			actionBarMenu.findItem(R.id.activity_book_create_create)
					.setVisible(true);
		} else if (this.mode == BookCreateActivity.MODE_OTHER) {
			// TODO No actions for MODE_OTHER yet, add in if needed later
		} else {
			// Handle no mode assigned
			actionBarMenu.findItem(R.id.activity_book_create_create)
					.setVisible(true);
			actionBarMenu.findItem(R.id.activity_book_create_cancel)
					.setVisible(true);
		}
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

	private void updateFragmentValues() {
		System.out.println("aBook is " + (aBook == null ? "NULL" : "NOT NULL"));
		System.out.println("aBookmark is "
				+ (aBookmark == null ? "NULL" : "NOT NULL"));
		fBookInformationFragment.setValues(aBook == null ? new Book() : aBook);
		fBookBookmarksFragment.setValues(aBookmark == null ? new Bookmark()
				: aBookmark, aBook == null ? 10000 : aBook.getPageCount());
	}

	private void initFragments() {
		// Initialize fragments
		fBookInformationFragment = BookInformationFragment.newInstance();
		fBookBookmarksFragment = BookBookmarksFragment.newInstance();

		// Update Fragment Values
		updateFragmentValues();

		// Initialize interfaces
		fBookBookmarksInterface = (BookCreateBookmarksInterface) fBookBookmarksFragment;
		fBookInformationInterface = (BookCreateInformationInterface) fBookInformationFragment;
		fBookCreateBookmarksInterface = fBookBookmarksFragment;
		fBookCreateInformationInterface = fBookInformationFragment;

		// Assign fragments to window
		this.getFragmentManager()
				.beginTransaction()
				.add(R.id.activity_book_create_information_fragment,
						fBookInformationFragment)
				.add(R.id.activity_book_create_bookmarks_fragment,
						fBookBookmarksFragment).commit();
	}

	public void bookChangeResult(int result) {
		this.finish();
	}

	private int confirmChange() {
		// Check if the fragments are ready to proceed
		if (!(fBookCreateBookmarksInterface.canProceed() && fBookCreateInformationInterface
				.canProceed())) {
			System.out.println("Activity cannot Proceed");
			return CHANGE_ERROR;
		} else {
			System.out.println("Activity can Proceed");
		}

		// Retrieve values from fragments
		Book retrievedBook = fBookInformationInterface.getBook();
		Bookmark retrievedBookmark = fBookBookmarksInterface.getBookmark();

		// Handle null values
		if (retrievedBook == null || retrievedBookmark == null) {
			System.out.println("One of the retrieved values was null");
			return CHANGE_UNKNOWN;
		}

		// Process null progress dialog
		if (aProgressDialog == null) {
			aProgressDialog = new ProgressDialog(this);
		}

		// Set progress dialog OnDismissDialog
		aProgressDialog
				.setOnDismissListener(new ProgressDialog.OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						bookChangeResult(0);
					}
				});

		// Display progress dialog
		aProgressDialog.show();

		// Update book and bookmark
		aBook = fBookInformationInterface.getBook();
		aBookmark = fBookBookmarksInterface.getBookmark();

		new AsyncTask<Context, Integer, String>() {
			/* Variables */
			// Object Variables
			private Context context;

			/* Methods */
			@Override
			protected String doInBackground(Context... params) {
				// Retrieve objects from array
				context = params[0];

				// Open up database helper
				BookDatabaseHelper bookDatabaseHelper = new BookDatabaseHelper(
						context);

				// Create database

				// Handle creating a new book
				try {
					if (aBook.getId() == -1) {
						System.out.println("Inserting new book into database");
						// Assign bookID to bookmark and create book
						aBookmark.setBookId(bookDatabaseHelper
								.createBook(aBook).getId());
						// Create bookmark
						System.out
								.println("Inserting new bookmark into database");
						bookDatabaseHelper.createBookmark(aBookmark);
					} else if (aBook.getId() >= 0) {
						// Update book and bookmark
						System.out.println("Updating book");
						bookDatabaseHelper.updateBook(aBook);
						aProgressDialog.setProgress(50);
						System.out.println("Updating bookmark");
						bookDatabaseHelper.updateBookmark(aBookmark);
						aProgressDialog.setProgress(100);
					}
					try {
						aProgressDialog.setMax(5000);
						double time = System.currentTimeMillis();
						while ((System.currentTimeMillis() - time) < 5000) {
							aProgressDialog.setProgress((int) (System
									.currentTimeMillis() - time));
						}
					} catch (Exception e) {

					}
				} catch (Exception e) {
					System.out.println(e);
				}

				// Return final value
				return null;
			}

			protected void onPostExecute(String result) {
				aProgressDialog.dismiss();
			}

		}.execute(this);

		// All went as planned, return true
		return CHANGE_OK;
	}

	/* Interface Methods. and Classes */
	public interface BookCreateInformationInterface {
		public abstract Book getBook();
	}

	public interface BookCreateBookmarksInterface {
		public abstract Bookmark getBookmark();
	}

	public interface BookCreateFragmentInterface {
		public abstract boolean canProceed();
	}
}
