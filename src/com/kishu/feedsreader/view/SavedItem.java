package com.kishu.feedsreader.view;

import com.kishu.feedsreader.CredentialStorage;

public class SavedItem implements ListItem {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Saved";
	}

	@Override
	public String getChildLabel(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "user/"
				+ CredentialStorage.getCredentialStorageInstance().getUserId()
				+ "/tag/global.saved";
	}

	@Override
	public boolean handleClick() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getChild(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getChildId(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

}
