package com.conradhaupt.bookmarker.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.conradhaupt.bookmarker.R;
import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookFragmentCursorAdapter extends CursorAdapter {
	/* Variables */
	// Object Variables
	private int mode = MODE_GRID;
	private LayoutInflater li;

	// Static Variables
	public static final int MODE_GRID = 1;
	public static final int MODE_LIST = 2;
	private static final int LAYOUT_GRID = R.layout.book_list_grid;
	private static final int LAYOUT_LIST = R.layout.book_list_list;

	/* Methods */
	public BookFragmentCursorAdapter(Context context, Cursor c,
			boolean autoRequery) {
		super(context, c, autoRequery);

		// Inflate the layout
		li = LayoutInflater.from(context);
	}

	public BookFragmentCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		// Inflate the layout
		li = LayoutInflater.from(context);
	}

	@Override
	protected void onContentChanged() {
		System.out.println("OnContentChanged Run");
		super.onContentChanged();
	}

	@Override
	public Cursor swapCursor(Cursor newCursor) {
		System.out.println("SwapCursor run " + newCursor.getCount());
		return super.swapCursor(newCursor);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		System.out.println("BindView run");
		// Get view holder
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		if (viewHolder == null) {
			viewHolder = new ViewHolder();
			viewHolder.textViewTitle = (TextView) view
					.findViewById(R.id.book_list_title);
			viewHolder.textViewAuthor = (TextView) view
					.findViewById(R.id.book_list_author);
			viewHolder.imageViewCover = (ImageView) view
					.findViewById(R.id.book_list_cover);
			viewHolder.imageViewOverflow = (ImageView) view
					.findViewById(R.id.book_list_more);
			view.setTag(viewHolder);
		}

		// Assign values
		viewHolder.textViewTitle.setText(cursor.getString(cursor
				.getColumnIndex(Book.COLUMN_TITLE)));
		viewHolder.textViewAuthor.setText(cursor.getString(cursor
				.getColumnIndex(Book.COLUMN_AUTHOR)));

		// TODO handle front cover image
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		System.out.println("NewView run");
		// View listView = li.inflate((mode == MODE_GRID ? LAYOUT_GRID
		// : LAYOUT_LIST), viewGroup, false);
		View listView = li.inflate(LAYOUT_GRID, viewGroup, true);

		// Return the list view
		return listView;
	}

	public static class ViewHolder {
		TextView textViewTitle;
		TextView textViewAuthor;
		TextView textViewISBN;
		ImageView imageViewCover;
		ImageView imageViewOverflow;
	}
}
