package com.kishu.feedsreader.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.kishu.feedsreader.R;

public class SubscriptionPreviewActivity extends FragmentActivity {

	public static final String FEED_ID = "com.kishu.feedsreader.subscription_preview_feed_id";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.subscription_preview);
		String feedId = getIntent().getStringExtra(FEED_ID);
		Bundle bundle = new Bundle();
		bundle.putString(FEED_ID, feedId);
		SubscriptionPreviewEntriesFragment fragment = new SubscriptionPreviewEntriesFragment();
		fragment.setArguments(bundle);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction()
				.add(R.id.subscription_preview_container, fragment).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.subscription_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.addSubscription) {

		}
		return super.onOptionsItemSelected(item);
	}

}
