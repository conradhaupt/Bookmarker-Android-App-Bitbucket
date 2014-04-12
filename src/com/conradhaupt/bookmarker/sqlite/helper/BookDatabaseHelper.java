package com.conradhaupt.bookmarker.sqlite.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.conradhaupt.bookmarker.sqlite.model.Book;

public class BookDatabaseHelper extends SQLiteOpenHelper {

	/* Variables */

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "booksManager";

	// Create Queries
	private static final String CREATE_TABLE_BOOKS = "CREATE TABLE "
			+ Book.TABLE_NAME + " (" + Book._ID + " INTEGER PRIMARY KEY,"
			+ Book.COLUMN_ISBN + " TEXT," + Book.COLUMN_NAME + " TEXT,"
			+ Book.COLUMN_AUTHOR + " TEXT," + Book.COLUMN_PAGERCOUNT
			+ " INTEGER," + Book.COLUMN_SENTENCE + " INTEGER,"
			+ Book.COLUMN_PARAGRAPH + " INTEGER," + Book.COLUMN_PAGE
			+ " INTEGER" + ")";

	public BookDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_BOOKS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop all tables
		db.execSQL("DROP TABLE IF EXISTS " + Book.TABLE_NAME);

		// Create new database
		onCreate(db);
	}

	public Book createBook(Book book) {
		System.out.println("Running createBook");
		// Get database
		SQLiteDatabase db = this.getWritableDatabase();

		// Assign values
		ContentValues values = new ContentValues();
		values.put(Book.COLUMN_ISBN, book.getIsbn());
		values.put(Book.COLUMN_NAME, book.getName());
		values.put(Book.COLUMN_AUTHOR, book.getAuthor());
		values.put(Book.COLUMN_PAGERCOUNT, book.getPageCount());
		values.put(Book.COLUMN_SENTENCE, book.getSentence());
		values.put(Book.COLUMN_PARAGRAPH, book.getParagraph());
		values.put(Book.COLUMN_PAGE, book.getPage());

		// Insert row
		book.setId(db.insert(Book.TABLE_NAME, null, values));

		// Return book id
		return book;
	}

	public Book getBook(long bookID) {
		System.out.println("Running getBook");
		// Get database
		SQLiteDatabase db = this.getReadableDatabase();

		// Run query
		Cursor cursor = db.query(Book.TABLE_NAME, null, Book._ID + " equals "
				+ bookID, null, null, null, null);

		// Assign values to shell object
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Book book = new Book();
		book.setId(cursor.getInt(cursor.getColumnIndex(Book._ID)));
		book.setIsbn(cursor.getString(cursor.getColumnIndex(Book.COLUMN_ISBN)));
		book.setName(cursor.getString(cursor.getColumnIndex(Book.COLUMN_NAME)));
		book.setAuthor(cursor.getString(cursor
				.getColumnIndex(Book.COLUMN_AUTHOR)));
		book.setPageCount(cursor.getInt(cursor
				.getColumnIndex(Book.COLUMN_PAGERCOUNT)));
		book.setSentence(cursor.getInt(cursor
				.getColumnIndex(Book.COLUMN_SENTENCE)));
		book.setParagraph(cursor.getInt(cursor
				.getColumnIndex(Book.COLUMN_PARAGRAPH)));
		book.setPage(cursor.getInt(cursor.getColumnIndex(Book.COLUMN_PAGE)));

		// Return book
		return book;
	}

	public List<Book> getAllBooks() {
		System.out.println("Running getAllBooks");
		// Get database
		SQLiteDatabase db = this.getReadableDatabase();

		// Run query
		Cursor cursor = db.query(Book.TABLE_NAME, null, null, null, null, null,
				null);

		// Begin loop to process all books
		List<Book> bookList = null;
		if (cursor.moveToFirst()) {
			do {
				// Assign values to shell object
				Book book = new Book();
				book.setId(cursor.getInt(cursor.getColumnIndex(Book._ID)));
				book.setIsbn(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_ISBN)));
				book.setName(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_NAME)));
				book.setAuthor(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_AUTHOR)));
				book.setPageCount(cursor.getInt(cursor
						.getColumnIndex(Book.COLUMN_PAGERCOUNT)));
				book.setSentence(cursor.getInt(cursor
						.getColumnIndex(Book.COLUMN_SENTENCE)));
				book.setParagraph(cursor.getInt(cursor
						.getColumnIndex(Book.COLUMN_PARAGRAPH)));
				book.setPage(cursor.getInt(cursor
						.getColumnIndex(Book.COLUMN_PAGE)));

				// Add shell object to list
				if (bookList == null) {
					bookList = new ArrayList<Book>();
				}
				bookList.add(book);
			} while (cursor.moveToNext());
		}

		// Return list
		return bookList;
	}

	public int updateBook(Book book) {
		System.out.println("Running updateBook");
		// Get Database
		SQLiteDatabase db = this.getWritableDatabase();

		// Assign values
		ContentValues values = new ContentValues();
		values.put(Book.COLUMN_ISBN, book.getIsbn());
		values.put(Book.COLUMN_NAME, book.getName());
		values.put(Book.COLUMN_AUTHOR, book.getAuthor());
		values.put(Book.COLUMN_PAGERCOUNT, book.getPageCount());
		values.put(Book.COLUMN_SENTENCE, book.getSentence());
		values.put(Book.COLUMN_PARAGRAPH, book.getParagraph());
		values.put(Book.COLUMN_PAGE, book.getPage());

		// Return value and update row
		return db.update(Book.TABLE_NAME, values, Book._ID + " = ?",
				new String[] { String.valueOf(book.getId()) });
	}

	public void deleteBook(Book book) {
		System.out.println("Running deleteBook");
		// Get Database
		SQLiteDatabase db = this.getWritableDatabase();

		// delete book
		db.delete(Book.TABLE_NAME, Book._ID + " = ?",
				new String[] { String.valueOf(book.getId()) });
	}

	public void closeDB() {
		System.out.println("Closing database");
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen()) {
			db.close();
		}
	}
}
