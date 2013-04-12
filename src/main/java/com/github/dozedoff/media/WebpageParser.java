package com.github.dozedoff.media;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebpageParser {
	private static final int HTTP_TIMEOUT = 5000;
	
	public static Elements parsePage(URL url, String entryRegex) throws IOException {
		Document page = Jsoup.parse(url, HTTP_TIMEOUT);
		Elements entries = page.select(entryRegex);
		
		return entries;
	}
}
