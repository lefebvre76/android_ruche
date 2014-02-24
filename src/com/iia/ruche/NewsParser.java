package com.iia.ruche;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.text.Html;

import com.iia.model.News;

/**
 * News Parser class.
 * @author loic
 *
 */
public class NewsParser extends DefaultHandler {

	/**My object.*/
	private static final String ITEM = "item";
	/**My title's object.*/
	private static final String TITLE = "title";
	/**My link object.*/
	private static final String LINK = "link";
	/**My date's object.*/
	private static final String PUBDATE = "pubDate";
	/**My description object.*/
	private static final String DESCRIPTION = "description";
	/**My news list.*/
	private ArrayList<News> myNews;
	/**To know if we are in the ITEM.*/
	private boolean inItem;
	/**My object.*/
	private News currentFeed;
	/**My buffer.*/
	private StringBuffer buffer;

	@Override
	public final void processingInstruction(final String target,
			final String data)
			throws SAXException {
		super.processingInstruction(target, data);
	}

	@Override
	public final void startDocument() throws SAXException {
		super.startDocument();
		myNews = new ArrayList<News>();

	}

	@Override
	public final void startElement(final String uri, final String localName,
			final String name, final Attributes attributes)
					throws SAXException {

		buffer = new StringBuffer();

		// If it's a new News
		if (localName.equalsIgnoreCase(ITEM)) {
			this.currentFeed = new News();
			inItem = true;
		}
	}

	@Override
	public final void endElement(final String uri, final String localName,
			final String name) throws SAXException {

		if (name.equals(TITLE)) {
			if (inItem) {
				this.currentFeed.setTitle(buffer.toString());
				buffer = null;
			}
		}
		if (localName.equalsIgnoreCase(PUBDATE)) {
			if (inItem) {
				final SimpleDateFormat format = new SimpleDateFormat(
						"EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
				try {
					final Date date = format.parse(buffer.toString());
					this.currentFeed.setPublicationDate(date);
				} catch (ParseException e) {

				}
				buffer = null;
			}
		}
		if (name.equals(DESCRIPTION)) {
			if (inItem) {
				this.currentFeed.setDescription(Html.fromHtml(
						buffer.toString()).toString());
				buffer = null;
			}
		}
		if (name.equals(LINK)) {
			if (inItem) {
				this.currentFeed.setLink(buffer.toString());
				buffer = null;
			}
		}
		if (name.equals(ITEM)) {
			myNews.add(currentFeed);
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
	 * get my news.
	 * @return my list news
	 */
	public final ArrayList<News> getData() {
		return myNews;
	}
}