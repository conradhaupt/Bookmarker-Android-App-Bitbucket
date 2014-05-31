package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.conradhaupt.bookmarker.BookCreateActivity.BookCreateBookmarksInterface;
import com.conradhaupt.bookmarker.BookCreateActivity.BookCreateFragmentInterface;
import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

public class BookBookmarksFragment extends Fragment implements
		BookCreateBookmarksInterface, BookCreateFragmentInterface {
	/* Variables */
	// Object Variables
	private Bookmark bookmark;
	private int maxPageCount;

	// View Variables
	private NumberPicker page;
	private NumberPicker chapter;
	private NumberPicker paragraph;
	private NumberPicker sentence;

	// Methods
	public static BookBookmarksFragment newInstance(Bookmark bookmark,
			int maxPageCount) {
		// Create and initialize fragment
		BookBookmarksFragment fragment = new BookBookmarksFragment();

		// Set Values
		fragment.setValues(bookmark == null ? null : bookmark,
				maxPageCount == -1 ? -1 : maxPageCount);

		// Return fragment
		return fragment;
	}

	public static BookBookmarksFragment newInstance() {
		return newInstance(null, -1);
	}

	public BookBookmarksFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViewVariables();
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

	public void setValues(Bookmark bookmark, int maxPageCount) {
		// Handle null value
		if (bookmark == null) {
			this.bookmark = new Bookmark();
		} else {
			this.bookmark = bookmark;
		}
		if (maxPageCount == -1) {
			this.maxPageCount = 10000;
		} else {
			this.maxPageCount = maxPageCount;
		}
	}

	private void updateBookmark() {
		// Retrieve values and assign to bookmark
		bookmark.setPage(page.getValue());
		bookmark.setChapter(chapter.getValue());
		bookmark.setParagraph(paragraph.getValue());
		bookmark.setSentence(sentence.getValue());
	}

	public void processBookmark() {
		// Assign view values
		page.setMaxValue(maxPageCount);
		page.setMinValue(0);
		page.setValue(bookmark.getPage());
		chapter.setMaxValue(getResources()
				.getInteger(R.integer.maxChapterCount));
		chapter.setMinValue(0);
		chapter.setValue(bookmark.getChapter());
		paragraph.setMaxValue(getResources().getInteger(
				R.integer.maxParagraphCount));
		paragraph.setMinValue(0);
		paragraph.setValue(bookmark.getParagraph());
		sentence.setMaxValue(getResources().getInteger(
				R.integer.maxSentenceCount));
		sentence.setMinValue(0);
		sentence.setValue(bookmark.getSentence());

	}

	private void initViewVariables() {
		// Initialize all views
		page = (NumberPicker) getActivity().findViewById(
				R.id.creation_bookmark_page_numberpicker);
		chapter = (NumberPicker) getActivity().findViewById(
				R.id.creation_bookmark_chapter_numberpicker);
		paragraph = (NumberPicker) getActivity().findViewById(
				R.id.creation_bookmark_paragraph_numberpicker);
		sentence = (NumberPicker) getActivity().findViewById(
				R.id.creation_bookmark_sentence_numberpicker);

		// Add values to bookmark
		processBookmark();

		// Assign listeners
		page.setOnValueChangedListener(new OnBookmarkValueChangeTitleListener()
				.setTitle(this.getActivity(),
						R.id.creation_bookmark_page_textview));
		chapter.setOnValueChangedListener(new OnBookmarkValueChangeTitleListener()
				.setTitle(this.getActivity(),
						R.id.creation_bookmark_chapter_textview));
		paragraph
				.setOnValueChangedListener(new OnBookmarkValueChangeTitleListener()
						.setTitle(this.getActivity(),
								R.id.creation_bookmark_paragraph_textview));
		sentence.setOnValueChangedListener(new OnBookmarkValueChangeTitleListener()
				.setTitle(this.getActivity(),
						R.id.creation_bookmark_sentence_textview));
	}

	@Override
	public Bookmark getBookmark() {
		// Return arraylist
		updateBookmark();
		return bookmark;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public boolean canProceed() {
		return true;
	}

	/* Classes */
	private class OnBookmarkValueChangeTitleListener implements
			NumberPicker.OnValueChangeListener {
		/* Variables */
		// Object Variables
		private Context context;
		private int id;
		private TextView title;

		// Static Variables

		/* Methods */
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// Handle empty value
			System.out.println("Value changed to " + newVal
					+ " with title null being " + (title == null));
			if (newVal == 0 && title != null) {
				title.setActivated(false);
			} else if (newVal != 0 && title != null) {
				title.setActivated(true);
			} else {
				try {
					title.setActivated(false);
				} catch (Exception e) {
				}
			}
		}

		public OnBookmarkValueChangeTitleListener setTitle(
				final Context context, final int id) {
			// Assign variable values
			this.context = context;
			this.id = id;

			// Initialize
			recalculateView();

			// Return this object
			return this;
		}

		private void recalculateView() {
			// Assign view new value
			Activity activity = (Activity) context;
			title = (TextView) activity.findViewById(id);
		}
	}
}
