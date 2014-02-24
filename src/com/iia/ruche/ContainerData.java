package com.iia.ruche;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.iia.model.News;

import android.content.Context;

/**
 * Container data class.
 * @author loic
 *
 */
public class ContainerData {

	/** The context.*/
	private static Context context;

	/**
	 * @return the context of my container.
	 */
	public static final Context getContext() {
		return context;
	}

	/**
	 * @param vContext the context to set.
	 */
	public static final void setContext(final Context vContext) {
		ContainerData.context = vContext;
	}

	/**
	 * get my news.
	 * @return my News' List
	 */
	public static ArrayList<News> getFeeds() {
		final SAXParserFactory myFactory = SAXParserFactory.newInstance();
		SAXParser parseur = null;
		URL url = null;
		ArrayList<News> entries = null;
		InputStream input;
		try {
			parseur = myFactory.newSAXParser();
		} catch (ParserConfigurationException e) {

		} catch (SAXException e) {

		}

		try {
			url = new URL("http://ruchenumerique.wordpress.com/feed/");
		} catch (MalformedURLException e1) {

		}

		final DefaultHandler handler = new NewsParser();
		try {
			input = url.openStream();
			if (input != null) {
				parseur.parse(input, handler);
				entries = ((NewsParser) handler).getData();
			}
		} catch (SAXException e) {

		} catch (IOException e) {

		}
		return entries;
	}

}