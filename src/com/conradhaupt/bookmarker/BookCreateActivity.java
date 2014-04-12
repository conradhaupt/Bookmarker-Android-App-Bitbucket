package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.conradhaupt.bookmarker.BookInformationFragment.BookInformationListener;
import com.conradhaupt.bookmarker.BookPositionFragment.BookPositionListener;
import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookCreateActivity extends Activity implements
		BookInformationListener, BookPositionListener {

	/* Variables */
	// Static variables
	public static final int REQUEST_UPDATE = 0;
	public static final int REQUEST_EDIT = 1;
	public static final int REQUEST_CREATE = 2;
	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_FAILURE = 1;
	public static final int RESULT_CANCELED = 2;
	public static final String MODE_IN_APP = "com.conradhaupt.bookmarker.MODE_IN_APP";

	// Dynamic variables
	private boolean isDoublePane = false;
	private boolean isInApp = false;
	private BookInformationFragment bookInformationFragment;
	private BookPositionFragment bookPositionFragment;
	private Book book;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_create);

		// Check if activity is in double pane mode
		if (this.findViewById(R.id.fragment_position) != null) {
			isDoublePane = true;
		}

		// Assign fragment variables
		openBookInformationFragment();
		if (isDoublePane) {
			bookPositionFragment = (BookPositionFragment) this
					.getFragmentManager().findFragmentById(
							R.id.fragment_position);
		}
		// Check if Activity is being launched as part of application
		Intent tempIntent = getIntent();
		if (tempIntent != null) {
			if (tempIntent.getBooleanExtra(MODE_IN_APP, false)) {
				System.out.println("Activity is in app");
				this.getActionBar().setDisplayHomeAsUpEnabled(true);
			}

			// Initialize Bundle Book
			initBundleBook(tempIntent);

		} else {
			System.out.println("Activity is not in app");
		}
	}

	@Override
	public void onBackPressed() {
		System.out.println("Back pressed");
		// If the activity is single pane and the second fragment is displaying
		// then return
		if (!isDoublePane
				&& this.getFragmentManager().getBackStackEntryCount() >= 2) {
			this.getFragmentManager().popBackStack();
			bookInformationFragment.setMenuVisibility(true);
			bookPositionFragment.setMenuVisibility(false);
			invalidateOptionsMenu();
			return;
		} else {
			this.setResult(RESULT_CANCELED);
			this.finish();
			return;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle home button being pressed
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		switch (item.getItemId()) {
		case R.id.activity_book_create_forward:
			this.onForwardPressed();
			break;
		case R.id.activity_book_create_cancel:
			this.onCancelPressed();
			break;
		case R.id.activity_book_create_save:
			this.onSavePressed();
			break;
		case R.id.activity_book_create_submit:
			this.onSubmitPressed();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onSubmitPressed() {
		// TODO add submit code
	}

	private void onSavePressed() {
		// TODO add save code
	}

	private void onCancelPressed() {
		// TODO add cancel code
	}

	private void onForwardPressed() {
		System.out.println("onForwardPressed running");
		// Handle doublepane actions
		if (!isDoublePane) {
			// Open book position fragment
			openBookPositionFragment();
		} else {
			System.out.println("ERROR: forward was pressed but is doublepane");
		}
	}

	private void openBookInformationFragment() {
		// Instantiate fragment if required
		if (bookInformationFragment == null) {
			bookInformationFragment = new BookInformationFragment();
		}

		// Add to fragment manager
		// Check if doesn't exist already
		if (this.getFragmentManager().getBackStackEntryCount() <= 1) {
			System.out.println("Adding fragment");
			this.getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.slide_in_left,
							R.animator.slide_out_left,
							R.animator.slide_in_right,
							R.animator.slide_out_right)
					.replace(R.id.fragment_container, bookInformationFragment)
					.addToBackStack("BookInformationFragment").commit();
			try {
				bookPositionFragment.setMenuVisibility(false);
			} catch (Exception e) {

			}
			bookInformationFragment.setMenuVisibility(true);
			invalidateOptionsMenu();
		}
	}

	private void openBookPositionFragment() {
		// Instantiate fragment if required
		if (bookPositionFragment == null) {
			bookPositionFragment = new BookPositionFragment();
		}

		// Add to fragment manager
		// Check if doesn't exist already
		if (this.getFragmentManager().getBackStackEntryCount() <= 1) {
			System.out.println("Adding fragment");
			this.getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.slide_in_left,
							R.animator.slide_out_left,
							R.animator.slide_in_right,
							R.animator.slide_out_right)
					.replace(R.id.fragment_container, bookPositionFragment)
					.addToBackStack("BookPositionFragment").commit();
			bookInformationFragment.setMenuVisibility(false);
			bookPositionFragment.setMenuVisibility(true);
			invalidateOptionsMenu();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_book_create, menu);
		return true;
	}

	private void initBundleBook(Intent intent) {
	}

	@Override
	public void onBookInformation(Book book) {
		System.out.println("Listener information run");
		// Process if book is null
		if (book == null)
			return;
		// Copy values to local version
		this.book.setAuthor(book.getAuthor());
		this.book.setName(book.getName());
		this.book.setIsbn(book.getIsbn());
		this.book.setPageCount(book.getPageCount());
	}

	@Override
	public void onBookPosition(Book book) {
		System.out.println("Listener position run");
		// Process if book is null
		if (book == null)
			return;
		// Copy values to local version
		this.book.setPage(book.getPage());
		this.book.setParagraph(book.getParagraph());
		this.book.setSentence(book.getSentence());
	}

	@Override
	public Book getBook() {
		return book;
	}

}
