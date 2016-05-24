package com.kishu.feedsreader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kishu.feedsreader.CredentialStorage;
import com.kishu.feedsreader.FeedReaderContract.TagColumn;
import com.kishu.feedsreader.data.EntriesDbHelper.EntryCursor;

public class TagDbHelper {

	private FeedReaderDbHelper dbHelper;
	private static TagDbHelper tagDbHelper;

	private TagDbHelper(FeedReaderDbHelper dbHelper) {
		this.dbHelper = dbHelper;

	}

	public static TagDbHelper getEntriesDbHelper(FeedReaderDbHelper dbHelper) {
		if (tagDbHelper == null)
			tagDbHelper = new TagDbHelper(dbHelper);
		return tagDbHelper;
	}

	public void addSavedTag() {
		if (isSavedTagExists())
			return;
		String tag = "user/"
				+ CredentialStorage.getCredentialStorageInstance().getUserId()
				+ "/tag/global.saved";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TagColumn.TAG_ID, tag);
		values.put(TagColumn.TAG_LABEL, "Saved");
		db.insert(TagColumn.TABLE_NAME, null, values);

	}

	public boolean isSavedTagExists() {
		String tag = "user/"
				+ CredentialStorage.getCredentialStorageInstance().getUserId()
				+ "/tag/global.saved";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "select count(*) from " + TagColumn.TABLE_NAME
				+ " where " + TagColumn.TAG_ID + "=\'" + tag + "\';";
		Cursor c = db.rawQuery(query, null);
		if (c.getCount() != 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}

	public EntryCursor getEntryCursor(String tagId) {

		return EntriesDbHelper.getEntriesDbHelper(dbHelper)
				.getEntriesCursorByTagId(tagId);

	}

	public List<Tag> getTags() {
		final List<Tag> tags = new ArrayList<Tag>();

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.query(TagColumn.TABLE_NAME, null, null, null, null, null,
				null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			do {
				Tag tag = new Tag();
				tag.setId(c.getString(c.getColumnIndex(TagColumn.TAG_ID)));
				tag.setLabel(c.getString(c.getColumnIndex(TagColumn.TAG_LABEL)));
				tags.add(tag);
			} while (c.moveToNext());
		}
		return tags;
	}

}
