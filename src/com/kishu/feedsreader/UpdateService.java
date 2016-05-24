package com.kishu.feedsreader;

import java.io.IOException;

import org.json.JSONException;

import android.app.IntentService;
import android.content.Intent;

public class UpdateService extends IntentService {

	public static final int SUBSCRIPTIONS_REFRESH = 1;
	public static final int ENTRIES_REFRESH = 2;
	public static final int PROFILE_REFRESH = 3;
	public static final int TOKEN_REFRESH = 4;
	public static final String ACTION = "com.kishu.feedsReader.intent";

	public UpdateService() {
		super("UpdateService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		switch (intent.getIntExtra(ACTION, 0)) {
		case SUBSCRIPTIONS_REFRESH:
			// updateSubscriptions();
			try {
				sync();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case PROFILE_REFRESH:
			updateProfile();
			break;
		case TOKEN_REFRESH:
			updateToken();
			break;
		}

	}

	private void updateProfile() {
		// TODO Auto-generated method stub
		new ProfileFetcher(getApplicationContext()).fetch();
	}

	private void updateSubscriptions() {
		// TODO Auto-generated method stub
		// new SubscriptionsFetcher(getApplicationContext()).fetch();

	}

	// Todo change this update token condition
	private void updateToken() {
		int expiresIn = FeedApplication.getInstance().getSharedPreferences()
				.getInt(Constants.EXPIRES_INT, 0);
		int lastRefreshed = FeedApplication.getInstance()
				.getSharedPreferences().getInt(Constants.LAST_REFRESHED, 0);
		// if(lastRefreshed + expiresIn*1000 < System.currentTimeMillis()/1000)
		// Authenticator.getInstance().updateAccessToken();
	}

	private void sync() throws IOException, JSONException {
		FeedlySync feedlySync = FeedlySync.getInstance();
		feedlySync.sync();
	}

}
