package com.kishu.feedsreader;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SearchResultItem {

	@Expose
	private List<Result> results = new ArrayList<Result>();
	@Expose
	private String hint;
	@Expose
	private List<String> related = new ArrayList<String>();

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public List<String> getRelated() {
		return related;
	}

	public void setRelated(List<String> related) {
		this.related = related;
	}

}
