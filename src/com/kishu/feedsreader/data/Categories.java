package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class Categories {

	private List<Category> list;
	private static Categories categories;
	private static FeedManager feedManager;

	public static Categories getInstance(Context ctx) {
		if (categories == null) {
			feedManager = FeedManager.getInstance();
			categories = new Categories();
		}
		return categories;
	}

	private Categories() {
		list = feedManager.getCategories();
	}

	private Categories(ArrayList<Category> l) {
		list = l;
	}

	public List<Category> getCategories() {
		return list;
	}

	public Category getCategory(String id) {
		for (Category c : list) {
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}
	/*
	 * public void updateDatabase() { for(Category c:list) { c.updateDatabase();
	 * } }
	 * 
	 * public void addCategory(Category c) { list.add(c); }
	 * 
	 * public void removeCategory(String id) { for(Category c:list) {
	 * if(c.getId().equals(id)) { list.remove(c); break;
	 * 
	 * } } } public void requery() {
	 * 
	 * }
	 */
}
