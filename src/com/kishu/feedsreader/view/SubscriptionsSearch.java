package com.kishu.feedsreader.view;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.kishu.feedsreader.R;
import com.kishu.feedsreader.SearchResultItem;
import com.kishu.feedsreader.sources.FeedlyManager;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class SubscriptionsSearch extends Activity implements
		OnItemClickListener {

	public static final String SEARCH_SUBSCRIPTIONS_ID = "com.kishu.feedsreader.search_subscriptions_id";
	public static final String TAG = "SubscriptionsSearch";

	public static final int SUBSCRIPTION_ADD = 1;

	private EditText searchBox;
	private ListView searchResultsListView;
	private SubscriptionsSearchResultAdapter adapter;
	private SearchResultItem searchResults;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_subcriptions);
		getActionBar().setTitle("Search");
		searchBox = (EditText) findViewById(R.id.searchId);
		progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
		searchResultsListView = (ListView) findViewById(R.id.searchResultListView);
		adapter = new SubscriptionsSearchResultAdapter(null, this);
		searchResultsListView.setAdapter(adapter);
		searchResultsListView.setOnItemClickListener(this);
	}

	public void onSearchButtonClicked(View v) {
		final String queryText = searchBox.getText().toString();
		new Thread() {
			public void run() {
				try {
					final SearchResultItem searchResultItem = FeedlyManager
							.getInstance().getSearchResults(queryText, null,
									null);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							onResponseSuccess(searchResultItem);
						}
					});
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							onResponseError(e.getMessage());
						}
					});
				}

			};
		}.start();
		progressBar.setVisibility(View.VISIBLE);
		Crouton.makeText(this, "Searching...", Style.INFO).show();
	}

	public void onResponseSuccess(SearchResultItem searchResults) {
		// TODO Auto-generated method stub
		// searchBox.setText(response);
		this.searchResults = searchResults;
		progressBar.setVisibility(View.INVISIBLE);
		Crouton.cancelAllCroutons();
		searchResultsListView.setVisibility(View.VISIBLE);
		adapter.setSearchResults(searchResults);

	}

	public void onResponseError(String error) {
		// TODO Auto-generated method stub
		this.searchResults = null;
		Crouton.makeText(this,
				"Problem while getting results.\nPlease Try Again", Style.INFO)
				.show();
	}

	public void onAddButtonClicked(String feedId) {
		addNewSubscription(feedId);
	}

	private void addNewSubscription(final String feedId) {
		// TODO Auto-generated method stub
		Crouton.showText(this, "Adding", Style.INFO);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					boolean result = FeedlyManager.getInstance()
							.subscribeToFeed(feedId);
					if (result) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								onSubscriptionAddSuccess();
							}
						});
					}
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							onSubscriptionAddError(e.getMessage());
						}
					});
				}
			}
		}.start();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		/*
		 * Result result = searchResults.getResults().get(position); Intent
		 * intent = new Intent(this,SubscriptionPreviewActivity.class);
		 * intent.putExtra(SubscriptionPreviewActivity.FEED_ID ,
		 * result.getFeedId()); startActivityForResult(intent,
		 * SUBSCRIPTION_ADD);
		 */
	}

	private void onSubscriptionAddSuccess() {
		Crouton.showText(this, "Subscription Added.", Style.INFO);
	}

	private void onSubscriptionAddError(String error) {
		Crouton.showText(this, error, Style.ALERT);
	}

}
