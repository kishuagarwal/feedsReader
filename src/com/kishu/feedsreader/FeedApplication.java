package com.kishu.feedsreader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;

public class FeedApplication extends Application {

	private SharedPreferences sharedPreferences;
	private static FeedApplication common;

	@Override
	public void onCreate() {
		super.onCreate();
		common = this;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	public static FeedApplication getInstance() {
		return common;
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public boolean isNetworkUp() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			if (info.getState() == State.CONNECTED)
				return true;
		}
		return false;
	}

	public Context getApplicationContext() {
		return getBaseContext();
	}
}
