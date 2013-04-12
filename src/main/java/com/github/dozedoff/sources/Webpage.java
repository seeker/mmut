/*  Copyright (C) 2013  Nicholas Wright
    
    This file is part of mmut - Multi-media update tracker
    
    mmut is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.github.dozedoff.sources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.dozedoff.media.ResultLink;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Webpage {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false)
	private String name;
	@DatabaseField(canBeNull=false)
	private String elementRegex;
	@DatabaseField(canBeNull=false)
	private String baseUrl;
	@DatabaseField(canBeNull=false)
	private String pagePattern;
	@DatabaseField(canBeNull=false)
	private int numOfPages;
	
	
	private final int HTTP_TIMEOUT = 5000;
	
	/**
	 * Intended for DAO
	 */
	@Deprecated
	public Webpage() {}

	
	public Webpage(String name, String baseUrl, String pagePattern, int numOfPages,
			String elementRegex) {
		this.name = name;
		this.baseUrl = baseUrl;
		this.pagePattern = pagePattern;
		this.numOfPages = numOfPages;
		this.elementRegex = elementRegex;
	}


	public List<ResultLink> getLinks() throws MalformedURLException {
		LinkedList<ResultLink> links = new LinkedList<ResultLink>();
		LinkedList<URL> pageUrls = makePageUrls();
		
		for(URL u : pageUrls){
			
		}
		return links;
	}
	
	private LinkedList<ResultLink> parsePage(URL url) throws IOException {
		LinkedList<ResultLink> links = new LinkedList<ResultLink>();
		Document page = Jsoup.parse(url, HTTP_TIMEOUT);
		Elements entries = page.select(elementRegex);
		
		for(Element entry : entries) {
			//TODO get href name and link
			
			
		}
		
		return links;
	}

	private LinkedList<URL> makePageUrls() throws MalformedURLException {
		// TODO generate URLs from baseUrl and page pattern with numOfPages
		LinkedList<URL> urls = new LinkedList<URL>();
		urls.add(new URL(baseUrl));
		return urls;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
