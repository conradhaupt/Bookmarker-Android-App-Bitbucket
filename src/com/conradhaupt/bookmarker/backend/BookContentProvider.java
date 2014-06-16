package com.conradhaupt.bookmarker.backend;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.conradhaupt.bookmarker.sqlite.helper.BookDatabaseHelper;
import com.conradhaupt.bookmarker.sqlite.helper.BookDatabaseHelper.DS;
import com.conradhaupt.bookmarker.sqlite.model.Book;
import com.conradhaupt.bookmarker.sqlite.model.Bookmark;

public class BookContentProvider extends ContentProvider {
	/* Variables */
	// Object Variables
	/**
	 * This is the local instance of the {@link BookDatabaseHelper} class that
	 * assists with creating, updating and deleting the database.
	 */
	private BookDatabaseHelper pBookDatabaseHelper;

	// Static Variables
	/**
	 * This is the tag to be used to identify any logs that are passed by the
	 * {@link ContentProvider}.
	 */
	public static final String TAG = "com.conradhaupt.bookmarker.BookContentProvider";

	/**
	 * This is the column name for the field defining whether a bookmark is the
	 * main bookmark or not.
	 */
	public static final String COLUMN_IS_MAIN = "isMain";

	/**
	 * The {@link #AUTHORITY} is the pseudo-construct to help identify URIs for
	 * the provider
	 */
	public static final String AUTHORITY = "com.conradhaupt.bookmarker.BookContentProvider";

	// These are the URI types
	/**
	 * This is the constant defining a URI as one which accesses all books
	 * available.
	 */
	public static final int TYPE_BOOKS = 0;

	/**
	 * This is the constant defining a URI as one which accesses a specific book
	 * defined by its ID.
	 */
	public static final int TYPE_BOOKS_ID = 1;

	/**
	 * This is the constant defining a URI as one which accesses all bookmarks
	 * for a specific book defined by its ID.
	 */
	public static final int TYPE_BOOKS_ID_BOOKMARKS = 2;

	/**
	 * This is the constant defining a URI as one which access the main bookmark
	 * of a specific book defined by its ID.
	 */
	public static final int TYPE_BOOKS_ID_BOOKMARKS_MAIN = 3;

	/**
	 * This is the constant defining a URI as one which accesses all bookmarks
	 * available.
	 */
	public static final int TYPE_BOOKMARKS = 4;

	/**
	 * This is the constant defining a URI as one which accesses a specific
	 * bookmark defined by its ID.
	 */
	public static final int TYPE_BOOKMARKS_ID = 5;

	/*
	 * URIs: These are the default URIs that could be retrieved
	 */
	/**
	 * This is the prefix that goes at the beginning of every URI that would be
	 * used by this {@link ContentProvider}
	 */
	private static final String PREFIX = "content://";

	/**
	 * This is the basic stricture of a URI that is meant to access information
	 * based off of the books. URIs that use this are:<br>
	 * {@link #TYPE_BOOKS}, {@link #TYPE_BOOKS_ID},
	 * {@link #TYPE_BOOKS_ID_BOOKMARKS}, and
	 * {@link #TYPE_BOOKS_ID_BOOKMARKS_MAIN}.
	 */
	public static final Uri URI_BOOKS = Uri.parse(PREFIX + AUTHORITY + "/"
			+ Book.TABLE_NAME);

	/**
	 * This is the basic structure of a URI that is meant to access information
	 * based off of the bookmarks. URIs that use this are:<br>
	 * {@link #TYPE_BOOKMARKS}, and {@link #TYPE_BOOKMARKS_ID}.
	 */
	public static final Uri URI_BOOKMARKS = Uri.parse(PREFIX + AUTHORITY + "/"
			+ Bookmark.TABLE_NAME);

	/*
	 * MIMEs: These are the possible MIMEs the provider would supply
	 */
	/**
	 * This is the value of the MIME that defines a set of books.
	 */
	public static final String MIME_BOOKS = "vnd.android.cursor.dir/vnd.com.conradhaupt.bookmarker.BookContentProvider.Book";

	/**
	 * This is the value of the MIME that defines a sepcific book.
	 */
	public static final String MIME_BOOK = "vnd.android.cursor.item/vnd.com.conradhaupt.bookmarker.BookContentProvider.Book";

	/**
	 * This is the value of the MIME that defines a set of bookmarks.
	 */
	public static final String MIME_BOOKMARKS = "vnd.android.cursor.dir/vnd.com.conradhaupt.bookmarker.BookContentProvider.Bookmark";

	/**
	 * This is the value of the MIME that defines a specific bookmark.
	 */
	public static final String MIME_BOOKMARK = "vnd.android.cursor.item/vnd.com.conradhaupt.bookmarker.BookContentProvider.Bookmark";

	// This URIMatcher helps handle the URIs
	private static final UriMatcher pUriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	// Assign the current URIs
	static {
		pUriMatcher.addURI(AUTHORITY, Book.TABLE_NAME, TYPE_BOOKS);
		pUriMatcher.addURI(AUTHORITY, Book.TABLE_NAME + "/#", TYPE_BOOKS_ID);
		pUriMatcher.addURI(AUTHORITY, Book.TABLE_NAME + "/#/"
				+ Bookmark.TABLE_NAME, TYPE_BOOKS_ID_BOOKMARKS);
		pUriMatcher.addURI(AUTHORITY, Book.TABLE_NAME + "/#/"
				+ Bookmark.TABLE_NAME + "/m", TYPE_BOOKS_ID_BOOKMARKS_MAIN);
		pUriMatcher.addURI(AUTHORITY, Bookmark.TABLE_NAME, TYPE_BOOKMARKS);
		pUriMatcher.addURI(AUTHORITY, Bookmark.TABLE_NAME + "/#",
				TYPE_BOOKMARKS_ID);
	}

	/**
	 * The systems calls onCreate when the Provider has been created. This
	 * method creates the database through the class {@link BookDatabaseHelper}.
	 */
	@Override
	public boolean onCreate() {
		// Instantiate the database helper
		this.pBookDatabaseHelper = new BookDatabaseHelper(getContext());

		// Return true to signal that the provider has been created
		return true;
	}

	// This method return the MIME type of the URI data that will be retrieved
	@Override
	public String getType(Uri uri) {
		// Check the URI Type
		switch (pUriMatcher.match(uri)) {
		case TYPE_BOOKS:
			return MIME_BOOKS;
		case TYPE_BOOKS_ID:
			return MIME_BOOK;
		case TYPE_BOOKS_ID_BOOKMARKS:
		case TYPE_BOOKS_ID_BOOKMARKS_MAIN:
		case TYPE_BOOKMARKS:
			return MIME_BOOKMARKS;
		case TYPE_BOOKMARKS_ID:
			return MIME_BOOKMARK;
		default:
			throw new UnsupportedOperationException("Not yet implemented");
		}
	}

	// This method is used to insert a certain object into the database/content
	// storage
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Log the method being run
		Log.w(BookContentProvider.TAG, "Inserting using URI (" + uri
				+ ") with the following values:\n" + values.toString());

		// Initialize the database
		SQLiteDatabase db = pBookDatabaseHelper.getWritableDatabase();

		boolean isBookmark = true;

		// Handle URI matches
		switch (pUriMatcher.match(uri)) {
		case TYPE_BOOKS:
			// Handle the table to be inserted into
			isBookmark = false;
		case TYPE_BOOKMARKS:
		case TYPE_BOOKS_ID_BOOKMARKS:
			// Do nothing as these are acceptable URIs
			break;
		default:
			throw new UnsupportedOperationException("URI unsupported: " + uri);
		}

		// Insert the object into the corresponding table
		long id = db.insert(isBookmark ? Bookmark.TABLE_NAME : Book.TABLE_NAME,
				null, values);

		// Notify resolver
		getContext().getContentResolver().notifyChange(uri, null);

		// Return the final URI
		return Uri.parse((isBookmark ? URI_BOOKMARKS : URI_BOOKS) + "/" + id);
	}

	// This method returns a cursor based off of certain conditions, similar to
	// an SQL SELECT query
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// Log the method being run
		Log.w(BookContentProvider.TAG, "Running a query using URI (" + uri
				+ ")");

		// Initialize the database and query builder
		SQLiteDatabase db = pBookDatabaseHelper.getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		boolean isBookmark = true;

		// Process whether the URI is for a book or bookmark
		switch (pUriMatcher.match(uri)) {
		case TYPE_BOOKS:
			isBookmark = false;
			break;
		case TYPE_BOOKS_ID:
			isBookmark = false;
			String id = uri.getPathSegments().get(1);
			qb.appendWhere(Book._ID + DS.SPACE + DS.EQUALS + DS.SPACE + id);
			break;
		case TYPE_BOOKS_ID_BOOKMARKS:
			String book_id = uri.getPathSegments().get(1);

			// Handle adding the main column
			if (projection == null) {
				projection = new String[1];
			} else {
				String[] tempProjection = new String[projection.length + 1];
				for (int i = 0; i < projection.length; i++) {
					tempProjection[i] = projection[i];
				}
				projection = tempProjection;
				projection[projection.length] = "CASE WHEN "
						+ Bookmark.TABLE_NAME + "." + Bookmark._ID + DS.EQUALS
						+ Book.TABLE_NAME + "." + Book.COLUMN_MAINBOOKMARK
						+ " THEN 1 ELSE 0 END AS " + COLUMN_IS_MAIN;
			}
			qb.appendWhere(DS.BRACKET_BEGIN + Book.TABLE_NAME + "." + Book._ID
					+ DS.SPACE + DS.EQUALS + DS.SPACE + book_id
					+ DS.BRACKET_END);
		case TYPE_BOOKS_ID_BOOKMARKS_MAIN:
			qb.appendWhere(DS.BRACKET_BEGIN
					+ (Book.TABLE_NAME + "." + Book.COLUMN_MAINBOOKMARK)
					+ DS.SPACE + DS.EQUALS + DS.SPACE
					+ (Bookmark.TABLE_NAME + "." + Bookmark.COLUMN_BOOK_ID)
					+ DS.BRACKET_END);
			break;
		case TYPE_BOOKMARKS:
			break;
		case TYPE_BOOKMARKS_ID:
			String bookmark_id = uri.getPathSegments().get(1);
			qb.appendWhere(Bookmark.COLUMN_BOOK_ID + DS.SPACE + DS.EQUALS
					+ DS.SPACE + bookmark_id);
			break;
		default:
			throw new UnsupportedOperationException("URI unsupported: " + uri);
		}

		// Handle query table
		qb.setTables(isBookmark ? (pUriMatcher.match(uri) == TYPE_BOOKS_ID_BOOKMARKS_MAIN ? Bookmark.TABLE_NAME
				+ "," + Book.TABLE_NAME
				: Bookmark.TABLE_NAME)
				: Book.TABLE_NAME);

		// Retrieve cursor
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);

		// Return cursor
		return c;
	}

	/**
	 * This method handles the deleting of certain entries in the database. If
	 * the entry to delete is a book then the bookmarks will be deleted as well.
	 * 
	 * @param uri
	 *            This is the type of data that is being supplied. If this is
	 *            {@link TYPE_BOOKS_ID} then the bookmarks associated to that
	 *            book will be deleted as well.
	 * @return The returned value is equal to amount of bookmarks deleted if the
	 *         URI is {@link URI_BOOKMARK}. If the URI is equal to
	 *         {@link URI_BOOK} then the result is equal to the amount of
	 *         bookmarks deleted. Therefore if the value is greater than 0 then
	 *         it is safe to assume that the book was deleted.
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Log the method being run
		Log.w(BookContentProvider.TAG, "Deleting using URI (" + uri + ")");

		// Initialize the database
		SQLiteDatabase db = pBookDatabaseHelper.getWritableDatabase();

		// Handle bookmark vs book
		boolean isBookmark = true;

		// Handle the selection
		String bookSelection = null;
		String bookmarkSelection = null;
		String id;

		// Handle the URI type
		switch (pUriMatcher.match(uri)) {
		case TYPE_BOOKS_ID:
			isBookmark = false;

			// Handle the selection
			id = uri.getPathSegments().get(1);
			bookSelection = Book._ID
					+ DS.SPACE
					+ DS.EQUALS
					+ DS.SPACE
					+ id
					+ (!TextUtils.isEmpty(selection) ? "(" + selection + ")"
							: "");
			bookmarkSelection = Bookmark.COLUMN_BOOK_ID + DS.SPACE + DS.EQUALS
					+ DS.SPACE + id;
			break;
		case TYPE_BOOKMARKS_ID:
			// Handle the selection
			id = uri.getPathSegments().get(1);
			bookSelection = Bookmark._ID
					+ DS.SPACE
					+ DS.EQUALS
					+ DS.SPACE
					+ id
					+ (!TextUtils.isEmpty(selection) ? "(" + selection + ")"
							: "");
			break;
		default:
			throw new UnsupportedOperationException("URI unsupported: " + uri);
		}

		// Log the method being run
		Log.w(BookContentProvider.TAG, "Delete Selection is:\n" + selection);

		// Delete entries
		int deleteCount;
		if (!isBookmark) {
			db.delete(Book.TABLE_NAME, bookSelection, selectionArgs);
			deleteCount = db.delete(Bookmark.TABLE_NAME, bookmarkSelection,
					selectionArgs);
		} else {
			deleteCount = db.delete(Book.TABLE_NAME, bookSelection,
					selectionArgs);
		}

		// Notify resolver
		getContext().getContentResolver().notifyChange(uri, null);

		// Return the amount of entries deleted
		return deleteCount;
	}

	// The update method updates a current entry in the corresponding table
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// Log the method being run
		Log.w(BookContentProvider.TAG, "Updating using URI (" + uri
				+ ") with the following condition:\n" + selection
				+ "\nand values:\n" + values.toString());

		// Initialize the database
		SQLiteDatabase db = pBookDatabaseHelper.getWritableDatabase();

		// Handle bookmark vs book
		boolean isBookmark = true;

		// Handle the different URIs
		switch (pUriMatcher.match(uri)) {
		case TYPE_BOOKS:
			isBookmark = false;
		case TYPE_BOOKMARKS:
			// Do nothing as all entries will be updated
			break;
		case TYPE_BOOKS_ID:
			isBookmark = false;
			String id = uri.getPathSegments().get(1);
			selection = Book._ID
					+ DS.SPACE
					+ DS.EQUALS
					+ DS.SPACE
					+ id
					+ (!TextUtils.isEmpty(selection) ? "(" + selection + ")"
							: "");
			break;
		case TYPE_BOOKS_ID_BOOKMARKS:
			String book_id = uri.getPathSegments().get(1);
			selection = Bookmark.COLUMN_BOOK_ID
					+ DS.SPACE
					+ DS.EQUALS
					+ DS.SPACE
					+ book_id
					+ (!TextUtils.isEmpty(selection) ? "(" + selection + ")"
							: "");
			break;
		case TYPE_BOOKMARKS_ID:
			String bookmark_id = uri.getPathSegments().get(1);
			selection = Bookmark._ID
					+ DS.SPACE
					+ DS.EQUALS
					+ DS.SPACE
					+ bookmark_id
					+ (!TextUtils.isEmpty(selection) ? "(" + selection + ")"
							: "");
			break;
		default:
			throw new UnsupportedOperationException("URI unsupported: " + uri);
		}

		// Update the entries
		int updateCount = db.update(isBookmark ? Bookmark.TABLE_NAME
				: Book.TABLE_NAME, values, selection, selectionArgs);

		// Notify resolver
		getContext().getContentResolver().notifyChange(uri, null);

		// Return the amount of rows altered
		return updateCount;
	}
}
