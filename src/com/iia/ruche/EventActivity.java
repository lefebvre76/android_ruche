package com.iia.ruche;

import java.text.SimpleDateFormat;

import com.iia.model.Event;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Event Activity class.
 * @author loic
 *
 */
@SuppressLint("SimpleDateFormat")
public class EventActivity extends Activity {
	@Override
	protected final void onCreate(final Bundle savedInstState) {
		super.onCreate(savedInstState);
		this.setContentView(R.layout.activity_event);

		final Typeface rucheFont = Typeface.createFromAsset(getAssets(),
				"fonts/Comfortaa-Bold.ttf");

		final Intent intent = this.getIntent();
		final Event event =
				(Event) intent.getExtras().getSerializable("EVENT");

		// My date
		final SimpleDateFormat myFormatDate = new SimpleDateFormat(
				getString(R.string.formatDate));
		final String myDate = myFormatDate.format(event.getBegin());

		// My schedule
		final SimpleDateFormat myFormatHour = new SimpleDateFormat(
				getString(R.string.formatTime));
		final String departTime = myFormatHour.format(event.getBegin());
		final String endTime = myFormatHour.format(event.getEnd());
		final String schedule = departTime + "-" + endTime;

		final TextView txtSchedule = (TextView) this.findViewById(
				R.id.txtViewSchedule);
		final TextView txtTitle = (TextView) this.findViewById(
				R.id.txtViewTitle);
		final TextView txtDate = (TextView) this.findViewById(
				R.id.txtViewDate);
		final TextView txtDescription = (TextView) this.findViewById(
			R.id.txtViewDescription);

		txtTitle.setText(event.getTitle());
		txtDate.setText(myDate);
		txtDescription.setText(event.getDescription());
		txtSchedule.setText(schedule);

		txtTitle.setTypeface(rucheFont);
		txtDate.setTypeface(rucheFont);
		txtDescription.setTypeface(rucheFont);
		txtSchedule.setTypeface(rucheFont);

		this.setResult(Activity.RESULT_OK);
	}
}
