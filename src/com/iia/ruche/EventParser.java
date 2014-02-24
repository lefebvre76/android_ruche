package com.iia.ruche;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.iia.model.Event;

/**
 * Event parser class.
 * @author loic
 *
 */
public class EventParser extends DefaultHandler {

	/**My object.*/
	private static final String ITEM = "entry";
	/**My title's object.*/
	private static final String TITLE = "title";
	/**My place's object.*/
	private static final String DATE = "when";
	/**My dates' object.*/
	private static final String PLACE = "where";
	/**My description's object.*/
	private static final String DESCRIPTION = "content";
	/**My event list.*/
	private ArrayList<Event> myEvents;
	/**To know if we are in the ITEM.*/
	private boolean inItem;
	/**My object.*/
	private Event currentFeed;
	/**My buffer.*/
	private StringBuffer buffer;

	@Override
	public final void processingInstruction(final String target,
			final String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	@Override
	public final void startDocument() throws SAXException {
		super.startDocument();
		myEvents = new ArrayList<Event>();
	}

	@Override
	public final void startElement(final String uri, final String localName,
			final String name, final Attributes attributes)
					throws SAXException {

		buffer = new StringBuffer();

		if (localName.equalsIgnoreCase(ITEM)) {
			this.currentFeed = new Event();
			inItem = true;
		}

		if (localName.equalsIgnoreCase(DATE)) {
			final SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
			if (attributes.getValue("startTime") != null) {
				try {
					final Date date = format.parse(
							attributes.getValue("startTime"));
					this.currentFeed.setBegin(date);
				} catch (ParseException e) {

				}
				buffer = null;
			}
			if (attributes.getValue("endTime") != null) {
				try {
					final Date date = format.parse(
							attributes.getValue("endTime"));
					this.currentFeed.setEnd(date);
				} catch (ParseException e) {

				}
				buffer = null;
			}
		}
		// If it's the place
		if (localName.equalsIgnoreCase(PLACE)) {
			this.currentFeed.setPlace(attributes.getValue("valueString"));
		}
	}

	@Override
	public final void endElement(final String uri, final String localName,
			final String name) throws SAXException {

		if (name.equals(TITLE) && inItem) {
			this.currentFeed.setTitle(buffer.toString());
			buffer = null;
		}
		if (name.equals(DESCRIPTION) && inItem) {
			this.currentFeed.setDescription(buffer.toString());
			buffer = null;
		}
		if (name.equals(ITEM)) {
			myEvents.add(currentFeed);
			inItem = false;
		}
	}

	/**
	 * characters.
	 * @param myChar is the character.
	 * @param start is the number of the first character.
	 * @param length is the length of the tag.
	 * @throws SAXException use SAX.
	 */
	public final void characters(final char[] myChar, final int start,
			final int length) throws SAXException {
		final String reader = new String(myChar, start, length);
		if (buffer != null) {
			buffer.append(reader);
		}
	}

	/**
	 * Get my events of the rss.
	 * @return my events.
	 */
	public final ArrayList<Event> getData() {
		return this.myEvents;
	}
}