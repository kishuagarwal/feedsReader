package com.kishu.feedsreader.data;

import com.google.gson.annotations.Expose;

public class Origin {

	@Expose
	private String title;
	@Expose
	private String htmlUrl;
	@Expose
	private String streamId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

}
