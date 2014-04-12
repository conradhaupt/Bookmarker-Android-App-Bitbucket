/**
 * 
 */
package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookInformationFragment extends Fragment implements
		NumberPicker.OnValueChangeListener {

	/* Variables */
	private BookInformationListener bookInformationListener = null;
	private NumberPicker pageCountPicker = null;
	private Book book;
	// View Variables
	EditText bTitle;
	EditText bISBN;
	EditText bAuthor;
	NumberPicker bPageCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_book_information, container,
				false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_book_information, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
		// switch (item.getItemId()) {
		// default:
		// return this.getActivity().onOptionsItemSelected(item);
		// }
	}

	@Override
	public void onResume() {
		initPicker();
		super.onResume();
	}

	@Override
	public void onStart() {
		System.out.println("onStart run");
		this.book = new Book();
		this.bTitle = (EditText) this.getActivity().findViewById(
				R.id.fragment_book_information_edittext_title);
		this.bISBN = (EditText) this.getActivity().findViewById(
				R.id.fragment_book_information_edittext_isbn);
		this.bAuthor = (EditText) this.getActivity().findViewById(
				R.id.fragment_book_information_edittext_author);
		this.bPageCount = (NumberPicker) this.getActivity().findViewById(
				R.id.fragment_book_information_picker_pagecount);
		super.onStart();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			bookInformationListener = (BookInformationListener) activity;
			System.out.println("BookInformationListener assigned");
			this.book = bookInformationListener.getBook();
			processNewBook();
		} catch (Exception e) {
			System.out
					.println("Was not a compatible activity for BookInformationListener");
		}
	}

	@Override
	public void onStop() {
		// Process listener
		processOldBook();
		super.onStop();
	}

	@Override
	public void onDetach() {
		bookInformationListener = null;
		super.onDetach();
	}

	public interface BookInformationListener {
		public void onBookInformation(Book book);

		public Book getBook();
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		if (oldVal == 1 && newVal != 1) {
			TextView title = (TextView) this.getActivity().findViewById(
					R.id.fragment_book_position_textview_paragraph);
			title.setText(this.getResources().getString(
					R.string.fragment_book_information_page_title_2));
		} else {
			if (newVal == 1) {
				TextView title = (TextView) this.getActivity().findViewById(
						R.id.fragment_book_position_textview_paragraph);
				title.setText(this.getResources().getString(
						R.string.fragment_book_information_page_title));
			}
		}
	}

	private void processNewBook() {
		bTitle.setText(this.book.getName());
		bISBN.setText(this.book.getIsbn());
		bAuthor.setText(this.book.getAuthor());
		bPageCount.setValue(this.book.getPageCount());
	}

	private void processOldBook() {
		// Assign new values to book
		this.book.setName(bTitle.getText().toString());
		this.book.setIsbn(bISBN.getText().toString());
		this.book.setAuthor(bAuthor.getText().toString());
		this.book.setPageCount(bPageCount.getValue());
		// Call book to listener
		try {
			this.bookInformationListener.onBookInformation(this.book);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void initPicker() {
		System.out.println("initPicker run");
		// Instantiate pageCountPicker if needed
		pageCountPicker = (NumberPicker) this.getActivity().findViewById(
				R.id.fragment_book_information_picker_pagecount);
		// Assign it values
		pageCountPicker.setMaxValue(this.getResources().getInteger(
				R.integer.maxPageCount));
		pageCountPicker.setMinValue(1);
		pageCountPicker.setWrapSelectorWheel(false);
		pageCountPicker.setOnValueChangedListener(this);
	}

}
