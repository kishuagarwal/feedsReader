package com.kishu.feedsreader.data;

import com.google.gson.annotations.Expose;

public class Tag {

	@Expose
	private String id;
	@Expose
	private String label;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
