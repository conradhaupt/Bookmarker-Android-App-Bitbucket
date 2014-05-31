package com.conradhaupt.bookmarker;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.conradhaupt.bookmarker.BookCreateActivity.BookCreateFragmentInterface;
import com.conradhaupt.bookmarker.BookCreateActivity.BookCreateInformationInterface;
import com.conradhaupt.bookmarker.backend.ISBNChecker;
import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookInformationFragment extends Fragment implements
		BookCreateInformationInterface, BookCreateFragmentInterface {
	/* Variables */
	// Object Variables
	private Book fBook = new Book();

	// View Variables
	private EditText fTitleEditText;
	private EditText fAuthorEditText;
	private EditText fISBNEditText;
	private EditText fPageCountEditText;

	// Static Variables

	/* Methods */
	public static BookInformationFragment newInstance(Book book) {
		// Create and initialize fragment
		BookInformationFragment bookInformationFragment = new BookInformationFragment();

		// Set Values
		bookInformationFragment.setValues(book == null ? null : book);

		// Return BookInformationFragment
		return bookInformationFragment;
	}

	public static BookInformationFragment newInstance() {
		BookInformationFragment fragment = new BookInformationFragment();
		return fragment;
	}

	public BookInformationFragment() {
		// Required empty public constructor
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_book_information, menu);
	}

	@Override
	public void onStart() {
		super.onStart();
		// Initialize View Variables
		initViewVariables();
	}

	@Override
	public void onResume() {
		super.onResume();
		// Process Books
		processBook();
	}

	public void setValues(Book book) {
		// Handle null value
		System.out.println("Setting BookInformationFragment values");
		if (book == null) {
			fBook = new Book();
		} else {
			fBook = book;
		}
	}

	private void updateBook() {
		// Assign new values if they exist
		if (!fTitleEditText.getText().toString().equalsIgnoreCase("")) {
			fBook.setTitle(fTitleEditText.getText().toString());
		}
		if (!fAuthorEditText.getText().toString().equalsIgnoreCase("")) {
			fBook.setAuthor(fAuthorEditText.getText().toString());
		}
		if (!fISBNEditText.getText().toString().equalsIgnoreCase("")) {
			fBook.setIsbn(fISBNEditText.getText().toString());
		}
		if (!fPageCountEditText.getText().toString().equals("0")
				&& !fPageCountEditText.getText().toString().equals("")) {
			fBook.setPageCount(Integer.parseInt(fPageCountEditText.getText()
					.toString()));

			// Handle negative numbers
			if (fBook.getPageCount() < 0) {
				System.out
						.println("Page count was negative, setting page count as 0");
				fBook.setPageCount(0);
			}
		}
	}

	public void processBook() {
		// Assign book values to views
		System.out.println("Processing book");
		fTitleEditText.setText(fBook.getTitle());
		fAuthorEditText.setText(fBook.getAuthor());
		fISBNEditText.setText(fBook.getIsbn());
		fPageCountEditText.setText(fBook.getPageCount() + "");
	}

	private void initViewVariables() {
		// Initialise the view variables
		fTitleEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_title_edittext);
		fAuthorEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_author_edittext);
		fISBNEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_isbn_edittext);
		fPageCountEditText = (EditText) this.getActivity().findViewById(
				R.id.creation_page_count_edittext);
		System.out.println("Views created");

		// Add error checker for PageCountEditText
		fPageCountEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// Verify value

						// Test empty condition
						if (fPageCountEditText.getText().toString().equals("")) {
							fPageCountEditText
									.setError(getResources()
											.getString(
													R.string.fragment_book_information_page_count_error_none));

							return;
						}

						try {
							int pageCount = Integer.parseInt(fPageCountEditText
									.getText().toString());
							if (pageCount == 0) {
								fPageCountEditText
										.setError(getResources()
												.getString(
														R.string.fragment_book_information_page_count_error_zero));
							} else if (pageCount < 0) {
								fPageCountEditText
										.setError(getResources()
												.getString(
														R.string.fragment_book_information_page_count_error_neg));
							} else {
								fPageCountEditText.setError(null);
							}
						} catch (Exception e) {
						}
					}
				});

		// Add error checker for ISBNEditText
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

	@Override
	public Book getBook() {
		updateBook();
		return this.fBook;
	}

	@Override
	public boolean canProceed() {
		boolean canProceed = true;

		System.out.println("CanProceed Run");

		// Check all requirements
		if (fISBNEditText.getError() != null) {
			canProceed = false;
		}
		if (fPageCountEditText.getError() != null) {
			canProceed = false;
		}
		if (fTitleEditText.getText().toString().equals("")) {
			canProceed = false;
		}
		if (fAuthorEditText.getText().toString().equals("")) {
			canProceed = false;
		}

		System.out.println("CanProceed is " + canProceed);

		// Return result
		return canProceed;
	}
}
