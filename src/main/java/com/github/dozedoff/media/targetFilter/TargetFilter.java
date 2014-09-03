/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.media.targetFilter;

import java.util.List;

import org.jsoup.nodes.Element;

public interface TargetFilter {
	public List<Element> parse();
}
