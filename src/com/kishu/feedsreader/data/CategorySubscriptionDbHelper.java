package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kishu.feedsreader.CredentialStorage;
import com.kishu.feedsreader.FeedReaderContract.CategorySubscriptionColumn;

public class CategorySubscriptionDbHelper {

	private FeedReaderDbHelper dbHelper;
	private Context ctx;
	private static CategorySubscriptionDbHelper categorySubscriptionDbHelper;

	public static CategorySubscriptionDbHelper getCategorySubscriptionDbHelper(
			FeedReaderDbHelper dbHelper) {
		if (categorySubscriptionDbHelper == null) {
			categorySubscriptionDbHelper = new CategorySubscriptionDbHelper(
					dbHelper);
		}
		return categorySubscriptionDbHelper;
	}

	private CategorySubscriptionDbHelper(FeedReaderDbHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public void addSubscriptions(
			List<com.kishu.feedsreader.data.Subscription> subs) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		List<CategorySubscriptionItem> current = getCurrentMap();
		List<CategorySubscriptionItem> newMap = new ArrayList<CategorySubscriptionItem>();
		for (com.kishu.feedsreader.data.Subscription s : subs) {
			if (s.getCategories().size() == 0) {
				String userId = CredentialStorage
						.getCredentialStorageInstance().getUserId();
				CategorySubscriptionItem item = new CategorySubscriptionItem(
						"user/" + userId + "/category/global.uncategorized",
						s.getId());
				newMap.add(item);

			} else
				for (com.kishu.feedsreader.data.Category c : s.getCategories()) {
					CategorySubscriptionItem item = new CategorySubscriptionItem(
							c.getId(), s.getId());
					newMap.add(item);
				}
		}
		for (CategorySubscriptionItem item : current) {
			newMap.remove(item);
		}
		for (CategorySubscriptionItem item : newMap) {
			values.put(CategorySubscriptionColumn.CATEGORY_ID, item.categoryId);
			values.put(CategorySubscriptionColumn.SUBSCRIPTION_ID,
					item.subscriptionId);
			db.insert(CategorySubscriptionColumn.TABLE_NAME, null, values);
		}

	}

	public void addSubscription(Subscription sub) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		for (Category c : sub.getCategories()) {
			values.put(CategorySubscriptionColumn.CATEGORY_ID, c.getId());
			values.put(CategorySubscriptionColumn.SUBSCRIPTION_ID, sub.getId());
			db.insert(CategorySubscriptionColumn.TABLE_NAME, null, values);
		}

	}

	public List<CategorySubscriptionItem> getCurrentMap() {
		List<CategorySubscriptionItem> current = new ArrayList<CategorySubscriptionDbHelper.CategorySubscriptionItem>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "select * from " + CategorySubscriptionColumn.TABLE_NAME
				+ ";";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			do {
				CategorySubscriptionItem item = new CategorySubscriptionItem(
						c.getString(c
								.getColumnIndex(CategorySubscriptionColumn.CATEGORY_ID)),
						c.getString(c
								.getColumnIndex(CategorySubscriptionColumn.SUBSCRIPTION_ID)));
				current.add(item);
			} while (c.moveToNext());
		}
		c.close();
		return current;

	}

	static class CategorySubscriptionItem {
		String categoryId;
		String subscriptionId;

		public CategorySubscriptionItem(String cId, String subId) {
			// TODO Auto-generated constructor stub
			this.categoryId = cId;
			this.subscriptionId = subId;

		}

		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			CategorySubscriptionItem item = (CategorySubscriptionItem) o;
			if (this.categoryId.equals(item.categoryId)
					&& this.subscriptionId.equals(item.subscriptionId))
				return true;
			else
				return false;
		}
	}

}
