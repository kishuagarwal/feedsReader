package com.kishu.feedsreader.data;

import com.google.gson.annotations.Expose;

public class Alternate {

	@Expose
	private String type;
	@Expose
	private String href;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
