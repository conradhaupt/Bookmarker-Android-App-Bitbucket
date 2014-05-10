package com.conradhaupt.bookmarker.sqlite.model;

import android.provider.BaseColumns;

public class Book implements BaseColumns {

	/* Variables */
	// Object Variables
	private long id;
	private String isbn;
	private String title;
	private String author;
	private int pageCount;

	// Static Variables
	public static final String TABLE_NAME = "tbl_books";
	public static final String COLUMN_ISBN = "isbn";
	public static final String COLUMN_TITLE = "name";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_PAGECOUNT = "pageCount";

	// Static Defaults
	public static final Book DEFAULT = new Book();

	/* Constructors */
	public Book() {
	}

	public Book(long id, String isbn, String title, String author, int pageCount) {
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

	/*
	 * Methods and variables for interface Parcelable
	 */
}
