/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.media;

public class ResultLink {
	String description, url;
	SourceType sourceType;
	TargetType targetType;

	public ResultLink(String description, String url, SourceType sourceType, TargetType targetType) {
		super();
		this.description = description;
		this.url = url;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}
}
