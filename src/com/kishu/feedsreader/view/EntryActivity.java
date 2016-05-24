package com.kishu.feedsreader.view;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

import com.kishu.feedsreader.R;
import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.data.Item;
import com.kishu.feedsreader.data.ItemHelper;

public class EntryActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private List<String> entryIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));
		setContentView(R.layout.viewpager);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		Intent intent = getIntent();
		String feedId = intent.getStringExtra(FeedListFragment.FEED_ID);
		String feedTitle = intent.getStringExtra(FeedListFragment.FEED_TITLE);
		getActionBar().setTitle(feedTitle);
		String currentEntryId = intent.getStringExtra(EntryFragment.ENTRY_ID);
		entryIds = FeedManager.getInstance().getEntryIds(feedId);
		getActionBar().setTitle(feedId);
		mViewPager
				.setAdapter(new EntryPagerAdapter(getSupportFragmentManager()));
		mViewPager.setCurrentItem(entryIds.indexOf(currentEntryId));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int newPageIndex) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.entry_activity_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		String entryId = entryIds.get(mViewPager.getCurrentItem());
		Item entry = ItemHelper.getItem(entryId);
		switch (item.getItemId()) {
		case R.id.Star:
			
			
			break;
		case R.id.readMarker:
			if(entry.isUnread()){
				ItemHelper.markItemAsRead(entry);
			}else
				ItemHelper.markItemAsUnread(entry);
			
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private class EntryPagerAdapter extends FragmentStatePagerAdapter {

		public EntryPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return EntryFragment.getInstance(entryIds.get(position));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return entryIds.size();
		}

	}

}
