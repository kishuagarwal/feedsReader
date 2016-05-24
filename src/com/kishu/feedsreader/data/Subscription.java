package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Subscription {

	@Expose
	private String sortid;
	@Expose
	private String title;
	@Expose
	private long updated;
	@Expose
	private String id;
	@Expose
	private List<Category> categories = new ArrayList<Category>();
	@Expose
	private String visualUrl;
	@Expose
	private long added;
	@Expose
	private String website;

	private String continuationId;

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getVisualUrl() {
		return visualUrl;
	}

	public void setVisualUrl(String visualUrl) {
		this.visualUrl = visualUrl;
	}

	public long getAdded() {
		return added;
	}

	public void setAdded(long added) {
		this.added = added;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getContinuationId() {
		return continuationId;
	}

	public void setContinuationId(String continuationId) {
		this.continuationId = continuationId;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Subscription s = (Subscription) o;
		if (s.getId().equals(this.id))
			return true;
		else
			return false;
	}

}
