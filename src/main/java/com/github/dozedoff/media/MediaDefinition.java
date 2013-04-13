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

import com.github.dozedoff.sources.Webpage;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MediaDefinition {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false, foreign=true)
	Webpage parent;
	@DatabaseField(canBeNull=false)
	String name;
	@DatabaseField(canBeNull=false)
	private String whitelist;
	@DatabaseField(canBeNull=false)
	private String blacklist;
	@DatabaseField(canBeNull=false)
	private TargetType type;
	
	/**
	 * Intended for DAO
	 */
	@Deprecated
	public MediaDefinition() {}

	public MediaDefinition(String name, TargetType type, Webpage parent) {
		this.name = name;
		this.type = type;
		this.parent = parent;
		whitelist = "";
		blacklist = "";
	}

	public TargetType getType() {
		return type;
	}

	public void setType(TargetType type) {
		this.type = type;
	}

	public String getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(String whitelist) {
		this.whitelist = whitelist;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}
	
	public Webpage getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
