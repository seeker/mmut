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
package com.github.dozedoff.db;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.sources.Webpage;

public class Persistence {
	private static Persistence instance = null;
	private static Logger logger = LoggerFactory.getLogger(Persistence.class);
	private Persistence() {}
	
	public static Persistence getInstance() {
		if(instance == null) {
			instance = new Persistence();
		}
		
		return instance;
	}
	
	public void saveWebpage(Webpage page) {
		//TODO method stub
		logger.warn("Method not implemented!");
	}
	
	public List<Webpage> loadWebpage() {
		LinkedList<Webpage> pages = new LinkedList<Webpage>();
		//TODO method stub
		logger.warn("Method not implemented!");
		return pages;
	}
	
	public void deleteWebpage(Webpage page) {
		//TODO method stub
		logger.warn("Method not implemented!");
	}
}
