package com.kishu.feedsreader;

import java.util.List;

public class StreamIdsResponse {

	private List<String> ids;
	private String continuationId;

	public String getContinuationId() {
		return continuationId;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setContinuationId(String continuationId) {
		this.continuationId = continuationId;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
