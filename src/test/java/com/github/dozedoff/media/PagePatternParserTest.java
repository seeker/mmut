/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.media;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PagePatternParserTest {
	List<String> result;
	private final String[] items = { "page0", "page1", "page2" };

	@Before
	public void setUp() throws Exception {
		result = null;
	}

	@Test
	public void testNoPattern() {
		result = PagePatternParser.parsePagePattern("page", 0, 1);

		assertThat(result, hasItem("page"));
		assertThat(result.size(), is(1));
	}

	@Test
	public void testSimplePattern() {
		result = PagePatternParser.parsePagePattern("page%%", 0, 2);

		assertThat(result, hasItems(items));
		assertThat(result.size(), is(3));
	}

	@Test
	public void testPatternWithPercent() {
		result = PagePatternParser.parsePagePattern("page%51", 0, 1);

		assertThat(result, hasItem("page%51"));
		assertThat(result.size(), is(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPattern() {
		result = PagePatternParser.parsePagePattern(null, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPageRange() {
		result = PagePatternParser.parsePagePattern("foo", -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPageRange2() {
		result = PagePatternParser.parsePagePattern("foo", 0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSwappedPageRange() {
		result = PagePatternParser.parsePagePattern("foo", 2, 1);
	}
}
