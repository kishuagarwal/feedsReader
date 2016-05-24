package com.kishu.feedsreader.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kishu.feedsreader.FeedlySync;
import com.kishu.feedsreader.FeedlySync.OnEntryUpdatedListener;
import com.kishu.feedsreader.SQLiteCursorLoader;
import com.kishu.feedsreader.data.EntriesDbHelper.EntryCursor;
import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.data.Item;

public class FeedListFragment extends ListFragment implements
		LoaderCallbacks<Cursor>, OnEntryUpdatedListener {

	public static final String FEED_ID = "com.kishu.feedsreader.feedlistfragment.feed_id";
	public static final String FEED_TITLE = "com.kishu.feedsreader.feedlistfragment.feed_title"; 
	private static String id;
	private static String feedTitle; 

	// private EntryCursor cursor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		id = args.getString(FEED_ID);
		feedTitle = args.getString(FEED_TITLE); 
		getLoaderManager().initLoader(0, null, this);
		/*
		 * if(id == "saved") { cursor =
		 * FeedManager.getInstance(getActivity()).getSavedEntriesCursor(); }
		 * else cursor =
		 */
		// getLoaderManager().initLoader(arg0, arg1, arg2)
		// setListAdapter(new EntryCursorAdapter(getActivity(),cursor));
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FeedlySync.getInstance().registerOnEntryUpdatedListener(this);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		FeedlySync.getInstance().unregisterOnEntryUpdatedListener();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Item item = ((EntryCursor) ((EntryCursorAdapter) getListAdapter())
				.getItem(position)).getItem();
		item.isUnread();
		Intent intent = new Intent(getActivity(), EntryActivity.class);
		intent.putExtra(EntryFragment.ENTRY_ID, item.getId());
		intent.putExtra(FEED_ID, FeedListFragment.id);
		intent.putExtra(FEED_TITLE, feedTitle);
		// String content = item.getContent();
		// intent.putExtra("content", content);
		// intent.putExtra("title",item.getTitle());
		// intent.putExtra("published", item.getPublished());
		// intent.putExtra("SubscriptionTitle",getIntent().getStringExtra("Subscriptions_title"));
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new EntryCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		// TODO Auto-generated method stub
		EntryCursorAdapter adapter = new EntryCursorAdapter(getActivity(),
				(EntryCursor) c);
		setListAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		setListAdapter(null);
	}

	private static class EntryCursorLoader extends SQLiteCursorLoader {

		public EntryCursorLoader(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Cursor loadCursor() {
			// TODO Auto-generated method stub
			return FeedManager.getInstance().getEntriesCursor(id);
		}

	}

	@Override
	public void onEntriesUpdated() {
		// TODO Auto-generated method stub
		
	}

}
