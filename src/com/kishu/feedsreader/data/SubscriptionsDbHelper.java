package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kishu.feedsreader.FeedReaderContract;
import com.kishu.feedsreader.FeedReaderContract.CategorySubscriptionColumn;
import com.kishu.feedsreader.FeedReaderContract.SubscriptionColumn;

public class SubscriptionsDbHelper {

	private FeedReaderDbHelper dbHelper;
	private static SubscriptionsDbHelper subscriptionsDbHelper;

	private SubscriptionsDbHelper(FeedReaderDbHelper dbHelper) {

		this.dbHelper = dbHelper;
	}

	public static SubscriptionsDbHelper getSubscriptionsDbHelper(
			FeedReaderDbHelper dbHelper) {
		if (subscriptionsDbHelper == null)
			subscriptionsDbHelper = new SubscriptionsDbHelper(dbHelper);
		return subscriptionsDbHelper;
	}

	public List<Subscription> getSubscriptions(String[] ids) {
		// Todo change this to make a custom query like query that containes IN
		List<Subscription> subscriptions = getSubscriptions();
		List<Subscription> s = new ArrayList<Subscription>();
		for (String id : ids) {
			for (Subscription su : subscriptions) {
				if (su.getId().equals(id))
					s.add(su);
			}
		}
		return s;

	}

	public List<Subscription> getSubscriptionsByCategoryId(String id) {

		if (id.equals(SpecialCategories.getGlobalAllCategoryId()))
			return getSubscriptions();

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sqlQuery = "select * from "
				+ CategorySubscriptionColumn.TABLE_NAME + " join "
				+ SubscriptionColumn.TABLE_NAME + " on "
				+ CategorySubscriptionColumn.TABLE_NAME + "."
				+ CategorySubscriptionColumn.SUBSCRIPTION_ID + "="
				+ SubscriptionColumn.TABLE_NAME + "."
				+ SubscriptionColumn.SUBSCRIPTIONS_ID + " where "
				+ CategorySubscriptionColumn.CATEGORY_ID + "=\"" + id + "\";";
		Cursor result = db.rawQuery(sqlQuery, null);
		result.moveToFirst();
		List<Subscription> subs = new ArrayList<Subscription>();
		if (!result.isAfterLast()) {
			do {
				ContentValues values = new ContentValues();
				values.put(
						FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID)));
				values.put(
						FeedReaderContract.SubscriptionColumn.LAST_UPDATED,
						result.getLong(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.LAST_UPDATED)));
				values.put(
						FeedReaderContract.SubscriptionColumn.TITLE,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.TITLE)));
				values.put(
						FeedReaderContract.SubscriptionColumn.VISUALURL,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.VISUALURL)));
				values.put(
						FeedReaderContract.SubscriptionColumn.CONTINUATION,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.CONTINUATION)));
				values.put(
						FeedReaderContract.SubscriptionColumn.WEBSITE,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.WEBSITE)));
				subs.add(convertContentValuesToSubscription(values));
			} while (result.moveToNext());
		}
		result.close();
		return subs;
	}

	public Subscription getSubscripitionById(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor result = db.query(
				FeedReaderContract.SubscriptionColumn.TABLE_NAME, null,
				FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID
						+ " = \"" + id + "\"", null, null, null, null);
		if (result.getCount() == 0) {
			result.close();
			return null;
		}
		ContentValues values = new ContentValues();
		values.put(
				FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID,
				result.getString(result
						.getColumnIndex(FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID)));
		values.put(
				FeedReaderContract.SubscriptionColumn.LAST_UPDATED,
				result.getLong(result
						.getColumnIndex(FeedReaderContract.SubscriptionColumn.LAST_UPDATED)));
		values.put(
				FeedReaderContract.SubscriptionColumn.TITLE,
				result.getString(result
						.getColumnIndex(FeedReaderContract.SubscriptionColumn.TITLE)));
		values.put(
				FeedReaderContract.SubscriptionColumn.VISUALURL,
				result.getString(result
						.getColumnIndex(FeedReaderContract.SubscriptionColumn.VISUALURL)));
		values.put(
				FeedReaderContract.SubscriptionColumn.CONTINUATION,
				result.getString(result
						.getColumnIndex(FeedReaderContract.SubscriptionColumn.CONTINUATION)));
		values.put(
				FeedReaderContract.SubscriptionColumn.WEBSITE,
				result.getString(result
						.getColumnIndex(FeedReaderContract.SubscriptionColumn.WEBSITE)));
		Subscription sub = convertContentValuesToSubscription(values);

		result.close();
		return sub;
	}

	// Returns the subscriptions stored in the db in the form of array list.
	public List<Subscription> getSubscriptions() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor result = db.query(
				FeedReaderContract.SubscriptionColumn.TABLE_NAME, null, null,
				null, null, null, null);
		result.moveToFirst();
		List<Subscription> list = new ArrayList<Subscription>();
		while (!result.isAfterLast()) {
			do {
				ContentValues values = new ContentValues();
				values.put(
						FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID)));
				values.put(
						FeedReaderContract.SubscriptionColumn.LAST_UPDATED,
						result.getLong(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.LAST_UPDATED)));
				values.put(
						FeedReaderContract.SubscriptionColumn.TITLE,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.TITLE)));
				values.put(
						FeedReaderContract.SubscriptionColumn.VISUALURL,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.VISUALURL)));
				values.put(
						FeedReaderContract.SubscriptionColumn.CONTINUATION,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.CONTINUATION)));
				values.put(
						FeedReaderContract.SubscriptionColumn.WEBSITE,
						result.getString(result
								.getColumnIndex(FeedReaderContract.SubscriptionColumn.WEBSITE)));
				list.add(convertContentValuesToSubscription(values));
			} while (result.moveToNext());
		}
		result.close();
		return list;

	}

	public void updateSubscriptionsIfNecessary(List<Subscription> subscriptions) {
		List<Subscription> current = getSubscriptions();
		for (com.kishu.feedsreader.data.Subscription s : subscriptions) {
			if (!current.contains(s))
				addSubscription(s);
		}
		CategorySubscriptionDbHelper.getCategorySubscriptionDbHelper(dbHelper)
				.addSubscriptions(subscriptions);

	}

	public void addSubscription(com.kishu.feedsreader.data.Subscription s) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = convertSubscriptionIntoContentValues(s);
		db.insert(FeedReaderContract.SubscriptionColumn.TABLE_NAME, null,
				values);
		// CategorySubscriptionDbHelper.getCategorySubscriptionDbHelper(dbHelper).addSubscription(c);
	}

	public void updateSubscription(com.kishu.feedsreader.data.Subscription c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = convertSubscriptionIntoContentValues(c);
		db.update(FeedReaderContract.SubscriptionColumn.TABLE_NAME, values,
				FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID + "=\""
						+ c.getId() + "\"", null);

	}

	private ContentValues convertSubscriptionIntoContentValues(
			com.kishu.feedsreader.data.Subscription s) {
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID,
				s.getId());
		values.put(FeedReaderContract.SubscriptionColumn.LAST_UPDATED,
				s.getUpdated());
		values.put(FeedReaderContract.SubscriptionColumn.TITLE, s.getTitle());
		values.put(FeedReaderContract.SubscriptionColumn.VISUALURL,
				s.getVisualUrl());
		values.put(FeedReaderContract.SubscriptionColumn.CONTINUATION,
				s.getContinuationId());
		values.put(FeedReaderContract.SubscriptionColumn.WEBSITE,
				s.getWebsite());
		return values;
	}

	public void removeSubscription(Subscription c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(FeedReaderContract.SubscriptionColumn.TABLE_NAME,
				FeedReaderContract.SubscriptionColumn.SUBSCRIPTIONS_ID + "=\""
						+ c.getId() + "\"", null);

	}

	private Subscription convertContentValuesToSubscription(ContentValues values) {
		Subscription subscription = new Subscription();
		subscription.setId(values
				.getAsString(SubscriptionColumn.SUBSCRIPTIONS_ID));
		subscription.setTitle(values.getAsString(SubscriptionColumn.TITLE));
		subscription.setUpdated(values
				.getAsLong(SubscriptionColumn.LAST_UPDATED));
		subscription.setVisualUrl(values
				.getAsString(SubscriptionColumn.VISUALURL));
		subscription.setContinuationId(values
				.getAsString(SubscriptionColumn.CONTINUATION));
		subscription.setWebsite(values.getAsString(SubscriptionColumn.WEBSITE));
		return subscription;
	}

	public void close() {
		dbHelper.close();
	}

}
