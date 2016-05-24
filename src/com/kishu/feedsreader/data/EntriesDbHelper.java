package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.kishu.feedsreader.FeedReaderContract;
import com.kishu.feedsreader.FeedReaderContract.EntryColumn;
import com.kishu.feedsreader.FeedReaderContract.SubscriptionEntryColumn;
import com.kishu.feedsreader.FeedReaderContract.TagEntryColumn;

public class EntriesDbHelper {

	private FeedReaderDbHelper dbHelper;
	private static EntriesDbHelper entriesDbHelper;


	private EntriesDbHelper(FeedReaderDbHelper dbHelper) {
		this.dbHelper = dbHelper;

	}

	public static EntriesDbHelper getEntriesDbHelper(FeedReaderDbHelper dbHelper) {
		if (entriesDbHelper == null)
			entriesDbHelper = new EntriesDbHelper(dbHelper);
		return entriesDbHelper;
	}

	public List<String> getEntryIds(String feedId){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select "+EntryColumn.TABLE_NAME + "."+EntryColumn.ENTRY_ID + " from " + SubscriptionEntryColumn.TABLE_NAME
				+ " natural join " + EntryColumn.TABLE_NAME);
		sqlQuery.append(" where " + SubscriptionEntryColumn.SUBSCRIPTION_ID
				+ " = \"" + feedId + "\";");
		Cursor result = db.rawQuery(sqlQuery.toString(), null);
		result.moveToFirst();
		List<String> list = new ArrayList<String>();
		while (!result.isAfterLast()) {
			do {
				list.add(result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.ENTRY_ID)));
		} while (result.moveToNext());
		}
		result.close();
		return list;


	}
	
	// Returns the subscriptions stored in the db in the form of array list.
	public List<Item> getEntries(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select *  from " + SubscriptionEntryColumn.TABLE_NAME
				+ " natural join " + EntryColumn.TABLE_NAME);
		sqlQuery.append(" where " + SubscriptionEntryColumn.SUBSCRIPTION_ID
				+ " = \"" + id + "\";");
		Cursor result = db.rawQuery(sqlQuery.toString(), null);
		result.moveToFirst();
		ArrayList<Item> list = new ArrayList<Item>();
		while (!result.isAfterLast()) {
			do {
				ContentValues values = new ContentValues();
				values.put(
						FeedReaderContract.EntryColumn.ENTRY_ID,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.ENTRY_ID)));
				values.put(
						FeedReaderContract.EntryColumn.AUTHOR,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.AUTHOR)));
				values.put(
						FeedReaderContract.EntryColumn.CONTENT,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.CONTENT)));
				values.put(
						FeedReaderContract.EntryColumn.KEYWORDS,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.KEYWORDS)));
				values.put(
						FeedReaderContract.EntryColumn.PUBLISHED,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.PUBLISHED)));
				values.put(
						FeedReaderContract.EntryColumn.TITLE,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.TITLE)));
				values.put(
						FeedReaderContract.EntryColumn.UNREAD,
						result.getString(result
								.getColumnIndex(FeedReaderContract.EntryColumn.UNREAD)));

				list.add(new Item(values));
			} while (result.moveToNext());
		}
		result.close();
		return list;

	}

	public Item getItem(String entryId) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String selection = FeedReaderContract.EntryColumn.ENTRY_ID + "= ?";
		String args[] = { entryId };
		// String orderBy = FeedReaderContract.EntryColumn.PUBLISHED + " DESC";
		Cursor result = db.query(FeedReaderContract.EntryColumn.TABLE_NAME,
				null, selection, args, null, null, null);
		if (result.getCount() == 0) {
			result.close();
			return null;
		}

		result.moveToFirst();
		ContentValues values = new ContentValues();
		values.put(
				FeedReaderContract.EntryColumn.ENTRY_ID,
				result.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.ENTRY_ID)));
		values.put(FeedReaderContract.EntryColumn.AUTHOR, result
				.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.AUTHOR)));
		values.put(
				FeedReaderContract.EntryColumn.CONTENT,
				result.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.CONTENT)));
		values.put(
				FeedReaderContract.EntryColumn.KEYWORDS,
				result.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.KEYWORDS)));
		values.put(
				FeedReaderContract.EntryColumn.PUBLISHED,
				result.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.PUBLISHED)));
		values.put(FeedReaderContract.EntryColumn.TITLE, result
				.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.TITLE)));
		values.put(FeedReaderContract.EntryColumn.UNREAD, result
				.getString(result
						.getColumnIndex(FeedReaderContract.EntryColumn.UNREAD)));
				result.close();
		return new Item(values);

	}

	public void addItem(Item c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.EntryColumn.AUTHOR, c.getAuthor());
		values.put(FeedReaderContract.EntryColumn.CONTENT, c.getContent()
				.getContent());
		values.put(FeedReaderContract.EntryColumn.ENTRY_ID, c.getId());
		values.put(FeedReaderContract.EntryColumn.KEYWORDS,
				c.getKeywordsAsString());
		values.put(FeedReaderContract.EntryColumn.PUBLISHED, c.getPublished());
		values.put(FeedReaderContract.EntryColumn.TITLE, c.getTitle());
		values.put(FeedReaderContract.EntryColumn.UNREAD, c.isUnread());
		values.put(EntryColumn.STATUS, Status.OK);
		values.put(EntryColumn.PENDING_OPEATION, ItemPendingOperation.NO_PENDING_OPERATION);
		db.insert(FeedReaderContract.EntryColumn.TABLE_NAME, null, values);
	}

	public void updateItem(Item c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.EntryColumn.AUTHOR, c.getAuthor());
		values.put(FeedReaderContract.EntryColumn.CONTENT, c.getContent()
				.getContent());
		values.put(FeedReaderContract.EntryColumn.ENTRY_ID, c.getId());
		values.put(FeedReaderContract.EntryColumn.KEYWORDS,
				c.getKeywordsAsString());
		values.put(FeedReaderContract.EntryColumn.PUBLISHED, c.getPublished());
		values.put(FeedReaderContract.EntryColumn.TITLE, c.getTitle());
		values.put(FeedReaderContract.EntryColumn.UNREAD, c.isUnread());
		db.update(FeedReaderContract.EntryColumn.TABLE_NAME, values,
				FeedReaderContract.EntryColumn.ENTRY_ID + "=\"" + c.getId()
						+ "\"", null);

	}

	public void removeItem(Item c) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(FeedReaderContract.EntryColumn.TABLE_NAME,
				FeedReaderContract.EntryColumn.ENTRY_ID + "=\"" + c.getId()
						+ "\"", null);

	}

	public void updateEntriesIfNecessary(ArrayList<Item> entries) {
		// TODO Auto-generated method stub
		for (Item e : entries) {
			// addEntry(e);
		}
	}

	// TODO see method for natural join . now just a quick hack
	public EntryCursor getEntriesCursor(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select *  from " + SubscriptionEntryColumn.TABLE_NAME
				+ " inner join " + EntryColumn.TABLE_NAME);
		sqlQuery.append(" on " + SubscriptionEntryColumn.TABLE_NAME + "."
				+ SubscriptionEntryColumn.ENTRY_ID + "="
				+ EntryColumn.TABLE_NAME + "." + EntryColumn.ENTRY_ID);
		sqlQuery.append(" where " + SubscriptionEntryColumn.SUBSCRIPTION_ID
				+ "=\"" + id + "\";");
		Cursor result = db.rawQuery(sqlQuery.toString(), null);
		result.moveToFirst();
		return new EntryCursor(result);
	}

	public void close() {
		dbHelper.close();
	}

	public static class EntryCursor extends CursorWrapper {

		public EntryCursor(Cursor cursor) {
			super(cursor);
			// TODO Auto-generated constructor stub
		}

		public Item getItem() {
			if (isBeforeFirst() || isAfterLast())
				return null;
			ContentValues values = new ContentValues();
			values.put(
					FeedReaderContract.EntryColumn.ENTRY_ID,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.ENTRY_ID)));
			values.put(
					FeedReaderContract.EntryColumn.AUTHOR,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.AUTHOR)));
			values.put(
					FeedReaderContract.EntryColumn.CONTENT,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.CONTENT)));
			values.put(
					FeedReaderContract.EntryColumn.KEYWORDS,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.KEYWORDS)));
			values.put(
					FeedReaderContract.EntryColumn.PUBLISHED,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.PUBLISHED)));
			values.put(
					FeedReaderContract.EntryColumn.TITLE,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.TITLE)));
			values.put(
					FeedReaderContract.EntryColumn.UNREAD,
					getString(getColumnIndex(FeedReaderContract.EntryColumn.UNREAD)));
			return new Item(values);
		}
	}

	public EntryCursor getSavedEntries() {
		// TODO Auto-generated method stub
		return getEntriesCursorByTagId(SpecialCategories.getSavedTagId());

	}

	public EntryCursor getEntriesCursorByTagId(String tagId) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from " + EntryColumn.TABLE_NAME + " join "
				+ TagEntryColumn.TABLE_NAME + " where " + TagEntryColumn.TAG_ID
				+ "=\"" + tagId + "\";";
		Cursor c = db.rawQuery(sql, null);
		return new EntryCursor(c);
	}

	public void addEntries(StreamContentResponse response) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SubscriptionEntryDbHelper subscriptionEntryDbHelper = SubscriptionEntryDbHelper
				.getsSubscriptionTagDbHelper(dbHelper);
		List<Item> items = response.getItems();
		List<String> entryIds = new ArrayList<String>();
		List<String> subscriptionIds = new ArrayList<String>();
		for (Item i : items) {
			subscriptionIds.add(i.getOrigin().getStreamId());
			entryIds.add(i.getId());
			addItem(i);

		}
		subscriptionEntryDbHelper.addEntries(entryIds, subscriptionIds);
	}

	public void markItemAsRead(String itemId) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(EntryColumn.STATUS,Status.PENDING);
		values.put(EntryColumn.PENDING_OPEATION, ItemPendingOperation.MARK_AS_READ);
		values.put(EntryColumn.UNREAD,false);
		db.update(EntryColumn.TABLE_NAME, values, EntryColumn.ENTRY_ID + " = ?", new String[]{itemId});
	}
	
	public void markItemAsUnread(String itemId){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(EntryColumn.STATUS,Status.PENDING);
		values.put(EntryColumn.PENDING_OPEATION, ItemPendingOperation.MARK_AS_UNREAD);
		values.put(EntryColumn.UNREAD,true);
		db.update(EntryColumn.TABLE_NAME, values, EntryColumn.ENTRY_ID + " = ?", new String[]{itemId});
	
	}
	
	//bug - user tag an item,untag item and again tag
	public void markItemAsTagged(String itemId, String tagId){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(TagEntryColumn.STATUS,Status.PENDING);
		values.put(TagEntryColumn.PENDING_OPERATION, ItemPendingOperation.TAG_ITEM);
		values.put(TagEntryColumn.ENTRY_ID,itemId);
		values.put(TagEntryColumn.TAG_ID, tagId);
		db.insert(TagEntryColumn.TABLE_NAME, null, values);
	}
	
	public void markItemAsUntagged(String itemId, String tagId){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(TagEntryColumn.STATUS, Status.PENDING);
		values.put(TagEntryColumn.PENDING_OPERATION, ItemPendingOperation.UNTAG_ITEM);
		db.update(TagEntryColumn.TABLE_NAME, values, TagEntryColumn.ENTRY_ID+"= ? and "+
		TagEntryColumn.TAG_ID + " = ?", new String[] {itemId,tagId});
	}
}
