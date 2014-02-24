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

import com.iia.data.DaoNews;
import com.iia.model.News;
import com.iia.data.ApplicationSQLiteOpenHelper;

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
 * ListNewsActivity class.
 * @author loic
 *
 */
public class ListNewsActivity extends Activity {

	/**To check if start activity is ok.*/
	private static final int REQUEST_CODE = 1000;
	/**ApplicationSQLiteOpenHelper.*/
	private ApplicationSQLiteOpenHelper myHelper;
	/**My news list.*/
	private ArrayList<News> news = new ArrayList<News>();

	/**
	 * @return the myHelper
	 */
	public final ApplicationSQLiteOpenHelper getMyHelper() {
		return myHelper;
	}

	/**
	 * @param vMyHelper the myHelper to set
	 */
	public final void setMyHelper(
			final ApplicationSQLiteOpenHelper vMyHelper) {
		this.myHelper = vMyHelper;
	}

	/**
	 * @return the news
	 */
	public final ArrayList<News> getNews() {
		return news;
	}

	/**
	 * @param vNews the news to set
	 */
	public final void setNews(final ArrayList<News> vNews) {
		this.news = vNews;
	}

	@Override
	protected final void onCreate(final Bundle savedInstState) {
		super.onCreate(savedInstState);
		this.setContentView(R.layout.activity_list_news);

		SQLiteDatabase myDataBase = null;
		try {
			myHelper = new ApplicationSQLiteOpenHelper(
					this,
					"database",
					null,
					getPackageManager().getPackageInfo(
							getPackageName(), 0).versionCode);
			myDataBase = myHelper.getWritableDatabase();
			final DaoNews daoN = new DaoNews(myDataBase);

			// Use my custom row
			news = daoN.getAllNews();
			this.showListView(news);

		}
		catch (Exception e) {
			Toast.makeText(ListNewsActivity.this,
					R.string.errorConnectionDatabase,
					Toast.LENGTH_LONG).show();
		} finally {
			if (myDataBase != null) {
				myDataBase.close();
			}
		}
		new MyTask().execute();
	}

	/**
	 * Show my News in ListView.
	 * @param myNews is the list to show.
	 */
	public final void showListView(final ArrayList<News> myNews) {
		final ArrayList<News> myNewsList = myNews;
		final NewsAdapter adapter = new NewsAdapter(
				ListNewsActivity.this,
				R.layout.row,
				myNewsList);

		final ListView list = (ListView) this.findViewById(R.id.listViewNews);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0,
					final View arg1,
					final int position,
					final long arg3) {
				final News myNews = myNewsList.get(position);

				final Intent intent = new Intent(ListNewsActivity.this,
						NewsActivity.class);

				final Bundle myBundle = new Bundle();
				myBundle.putSerializable("NEWS", myNews);
				intent.putExtras(myBundle);
				ListNewsActivity.this.startActivityForResult(intent,
						ListNewsActivity.REQUEST_CODE);
			}
		});
	}

	/**
	 * Use my row's style.
	 * @author loic
	 *
	 */
	public class NewsAdapter extends ArrayAdapter<News> {
		/**My ressource.*/
		private final int res;

		/**
		 * COnstructor.
		 * @param context is the context
		 * @param resource is the ressource
		 * @param items is the news list
		 */
		public NewsAdapter(final Context context, final int resource,
				final List<News> items) {
			super(context, resource, items);
			this.res = resource;
		}

		@Override
		@SuppressWarnings("deprecation")
		@SuppressLint("SimpleDateFormat")
		public final View getView(final int position, final View convertView,
				final ViewGroup parent) {
			final News item = this.getItem(position);

			final Typeface rucheFont = Typeface.createFromAsset(getAssets(),
					"fonts/Comfortaa-Bold.ttf");

			final LayoutInflater inf = LayoutInflater.from(getContext());
			final View theView = inf.inflate(this.res, null);

			final TextView txtViewDay = (TextView) theView.findViewById(
					R.id.row_day);
			txtViewDay.setText(String.valueOf(
					item.getPublicationDate().getDate()));
			txtViewDay.setTypeface(rucheFont);

			final TextView txtViewMonth = (TextView) theView.findViewById(
					R.id.row_month);

			final SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
			final String myMonth = monthFormat.format(
					item.getPublicationDate());

			txtViewMonth.setText(myMonth);
			txtViewMonth.setTypeface(rucheFont);

			final TextView txtViewTitle = (TextView) theView.findViewById(
					R.id.row_title);
			txtViewTitle.setText(item.getTitle());
			txtViewTitle.setTypeface(rucheFont);

			return theView;
		}
	}

	/**
	 * Use Asynchrone request
	 * @author loic
	 *
	 */
	private class MyTask extends AsyncTask<Void, Void, String> {

		/**My event loading.*/
		private ArrayList<News> entries;
		/**My SQLite open helper.*/
		private ApplicationSQLiteOpenHelper myHelper;

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(final Void... params) {
			String result = null;

			final SAXParserFactory myFactory = SAXParserFactory.newInstance();
			SAXParser myParser = null;
			entries = new ArrayList<News>();
			try {
				myParser = myFactory.newSAXParser();
			} catch (ParserConfigurationException e) {

			} catch (SAXException e) {

			}

			URL url = null;
			try {
				url = new URL("http://ruchenumerique.wordpress.com/feed/");
			} catch (MalformedURLException e1) {
				result = getString(R.string.newsErrorConnectionUpdate);
			}

			final NewsParser handler = new NewsParser();
			try {
				final InputStream input = url.openStream();
				if (input != null) {
					myParser.parse(input, handler);
					entries = handler.getData();
					SQLiteDatabase myDataBase = null;
					try {
						myHelper = new ApplicationSQLiteOpenHelper(
								ListNewsActivity.this,
								"database",
								null,
								getPackageManager().getPackageInfo(
										getPackageName(), 0).versionCode);
						myDataBase = myHelper.getWritableDatabase();
						final DaoNews daoN = new DaoNews(myDataBase);
						daoN.updateAllNews(entries);
						result = getString(
								R.string.newsSuccessConnectionUpdate);

					} catch (Exception e) {
						result = getString(
								R.string.newsErrorConnectionUpdate);
					} finally {
						if (myDataBase != null) {
							myDataBase.close();
						}
					}
				}
			} catch (SAXException e) {
				result = getString(R.string.newsErrorConnectionUpdate);
			} catch (IOException e) {
				result = getString(R.string.newsErrorConnectionUpdate);
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
				ListNewsActivity.this.showListView(entries);
			}
			Toast.makeText(ListNewsActivity.this,
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
