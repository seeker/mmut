package com.github.dozedoff.media;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class URLgenerator {
	public LinkedList<URL> makePageUrls(String baseUrl, String pagePattern, int numberOfPages) throws MalformedURLException {
		// TODO generate URLs from baseUrl and page pattern with numOfPages
		LinkedList<URL> urls = new LinkedList<URL>();
		URL base = new URL(baseUrl);
		urls.add(base);
		
		List<String> relativePages = PagePatternParser.parsePagePattern(pagePattern, 0, numberOfPages);
		
		for(String relativePage : relativePages) {
			urls.add(new URL(base, relativePage));
		}
		
		return urls;
	}
}
