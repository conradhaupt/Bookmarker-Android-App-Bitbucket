package com.conradhaupt.bookmarker.ui;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.conradhaupt.bookmarker.BookCreateActivity;
import com.conradhaupt.bookmarker.R;
import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookFragmentAdapter extends ArrayAdapter<Book> {
	/* Variables */
	// Object Variables
	private int LAYOUT = 0;
	private BookFragmentAdapter.OnMenuItemClickListener<Book> onMenuItemClickListener;

	// Static Variables
	private static final int LAYOUT_GRID = 0;
	private static final int LAYOUT_LIST = 1;

	/* Methods */
	public BookFragmentAdapter(Context context, int resource, List<Book> objects) {
		super(context, resource, objects);
	}

	public BookFragmentAdapter(Context context, int resource,
			List<Book> objects,
			BookFragmentAdapter.OnMenuItemClickListener onMenuItemClickListener) {
		super(context, resource, objects);
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final View view;
		// Inflate the view if null
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(
					LAYOUT == LAYOUT_GRID ? R.layout.book_list_grid
							: R.layout.book_list_list, parent, false);
		} else {
			view = convertView;
		}

		// Change view values
		TextView title = (TextView) view.findViewById(R.id.book_list_title);
		title.setText(getItem(position).getTitle());
		TextView author = (TextView) view.findViewById(R.id.book_list_author);
		author.setText(getItem(position).getAuthor());

		// Handle Menu Context Menu
		ImageView more = (ImageView) view.findViewById(R.id.book_list_more);
		more.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupMenu popUpMenu = new PopupMenu(getContext(), v);
				MenuInflater menuInflater = new MenuInflater(getContext());
				menuInflater.inflate(R.menu.book_list_menu, popUpMenu.getMenu());

				// Attach OnMenuItemClickListener
				popUpMenu
						.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(final MenuItem item) {
								if (item.getItemId() == R.id.book_list_menu_delete) {
									// Begin animation
									Animation convertViewAnimation = AnimationUtils
											.loadAnimation(getContext(),
													R.anim.book_list_delete);

									// Set animation listener
									convertViewAnimation
											.setAnimationListener(new Animation.AnimationListener() {

												@Override
												public void onAnimationStart(
														Animation animation) {
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onAnimationRepeat(
														Animation animation) {
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onAnimationEnd(
														Animation animation) {
													// Let the activity handle
													// the process
													onMenuItemClickListener
															.onMenuItemClick(
																	position,
																	item);
													view.setVisibility(View.GONE);
												}
											});
									view.startAnimation(convertViewAnimation);
								} else if (item.getItemId() == R.id.book_list_menu_edit) {
									// Edit book
									Intent intent = new Intent(getContext(),
											BookCreateActivity.class);
									Bundle bundle = new Bundle();
									bundle.putInt(BookCreateActivity.BOOK_ID,
											(int) (getItem(position).getId()));
									bundle.putInt(BookCreateActivity.MODE,
											BookCreateActivity.MODE_EDIT);
									intent.putExtras(bundle);
									getContext().startActivity(intent);
								}

								// Return true to finish menu click handle
								return true;
							}
						});
				popUpMenu.show();
			}
		});

		// Return view
		return view;
	}

	public void onDeleteBookAnimations(int[] positions) {
	}

	/* Classes */
	public abstract class BookFragmentAdapterInterface {
		public static final int MODE_NORMAL = -1;
		public static final int MODE_SELECTING = 0;

		public abstract void onAdapterModeChanged(int mode);

	}

	public interface OnMenuItemClickListener<T> {
		public abstract boolean onMenuItemClick(int position, MenuItem menuItem);
	}
}
