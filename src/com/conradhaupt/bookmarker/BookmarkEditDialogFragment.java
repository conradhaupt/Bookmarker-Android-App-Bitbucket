package com.conradhaupt.bookmarker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link BookmarkEditDialogFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class BookmarkEditDialogFragment extends Fragment {
	/* Variables */
	// Object Variables
	private String title;
	private int page;
	private int chapter;
	private int paragraph;
	private int sentence;

	// Static Variables

	/* Methods */
	public static BookmarkEditDialogFragment newInstance(Bookmark bookmark) {

		// Create a new fragment
		BookmarkEditDialogFragment fragment = new BookmarkEditDialogFragment();

		// Return the new fragment
		return fragment;
	}

	public BookmarkEditDialogFragment() {
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
		return inflater.inflate(R.layout.fragment_bookmark_edit_dialog,
				container, false);
	}

}
