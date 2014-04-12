package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link BooksFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link BooksFragment#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class BooksFragment extends Fragment {

	private Menu menu;
	private int viewType = 0;// 0 = List, 1 = Grid

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @return A new instance of fragment BooksFragment.
	 */
	public static BooksFragment newInstance() {
		BooksFragment fragment = new BooksFragment();
		// Bundle args = new Bundle();
		// args.putString(ARG_PARAM1, param1);
		// args.putString(ARG_PARAM2, param2);
		// fragment.setArguments(args);

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
		// if (getArguments() != null)
		// {
		// mParam1 = getArguments().getString(ARG_PARAM1);
		// mParam2 = getArguments().getString(ARG_PARAM2);
		// }
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
		this.getActivity().getActionBar()
				.setTitle(R.string.activity_main_title_drawer_closed);
		this.getActivity().invalidateOptionsMenu();
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	private void onAddBook() {
		System.out.println("Add book pressed");
		startActivity(new Intent(this.getActivity(), BookCreateActivity.class)
				.putExtra(BookCreateActivity.MODE_IN_APP, true));
	}

	private void onViewList() {
		this.viewType = 1;
		System.out.println("View List pressed");
		onUpdateViewType();
		new BookPositionFragment().show(this.getFragmentManager(),
				"bookPositionDialog");
	}

	private void onViewGrid() {
		this.viewType = 0;
		System.out.println("View Grid pressed");
		onUpdateViewType();
	}

	private void onSortAlphabetical() {
		System.out.println("Sort Alphabetically pressed");

	}

	private void onSortDate() {
		System.out.println("Sort Date pressed");
	}

	private void onSortRandom() {
		System.out.println("Sort Random pressed");
	}

	private void onUpdateViewType() {
		System.out.println("OnUpdateViewType run with viewtype value of "
				+ this.viewType);
		menu.findItem(R.id.fragment_books_view_list).setVisible(
				this.viewType == 0 ? true : false);
		menu.findItem(R.id.fragment_books_view_grid).setVisible(
				this.viewType == 0 ? false : true);
	}

}
