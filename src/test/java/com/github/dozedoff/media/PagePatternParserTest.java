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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PagePatternParserTest {
	List<String> result;
	
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
		String[] items = {"page0", "page1", "page2"}; 
		assertThat(result.size(), is(3));
		assertThat(result, hasItems(items));
	}

	@Test
	public void testPatternWithPercent() {
		result = PagePatternParser.parsePagePattern("page%51", 0, 1);
		
		assertThat(result, hasItem("page%51"));
		assertThat(result.size(), is(1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullPattern() {
		result = PagePatternParser.parsePagePattern(null, 0, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPageRange() {
		result = PagePatternParser.parsePagePattern("foo", -1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPageRange2() {
		result = PagePatternParser.parsePagePattern("foo", 0, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSwappedPageRange() {
		result = PagePatternParser.parsePagePattern("foo", 2, 1);
	}
}
