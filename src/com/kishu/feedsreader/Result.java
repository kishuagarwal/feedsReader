package com.kishu.feedsreader;

import com.google.gson.annotations.Expose;

public class Result {

	@Expose
	private boolean featured;
	@Expose
	private String title;
	@Expose
	private long subscribers;
	@Expose
	private boolean curated;
	@Expose
	private double velocity;
	@Expose
	private String feedId;
	@Expose
	private String website;

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(long subscribers) {
		this.subscribers = subscribers;
	}

	public boolean isCurated() {
		return curated;
	}

	public void setCurated(boolean curated) {
		this.curated = curated;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
