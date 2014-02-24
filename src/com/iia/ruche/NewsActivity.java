package com.iia.ruche;

import java.text.SimpleDateFormat;

import com.iia.model.News;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * my news activity class.
 * @author loic
 */
@SuppressLint("SimpleDateFormat")
public class NewsActivity extends Activity {
	@Override
	protected final void onCreate(final Bundle savedInstState) {
		super.onCreate(savedInstState);
		this.setContentView(R.layout.activity_news);

		final Typeface rucheFont = Typeface.createFromAsset(getAssets(),
				"fonts/Comfortaa-Bold.ttf");

		final Intent intent = this.getIntent();
		final News news = (News) intent.getExtras().getSerializable("NEWS");

		final SimpleDateFormat formatDate = new SimpleDateFormat(
				getString(R.string.formatDate));
		final String myDate = formatDate.format(news.getPublicationDate());

		final TextView txtTitle = (TextView) this.findViewById(
				R.id.txtViewTitleEvent);
		final TextView txtDate = (TextView) this.findViewById(
				R.id.txtViewDateEvent);
		final TextView txtDescription = (TextView) this.findViewById(
			R.id.txtViewDescriptionEvent);
		final Button butDetails = (Button) this.findViewById(
			R.id.buttonDetails);
		txtTitle.setText(news.getTitle());
		txtTitle.setTypeface(rucheFont);
		txtDescription.setText(news.getDescription());
		txtDescription.setTypeface(rucheFont);
		txtDate.setText(myDate);
		txtDate.setTypeface(rucheFont);
		butDetails.setTypeface(rucheFont);

		butDetails.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				final Intent browserIntent = new Intent(
						Intent.ACTION_VIEW, Uri.parse(news.getLink()));
				startActivity(browserIntent);
			}
		});
		this.setResult(Activity.RESULT_OK);
	}
}
