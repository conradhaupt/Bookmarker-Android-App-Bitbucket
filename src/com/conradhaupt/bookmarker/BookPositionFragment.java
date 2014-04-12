package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookPositionFragment extends DialogFragment {

	/* Variables */
	private BookPositionListener bookPositionListener = null;
	private boolean isFragment = false;
	private Book book;
	// View Variables
	TextView bTitle;
	TextView bAuthor;
	NumberPicker bPage;
	NumberPicker bParagraph;
	NumberPicker bSentence;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStyle(STYLE_NORMAL,
				android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_book_position, container,
				false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_book_position, menu);
	}

	@Override
	public void onStart() {
		this.book = new Book();
		this.bAuthor = (TextView) this.getActivity().findViewById(
				R.id.fragment_book_position_book_author);
		this.bTitle = (TextView) this.getActivity().findViewById(
				R.id.fragment_book_position_book_title);
		this.bPage = (NumberPicker) this.getActivity().findViewById(
				R.id.fragment_book_position_picker_page);
		this.bParagraph = (NumberPicker) this.getActivity().findViewById(
				R.id.fragment_book_position_picker_paragraph);
		this.bSentence = (NumberPicker) this.getActivity().findViewById(
				R.id.fragment_book_position_picker_sentence);
		super.onStart();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			bookPositionListener = (BookPositionListener) activity;
			isFragment = true;
			System.out.println("Was a compatible activity");
			this.book = bookPositionListener.getBook();
			processNewBook();
		} catch (Exception e) {
			System.out.println("Was not a compatible activity");
			System.out.println(e);
		}
	}

	private void processNewBook() {
		System.out.println("ProcessNewBook Position run");
		bTitle.setText(this.book.getName());
		bAuthor.setText(this.book.getAuthor());
		bPage.setMaxValue(this.book.getPageCount());
		bParagraph.setMaxValue(this.getResources().getInteger(
				R.integer.maxParagraphCount));
		bSentence.setMaxValue(this.getResources().getInteger(
				R.integer.maxSentenceCount));
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		bookPositionListener = null;
	}

	public interface BookPositionListener {
		public void onBookPosition(Book book);

		public Book getBook();
	}
}
