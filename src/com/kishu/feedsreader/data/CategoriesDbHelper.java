package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kishu.feedsreader.FeedReaderContract;
import com.kishu.feedsreader.FeedReaderContract.CategoriesColumn;
import com.kishu.feedsreader.view.CategoriesSubscriptionsItem;

/**
 * @author Kishu Agarwal
 * 
 */
public class CategoriesDbHelper {
	private FeedReaderDbHelper dbHelper;
	private Context ctx;
	private static CategoriesDbHelper categoriesDbHelper;

	public static CategoriesDbHelper getCategoriesDbHelper(
			FeedReaderDbHelper dbHelper) {
		if (categoriesDbHelper == null) {
			categoriesDbHelper = new CategoriesDbHelper(dbHelper);
		}
		return categoriesDbHelper;
	}

	private CategoriesDbHelper(FeedReaderDbHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public void updateCategoriesIfNecessary(ArrayList<Category> updated) {
		// ArrayList<Category> current = getCategories();
		SubscriptionsDbHelper helper = SubscriptionsDbHelper
				.getSubscriptionsDbHelper(dbHelper);
		for (Category c : updated) {
			addCategory(c);
			// helper.updateSubscriptionsIfNecessary(c.getSubscriptions());

		}

	}

	// Get all the categories
	public ArrayList<Category> getCategories() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ArrayList<Category> categories = new ArrayList<Category>();
		String columns[] = new String[] {
				FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
				FeedReaderContract.CategoriesColumn.CATEGORY_LABEL };
		Cursor result = db.query(
				FeedReaderContract.CategoriesColumn.TABLE_NAME, columns, null,
				null, null, null, null);
		result.moveToFirst();
		if (!result.isAfterLast()) {
			ContentValues values = new ContentValues();
			do {
				values.put(
						FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
						result.getString(result
								.getColumnIndex(FeedReaderContract.CategoriesColumn.CATEGORIES_ID)));
				values.put(
						FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
						result.getString(result
								.getColumnIndex(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL)));
				categories.add(convertContentValuesToCategory(values));
			} while (result.moveToNext());
		}
		result.close();
		return categories;
	}

	public void addCategory(Category c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CategoriesColumn.CATEGORIES_ID, c.getId());
		values.put(CategoriesColumn.CATEGORY_LABEL, c.getLabel());
		db.insert(CategoriesColumn.TABLE_NAME, null, values);
		/*
		 * ArrayList<String> subscriptionsList = getSubscriptonsInCategoryById(c
		 * .getId()); if (subscriptionsList == null) // category c is not
		 * present { ContentValues values = new ContentValues();
		 * values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
		 * c.getId());
		 * values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
		 * c.getLabel()); if (c.getSubscriptions().size() == 0) // no
		 * subscriptions {
		 * db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME, null,
		 * values); } else { for (Subscription sub : c.getSubscriptions()) {
		 * values.put( FeedReaderContract.CategoriesColumn.SUBSCRIPTION_ID,
		 * sub.getId());
		 * db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME, null,
		 * values); } } } else { // category c is already present in the db so
		 * insert only the // new subscriptions ContentValues values = new
		 * ContentValues();
		 * values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
		 * c.getId());
		 * values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
		 * c.getLabel()); for (Subscription sub : c.getSubscriptions()) { if
		 * (!subscriptionsList.contains(sub.getId())) { values.put(
		 * FeedReaderContract.CategoriesColumn.SUBSCRIPTION_ID, sub.getId());
		 * db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME, null,
		 * values); } } }
		 */

	}

	/*
	 * public ArrayList<String> getSubscriptonsInCategoryById(String id) {
	 * SQLiteDatabase db = dbHelper.getReadableDatabase(); ArrayList<String>
	 * list = null; Cursor result = db.query(
	 * FeedReaderContract.CategoriesColumn.TABLE_NAME, null,
	 * FeedReaderContract.CategoriesColumn.CATEGORIES_ID + " = \"" + id + "\"",
	 * null, null, null, null); if (result.getCount() > 0) { list = new
	 * ArrayList<String>(); result.moveToFirst(); if (!result.isAfterLast()) {
	 * do { list.add(result.getString(result
	 * .getColumnIndex(FeedReaderContract.CategoriesColumn.SUBSCRIPTION_ID))); }
	 * while (result.moveToNext());
	 * 
	 * } } result.close(); return list;
	 * 
	 * }
	 */

	// public void updateCategory(Category c){
	// SQLiteDatabase db = dbHelper.getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID, c.getId());
	// values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
	// c.getLabel());
	// values.put(FeedReaderContract.CategoriesColumn.SUBSCRIPTIONS,
	// c.getSubscriptionsAsStringIds());
	// db.update(FeedReaderContract.CategoriesColumn.TABLE_NAME, values,
	// FeedReaderContract.CategoriesColumn.CATEGORIES_ID+"=\""+c.getId()+"\"",
	// null);
	//
	// }
	public void removeCategory(Category c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(
				FeedReaderContract.CategoriesColumn.TABLE_NAME,
				FeedReaderContract.CategoriesColumn.CATEGORIES_ID + "=\""
						+ c.getId() + "\"", null);

	}

	public void close() {
		dbHelper.close();
	}

	public void installGlobalIds(String id) {
		// TODO Auto-generated method stub
		if (!globalIdsAlreadyInstalled()) // would be empty on the first run
											// ,change it quick hack
			return;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String categoryId = "user/" + id + "/category/";
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
				categoryId + "global.must");
		values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL, "Must");
		db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME, null, values);

		values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
				categoryId + "global.all");
		values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
				"All Categories");
		db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME, null, values);

		values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
				categoryId + "global.uncategorized");
		values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
				"Uncategorized");
		db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME, null, values);

		// values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
		// categoryId+"global.read");
		// values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
		// "All Categories");
		// db.insertWithOnConflict(FeedReaderContract.CategoriesColumn.TABLE_NAME,
		// null, values,SQLiteDatabase.CONFLICT_REPLACE);
		//
		values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID, "user/"
				+ id + "/tag/global.saved");
		values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL, "Saved");
		db.insertWithOnConflict(FeedReaderContract.CategoriesColumn.TABLE_NAME,
				null, values, SQLiteDatabase.CONFLICT_REPLACE);

	}

	public List<CategoriesSubscriptionsItem> getCategoriesSubscriptionsItem() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SubscriptionsDbHelper subscriptionsDbHelper = SubscriptionsDbHelper
				.getSubscriptionsDbHelper(dbHelper);
		Cursor c = db.query(CategoriesColumn.TABLE_NAME, null, null, null,
				null, null, null);
		c.moveToFirst();
		List<CategoriesSubscriptionsItem> items = new ArrayList<CategoriesSubscriptionsItem>();
		if (!c.isAfterLast()) {
			do {
				CategoriesSubscriptionsItem item = new CategoriesSubscriptionsItem();
				String categoryId = c.getString(c
						.getColumnIndex(CategoriesColumn.CATEGORIES_ID));
				item.setId(categoryId);
				item.setLabel(c.getString(c
						.getColumnIndex(CategoriesColumn.CATEGORY_LABEL)));
				item.setSubscriptions(subscriptionsDbHelper
						.getSubscriptionsByCategoryId(categoryId));
				items.add(item);
			} while (c.moveToNext());
		}
		c.close();
		return items;
	}

	public boolean globalIdsAlreadyInstalled() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor q = db.query(FeedReaderContract.CategoriesColumn.TABLE_NAME,
				null, CategoriesColumn.CATEGORIES_ID + " LIKE '%global.all%'",
				null, null, null, null);
		boolean isEmpty = false;
		if (q.getCount() == 0)
			isEmpty = true;
		q.close();
		return isEmpty;
	}

	/*
	 * // TODO change all these methods to queries like "like global.all" public
	 * Category getAllCategory() { SQLiteDatabase db =
	 * dbHelper.getReadableDatabase(); String selection =
	 * FeedReaderContract.CategoriesColumn.CATEGORIES_ID + " = ?"; String
	 * selectionArgs[] = { "\"global.all\"" }; Cursor query =
	 * db.query(FeedReaderContract.CategoriesColumn.TABLE_NAME, null, selection,
	 * selectionArgs, null, null, null); ContentValues values = new
	 * ContentValues(); values.put(
	 * FeedReaderContract.CategoriesColumn.CATEGORIES_ID, query.getString(query
	 * .getColumnIndex(FeedReaderContract.CategoriesColumn.CATEGORIES_ID)));
	 * values.put( FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
	 * query.getString(query
	 * .getColumnIndex(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL)));
	 * 
	 * query.close(); return new Category(values);
	 * 
	 * }
	 */
	/*
	 * public Category getUncategorizedCategory() { // TODO Auto-generated
	 * method stub SQLiteDatabase db = dbHelper.getReadableDatabase(); String
	 * selection = FeedReaderContract.CategoriesColumn.CATEGORIES_ID + " = ?";
	 * String selectionArgs[] = { "\"global.all\"" }; Cursor query =
	 * db.query(FeedReaderContract.CategoriesColumn.TABLE_NAME, null, selection,
	 * selectionArgs, null, null, null); ContentValues values = new
	 * ContentValues(); values.put(
	 * FeedReaderContract.CategoriesColumn.CATEGORIES_ID, query.getString(query
	 * .getColumnIndex(FeedReaderContract.CategoriesColumn.CATEGORIES_ID)));
	 * values.put( FeedReaderContract.CategoriesColumn.CATEGORY_LABEL,
	 * query.getString(query
	 * .getColumnIndex(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL)));
	 * 
	 * query.close(); return new Category(values); }
	 */

	private Category convertContentValuesToCategory(ContentValues values) {
		Category category = new Category();
		category.setId(values.getAsString(CategoriesColumn.CATEGORIES_ID));
		category.setLabel(values.getAsString(CategoriesColumn.CATEGORY_LABEL));
		return category;
	}

}
