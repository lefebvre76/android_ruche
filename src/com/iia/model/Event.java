package com.iia.model;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * My Event Class.
 * @author Lefebvre Loic
 *
 */
public class Event implements Serializable {

	/**
	 * my ID to serialize my object.
	 */
	private static final long serialVersionUID = 3844469010427868566L;

	/** Event id.*/
	private int id;
	/** Event title.*/
	private String title;
	/** Event description.*/
	private String description;
	/** Event begin date time.*/
	private Date begin;
	/** Event begin date time.*/
	private Date end;
	/** Event place.*/
	private String place;

	/** My simpleDateFormat.*/
	@SuppressLint("SimpleDateFormat")
	private final SimpleDateFormat dateFormat =
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	/**
	 * Empty constructor.
	 */
	public Event() {
		super();
	}

	/**
	 * Constructor to create a new event. 
	 * @param vId my Event id.
	 * @param vTitle my Event title.
	 * @param vDescription my Event description.
	 * @param vPlace my Event place.
	 * @param vBegin my Event begining date.
	 * @param vEnd my Event ending date.
	 */
	public Event(final int vId, final String vTitle,
			final String vDescription, final String vPlace,
			final Date vBegin, final Date vEnd) {
		super();
		this.id = vId;
		this.title = vTitle;
		this.description = vDescription;
		this.place = vPlace;
		this.begin = vBegin;
		this.end = vEnd;
	}

	/**
	 * Constructor.
	 * @param vId my Event id.
	 * @param vTitle my Event title.
	 * @param vDescription my Event description
	 * @param vPlace my Event place.
	 * @param beginISO8601 my Event begining date in format ISO 8601.
	 * @param endISO8601 my Event ending date in format ISO 8601.
	 */
	public Event(final int vId, final String vTitle,
			final String vDescription, final String vPlace,
			final String beginISO8601, final String endISO8601) {
		super();
		this.id = vId;
		this.title = vTitle;
		this.description = vDescription;
		this.place = vPlace;
		try {
			this.begin = dateFormat.parse(beginISO8601);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		try {
			this.end = dateFormat.parse(endISO8601);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param vId the id to set.
	 */
	public final void setId(final int vId) {
		this.id = vId;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param vTitle the title to set.
	 */
	public final void setTitle(final String vTitle) {
		this.title = vTitle;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param vDescription the description to set.
	 */
	public final void setDescription(final String vDescription) {
		this.description = vDescription;
	}

	/**
	 * @return the begin
	 */
	public final Date getBegin() {
		return begin;
	}

	/**
	 * @param vBegin the begin to set
	 */
	public final void setBegin(final Date vBegin) {
		this.begin = vBegin;
	}

	/**
	 * @return the end
	 */
	public final Date getEnd() {
		return end;
	}

	/**
	 * @param vEnd the end to set
	 */
	public final void setEnd(final Date vEnd) {
		this.end = vEnd;
	}

	/**
	 * @return the place
	 */
	public final String getPlace() {
		return place;
	}

	/**
	 * @param vPlace the place to set.
	 */
	public final void setPlace(final String vPlace) {
		this.place = vPlace;
	}

	/**
	 * @return the begin date like string with ISO 8601 format
	 */
	public final String getBeginISO8601() {
		return dateFormat.format(this.begin);
	}

	/**
	 * @return the end date like string with ISO 8601 format
	 */
	public final String getEndISO8601() {
		return dateFormat.format(this.end);
	}

}
