package com.iia.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.model.News;

/**
 * Dao News class.
 * @author loic
 *
 */
public class DaoNews {

	/** my news name.*/
	public static final String TABLE_NEWS = "news";

	/**my news's id col's name.*/
	public static final String NEWS_ID = "id";
	/**my news's title col's name.*/
	public static final String NEWS_TITLE = "title";
	/**my news's description col's name.*/
	public static final String NEWS_DESCRIPTION = "description";
	/**my news's publication date col's name.*/
	public static final String NEWS_DATE = "publication_date";
	/**my news's link col's name.*/
	public static final String NEWS_LINK = "link";

	/**my database.*/
	private SQLiteDatabase myDatabase;

	/**
	 * @return the myDatabase
	 */
	public final SQLiteDatabase getMyDatabase() {
		return myDatabase;
	}

	/**
	 * @param vDatabase the myDatabase to set
	 */
	public final void setMyDatabase(final SQLiteDatabase vDatabase) {
		this.myDatabase = vDatabase;
	}

	/**
	 * COnstructor.
	 * @param vDatabase is my database
	 */
	public DaoNews(final SQLiteDatabase vDatabase) {
		this.myDatabase = vDatabase;
	}

	/**
	 * Get my shema to create my Table NEWS.
	 * @return my sql request to create my Table NEWS.
	 */
	public static String getShema() {
		return "CREATE TABLE " + TABLE_NEWS + " ("
				+ NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ NEWS_TITLE + " TEXT, "
				+ NEWS_DESCRIPTION + " TEXT, "
				+ NEWS_DATE + " DATETIME, "
				+ NEWS_LINK + " TEXT "
				+ ")";
	}

	/**
	 * Insert myNews in table NEWS.
	 * @param myNews to insert in database.
	 */
	public final void insert(final News myNews) {
		// Creat contentValue
		final ContentValues myContent = new ContentValues();
		myContent.put(DaoNews.NEWS_TITLE, myNews.getTitle());
		myContent.put(DaoNews.NEWS_DESCRIPTION, myNews.getDescription());
		myContent.put(DaoNews.NEWS_DATE, myNews.getPublicationDateISO8601());
		myContent.put(DaoNews.NEWS_LINK, myNews.getLink());

		this.myDatabase.insert(TABLE_NEWS, null, myContent);
	}

	/**
	 * Update myNews in table NEWS.
	 * @param myNews to update in my database.
	 */
	public final void update(final News myNews) {
		// Create contentValue
		final ContentValues myContent = new ContentValues();
		myContent.put(DaoNews.NEWS_TITLE, myNews.getTitle());
		myContent.put(DaoNews.NEWS_DESCRIPTION, myNews.getDescription());
		myContent.put(DaoNews.NEWS_DATE, myNews.getPublicationDateISO8601());
		myContent.put(DaoNews.NEWS_LINK, myNews.getLink());

		final String[] whereClause = {String.valueOf(myNews.getId())};

		this.myDatabase.update(TABLE_NEWS, myContent, NEWS_ID, whereClause);
	}

	/**
	 * Delete myNews in table NEWS.
	 * @param myNews to delete.
	 */
	public final void delete(final News myNews) {
		final String[] whereClause = {String.valueOf(myNews.getId())};
		this.myDatabase.delete(TABLE_NEWS, NEWS_ID, whereClause);
	}

	/**
	 * Get all news in my table NEWS.
	 * @return ArrayList<News> All my news in my table.
	 */
	public final ArrayList<News> getAllNews() {

		final ArrayList<News> result = new ArrayList<News>();
		News myNews;

		final String[] cols = {NEWS_ID,
				NEWS_TITLE,
				NEWS_DESCRIPTION,
				NEWS_DATE,
				NEWS_LINK};

		final Cursor myCursor = this.myDatabase.query(TABLE_NEWS, cols,
				null, null, null, null, null);

		if (myCursor.getCount() > 0) {
			myCursor.moveToFirst();
			do {
				myNews = new News(
						myCursor.getInt(
								myCursor.getColumnIndex(NEWS_ID)),
						myCursor.getString(
								myCursor.getColumnIndex(NEWS_TITLE)),
						myCursor.getString(
								myCursor.getColumnIndex(NEWS_DESCRIPTION)), 
						myCursor.getString(
								myCursor.getColumnIndex(NEWS_DATE)), 
						myCursor.getString(
								myCursor.getColumnIndex(NEWS_LINK)));
				result.add(myNews);
			} while (myCursor.moveToNext());

		}

		return result;
	}

	/**
	 * Update my Table NEWS.
	 * @param myNewsList to add in my database
	 */
	public final void updateAllNews(final ArrayList<News> myNewsList) {
		this.myDatabase.execSQL("DELETE FROM " + TABLE_NEWS);
		for (int i = 0; i < myNewsList.size(); i++) {
			this.insert(myNewsList.get(i));
		}
	}

}
