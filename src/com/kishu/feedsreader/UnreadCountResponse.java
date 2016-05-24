package com.kishu.feedsreader;

import java.util.List;

public class UnreadCountResponse {

	private List<UnreadCount> unreadCounts;

	public void setUnreadCounts(List<UnreadCount> unreadCounts) {
		this.unreadCounts = unreadCounts;
	}

	public List<UnreadCount> getUnreadCounts() {
		return unreadCounts;
	}
}
