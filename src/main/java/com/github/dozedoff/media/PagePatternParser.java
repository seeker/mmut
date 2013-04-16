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

import java.util.LinkedList;
import java.util.List;

public class PagePatternParser {
	private static final String PATTERN_SYMBOL = "%%";
	public static List<String> parsePagePattern(String pattern, int pageStart, int pageEnd) throws IllegalArgumentException {
		List<String> relativePages = new LinkedList<>();

		if(pattern == null) {
			throw new IllegalArgumentException("Pattern cannot be null");
		}
		
		pageRangeCheck(pageStart, pageEnd);
		
		for(int i = pageStart; i < pageEnd; i++) {
			String relativePage = replacePattern(pattern, i);
			relativePages.add(relativePage);
		}
		
		return relativePages;
	}

	private static void pageRangeCheck(int pageStart, int pageEnd) throws IllegalArgumentException{
		if(pageStart < 0 || pageEnd < 0) {
			throw new IllegalArgumentException("Page values must be => 0");
		}
		
		if(pageStart > pageEnd) {
			throw new IllegalArgumentException("Start must be =< end");
		}
	}
	
	private static String replacePattern(String pattern, int page) {
		return pattern.replace(PATTERN_SYMBOL, String.valueOf(page));
	}
}
