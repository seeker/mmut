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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefinitionFilter {
	private static final Logger logger = LoggerFactory.getLogger(DefinitionFilter.class);
	
	public List<Element> filterDefinition(MediaDefinition def, List<Element> elements) {
		LinkedList<Element> filteredElements = new LinkedList<>();
		
		try{
			Pattern.compile(def.getWhitelist());
			Pattern.compile(def.getBlacklist());
		}catch (PatternSyntaxException e) {
			Object[] logdata = {def.getName(), def.getParent(), e};
			logger.warn("Unable to compile regex for {} ({})", logdata);
			return filteredElements;
		}
		
		for(Element node : elements) {
			String html = node.outerHtml(); //TODO look for title or href descriptions
			
			if(isAccepted(html, def)) {
				filteredElements.add(node);
			}
		}
		Object[] logdata = {filteredElements.size(), def.getName(), def.getParent()};
		logger.info("{} entries left for {} ({}) after filtering", logdata);
		
		return filteredElements;
	}
	
	private boolean isAccepted(String name, MediaDefinition def) {
		//FIXME replace .contains() with regex matcher
		if(name.contains(def.getWhitelist()) && !name.contains((def.getBlacklist()))) {
			return true;
		} else {
			return false;
		}
	}
}
