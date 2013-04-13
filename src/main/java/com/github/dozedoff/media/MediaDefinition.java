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

import com.github.dozedoff.sources.Webpage;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MediaDefinition {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false, foreign=true)
	Webpage parent;
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	private LinkedList<String> whitelist;
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	private LinkedList<String> blacklist;
	@DatabaseField(canBeNull=false)
	private TargetType type;
	
	/**
	 * Intended for DAO
	 */
	@Deprecated
	public MediaDefinition() {}

	public MediaDefinition(TargetType type, Webpage parent) {
		this.type = type;
		this.parent = parent;
		whitelist = new LinkedList<>();
		blacklist = new LinkedList<>();
	}
	
	private boolean isValidEntry(String entry) {
		if(entry == null || entry.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean addToWhitelist(String entry) {
		if(! isValidEntry(entry)) {
			return false;
		}
		
		whitelist.add(entry);
		
		return true;
	}
	
	public boolean removeFromWhitelist(String entry) {
		if(! isValidEntry(entry)) {
			return false;
		}
		
		return whitelist.remove(entry);
	}
	
	public boolean addToBlacklist(String entry) {
		if(! isValidEntry(entry)) {
			return false;
		}
		
		blacklist.add(entry);
		
		return true;
	}
	
	public boolean removeFromBlacklist(String entry) {
		if(! isValidEntry(entry)) {
			return false;
		}
		
		return blacklist.remove(entry);
	}

	public TargetType getType() {
		return type;
	}
}
