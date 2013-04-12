package com.github.dozedoff.media;

import java.util.List;

public class MediaDefinition {
	private List<String> whitelist;
	private List<String> blacklist;
	private TargetType type;
	
	/**
	 * Intended for DAO
	 */
	@Deprecated
	public MediaDefinition() {}

	public MediaDefinition(TargetType type) {
		this.type = type;
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
