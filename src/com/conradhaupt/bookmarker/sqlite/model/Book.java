package com.conradhaupt.bookmarker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Book implements BaseColumns, Parcelable {

	/* Variables */
	// Object Variables
	private long id;
	private String isbn;
	private String name;
	private String author;
	private int pageCount;
	private int sentence;
	private int paragraph;
	private int page;

	// Static Variables
	public static final String TABLE_NAME = "tbl_books";
	public static final String COLUMN_ISBN = "isbn";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_PAGERCOUNT = "pageCount";
	public static final String COLUMN_SENTENCE = "sentence";
	public static final String COLUMN_PARAGRAPH = "paragraph";
	public static final String COLUMN_PAGE = "page";

	/* Constructors */
	public Book() {
	}

	public Book(long id, String isbn, String name, String author,
			int pageCount, int sentence, int paragraph, int page) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.pageCount = pageCount;
		this.sentence = sentence;
		this.paragraph = paragraph;
		this.page = page;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the sentence
	 */
	public int getSentence() {
		return sentence;
	}

	/**
	 * @param sentence
	 *            the sentence to set
	 */
	public void setSentence(int sentence) {
		this.sentence = sentence;
	}

	/**
	 * @return the paragraph
	 */
	public int getParagraph() {
		return paragraph;
	}

	/**
	 * @param paragraph
	 *            the paragraph to set
	 */
	public void setParagraph(int paragraph) {
		this.paragraph = paragraph;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/*
	 * Methods and variables for interface Parcelable
	 */
	public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {

		@Override
		public Book createFromParcel(Parcel source) {
			// Create dummy object
			Book book = null;

			// Process book

			// Return book
			return book;
		}

		@Override
		public Book[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

}
