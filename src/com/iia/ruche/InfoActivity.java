package com.iia.ruche;

import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
 * InfoActivity class.
 * @author loic
 *
 */
public class InfoActivity extends Activity {

	/**The google map zoom.*/
	private static final int ZOOM = 13;
	/**Ruche latitude.*/
	private static final double LATITUDE = 47.99451;
	/**Ruche longitude.*/
	private static final double LONGITUDE = 0.192383;
	@Override
	protected final void onCreate(final Bundle savedInstState) {
		super.onCreate(savedInstState);
		this.setContentView(R.layout.activity_infos);

		final Typeface rucheFont = Typeface.createFromAsset(getAssets(),
				"fonts/Comfortaa-Regular.ttf");

		final TextView tVLblAddress = (TextView) this.findViewById(
				R.id.txtViewLblAddress);
		tVLblAddress.setTypeface(rucheFont);

		final TextView tVAddress = (TextView) this.findViewById(
				R.id.txtViewAddress);
		tVAddress.setTypeface(rucheFont);

		final TextView tVDistrict = (TextView) this.findViewById(
				R.id.txtViewDistrict);
		tVDistrict.setTypeface(rucheFont);

		final TextView tVBuilding = (TextView) this.findViewById(
				R.id.txtViewBuilding);
		tVBuilding.setTypeface(rucheFont);

		final TextView tVLblPhone = (TextView) this.findViewById(
				R.id.txtViewLblPhone);
		tVLblPhone.setTypeface(rucheFont);

		final Button bPhone = (Button) this.findViewById(R.id.buttonPhone);
		bPhone.setTypeface(rucheFont);

		final TextView tvLblMail = (TextView) this.findViewById(
				R.id.txtViewLblMail);
		tvLblMail.setTypeface(rucheFont);

		final Button bMail = (Button) this.findViewById(R.id.buttonMail);
		bMail.setTypeface(rucheFont);

		// Get a handle to the Map Fragment
		final GoogleMap map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

        final LatLng ruchePosition = new LatLng(LATITUDE, LONGITUDE);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ruchePosition, ZOOM));

        final String description = InfoActivity.this.getString(
        		R.string.description);

        map.addMarker(new MarkerOptions()
                .title("La ruche numérique")
                .snippet(description)
                .position(ruchePosition));

        bPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View myView) {
				final Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:+33243210001"));
				startActivity(callIntent);
			}
		});

        bMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View myView) {
				final Intent mailIntent = new Intent(Intent.ACTION_SEND);
				mailIntent.setType("message/rfc822");
				mailIntent.putExtra(Intent.EXTRA_EMAIL,
						new String[]{getString(R.string.mail)});
				startActivity(Intent.createChooser(mailIntent,
						"Send mail..."));
			}
		});
	}
}
