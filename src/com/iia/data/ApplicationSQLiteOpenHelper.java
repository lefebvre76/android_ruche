package com.iia.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * Class ApplicationSQLiteOpenHelper.
 * @author loic
 *
 */
public class ApplicationSQLiteOpenHelper extends SQLiteOpenHelper {

	/**
	 * Constructor.
	 * @param context the context.
	 * @param name the database's name.
	 * @param factory the factory (generally null).
	 * @param version the version.
	 */
	public ApplicationSQLiteOpenHelper(final Context context, final String name,
			final CursorFactory factory, final int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(
	 * android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public final void onCreate(final SQLiteDatabase myDatabase) {
		// Creat shema
		myDatabase.execSQL(DaoEvent.getShema());
		myDatabase.execSQL(DaoNews.getShema());
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(
	 * android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase myDatabase, final int oldVersion,
			final int newVersion) {
		// TODO Auto-generated method stub
	}
}
