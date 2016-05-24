package com.kishu.feedsreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kishu.feedsreader.FeedReaderContract;
import com.kishu.feedsreader.FeedReaderContract.CategoriesColumn;
import com.kishu.feedsreader.FeedReaderContract.CategorySubscriptionColumn;
import com.kishu.feedsreader.FeedReaderContract.EntryColumn;
import com.kishu.feedsreader.FeedReaderContract.SubscriptionColumn;
import com.kishu.feedsreader.FeedReaderContract.SubscriptionEntryColumn;
import com.kishu.feedsreader.FeedReaderContract.TagColumn;
import com.kishu.feedsreader.FeedReaderContract.TagEntryColumn;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 15;
	public static final String DATABASE_NAME = "FeedReader.db";
	private static final String CREATE_SUBCRIPTIONS_TABLE_SQL = "CREATE TABLE "
			+ SubscriptionColumn.TABLE_NAME + " (" + SubscriptionColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SubscriptionColumn.SUBSCRIPTIONS_ID + " TEXT  NOT NULL, "
			+ SubscriptionColumn.CONTINUATION + " TEXT, "
			+ SubscriptionColumn.LAST_UPDATED + " INTEGER, "
			+ SubscriptionColumn.TITLE + " TEXT, " + SubscriptionColumn.WEBSITE
			+ " TEXT, " + SubscriptionColumn.VISUALURL + " TEXT);";
	private static final String CREATE_CATEGORIES_TABLE_SQL = "CREATE TABLE "
			+ CategoriesColumn.TABLE_NAME + " (" + CategoriesColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CategoriesColumn.CATEGORIES_ID + " TEXT  NOT NULL, "
			+ CategoriesColumn.CATEGORY_LABEL + " TEXT NOT NULL); ";

	private static final String CREATE_CATEGORY_SUBSCRITIPTON_TABLE_SQL = "CREATE TABLE "
			+ CategorySubscriptionColumn.TABLE_NAME
			+ " ("
			+ CategorySubscriptionColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CategorySubscriptionColumn.CATEGORY_ID
			+ " TEXT  NOT NULL, "
			+ CategorySubscriptionColumn.SUBSCRIPTION_ID + " TEXT NOT NULL );";

	private static final String CREATE_TAG_TABLE_SQL = "CREATE TABLE "
			+ TagColumn.TABLE_NAME + " (" + TagColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TagColumn.TAG_ID
			+ " TEXT  NOT NULL, " + TagColumn.TAG_LABEL + " TEXT NOT NULL );";
	private static final String CREATE_TAG_ENTRY_TABLE_SQL = "CREATE TABLE "
			+ TagEntryColumn.TABLE_NAME + " (" + TagEntryColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TagEntryColumn.TAG_ID
			+ " TEXT  NOT NULL, " 
			+ TagEntryColumn.STATUS + " INTEGER, "
			+ TagEntryColumn.PENDING_OPERATION + " INTEGER, "
			+ TagEntryColumn.ENTRY_ID
			+ " TEXT NOT NULL );";

	private static final String CREATE_ENTRY_TABLE_SQL = "CREATE TABLE "
			+ EntryColumn.TABLE_NAME + " (" + EntryColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EntryColumn.AUTHOR
			+ " TEXT, " + EntryColumn.CONTENT + " TEXT NOT NULL, "
			+ EntryColumn.ENTRY_ID + " TEXT  NOT NULL, " + EntryColumn.KEYWORDS
			+ " TEXT, " 
			+ EntryColumn.STATUS + " INTEGER, "
			+ EntryColumn.PENDING_OPEATION + " INTEGER, "
			+ EntryColumn.PUBLISHED + " INTEGER, " + EntryColumn.TITLE
			+ " TEXT, " + EntryColumn.UNREAD + " BOOLEAN );";

	private static final String CREATE_SUBSCRIPTION_ENTRY_TABLE_SQL = "CREATE TABLE "
			+ SubscriptionEntryColumn.TABLE_NAME
			+ " ("
			+ SubscriptionEntryColumn._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SubscriptionEntryColumn.ENTRY_ID
			+ " TEXT  NOT NULL, "
			+ SubscriptionEntryColumn.SUBSCRIPTION_ID + " TEXT NOT NULL );";

	public FeedReaderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_SUBCRIPTIONS_TABLE_SQL);
		db.execSQL(CREATE_ENTRY_TABLE_SQL);
		db.execSQL(CREATE_CATEGORIES_TABLE_SQL);
		db.execSQL(CREATE_TAG_TABLE_SQL);
		db.execSQL(CREATE_TAG_ENTRY_TABLE_SQL);
		db.execSQL(CREATE_CATEGORY_SUBSCRITIPTON_TABLE_SQL);
		db.execSQL(CREATE_SUBSCRIPTION_ENTRY_TABLE_SQL);
		// ContentValues values = new ContentValues();
		// values.put(FeedReaderContract.CategoriesColumn.CATEGORIES_ID,
		// "Uncategorized");
		// values.put(FeedReaderContract.CategoriesColumn.CATEGORY_LABEL ,
		// "Uncategorized");
		// values.put(FeedReaderContract.CategoriesColumn.SUBSCRIPTIONS, "");
		// db.insert(FeedReaderContract.CategoriesColumn.TABLE_NAME , null,
		// values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.CategoriesColumn.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.SubscriptionColumn.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.EntryColumn.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.TagColumn.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.TagEntryColumn.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.CategorySubscriptionColumn.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ FeedReaderContract.SubscriptionEntryColumn.TABLE_NAME);
		onCreate(db);
	}

}
