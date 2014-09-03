/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.sources;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.github.dozedoff.media.MediaDefinition;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Webpage {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String elementRegex;
	@DatabaseField(canBeNull = false)
	private String baseUrl;
	@DatabaseField(canBeNull = false)
	private String pagePattern;
	@DatabaseField(canBeNull = false)
	private int pageStartIndex;
	@DatabaseField(canBeNull = false)
	private int pageEndIndex;
	@ForeignCollectionField(eager = true)
	private Collection<MediaDefinition> mediaDefinitions;

	/**
	 * Intended for DAO
	 */
	@Deprecated
	public Webpage() {
	}

	public Webpage(String name, String elementRegex, String baseUrl, String pagePattern, int pageStartIndex, int pageEndIndex) {
		super();
		this.name = name;
		this.elementRegex = elementRegex;
		this.baseUrl = baseUrl;
		this.pagePattern = pagePattern;
		this.pageStartIndex = pageStartIndex;
		this.pageEndIndex = pageEndIndex;
		mediaDefinitions = new LinkedList<>();
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElementRegex() {
		return elementRegex;
	}

	public void setElementRegex(String elementRegex) {
		this.elementRegex = elementRegex;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPagePattern() {
		return pagePattern;
	}

	public void setPagePattern(String pagePattern) {
		this.pagePattern = pagePattern;
	}

	public int getPageStartIndex() {
		return pageStartIndex;
	}

	public void setPageStartIndex(int pageStartIndex) {
		this.pageStartIndex = pageStartIndex;
	}

	public int getPageEndIndex() {
		return pageEndIndex;
	}

	public void setPageEndIndex(int pageEndIndex) {
		this.pageEndIndex = pageEndIndex;
	}

	public void addDefinition(MediaDefinition md) {
		mediaDefinitions.add(md);
	}

	public List<MediaDefinition> getDefinitions() {
		return new LinkedList<>(mediaDefinitions);
	}
}
