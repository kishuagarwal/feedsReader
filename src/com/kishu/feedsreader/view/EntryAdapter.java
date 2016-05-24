package com.kishu.feedsreader.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kishu.feedsreader.R;
import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.data.Item;

public class EntryAdapter extends BaseAdapter {

	// private feeds feed;
	private List<Item> entries;
	private Context ctx;

	public EntryAdapter(Context ctx, String feedId) {
		// feed = new feeds(feedId,this);
		// feed.fetch();
		this.ctx = ctx;
		entries = FeedManager.getInstance().getEntries(feedId);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entries.size();
	}

	@Override
	public Item getItem(int pos) {
		// TODO Auto-generated method stub
		return entries.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.feeds_item, parent, false);
		}
		TextView feedsTitle = (TextView) view.findViewById(R.id.title);
		feedsTitle.setText(entries.get(pos).getTitle());

		TextView feedsUpdated = (TextView) view.findViewById(R.id.updated);
		feedsUpdated.setText(entries.get(pos).getUpdatedString());
		return view;
	}

	// @Override
	// public void onFeedUpdate() {
	// // TODO Auto-generated method stub
	// notifyDataSetChanged();
	//
	// }

}
