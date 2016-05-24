package com.kishu.feedsreader;

import android.provider.BaseColumns;

public class FeedReaderContract {

	private FeedReaderContract() {
	}

	public static abstract class CategoriesColumn implements BaseColumns {
		public static final String TABLE_NAME = "categories";
		public static final String CATEGORIES_ID = "category_id";
		public static final String CATEGORY_LABEL = "label";
		// public static final String SUBSCRIPTION_ID = "subscription_id";
	}

	public static abstract class CategorySubscriptionColumn implements
			BaseColumns {
		public static final String TABLE_NAME = "category_subscription";
		public static final String CATEGORY_ID = "category_id";
		public static final String SUBSCRIPTION_ID = "subscription_id";
		
	
	}

	public static abstract class TagColumn implements BaseColumns {
		public static final String TABLE_NAME = "tags";
		public static final String TAG_ID = "tag_id";
		public static final String TAG_LABEL = "tag_label";

	}

	public static abstract class TagEntryColumn implements BaseColumns {
		public static final String TABLE_NAME = "tagentry";
		public static final String TAG_ID = "tag_id";
		public static final String ENTRY_ID = "entry_id";
		public static final String STATUS = "status";
		public static final String PENDING_OPERATION = "pending_operation";
	
	}

	public static abstract class SubscriptionColumn implements BaseColumns {
		public static final String TABLE_NAME = "subscription";
		public static final String SUBSCRIPTIONS_ID = "subscription_id";
		public static final String LAST_UPDATED = "last_updated";
		public static final String TITLE = "title";
		public static final String WEBSITE = "website";
		public static final String VISUALURL = "visual_url";
		public static final String CONTINUATION = "continuation";

	}

	public static abstract class EntryColumn implements BaseColumns {

		public static final String TABLE_NAME = "entries";
		public static final String ENTRY_ID = "entry_id";
		public static final String PUBLISHED = "published";
		public static final String KEYWORDS = "keywords";
		public static final String AUTHOR = "author";
		public static final String TITLE = "title";
		public static final String UNREAD = "unread";
		public static final String CONTENT = "content";
		public static final String STATUS = "status";
		public static final String PENDING_OPEATION = "pending_operation";
	}

	public static abstract class SubscriptionEntryColumn implements BaseColumns {

		public static final String TABLE_NAME = "subscriptionsEntries";
		public static final String ENTRY_ID = "entry_id";
		public static final String SUBSCRIPTION_ID = "subscription_id";
	}

}
