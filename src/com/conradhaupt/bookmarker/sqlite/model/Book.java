package com.conradhaupt.bookmarker.sqlite.model;

import java.util.List;

import android.content.ContentValues;
import android.provider.BaseColumns;

public class Book implements BaseColumns {

	/* Variables */
	// Object Variables
	private long id = -1;
	private String title;
	private String author;
	private String isbn;
	private int pageCount;
	private int mainBookmarkID = -1;

	// This variable is used to combine the two classes into one for ease of use
	public List<Bookmark> bookmarks;

	// Static Variables
	public static final String TABLE_NAME = "tbl_books";
	public static final String COLUMN_TITLE = "name";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_ISBN = "isbn";
	public static final String COLUMN_PAGECOUNT = "pageCount";
	public static final String COLUMN_MAINBOOKMARK = "mainBookmark";

	// Static Defaults
	public static final Book DEFAULT = new Book();

	// Content Provider Variables
	public static final String BOOK_TYPE = "vnd.android.cursor.dir/vnd.bookmarker.book";
	public static final String BOOK_ITEM_TYPE = "vnd.android.cursor.item/vnd.bookmarker.book";

	/* Constructors */
	public Book() {
	}

	public Book(long id, String title, String note, String author,
			int pageCount, String isbn) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.pageCount = pageCount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/* Methods */

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/* Methods */

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the id associated with the books main bookmark
	 */
	public int getMainBookmarkID() {
		return mainBookmarkID;
	}

	/**
	 * @param mainBookmarkID
	 *            The id associated with the main bookmark for this book
	 */
	public void setMainBookmarkID(int mainBookmarkID) {
		this.mainBookmarkID = mainBookmarkID;
	}

	/**
	 * @return The values inside the book object in a ContentValues object
	 */
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.getId() != -1)
			values.put(Book._ID, this.getId());
		values.put(Book.COLUMN_TITLE, this.getTitle());
		values.put(Book.COLUMN_AUTHOR, this.getAuthor());
		values.put(Book.COLUMN_ISBN, this.getIsbn());
		values.put(Book.COLUMN_PAGECOUNT, this.getPageCount());
		values.put(Book.COLUMN_MAINBOOKMARK, this.getMainBookmarkID());
		return values;
	}
	/*
	 * Methods and variables for interface Parcelable
	 */
}
