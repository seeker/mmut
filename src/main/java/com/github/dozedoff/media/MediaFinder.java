package com.github.dozedoff.media;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.db.Persistence;
import com.github.dozedoff.sources.Webpage;

public class MediaFinder {
	private static Logger logger = LoggerFactory.getLogger(MediaFinder.class);
	
	public static List<ResultLink> findAll() {
		LinkedList<ResultLink> results = new LinkedList<>();
		
		List<Webpage> pages = Persistence.getInstance().loadWebpage();
		
		for(Webpage page : pages) {
			URL url;
			try {
				url = new URL(page.getBaseUrl());
				Elements elements = WebpageParser.parsePage(url, page.getElementRegex());
				logger.info("Found {} entries for {}",elements.size(), page.getName());
			} catch (MalformedURLException e) {
				logger.warn("Unable to create url from {} for source {}", page.getBaseUrl(), page.getName());
			} catch (IOException e) {
				logger.warn("Unable to parse page {} for source {}", page.getBaseUrl(), page.getName());
			}
			
			
		}
		
		return results;
	}
}
