package com.conradhaupt.bookmarker;

import com.conradhaupt.bookmarker.sqlite.model.Book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link BookInformationFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class BookInformationFragment extends Fragment {
	/* Variables */
	// Object Variables
	/**
	 * This is the bookID to be used by the fragment to populate the information
	 * from the {@link BookContentProvider}. If no bookID is set, by using the
	 * {@link #newInstance} method, then the value is left as 1 and the mode
	 * defaults to {@link BookCreateActivity#MODE_CREATE}
	 */
	private long bookID = -1;

	/**
	 * This is the mode of the fragment and should be the same as its parent
	 * {@link BookCreateActivity}. If no bookID has been set then it defaults to
	 * {@link BookCreateActivity#MODE_CREATE}, if a bookID has been set but no
	 * mode is set then it defaults to {@link BookCreateActivity#MODE_VIEW}.
	 */
	private int mode = -1;

	// Static Variables

	/**
	 * This is the standard way of initializing the BookInformationFragment. It
	 * handles the parameters and assigns the returned fragment a bundle with
	 * the paramters as extras.
	 * 
	 * @param bookID
	 *            The id to be used to retrieve the information that will be
	 *            displayed.
	 * @return A new instance of {@link BookInformationFragment} with a bundle
	 *         already assigned.
	 */
	public static BookInformationFragment newInstance(long bookID, int mode) {
		// Create a new fragment
		BookInformationFragment fragment = new BookInformationFragment();

		// Create and assign the bundle
		Bundle args = new Bundle();
		args.putLong(Book._ID, bookID);
		args.putInt(BookCreateActivity.MODE, mode);
		fragment.setArguments(args);

		// Return the fragment
		return fragment;
	}

	public static BookInformationFragment newInstance() {
		return newInstance(-1, -1);
	}

	public BookInformationFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Handle bundle
		if (getArguments() != null) {
			bookID = getArguments().getLong(Book._ID, -1);
			mode = getArguments().getInt(BookCreateActivity.MODE,
					BookCreateActivity.MODE_CREATE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_book_information, container,
				false);
	}

}
