package com.conradhaupt.bookmarker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conradhaupt.bookmarker.BookCreateActivity.BookCreateBookmarksInterface;
import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

public class BookBookmarksFragment extends Fragment implements
		BookCreateBookmarksInterface {
	/* Variables */
	// Object Variables
	private BookBookmarksInterface fBookBookmarksInterface = null;
	private ArrayList<Bookmark> bookmarks;

	// View Variables

	// Methods
	public static BookBookmarksFragment newInstance() {
		BookBookmarksFragment fragment = new BookBookmarksFragment();
		return fragment;
	}

	public BookBookmarksFragment() {
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
		return inflater.inflate(R.layout.fragment_book_bookmarks, container,
				false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_book_bookmarks, menu);
	}

	@Override
	public void onAttach(Activity activity) {
		// Assign interfaces
		fBookBookmarksInterface = (BookBookmarksInterface) activity;

		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		// Nullify Interfaces
		fBookBookmarksInterface.onBookBookmarksFinished(this.getBookmarks());
		fBookBookmarksInterface = null;

		super.onDetach();
	}

	private void updateArrayList() {

	}

	private void initViewVariables() {
		// Initialize all view variables from current fragment
	}

	/* Interface Methods and Classes */
	public interface BookBookmarksInterface {
		public abstract ArrayList<Bookmark> onBookBookmarksGet();

		public abstract boolean onBookBookmarksFinished(
				ArrayList<Bookmark> bookmarks);
	}

	@Override
	public ArrayList<Bookmark> getBookmarks() {
		// Return arraylist
		updateArrayList();
		return bookmarks;
	}
}
