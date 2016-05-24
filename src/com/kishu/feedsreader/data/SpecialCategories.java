package com.kishu.feedsreader.data;

import com.kishu.feedsreader.CredentialStorage;

public class SpecialCategories {

	public static String getUncategorizedCategoryId() {
		return "user/" + getUserId() + "/category/global.uncategorized";
	}

	public static String getGlobalAllCategoryId() {
		return "user/" + getUserId() + "/category/global.all";
	}

	public static String getMustCategoryId() {
		return "user/" + getUserId() + "/category/global.must";
	}
	
	public static String getSavedTagId(){
		return "user/" + getUserId() + "/tag/global.saved";
	}

	public static String getUserId() {
		return CredentialStorage.getCredentialStorageInstance().getUserId();
	}

}
