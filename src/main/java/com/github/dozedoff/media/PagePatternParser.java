/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.media;

import java.util.LinkedList;
import java.util.List;

public class PagePatternParser {
	private static final String PATTERN_SYMBOL = "%%";

	public static List<String> parsePagePattern(String pattern, int pageStart, int pageEnd) throws IllegalArgumentException {
		List<String> relativePages = new LinkedList<>();

		if (pattern == null) {
			throw new IllegalArgumentException("Pattern cannot be null");
		}

		pageRangeCheck(pageStart, pageEnd);

		if (!pattern.contains(PATTERN_SYMBOL)) {
			relativePages.add(pattern);
			return relativePages;
		}

		for (int i = pageStart; i <= pageEnd; i++) {
			String relativePage = replacePattern(pattern, i);
			relativePages.add(relativePage);
		}

		return relativePages;
	}

	private static void pageRangeCheck(int pageStart, int pageEnd) throws IllegalArgumentException {
		if (pageStart < 0 || pageEnd < 0) {
			throw new IllegalArgumentException("Page values must be => 0");
		}

		if (pageStart > pageEnd) {
			throw new IllegalArgumentException("Start must be =< end");
		}
	}

	private static String replacePattern(String pattern, int page) {
		return pattern.replace(PATTERN_SYMBOL, String.valueOf(page));
	}
}
