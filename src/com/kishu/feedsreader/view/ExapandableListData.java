package com.kishu.feedsreader.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.kishu.feedsreader.data.FeedManager;

public class ExapandableListData {

	private List<ListItem> data;
	private Context ctx;
	private static ExapandableListData expandableListData;
	private static FeedManager feedmanager;

	public static ExapandableListData getExapandableListData(Context ctx) {
		if (expandableListData == null) {
			expandableListData = new ExapandableListData(ctx);
		}

		return expandableListData;
	}

	private ExapandableListData(Context ctx) {
		feedmanager = FeedManager.getInstance();
		data = new ArrayList<ListItem>();
		init();
	}

	public void requery(){
		data.clear();
		init();
	}
	
	private void init() {
		data = new ArrayList<ListItem>();
		data.addAll(feedmanager.getCategoriesSubscriptionsItem());
		TagsItem item = new TagsItem(feedmanager.getTags());
		data.add(item);
	}
	
	

	public List<ListItem> getListData() {
		return data;
	}


}
