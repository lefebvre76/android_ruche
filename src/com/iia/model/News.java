package com.iia.model;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * News class.
 * @author loic
 */
@SuppressLint("SimpleDateFormat")
public class News implements Serializable {

	/**
	 * my ID to serialize my object.
	 */
	private static final long serialVersionUID = 1058640032029388844L;

	/** News id.*/
	private int id;
	/** News title.*/
	private String title;
	/** News description.*/
	private String description;
	/** News publication date.*/
	private Date publicationDate;
	/** News Link.*/
	private String link;


	/**
	 * Empty constructor.
	 */
	public News() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * COnstructor.
	 * @param vId is my news id.
	 * @param vTitle is my news title.
	 * @param vDescription is my news description.
	 * @param vPublicationDate is my news publication date.
	 * @param vLink is my news link.
	 */
	public News(final int vId, final String vTitle,
			final String vDescription, final Date vPublicationDate,
			final String vLink) {
		super();
		this.id = vId;
		this.title = vTitle;
		this.description = vDescription;
		this.publicationDate = vPublicationDate;
		this.link = vLink;
	}

	/**
	 * Constructor.
	 * @param vId is my news id.
	 * @param vTitle is my news title.
	 * @param vDescription is my news description.
	 * @param dateISO8601 is publicationDate in ISO 8601
	 * @param vLink is my news link.
	 */
	public News(final int vId, final String vTitle,
			final String vDescription, final String dateISO8601,
			final String vLink) {
		super();
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date myDate = null;
		try {
			myDate = dateFormat.parse(dateISO8601);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.id = vId;
		this.title = vTitle;
		this.description = vDescription;
		this.publicationDate = myDate;
		this.link = vLink;
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param vId the id to set
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
		return description.substring(0, description.length() - 1);
	}

	/**
	 * @param vDescription the description to set.
	 */
	public final void setDescription(final String vDescription) {
		this.description = vDescription;
	}

	/**
	 * @return the publicationDate
	 */
	public final Date getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @return the publicationDate like string with ISO 8601 format
	 */
	@SuppressLint("SimpleDateFormat")
	public final String getPublicationDateISO8601() {
		final SimpleDateFormat myFormat =
				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String result = myFormat.format(this.publicationDate);
		return result;
	}

	/**
	 * @param vPublicationDate the publicationDate to set.
	 */
	public final void setPublicationDate(final Date vPublicationDate) {
		this.publicationDate = vPublicationDate;
	}

	/**
	 * @return the link
	 */
	public final String getLink() {
		return link;
	}

	/**
	 * @param vLink the link to set.
	 */
	public final void setLink(final String vLink) {
		this.link = vLink;
	}
}
