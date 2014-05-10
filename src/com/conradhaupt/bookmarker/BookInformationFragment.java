package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.conradhaupt.bookmarker.BookCreateActivity.BookCreateInformationInterface;
import com.conradhaupt.bookmarker.backend.ISBNChecker;
import com.conradhaupt.bookmarker.backend.ISBNChecker.ISBN;
import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookInformationFragment extends Fragment implements
		BookCreateInformationInterface {
	/* Variables */
	// Object Variables
	private BookInformationInterface fBookInformationInterface = null;
	private Book fBook = null;

	// View Variables
	private EditText fTitleEditText;
	private EditText fAuthorEditText;
	private EditText fISBNEditText;

	// Static Variables

	/* Methods */
	public static BookInformationFragment newInstance() {
		BookInformationFragment fragment = new BookInformationFragment();
		return fragment;
	}

	public BookInformationFragment() {
		// Required empty public constructor
	}

	@Override
	public void onAttach(Activity activity) {
		// Assign interface variables
		try {
			fBookInformationInterface = (BookInformationInterface) activity;
			fBook = fBookInformationInterface.onBookInformationGet();
		} catch (Exception e) {
			System.out
					.println("Activity attached to does not implement interface for BookInformationFragment");
		}
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		// Nullify interface variables
		fBookInformationInterface.onBookInformationFinished(getBook());
		fBookInformationInterface = null;

		super.onDetach();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_book_information, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// Initialize views
		initViewVariables();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_book_information, menu);
	}

	private void updateBook() {
		// Assign new values
		fBook.setTitle(fTitleEditText.getText().toString());
		fBook.setAuthor(fAuthorEditText.getText().toString());
		fBook.setIsbn(fISBNEditText.getText().toString());
	}

	private void initViewVariables() {
		// Initialise the view variables
		fTitleEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_title_edittext);
		fAuthorEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_author_edittext);
		fISBNEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_isbn_edittext);

		// Add formatter for ISBNEditText
		fISBNEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// If focus has been gained then remove all formatting for easy
				// editing
				System.out.println("onFocusChange Run");
				if (hasFocus) {
				} else {
					// If focus has been lost then format accordingly
					// Store isbn number
					String isbnNumber = fISBNEditText.getText().toString();
					System.out.println("Lost Focus");

					// Check if anything is there, if not end method
					if (isbnNumber.equals("")) {
						System.out
								.println("Nothing inside EditText, removing error");
						fISBNEditText.setError(null);
						return;
					}

					System.out.println("Current ISBN number is not empty");
					fISBNEditText.setError(null);
					// Process formatting
					try {
						// Check if the isbn number is valid
						if (ISBNChecker.isISBN(ISBNChecker
								.convertToISBNCharSequence(isbnNumber))) {
							// The ISBN number conforms with standards and is
							// valid
							System.out.println("ISBN Number "
									+ ISBNChecker
											.convertToISBNCharSequence(isbnNumber)
									+ " conforms with standards");
						} else {
							// The ISBN number conforms with standards but is
							// invalid
							System.out.println("ISBN Number "
									+ ISBNChecker
											.convertToISBNCharSequence(isbnNumber)
									+ " conforms with standards but has an incorrect check digit");
							fISBNEditText
									.setError(getResources()
											.getString(
													R.string.fragment_book_information_isbn_error));
						}
					} catch (Exception e) {
						System.out.println(e);
						// The ISBN number contains illegal characters
						System.out.println("ISBN Number " + isbnNumber
								+ " does not conform with standards");
						System.out.println(e);
						fISBNEditText
								.setError(getResources()
										.getString(
												R.string.fragment_book_information_isbn_error_char));
					}
				}
			}
		});
	}

	/* Classes */
	public interface BookInformationInterface {
		public abstract Book onBookInformationGet();

		public abstract boolean onBookInformationFinished(Book book);
	}

	@Override
	public Book getBook() {
		updateBook();
		return this.fBook;
	}
}
