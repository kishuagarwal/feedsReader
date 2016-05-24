package com.kishu.feedsreader;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kishu.feedsreader.data.FeedManager;

public class ProfileFetcher implements DownloadTask.ResponseHandler {

	public static final String PROFILE_FETCH = "com.kishu.feedsreader.profile_fetch";
	private Context ctx;

	public ProfileFetcher(Context ctx) {
		this.ctx = ctx;
	}

	public void fetch() {
		String url = "http://sandbox.feedly.com/v3/profile";
		DownloadTask task = new DownloadTask(this, url, null, false,
				PROFILE_FETCH);
		task.enableAuthentication();
		task.execute();
	}

	@Override
	public void onResponseSuccess(String response, String request) {
		// TODO Auto-generated method stub
		try {
			JSONObject object = new JSONObject(response);
			// FeedApplication.getInstance().getSharedPreferences().edit()
			// .putString("id", object.getString("id")).commit();
			FeedManager.getInstance().installGlobalIds(
					object.getString("id"));
			Intent intent = new Intent(ctx, UpdateService.class);
			intent.putExtra(UpdateService.ACTION,
					UpdateService.SUBSCRIPTIONS_REFRESH);
			ctx.startService(intent);
		} catch (JSONException e) {
			Log.d("Profile Fetcher", e.getMessage());
		}

	}

	@Override
	public void onResponseError(String error) {
		// TODO Auto-generated method stub
		Log.d("Profile Fetcher", error);
	}

}
