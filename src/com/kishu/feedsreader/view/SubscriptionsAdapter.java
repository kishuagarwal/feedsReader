package com.kishu.feedsreader.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.kishu.feedsreader.R;

public class SubscriptionsAdapter extends BaseExpandableListAdapter implements com.kishu.feedsreader.FeedlySync.OnSubscriptionUpdateListener {
	private List<ListItem> list;
	private ExapandableListData data;
	private Context ctx;

	// private Subscriptions subcriptions;
	public SubscriptionsAdapter(Context ctx) {
		this.ctx = ctx;
		data = ExapandableListData.getExapandableListData(ctx);
		list = data.getListData();
	}

	@Override
	public Object getChild(int groupPos, int itemPos) {
		// TODO Auto-generated method stub
		return list.get(groupPos).getChild(itemPos);
	}

	@Override
	public long getChildId(int group, int child) {
		// TODO Auto-generated method stub
		return group * 10 + child;
	}

	@Override
	public View getChildView(int groupPos, int childPos, boolean isLastChild,
			View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.expandable_list_child_item, parent, false);
		}
		// row.setTypeface(Typeface.DEFAULT_BOLD);
		TextView tv = (TextView) view
				.findViewById(R.id.expandable_list_child_item);
		tv.setText(list.get(groupPos).getChildLabel(childPos));

		return view;
	}

	@Override
	public int getChildrenCount(int groupPos) {
		// TODO Auto-generated method stub
		return list.get(groupPos).getCount();
	}

	@Override
	public Object getGroup(int groupPos) {
		// TODO Auto-generated method stub
		return list.get(groupPos);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int groupPos) {
		// TODO Auto-generated method stub
		return groupPos;
	}

	@Override
	public View getGroupView(int groupPos, boolean isExpanded, View view,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.expandable_list_group, parent, false);
		}
		// row.setTypeface(Typeface.DEFAULT_BOLD);
		TextView tv = (TextView) view
				.findViewById(R.id.expandable_list_group_item);
		tv.setText(list.get(groupPos).getLabel());

		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onSubscriptionUpdate() {
		// TODO Auto-generated method stub
		data.requery();
		list = data.getListData();
		notifyDataSetChanged();
	}

}
