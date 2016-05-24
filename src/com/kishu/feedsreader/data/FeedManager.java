package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.kishu.feedsreader.FeedApplication;
import com.kishu.feedsreader.data.EntriesDbHelper.EntryCursor;
import com.kishu.feedsreader.view.CategoriesSubscriptionsItem;

public class FeedManager {

	private FeedReaderDbHelper dbHelper;
	private static FeedManager feedManager;
	private Context ctx;

	public static FeedManager getInstance() {
		if (feedManager == null) {
			feedManager = new FeedManager();
		}
		return feedManager;

	}

	private FeedManager() {
		this.ctx = FeedApplication.getInstance().getApplicationContext();
		dbHelper = new FeedReaderDbHelper(ctx);
	}

	public void installGlobalIds(String id) {
		CategoriesDbHelper.getCategoriesDbHelper(dbHelper).installGlobalIds(id);
		TagDbHelper.getEntriesDbHelper(dbHelper).addSavedTag();
	}

	public List<Subscription> getSubscriptions() {
		return SubscriptionsDbHelper.getSubscriptionsDbHelper(dbHelper)
				.getSubscriptions();
	}

	public ArrayList<Category> getCategories() {
		return CategoriesDbHelper.getCategoriesDbHelper(dbHelper)
				.getCategories();
	}

	public List<Item> getEntries(String feedId) {
		return EntriesDbHelper.getEntriesDbHelper(dbHelper).getEntries(feedId);

	}

	public Item getEntry(String entryId) {
		return EntriesDbHelper.getEntriesDbHelper(dbHelper).getItem(entryId);
	}

	public void updateCategories(ArrayList<Category> updated) {
		CategoriesDbHelper.getCategoriesDbHelper(dbHelper)
				.updateCategoriesIfNecessary(updated);
	}

	public void updateEntries(ArrayList<Item> entries) {
		EntriesDbHelper.getEntriesDbHelper(dbHelper).updateEntriesIfNecessary(
				entries);
	}

	public EntryCursor getSavedEntriesCursor() {
		return EntriesDbHelper.getEntriesDbHelper(dbHelper).getSavedEntries();
	}

	public EntryCursor getEntriesCursor(String id) {
		// TODO Auto-generated method stub
		return EntriesDbHelper.getEntriesDbHelper(dbHelper)
				.getEntriesCursor(id);
	}

	public List<CategoriesSubscriptionsItem> getCategoriesSubscriptionsItem() {
		return CategoriesDbHelper.getCategoriesDbHelper(dbHelper)
				.getCategoriesSubscriptionsItem();
	}

	public List<Tag> getTags() {
		return TagDbHelper.getEntriesDbHelper(dbHelper).getTags();
	}

	public void updateSubscriptionsIfNecessary(
			List<com.kishu.feedsreader.data.Subscription> subscriptions) {
		// TODO Auto-generated method stub
		SubscriptionsDbHelper.getSubscriptionsDbHelper(dbHelper)
				.updateSubscriptionsIfNecessary(subscriptions);
	}

	
	
	public void addEntries(
			com.kishu.feedsreader.data.StreamContentResponse response) {
		EntriesDbHelper.getEntriesDbHelper(dbHelper).addEntries(response);
	}

	public List<String> getEntryIds(String feedId) {
		return EntriesDbHelper.getEntriesDbHelper(dbHelper).getEntryIds(feedId);
	}
	
	public void markItemAsRead(String itemId){
		EntriesDbHelper.getEntriesDbHelper(dbHelper).markItemAsRead(itemId);
	}
	
	public void markItemAsUnread(String itemId){
		EntriesDbHelper.getEntriesDbHelper(dbHelper).markItemAsUnread(itemId);
	}
	
	public void markItemAsTagged(String itemId,String tagId){
		EntriesDbHelper.getEntriesDbHelper(dbHelper).markItemAsTagged(itemId, tagId);
	}
	
	public void markItemAsUnTagged(String itemId,String tagId){
		EntriesDbHelper.getEntriesDbHelper(dbHelper).markItemAsUntagged(itemId, tagId);
	}

}
