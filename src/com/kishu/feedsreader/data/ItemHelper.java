package com.kishu.feedsreader.data;

import android.support.v4.util.LruCache;


public class ItemHelper {

	private static ItemHelper itemHelper;
	private static LruCache<String,Item> cache;
	
	public static ItemHelper getInstance(){
		if(itemHelper == null){
			itemHelper = new ItemHelper();
		}
		return itemHelper;
	}
	
	private ItemHelper(){
          	cache = new LruCache<String, Item>(10);
	}
	
	
	public static Item getItem(String id){
		Item item = cache.get(id);
		if(item != null)
			return item;
		item = FeedManager.getInstance().getEntry(id);
		cache.put(id, item);
		return item;
	}
	
	
	public static void markItemAsRead(Item item){
		item.setUnread(false);
		FeedManager.getInstance().markItemAsRead(item.getId());
	}
	
	public static void markItemAsUnread(Item item){
		item.setUnread(true);
		FeedManager.getInstance().markItemAsUnread(item.getId());
	}
	
	public static void markItemAsSaved(Item item){
		FeedManager.getInstance().markItemAsTagged(item.getId(), SpecialCategories.getSavedTagId());
	}
	
	public static void markItemAsUnsaved(Item item)
	{
		FeedManager.getInstance().markItemAsUnTagged(item.getId(), SpecialCategories.getSavedTagId());
	}
	
	
	
}
