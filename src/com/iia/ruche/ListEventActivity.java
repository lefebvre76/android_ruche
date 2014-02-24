package com.iia.ruche;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.iia.model.Event;
import com.iia.data.ApplicationSQLiteOpenHelper;
import com.iia.data.DaoEvent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ListEventActivity Class.
 * @author loic
 *
 */
public class ListEventActivity extends Activity {

	/**To check if start activity is ok.*/
	private static final int REQUEST_CODE = 1000;
	/**ApplicationSQLiteOpenHelper.*/
	private ApplicationSQLiteOpenHelper myHelper;
	/**My event list.*/
	private ArrayList<Event> event = new ArrayList<Event>();

	@Override
	protected final void onCreate(final Bundle savedInstState) {
		super.onCreate(savedInstState);
		this.setContentView(R.layout.activity_list_event);
		SQLiteDatabase myDatabase = null;
		try {
			myHelper = new ApplicationSQLiteOpenHelper(
					this,
					"database",
					null,
					getPackageManager().getPackageInfo(
							getPackageName(), 0).versionCode);
			myDatabase = myHelper.getWritableDatabase();
			final DaoEvent daoE = new DaoEvent(myDatabase);

			// Use my custom row
			event = daoE.getAllEvent();
			this.showListView(event);

		}
		catch (Exception e) {
			Toast.makeText(ListEventActivity.this,
					R.string.errorConnectionDatabase,
					Toast.LENGTH_LONG).show();
		} finally {
			if (myDatabase != null) {
				myDatabase.close();
			}
		}
		new MyTask().execute();
	}

	/**
	 * Show my listView with the event
	 * @param events to show.
	 */
	public final void showListView(final ArrayList<Event> events) {
		final ArrayList<Event> myEventList = events;
		final EventAdapter adapter = new EventAdapter(
				ListEventActivity.this,
				R.layout.row,
				myEventList);

		// Show my Event list in a listView
		final ListView list = (ListView) this.findViewById(
				R.id.listViewEvent);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0,
					final View myView,
					final int position,
					final long arg3) {
				final Event myEvent = myEventList.get(position);
				final Intent intent = new Intent(ListEventActivity.this,
						EventActivity.class);

				final Bundle myB = new Bundle();
				myB.putSerializable("EVENT", myEvent);
				intent.putExtras(myB);
				ListEventActivity.this.startActivityForResult(intent,
						ListEventActivity.REQUEST_CODE);
			}
		});
	}

	/**
	 * Use my row's style.
	 * @author loic
	 *
	 */
	@SuppressLint("SimpleDateFormat")
	public class EventAdapter extends ArrayAdapter<Event> {
		/**My ressource.*/
		private final int res;

		/**
		 * COnstructor.
		 * @param context of my View
		 * @param resource is my adapter ressource
		 * @param items is my event's list
		 */
		public EventAdapter(final Context context, final int resource,
				final List<Event> items) {
			super(context, resource, items);
			this.res = resource;
		}

		@Override
		@SuppressWarnings("deprecation")
		public final View getView(final int position, final View convertView,
				final ViewGroup parent) {

			final Event item = this.getItem(position);
			final Typeface rucheFont = Typeface.createFromAsset(getAssets(),
					"fonts/Comfortaa-Bold.ttf");

			final LayoutInflater inf = LayoutInflater.from(getContext());
			final View theListView = inf.inflate(this.res, null);

			final TextView txtViewDay = (TextView) theListView.findViewById(
					R.id.row_day);
			txtViewDay.setText(String.valueOf(
					item.getBegin().getDate()));
			txtViewDay.setTypeface(rucheFont);

			final TextView txtViewMonth = (TextView) theListView.findViewById(
					R.id.row_month);

			/* Get my abbreviated month */
			final SimpleDateFormat myFormat = new SimpleDateFormat("MMM");
			final String myMonth = myFormat.format(item.getBegin());

			txtViewMonth.setText(myMonth);
			txtViewMonth.setTypeface(rucheFont);

			final TextView txtViewTitle = (TextView) theListView.findViewById(
					R.id.row_title);
			txtViewTitle.setText(item.getTitle());
			txtViewTitle.setTypeface(rucheFont);

			return theListView;
		}
	}

	/**
	 * Use Asynchrone request.
	 * @author loic
	 *
	 */
	private class MyTask extends AsyncTask<Void, Void, String> {

		/**My event loading.*/
		private ArrayList<Event> entries;
		/**My SQLite open helper.*/
		private ApplicationSQLiteOpenHelper myHelper;

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(final Void... params) {

			String result = null;
			final SAXParserFactory myFactory = SAXParserFactory.newInstance();
			entries = new ArrayList<Event>();
			SAXParser myParser = null;
			try {
				myParser = myFactory.newSAXParser();
			} catch (ParserConfigurationException e) {

			} catch (SAXException e) {

			}

			URL url = null;
			try {
				url = new URL("http://www.google.com/calendar/feeds/"
						+ "ruchenumerique%40gmail.com/public/full?"
						+ "singleevents=true&futureevents=true"
						+ "&orderby=starttime&sortorder=ascending");
			} catch (MalformedURLException e1) {
				result = getString(R.string.eventErrorConnectionUpdate);
			}

			final EventParser handler = new EventParser();
			try {
				final InputStream input = url.openStream();
				if (input != null) {
					myParser.parse(input, handler);
					entries = handler.getData();
					SQLiteDatabase myDatabase = null;
					try {
						myHelper = new ApplicationSQLiteOpenHelper(
								ListEventActivity.this,
								"database",
								null,
								getPackageManager().getPackageInfo(
										getPackageName(), 0).versionCode);
						myDatabase = myHelper.getWritableDatabase();
						final DaoEvent daoE = new DaoEvent(myDatabase);
						daoE.updateAllEvent(entries);
						result = getString(
								R.string.eventSuccessConnectionUpdate);
					}
					catch (Exception e) {
						result = getString(
								R.string.eventErrorConnectionUpdate);
					} finally {
						if (myDatabase != null) {
							myDatabase.close();
						}
					}
				}
			} catch (SAXException e) {
				result = getString(R.string.eventErrorConnectionUpdate);
			} catch (IOException e) {
				result = getString(R.string.eventErrorConnectionUpdate);
			}
			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(final String result) {
			super.onPostExecute(result);
			if (entries .size() > 0) {
				ListEventActivity.this.showListView(entries);
			}
			Toast.makeText(ListEventActivity.this,
					result,
					Toast.LENGTH_LONG).show();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
}
