package com.kishu.feedsreader;

public class UnreadCount {

	private String id;
	private long updated;
	private int count;

	public int getCount() {
		return count;
	}

	public String getId() {
		return id;
	}

	public long getUpdated() {
		return updated;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

}
