package com.conradhaupt.bookmarker.sqlite.model;

import android.provider.BaseColumns;

public class Bookmark implements BaseColumns {
	/* Variables */
	// Object Variables
	private long id;
	private long bookId;
	private int page;
	private int chapter;
	private int paragraph;
	private int sentence;

	// Static Variables
	public static final String TABLE_NAME = "tbl_bookmarks";
	public static final String COLUMN_BOOK_ID = "bookId";
	public static final String COLUMN_PAGE = "page";
	public static final String COLUMN_CHAPTER = "chapter";
	public static final String COLUMN_PARAGRAPH = "paragraph";
	public static final String COLUMN_SENTENCE = "sentence";

	// Static Defaults
	public static final Bookmark DEFAULT = new Bookmark();

	/* Constructors */
	public Bookmark() {

	}

	public Bookmark(long id, long bookId, int page, int chapter, int paragraph,
			int sentence) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.page = page;
		this.chapter = chapter;
		this.paragraph = paragraph;
		this.sentence = sentence;
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

	/**
	 * @return the bookId
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the bookId to set
	 */
	public void setBookId(long bookId) {
		this.bookId = bookId;
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

	/**
	 * @return the chapter
	 */
	public int getChapter() {
		return chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(int chapter) {
		this.chapter = chapter;
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

	/*
	 * Methods and variables for interface Parcelable
	 */
}
