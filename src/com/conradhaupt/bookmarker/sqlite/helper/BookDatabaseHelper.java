package com.conradhaupt.bookmarker.sqlite.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.conradhaupt.bookmarker.sqlite.model.Book;
import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

public class BookDatabaseHelper extends SQLiteOpenHelper {

	/* Variables */
	// Database Syntax Constants
	private static final String BRACKET_BEGIN = "(";
	private static final String BRACKET_END = ")";
	private static final String SPACE = " ";
	private static final String TYPE_TEXT = "TEXT";
	private static final String TYPE_INTEGER = "INTEGER";
	private static final String TYPE_PRIMARY_KEY = "PRIMARY KEY";
	private static final String CREATE_TABLE = "CREATE TABLE";
	private static final String COMMA = ",";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "booksManager";

	// Create Queries
	private static final String CREATE_TABLE_BOOKS = CREATE_TABLE + SPACE
			+ Book.TABLE_NAME + SPACE + BRACKET_BEGIN + Book._ID + SPACE
			+ TYPE_INTEGER + SPACE + TYPE_PRIMARY_KEY + COMMA
			+ Book.COLUMN_ISBN + SPACE + TYPE_TEXT + COMMA + Book.COLUMN_TITLE
			+ SPACE + TYPE_TEXT + COMMA + Book.COLUMN_AUTHOR + SPACE
			+ TYPE_TEXT + COMMA + Book.COLUMN_PAGECOUNT + SPACE + TYPE_INTEGER
			+ BRACKET_END;
	private static final String CREATE_TABLE_BOOKMARKS = CREATE_TABLE + SPACE
			+ Bookmark.TABLE_NAME + SPACE + BRACKET_BEGIN + Bookmark._ID
			+ SPACE + TYPE_INTEGER + SPACE + TYPE_PRIMARY_KEY + COMMA
			+ Bookmark.COLUMN_BOOK_ID + SPACE + TYPE_INTEGER + COMMA
			+ Bookmark.COLUMN_TITLE + SPACE + TYPE_TEXT + COMMA
			+ Bookmark.COLUMN_PAGE + SPACE + TYPE_INTEGER + COMMA
			+ Bookmark.COLUMN_CHAPTER + SPACE + TYPE_INTEGER + COMMA
			+ Bookmark.COLUMN_PARAGRAPH + SPACE + TYPE_INTEGER + COMMA
			+ Bookmark.COLUMN_SENTENCE + SPACE + TYPE_INTEGER + BRACKET_END;

	public BookDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Creating database");
		db.execSQL(CREATE_TABLE_BOOKS);
		db.execSQL(CREATE_TABLE_BOOKMARKS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop all tables
		db.execSQL("DROP TABLE IF EXISTS " + Book.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Bookmark.TABLE_NAME);

		// Create new database
		onCreate(db);

	}

	// Book Database Methods
	public Book createBook(Book book) {
		System.out.println("Running createBook");
		// Get database
		SQLiteDatabase db = this.getWritableDatabase();

		// Assign values
		ContentValues values = new ContentValues();
		values.put(Book.COLUMN_ISBN, book.getIsbn());
		values.put(Book.COLUMN_TITLE, book.getTitle());
		values.put(Book.COLUMN_AUTHOR, book.getAuthor());
		values.put(Book.COLUMN_PAGECOUNT, book.getPageCount());

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
		Cursor cursor = db.query(Book.TABLE_NAME, null, Book._ID + " = "
				+ bookID, null, null, null, null);

		// Assign values to shell object
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Book book = new Book();
		book.setId(cursor.getInt(cursor.getColumnIndex(Book._ID)));
		book.setIsbn(cursor.getString(cursor.getColumnIndex(Book.COLUMN_ISBN)));
		book.setTitle(cursor.getString(cursor.getColumnIndex(Book.COLUMN_TITLE)));
		book.setAuthor(cursor.getString(cursor
				.getColumnIndex(Book.COLUMN_AUTHOR)));
		book.setPageCount(cursor.getInt(cursor
				.getColumnIndex(Book.COLUMN_PAGECOUNT)));

		// Return book
		return book;
	}

	public List<Book> getAllBooksWithBookmarks() {
		System.out.println("Running getAllBooksWithBookmarks");
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
				book.setTitle(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_TITLE)));
				book.setAuthor(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_AUTHOR)));
				book.setPageCount(cursor.getInt(cursor
						.getColumnIndex(Book.COLUMN_PAGECOUNT)));

				// Get bookmarks for this book
				List<Bookmark> bookmarks = getBookmarksForBook(book.getId());
				book.bookmarks = bookmarks;

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
				book.setTitle(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_TITLE)));
				book.setAuthor(cursor.getString(cursor
						.getColumnIndex(Book.COLUMN_AUTHOR)));
				book.setPageCount(cursor.getInt(cursor
						.getColumnIndex(Book.COLUMN_PAGECOUNT)));

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
		values.put(Book.COLUMN_TITLE, book.getTitle());
		values.put(Book.COLUMN_AUTHOR, book.getAuthor());
		values.put(Book.COLUMN_PAGECOUNT, book.getPageCount());

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

	// Bookmark Database Methods
	public Bookmark createBookmark(Bookmark bookmark) {
		System.out.println("Running createBook");
		// Get database
		SQLiteDatabase db = this.getWritableDatabase();

		// Assign values
		ContentValues values = new ContentValues();
		values.put(Bookmark.COLUMN_BOOK_ID, bookmark.getBookId());
		values.put(Bookmark.COLUMN_TITLE, bookmark.getTitle());
		values.put(Bookmark.COLUMN_PAGE, bookmark.getPage());
		values.put(Bookmark.COLUMN_CHAPTER, bookmark.getChapter());
		values.put(Bookmark.COLUMN_PARAGRAPH, bookmark.getParagraph());
		values.put(Bookmark.COLUMN_SENTENCE, bookmark.getSentence());

		// Insert row
		bookmark.setId(db.insert(Bookmark.TABLE_NAME, null, values));

		// Return book id
		return bookmark;
	}

	public Bookmark getBookmark(long bookmarkID) {
		System.out.println("Running getBookmark");
		// Get database
		SQLiteDatabase db = this.getReadableDatabase();

		// Run query
		Cursor cursor = db.query(Bookmark.TABLE_NAME, null, Bookmark._ID
				+ " = " + bookmarkID, null, null, null, null);

		// Assign values to shell object
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Bookmark bookmark = new Bookmark();
		bookmark.setId(cursor.getLong(cursor.getColumnIndex(Bookmark._ID)));
		bookmark.setBookId(cursor.getLong(cursor
				.getColumnIndex(Bookmark.COLUMN_BOOK_ID)));
		bookmark.setTitle(cursor.getString(cursor
				.getColumnIndex(Bookmark.COLUMN_TITLE)));
		bookmark.setPage(cursor.getInt(cursor
				.getColumnIndex(Bookmark.COLUMN_PAGE)));
		bookmark.setChapter(cursor.getInt(cursor
				.getColumnIndex(Bookmark.COLUMN_CHAPTER)));
		bookmark.setParagraph(cursor.getInt(cursor
				.getColumnIndex(Bookmark.COLUMN_PARAGRAPH)));
		bookmark.setSentence(cursor.getInt(cursor
				.getColumnIndex(Bookmark.COLUMN_SENTENCE)));

		// Return bookmark
		return bookmark;
	}

	public List<Bookmark> getBookmarksForBook(long bookID) {
		System.out.println("Running getBookmarksForBook");

		// Create Bookmark array which will be the output
		List<Bookmark> bookmarks;

		// Get Database
		SQLiteDatabase db = this.getReadableDatabase();

		// Run Query
		Cursor cursor = db.query(Bookmark.TABLE_NAME, null, bookID == -1 ? null
				: Bookmark.COLUMN_BOOK_ID + " = " + bookID, null, null, null,
				null);

		// If the cursor retrieved nothing then end method
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}

		// Retrieve all bookmarks
		List<Long> bookmarkIDs = new ArrayList<Long>();
		while (!cursor.isAfterLast()) {
			bookmarkIDs
					.add(cursor.getLong(cursor.getColumnIndex(Bookmark._ID)));
			cursor.moveToNext();
		}

		// Process bookmarkIDs into bookmark objects
		bookmarks = new ArrayList<Bookmark>();
		for (int i = 0; i < bookmarkIDs.size(); i++) {
			bookmarks.add(getBookmark(bookmarkIDs.get(i)));
		}

		// Return Bookmark Array
		return bookmarks;
	}

	public List<Bookmark> getAllBookmarks() {
		return getBookmarksForBook(-1);
	}

	public int updateBookmark(Bookmark bookmark) {
		System.out.println("Running updateBookmark");
		// Get Database
		SQLiteDatabase db = this.getWritableDatabase();

		// Assign values
		ContentValues values = new ContentValues();
		// values.put(Book.COLUMN_ISBN, book.getIsbn());
		values.put(Bookmark.COLUMN_BOOK_ID, bookmark.getBookId());
		values.put(Bookmark.COLUMN_TITLE, bookmark.getTitle());
		values.put(Bookmark.COLUMN_PAGE, bookmark.getPage());
		values.put(Bookmark.COLUMN_CHAPTER, bookmark.getChapter());
		values.put(Bookmark.COLUMN_PARAGRAPH, bookmark.getParagraph());
		values.put(Bookmark.COLUMN_SENTENCE, bookmark.getSentence());

		// Return value and update row
		return db.update(Bookmark.TABLE_NAME, values, Bookmark._ID + " = ?",
				new String[] { String.valueOf(bookmark.getId()) });
	}

	public void deleteBookmark(Bookmark bookmark) {
		System.out.println("Running deleteBookmark");
		// Get Database
		SQLiteDatabase db = this.getWritableDatabase();

		// delete book
		db.delete(Bookmark.TABLE_NAME, Bookmark._ID + " = ?",
				new String[] { String.valueOf(bookmark.getId()) });
	}

	public void closeDB() {
		System.out.println("Closing database");
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen()) {
			db.close();
		}
	}
}
