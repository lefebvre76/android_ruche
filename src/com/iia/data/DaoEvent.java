package com.iia.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.model.Event;

/**
 * DaoEvent CLass.
 * @author loic
 *
 */
public class DaoEvent {

	/** my table name.*/
	public static final String TABLE_EVENT = "event";
	/**my event's id col's name.*/
	public static final String EVENT_ID = "id";
	/**my event's title col's name.*/
	public static final String EVENT_TITLE = "title";
	/**my event's description col's name.*/
	public static final String EVENT_DESCRIPTION = "description";
	/**my event's begin's date col's name.*/
	public static final String EVENT_BEGIN = "date_begin";
	/**my event's end's date col's name.*/
	public static final String EVENT_END = "date_end";
	/**my event's place col's name.*/
	public static final String EVENT_PLACE = "place";

	// Attributs
	/** my DaoEvent's database.*/
	private SQLiteDatabase myDatabase;

	/**
	 * @param vDatabase the myDatabase to set
	 */
	public final void setMyDatabase(final SQLiteDatabase vDatabase) {
		this.myDatabase = vDatabase;
	}

	/**
	 * @return the myDatabase
	 */
	public final SQLiteDatabase getMyDatabase() {
		return myDatabase;
	}
	/**
	 * Constructor.
	 * @param vDatabase is the database
	 */
	public DaoEvent(final SQLiteDatabase vDatabase) {
		this.myDatabase = vDatabase;
	}

	/**
	 * return my create table.
	 * @return my request to create my table Event
	 */
	public static String getShema() {
		return "CREATE TABLE " + TABLE_EVENT + " ("
				+ EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ EVENT_TITLE + " TEXT, "
				+ EVENT_DESCRIPTION + " TEXT, "
				+ EVENT_PLACE + " TEXT, "
				+ EVENT_BEGIN + " DATETIME, "
				+ EVENT_END + " DATETIME)";
	}

	/**
	 * Insert an event in database.
	 * @param myEvent to insert in database.
	 */
	public final void insert(final Event myEvent) {
		// Creat contentValue
		final ContentValues myContent = new ContentValues();
		myContent.put(DaoEvent.EVENT_TITLE, myEvent.getTitle());
		myContent.put(DaoEvent.EVENT_DESCRIPTION, myEvent.getDescription());
		myContent.put(DaoEvent.EVENT_BEGIN, myEvent.getBeginISO8601());
		myContent.put(DaoEvent.EVENT_END, myEvent.getEndISO8601());
		myContent.put(DaoEvent.EVENT_PLACE, myEvent.getPlace());

		this.myDatabase.insert(TABLE_EVENT, null, myContent);
	}

	/**
	 * update an event in database.
	 * @param myEvent to update
	 */
	public final void update(final Event myEvent) {

		final ContentValues myContent = new ContentValues();
		myContent.put(DaoEvent.EVENT_TITLE, myEvent.getTitle());
		myContent.put(DaoEvent.EVENT_DESCRIPTION, myEvent.getTitle());
		myContent.put(DaoEvent.EVENT_BEGIN, myEvent.getTitle());
		myContent.put(DaoEvent.EVENT_END, myEvent.getTitle());
		myContent.put(DaoEvent.EVENT_PLACE, myEvent.getPlace());

		final String[] whereClause = {String.valueOf(myEvent.getId())};

		this.myDatabase.update(TABLE_EVENT, myContent, EVENT_ID,
				whereClause);
	}

	/**
	 * delete an event to database.
	 * @param myEvent to delete
	 */
	public final void delete(final Event myEvent) {
		final String[] whereClause = {String.valueOf(myEvent.getId())};
		this.myDatabase.delete(TABLE_EVENT, EVENT_ID, whereClause);
	}

	/**
	 * get all my event to my database.
	 * @return my event list
	 */
	public final ArrayList<Event> getAllEvent() {

		final ArrayList<Event> result = new ArrayList<Event>();
		Event myEvent;

		final String[] cols = {EVENT_ID,
				EVENT_TITLE,
				EVENT_DESCRIPTION,
				EVENT_PLACE,
				EVENT_BEGIN,
				EVENT_END};

		final Cursor myCursor = this.myDatabase.query(TABLE_EVENT, cols,
				null, null, null, null, null);

		if (myCursor.getCount() > 0) {
			myCursor.moveToFirst();
			do {
				myEvent = new Event(
						myCursor.getInt(myCursor.getColumnIndex(EVENT_ID)),
						myCursor.getString(
								myCursor.getColumnIndex(EVENT_TITLE)),
						myCursor.getString(
								myCursor.getColumnIndex(EVENT_DESCRIPTION)),
						myCursor.getString(
								myCursor.getColumnIndex(EVENT_PLACE)),
						myCursor.getString(
								myCursor.getColumnIndex(EVENT_BEGIN)),
						myCursor.getString(
								myCursor.getColumnIndex(EVENT_END)));

				result.add(myEvent);
			} while (myCursor.moveToNext());

		}
		return result;
	}

	/**
	 * Update my Table EVENT.
	 * @param myEventsList to add in my database
	 */
	public final void updateAllEvent(final ArrayList<Event> myEventsList) {
		this.myDatabase.execSQL("DELETE FROM " + TABLE_EVENT);
		for (int i = 0; i < myEventsList.size(); i++) {
			this.insert(myEventsList.get(i));
		}
	}

}
