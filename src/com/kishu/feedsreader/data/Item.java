package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.text.format.DateUtils;

import com.google.gson.annotations.Expose;
import com.kishu.feedsreader.FeedReaderContract;

public class Item {

	@Expose
	private String title;
	@Expose
	private long updated;
	@Expose
	private String id;
	@Expose
	private long crawled;
	@Expose
	private List<Tag> tags = new ArrayList<Tag>();
	@Expose
	private List<Alternate> alternate = new ArrayList<Alternate>();
	@Expose
	private List<Category> categories = new ArrayList<Category>();
	@Expose
	private boolean unread;
	@Expose
	private long published;
	@Expose
	private String author;
	@Expose
	private Content summary;
	@Expose
	private Content content;
	@Expose
	private Origin origin;
	@Expose
	private double engagementRate;
	@Expose
	private List<Canonical> canonical = new ArrayList<Canonical>();
	@Expose
	private List<String> keywords = new ArrayList<String>();
	@Expose
	private long engagement;

	public Item(ContentValues values) {
		id = values.getAsString(FeedReaderContract.EntryColumn.ENTRY_ID);
		unread = values.getAsBoolean(FeedReaderContract.EntryColumn.UNREAD);
		title = values.getAsString(FeedReaderContract.EntryColumn.TITLE);
		published = values.getAsLong(FeedReaderContract.EntryColumn.PUBLISHED);
		author = values.getAsString(FeedReaderContract.EntryColumn.AUTHOR);
		setKeywordsFromString(values
				.getAsString(FeedReaderContract.EntryColumn.KEYWORDS));
		content = new Content();
		content.setContent(values
				.getAsString(FeedReaderContract.EntryColumn.CONTENT));
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

	public long getCrawled() {
		return crawled;
	}

	public void setCrawled(long crawled) {
		this.crawled = crawled;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Alternate> getAlternate() {
		return alternate;
	}

	public void setAlternate(List<Alternate> alternate) {
		this.alternate = alternate;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public long getPublished() {
		return published;
	}

	public void setPublished(long published) {
		this.published = published;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Content getContent() {
		if (content == null)
			return summary;
		else
			return content;
	}

	public void setContent(Content content) {
		this.summary = content;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public double getEngagementRate() {
		return engagementRate;
	}

	public void setEngagementRate(double engagementRate) {
		this.engagementRate = engagementRate;
	}

	public List<Canonical> getCanonical() {
		return canonical;
	}

	public void setCanonical(List<Canonical> canonical) {
		this.canonical = canonical;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public long getEngagement() {
		return engagement;
	}

	public String getKeywordsAsString() {
		if(keywords == null)
			return null;
		StringBuilder keys = new StringBuilder();
		for (int i = 0; i < keywords.size() - 1; i++) {
			keys.append(keywords.get(i) + ",");
		}
		if (keywords.size() != 0) {
			keys.append(keywords.get(keywords.size() - 1));
		}
		return keys.toString();
	}

	public void setKeywordsFromString(String s) {
		if(s == null)
		{
			keywords = null;
			return;
		}
		StringTokenizer tokens = new StringTokenizer(s, ",");
		List<String> keys = new ArrayList<String>();
		while (tokens.hasMoreTokens()) {
			keys.add(tokens.nextToken());
		}
		keywords = keys;
	}

	public void setEngagement(long engagement) {
		this.engagement = engagement;
	}

	public String getUpdatedString() {
		return (String) DateUtils.getRelativeTimeSpanString(published,
				System.currentTimeMillis(), 0);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Item item = (Item) o;
		if(item.getId().equals(this.id))
			return true;
		else
			return false;
	}
}