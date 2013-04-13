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
package com.github.dozedoff.media;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
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

				List<Element> entries =  filterDefinitions(page.getDefinitions(), elements);
				logger.info("{} entries left for {} after filtering", entries.size(), page.getName());
				
				
								
			} catch (MalformedURLException e) {
				logger.warn("Unable to create url from {} for source {}", page.getBaseUrl(), page.getName());
			} catch (IOException e) {
				logger.warn("Unable to parse page {} for source {}", page.getBaseUrl(), page.getName());
			}
		}
		
		return results;
	}

	private static List<Element> filterDefinitions(List<MediaDefinition> definitions, Elements elements) {
		LinkedList<Element> acceptedEntries = new LinkedList<>();
		DefinitionFilter defFilter = new DefinitionFilter();
		
		for(MediaDefinition def : definitions) {
			List<Element> result = defFilter.filterDefinition(def, elements);
			acceptedEntries.addAll(result);
		}
		
		return acceptedEntries;
	}
}
