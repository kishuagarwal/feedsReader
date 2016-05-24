package com.kishu.feedsreader.data;

import com.google.gson.annotations.Expose;

public class Content {

	@Expose
	private String direction;
	@Expose
	private String content;

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
