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
