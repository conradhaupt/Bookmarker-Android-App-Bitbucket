package com.conradhaupt.bookmarker.sqlite.model;

import android.content.ContentValues;
import android.provider.BaseColumns;

public class Bookmark implements BaseColumns {
	/* Variables */
	// Object Variables
	private long id = -1;
	private long bookId;
	private String title;
	private String note;
	private int page = 0;
	private int chapter = 0;
	private int paragraph = 0;
	private int sentence = 0;

	// Static Variables
	public static final String TABLE_NAME = "tbl_bookmarks";
	public static final String COLUMN_BOOK_ID = "bookId";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_NOTE = "note";
	public static final String COLUMN_PAGE = "page";
	public static final String COLUMN_CHAPTER = "chapter";
	public static final String COLUMN_PARAGRAPH = "paragraph";
	public static final String COLUMN_SENTENCE = "sentence";

	// Static Defaults
	public static final Bookmark DEFAULT = new Bookmark();

	// Content Provider Variables
	public static final String BOOKMARK_TYPE = "vnd.android.cursor.dir/vnd.bookmarker.bookmark";
	public static final String BOOKMARK_ITEM_TYPE = "vnd.android.cursor.item/vnd.bookmarker.bookmark";

	/* Constructors */
	public Bookmark() {

	}

	public Bookmark(long id, long bookId, String title, int page, int chapter,
			int paragraph, int sentence) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.title = title;
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

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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

	/**
	 * @return The values inside the bookmark object in a ContentValues object
	 */
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.getId() != -1)
			values.put(Bookmark._ID, this.getId());
		values.put(Bookmark.COLUMN_BOOK_ID, this.getBookId());
		values.put(Bookmark.COLUMN_TITLE, this.getTitle());
		values.put(Bookmark.COLUMN_NOTE, this.getNote());
		values.put(Bookmark.COLUMN_PAGE, this.getPage());
		values.put(Bookmark.COLUMN_CHAPTER, this.getChapter());
		values.put(Bookmark.COLUMN_PARAGRAPH, this.getParagraph());
		values.put(Bookmark.COLUMN_SENTENCE, this.getSentence());
		return values;
	}
}
