package com.kishu.feedsreader.view;

import java.util.List;

import com.kishu.feedsreader.data.Subscription;

public class CategoriesSubscriptionsItem implements ListItem {

	private String id;
	private String label;
	private List<Subscription> subscriptions;

	public CategoriesSubscriptionsItem() {

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return subscriptions.size();
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return label;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getChildLabel(int pos) {
		// TODO Auto-generated method stub
		return subscriptions.get(pos).getTitle();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handleClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getChild(int pos) {
		// TODO Auto-generated method stub
		return subscriptions.get(pos);
	}

	@Override
	public String getChildId(int pos) {
		// TODO Auto-generated method stub
		return subscriptions.get(pos).getId();
	}
}
