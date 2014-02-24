package com.iia.ruche;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * My main activity class.
 * @author loic
 */
public class MainActivity extends Activity {

	/**Constant to check if the activity is open.*/
	private static final int REQUEST_CODE = 1000;

	@Override
	protected final void onCreate(final Bundle savedInstState) {
		super.onCreate(savedInstState);
		setContentView(R.layout.activity_main);

		// Add Ruche's font
		final Typeface rucheFont = Typeface.createFromAsset(getAssets(),
				"fonts/Comfortaa-Bold.ttf");

		final Button buttonListEvent = (Button) this.findViewById(
				R.id.Events);
		buttonListEvent.setTypeface(rucheFont);
		final ImageButton imgButListE = (ImageButton) this.findViewById(
				R.id.imageEvent);
		final Button buttonInfos = (Button) this.findViewById(R.id.Infos);
		buttonInfos.setTypeface(rucheFont);
		final ImageButton imageButtonInfos = (ImageButton) this.findViewById(
				R.id.imageInfos);
		final Button buttonCome = (Button) this.findViewById(R.id.Come);
		buttonCome.setTypeface(rucheFont);
		final ImageButton imageButtonCome = (ImageButton) this.findViewById(
				R.id.imageCome);
		final Button buttonListNews = (Button) this.findViewById(R.id.News);
		buttonListNews.setTypeface(rucheFont);
		final ImageButton imgButListN = (ImageButton) this.findViewById(
				R.id.imageNews);

		buttonListEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openListEvent();
			}
		});

		imgButListE.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openListEvent();
			}
		});

		buttonInfos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openInfos();
			}
		});

		imageButtonInfos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openInfos();
			}
		});

		buttonCome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openCome();
			}
		});

		imageButtonCome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openCome();
			}
		});

		buttonListNews.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openNews();
			}
		});

		imgButListN.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				MainActivity.this.openNews();
			}
		});
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * open my activity Info.
	 */
	private void openInfos() {
		final Intent myIntent = new Intent(this, InfoActivity.class);
		this.startActivityForResult(myIntent, this.REQUEST_CODE);
	}

	/**
	 * open my news list Info.
	 */
	private void openNews() {
		final Intent myIntent = new Intent(this, ListNewsActivity.class);
		this.startActivityForResult(myIntent, this.REQUEST_CODE);
	}

	/**
	 * open my activity list event.
	 */
	private void openListEvent() {
		final Intent myIntent = new Intent(this, ListEventActivity.class);
		this.startActivityForResult(myIntent, this.REQUEST_CODE);
	}

	/**
	 * open my gps.
	 */
	private void openCome() {
		final String address = MainActivity.this.getString(R.string.address);
		final Intent myIntent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=" + address));
			startActivity(myIntent);
	}

}
