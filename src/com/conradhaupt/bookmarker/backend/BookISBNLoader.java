package com.conradhaupt.bookmarker.backend;

import com.conradhaupt.bookmarker.sqlite.model.Book;

import android.os.AsyncTask;

public class BookISBNLoader extends AsyncTask<String, Double, Book> {

	@Override
	protected Book doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Book result) {
		super.onPostExecute(result);
	}
}
