package com.conradhaupt.bookmarker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class BooksFragment extends Fragment {

	private Menu menu;
	private int viewType = 0;// 0 = List, 1 = Grid

	public static BooksFragment newInstance() {
		BooksFragment fragment = new BooksFragment();
		return fragment;
	}

	public BooksFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.out.println("OnActivityCreated run");
		System.out.println("BookManager instantiated");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.fragment_books_add:
			this.onAddBook();
			break;
		case R.id.fragment_books_sort_alphabetical:
			this.onSortAlphabetical();
			break;
		case R.id.fragment_books_sort_date:
			this.onSortDate();
			break;
		case R.id.fragment_books_sort_random:
			this.onSortRandom();
			break;
		case R.id.fragment_books_view_grid:
			this.onViewGrid();
			break;
		case R.id.fragment_books_view_list:
			this.onViewList();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_books, container, false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_books, menu);
		this.menu = menu;
		onUpdateViewType();
	}

	@Override
	public void onResume() {
		// Set actionBar title
		this.getActivity().getActionBar()
				.setTitle(R.string.fragment_main_title_drawer_closed);
		this.getActivity().invalidateOptionsMenu();
		super.onResume();
	};

	private void onAddBook() {
		System.out.println("Add book pressed");
		// TODO add code to add a book
		Intent intent = new Intent(this.getActivity(), BookCreateActivity.class);
		startActivity(intent);
	}

	private void onViewList() {
		this.viewType = 1;
		System.out.println("View List pressed");
		onUpdateViewType();
	}

	private void onViewGrid() {
		this.viewType = 0;
		System.out.println("View Grid pressed");
		onUpdateViewType();
	}

	private void onSortAlphabetical() {
		System.out.println("Sort Alphabetically pressed");
		// TODO add code to sort books alphabetically
	}

	private void onSortDate() {
		System.out.println("Sort Date pressed");
		// TODO add code to sort books by date
	}

	private void onSortRandom() {
		System.out.println("Sort Random pressed");
		// Add code to sort books randomly
	}

	private void onUpdateViewType() {
		System.out.println("OnUpdateViewType run with viewtype value of "
				+ this.viewType);

		// Modify menu item icon depending on state
		menu.findItem(R.id.fragment_books_view_list).setVisible(
				this.viewType == 0 ? true : false);
		menu.findItem(R.id.fragment_books_view_grid).setVisible(
				this.viewType == 0 ? false : true);

		// TODO add in code to change display of books
	}

}
