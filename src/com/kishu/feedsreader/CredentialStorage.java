package com.kishu.feedsreader;

import java.io.IOException;

import com.kishu.feedsreader.sources.FeedlyManager;

import android.content.Context;
import android.content.SharedPreferences;

public class CredentialStorage {

	private static SharedPreferences mSharedPreferences;
	private static String userId;
	private static CredentialStorage mCredentialStorage;

	public static CredentialStorage getCredentialStorageInstance() {
		if (mCredentialStorage == null)
			mCredentialStorage = new CredentialStorage();
		return mCredentialStorage;
	}

	public CredentialStorage() {
		Context ctx = FeedApplication.getInstance().getBaseContext();
		mSharedPreferences = ctx.getSharedPreferences(
				getDefaultSharedPreferencesName(ctx),
				getDefaultSharedPreferencesMode());
		userId = mSharedPreferences.getString(Constants.USER_ID, null);
	}

	public String getAccessToken() {
		return mSharedPreferences.getString(Constants.ACCESS_TOKEN, null);
	}

	public void invalidate() throws IOException {

		FeedlyManager.getInstance().updateAccessToken();
	}

	private static String getDefaultSharedPreferencesName(Context context) {
		return context.getPackageName() + "_preferences";
	}

	private static int getDefaultSharedPreferencesMode() {
		return Context.MODE_PRIVATE;
	}

	public String getUserId() {
		return userId;
	}

}
