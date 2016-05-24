package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class StreamContentResponse {

	@Expose
	private String title;
	@Expose
	private long updated;
	@Expose
	private String direction;
	@Expose
	private String id;
	@Expose
	private List<Alternate> alternate = new ArrayList<Alternate>();
	@Expose
	private List<Self> self = new ArrayList<Self>();
	@Expose
	private List<Item> items = new ArrayList<Item>();
	@Expose
	private String continuation;

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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Alternate> getAlternate() {
		return alternate;
	}

	public void setAlternate(List<Alternate> alternate) {
		this.alternate = alternate;
	}

	public List<Self> getSelf() {
		return self;
	}

	public void setSelf(List<Self> self) {
		this.self = self;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getContinuation() {
		return continuation;
	}

	public void setContinuation(String continuation) {
		this.continuation = continuation;
	}

}
