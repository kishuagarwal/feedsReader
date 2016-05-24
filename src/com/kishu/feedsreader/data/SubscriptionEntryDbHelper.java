package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kishu.feedsreader.FeedReaderContract.SubscriptionEntryColumn;

public class SubscriptionEntryDbHelper {

	private FeedReaderDbHelper dbHelper;
	private static SubscriptionEntryDbHelper subscriptionEntryDbHelper;

	public static SubscriptionEntryDbHelper getsSubscriptionTagDbHelper(
			FeedReaderDbHelper dbHelper) {
		if (subscriptionEntryDbHelper == null) {
			subscriptionEntryDbHelper = new SubscriptionEntryDbHelper(dbHelper);
		}
		return subscriptionEntryDbHelper;
	}

	private SubscriptionEntryDbHelper(FeedReaderDbHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public List<String> getEntryIds(String id) {
		String selection = SubscriptionEntryColumn.SUBSCRIPTION_ID + "= ?";
		String selectionArgs[] = new String[] { id };
		String columns[] = new String[] { SubscriptionEntryColumn.ENTRY_ID };
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor query = db.query(SubscriptionEntryColumn.TABLE_NAME, columns,
				selection, selectionArgs, null, null, null);
		query.moveToFirst();
		List<String> entryIds = new ArrayList<String>();
		if (!query.isAfterLast()) {

			do {
				entryIds.add(query.getString(query
						.getColumnIndex(SubscriptionEntryColumn.ENTRY_ID)));
			} while (query.moveToNext());
		}
		query.close();
		return entryIds;
	}

	public void addEntries(List<String> entryIds, List<String> subscriptionIds) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		int size = entryIds.size();
		for (int i = 0; i < size; i++) {
			addEntry(entryIds.get(i), subscriptionIds.get(i));
		}
		db.setTransactionSuccessful();
		db.endTransaction();

	}

	public void addEntry(String entryId, String subscriptionId) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SubscriptionEntryColumn.ENTRY_ID, entryId);
		values.put(SubscriptionEntryColumn.SUBSCRIPTION_ID, subscriptionId);
		db.insert(SubscriptionEntryColumn.TABLE_NAME, null, values);

	}

}
