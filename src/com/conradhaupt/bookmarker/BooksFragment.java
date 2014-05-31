package com.conradhaupt.bookmarker;

import android.widget.PopupMenu;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.conradhaupt.bookmarker.sqlite.helper.BookDatabaseHelper;
import com.conradhaupt.bookmarker.sqlite.model.Book;
import com.conradhaupt.bookmarker.ui.BookFragmentAdapter;

public class BooksFragment extends Fragment implements
		BookFragmentAdapter.OnMenuItemClickListener<Book> {
	/* Variables */
	// Object Variables
	private Menu menu;
	// private int viewType = VIEW_TYPE_LIST;

	// View Variables
	private GridView fGridView;
	private BookFragmentAdapter fGridViewArrayAdapter;
	private OnItemLongClickListener fGridViewOnItemLongClickListener;
	private BookFragmentAdapter.OnMenuItemClickListener<Book> onMenuItemClickListener = this;
	private List<Book> fBooks = null;

	// Static Variables
	public static final int VIEW_TYPE_LIST = 0;
	public static final int VIEW_TYPE_GRID = 1;

	/* Methods */
	public static BooksFragment newInstance() {
		BooksFragment fragment = new BooksFragment();
		return fragment;
	}

	public BooksFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		// case R.id.fragment_books_view_grid:
		// this.onViewGrid();
		// break;
		// case R.id.fragment_books_view_list:
		// this.onViewList();
		// break;
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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.initViewVariables(view);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_books, container, false);

		// Return view
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_books, menu);
		this.menu = menu;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		// Set actionBar title
		this.getActivity().getActionBar()
				.setTitle(R.string.fragment_main_title_drawer_closed);
		this.getActivity().invalidateOptionsMenu();

		// Reload books
		reloadBooks();

		super.onResume();
	};

	private void initViewVariables(View view) {
		// Initialize View Variables
		this.fGridView = (GridView) getView().findViewById(
				R.id.fragment_books_grid_view);
		this.fGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
	}

	private void reloadBooks() {
		// Start asynctask
		new AsyncTask<Book, Integer, List<Book>>() {

			private List<Book> aBooks;

			@Override
			protected List<Book> doInBackground(Book... params) {
				// Retrieve all books again
				BookDatabaseHelper bookDatabaseHelper = new BookDatabaseHelper(
						getActivity());
				aBooks = bookDatabaseHelper.getAllBooksWithBookmarks();

				return aBooks;
			}

			protected void onPostExecute(java.util.List<Book> result) {
				fBooks = aBooks;
				if (fGridViewArrayAdapter == null) {
					fGridViewArrayAdapter = new BookFragmentAdapter(
							getActivity(), 0, fBooks, onMenuItemClickListener);
					fGridView.setAdapter(fGridViewArrayAdapter);
					fGridView
							.setOnItemLongClickListener(fGridViewOnItemLongClickListener);
				} else {
					// Notify adapter of change
					fGridViewArrayAdapter.notifyDataSetChanged();
				}
			};

		}.execute(new Book[] { null });

	}

	private void onAddBook() {
		System.out.println("Add book pressed");
		// TODO add code to add a book
		Intent intent = new Intent(this.getActivity(), BookCreateActivity.class);
		startActivity(intent);
	}

	// private void onViewList() {
	// this.viewType = 1;
	// System.out.println("View List pressed");
	// onUpdateViewType();
	// }
	//
	// private void onViewGrid() {
	// this.viewType = 0;
	// System.out.println("View Grid pressed");
	// onUpdateViewType();
	// }

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

	// @Override
	// public boolean onMenuItemClick(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.book_list_menu_edit:
	// Intent intent = new Intent(this.getActivity(),
	// BookCreateActivity.class);
	// Bundle bundle = new Bundle();
	// // bundle.putInt(BookCreateActivity.BOOK_ID,item.get);
	// bundle.putInt(BookCreateActivity.MODE, BookCreateActivity.MODE_EDIT);
	// intent.putExtras(bundle);
	// getActivity().startActivity(intent);
	// break;
	// case R.id.book_list_menu_delete:
	// break;
	// default:
	// break;
	// }
	// return false;
	// }
	// These methods are not used currently but are left just incase
	// private void onUpdateViewType() {
	// System.out.println("OnUpdateViewType run with viewtype value of "
	// + this.viewType);
	//
	// // Modify menu item icon depending on state
	// menu.findItem(R.id.fragment_books_view_list).setVisible(
	// this.viewType == 0 ? true : false);
	// menu.findItem(R.id.fragment_books_view_grid).setVisible(
	// this.viewType == 0 ? false : true);
	//
	// // Change the Grid View depending on style
	// int numColumns = viewType == VIEW_TYPE_LIST ? 1 : getResources()
	// .getInteger(R.integer.fragment_books_grid_view_num_columns);
	// fGridView.setNumColumns(numColumns);
	//
	// // Reload the books from the database
	// reloadBooks();
	// }

	@Override
	public boolean onMenuItemClick(int position, MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.book_list_menu_delete:
			// Delete book
			deleteBooks(new int[] { (int) (fBooks.get(position).getId()) });
			return true;
		}
		return false;
	}

	private void deleteBooks(int[] bookIds) {
	}
}
