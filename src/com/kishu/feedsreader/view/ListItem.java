package com.kishu.feedsreader.view;

public interface ListItem {

	public int getCount();

	public String getLabel();

	public String getChildLabel(int pos);

	// applicable if the element handles its own click event like savedPages
	public String getId();

	public boolean handleClick();

	public Object getChild(int pos);

	public String getChildId(int pos);

}
