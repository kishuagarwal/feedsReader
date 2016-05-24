package com.kishu.feedsreader.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.kishu.feedsreader.Constants;
import com.kishu.feedsreader.CredentialStorage;
import com.kishu.feedsreader.FeedlySync;
import com.kishu.feedsreader.OAuth;
import com.kishu.feedsreader.R;
import com.kishu.feedsreader.StringUtils;
import com.kishu.feedsreader.UpdateService;
import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.data.FeedReaderDbHelper;
import com.kishu.feedsreader.sources.FeedlyManager;

public class MainActivity extends FragmentActivity implements
		OnChildClickListener {

	private SubscriptionsAdapter adapter;
	private OAuth authInstance;
	// private DrawerLayout mDrawerLayout;
	private ExpandableListView mListView;
	private SharedPreferences mSharedPreferences;
	private FeedReaderDbHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));

		authInstance = OAuth.getInstance(this.getApplicationContext());
		helper = new FeedReaderDbHelper(getApplicationContext());

		// mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mListView = (ExpandableListView) findViewById(R.id.listView);
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		// authInstance = Authenticator.getInstance();
		// authInstance.authenticate();
		adapter = new SubscriptionsAdapter(this.getApplicationContext());
		mListView.setAdapter(adapter);
		mListView.setOnChildClickListener(this);
		mListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView listview, View view,
					int groupPos, long arg3) {
				// TODO Auto-generated method stub
				ListItem li = (ListItem) adapter.getGroup(groupPos);
				if (li.handleClick()) {
					swapFragments(getSubscriptionFragment(li.getId()));
					return true;
				} else
					return false;
			}
		});

		// FileStorage.setContext(this.getApplicationContext());

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == OAuthWebViewActivity.REQUEST_CODE) {
			String returnString = data.getStringExtra("code");
			authInstance.processCode(StringUtils.getParamValue(returnString,
					"code"));

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!mSharedPreferences.contains(Constants.ACCESS_TOKEN)) {
			Log.d("SharedPreferences", "Not found.");

			Intent intent = new Intent(this, OAuthWebViewActivity.class);

			startActivityForResult(intent, OAuthWebViewActivity.REQUEST_CODE);
		}
		FeedlySync.getInstance().registerOnSubscriptionUpdateListener(adapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		FeedlySync.getInstance().unregisterOnSubscriptionUpdateListener();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.main_menu_refresh:
			Intent intent = new Intent(this, UpdateService.class);
			intent.putExtra(UpdateService.ACTION,
					UpdateService.SUBSCRIPTIONS_REFRESH);
			startService(intent);
			return true;
		case R.id.main_menu_addSubcription:
			startActivity(new Intent(this, SubscriptionsSearch.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public Fragment getSubscriptionFragment(String id) {
		FeedListFragment newFragment = new FeedListFragment();
		Bundle args = new Bundle();
		args.putString(FeedListFragment.FEED_ID, id);
		newFragment.setArguments(args);
		return newFragment;
	}

	public void swapFragments(Fragment newFragment) {

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.container, newFragment).commit();

	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1,
			int groupPos, int childPos, long arg4) {
		// TODO Auto-generated method stub

		ListItem li = (ListItem) adapter.getGroup(groupPos);
		swapFragments(getSubscriptionFragment(li.getChildId(childPos)));

		return true;
	}

}
