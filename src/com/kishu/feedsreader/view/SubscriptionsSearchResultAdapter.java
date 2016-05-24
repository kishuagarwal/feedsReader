package com.kishu.feedsreader.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kishu.feedsreader.R;
import com.kishu.feedsreader.Result;
import com.kishu.feedsreader.SearchResultItem;

public class SubscriptionsSearchResultAdapter extends BaseAdapter {

	private SearchResultItem searchResult;
	private final SubscriptionsSearch mSearch;

	public SubscriptionsSearchResultAdapter(SearchResultItem result,
			SubscriptionsSearch subscripitionSearch) {
		this.mSearch = subscripitionSearch;
		this.searchResult = result;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (searchResult == null)
			return 0;
		return searchResult.getResults().size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return searchResult.getResults().get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(int index, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.subscriptions_result_item, parent, false);
		}

		final Result result = searchResult.getResults().get(index);
		ImageView favicon = (ImageView) v.findViewById(R.id.favicon);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView description = (TextView) v
				.findViewById(R.id.descriptionResultItem);
		ImageView addButton = (ImageView) v
				.findViewById(R.id.addSubscriptionButton);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mSearch.onAddButtonClicked(result.getFeedId());
			}
		});
		// TextView subscribersCount = (TextView)
		// v.findViewById(R.id.subscribersCount);
		String titleText = result.getTitle();
		if (titleText != null)
			title.setText(titleText);
		// String descriptionText =
		// searchResult.getResults().get(index).getTitle();
		// if(descriptionText != null)
		// description.setText(descriptionText);
		// int subscribersCountText =
		// searchResult.get(index).getSubscribersCount();
		// if(subscribersCountText != 0)
		// subscribersCount.setText(subscribersCountText+"subscribers");

		return v;
	}

	public void setSearchResults(SearchResultItem searchResults) {
		// TODO Auto-generated method stub
		this.searchResult = searchResults;
		notifyDataSetChanged();
	}

}
