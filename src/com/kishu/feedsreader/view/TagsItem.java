package com.kishu.feedsreader.view;

import java.util.ArrayList;
import java.util.List;

import com.kishu.feedsreader.data.Tag;

public class TagsItem implements ListItem {

	private List<Tag> tags;

	public TagsItem() {
		tags = new ArrayList<Tag>();
	}

	public TagsItem(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tags.size();
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Tags";
	}

	@Override
	public String getChildLabel(int pos) {
		// TODO Auto-generated method stub
		return tags.get(pos).getLabel();
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
		return tags.get(pos);
	}

	@Override
	public String getChildId(int pos) {
		// TODO Auto-generated method stub
		return tags.get(pos).getId();
	}

}
