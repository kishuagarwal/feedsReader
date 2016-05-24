package com.kishu.feedsreader;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.sources.FeedlyConstants;

/**
 * @author Kishu Agarwal Class which handles all the authentication work.
 */
public class OAuth implements DownloadTask.ResponseHandler {

	public static final String TAG = "Authentication";

	private String USER_ID;
	private static String ACCESS_TOKEN;
	private static String REFRESH_TOKEN;
	private static long EXPIRES_INT;
	private static String TOKEN_TYPE;
	private static String PLAN;
	public static OAuth oauth;
	public final static String REQUEST_CODE = "com.kishu.feedsreader.request_code";
	public final static String REQUEST_TOKEN = "com.kishu.feedsreader.request_token";
	public final static String REQUEST_REFRESH_TOKEN = "com.kishu.feedsreader.request_refresh_token";
	private SharedPreferences mSharedPreferences;
	private static Context ctx;

	// private OAuth(){
	// if(FileStorage.isFileExists())
	// alreadyInitialized = true;
	// else
	// alreadyInitialized = false;

	// }

	public static OAuth getInstance(Context ctx) {
		if (oauth == null) {
			oauth = new OAuth(ctx);
		}
		return oauth;
	}

	private OAuth(Context ctx) {
		mSharedPreferences = FeedApplication.getInstance()
				.getSharedPreferences();
		this.ctx = ctx;
		if (mSharedPreferences.contains(Constants.ACCESS_TOKEN)) {
			readParameters();
		}
	}

	private void readParameters() {
		// TODO Auto-generated method stub
		ACCESS_TOKEN = mSharedPreferences.getString(Constants.ACCESS_TOKEN,
				null);
		EXPIRES_INT = mSharedPreferences.getLong(Constants.EXPIRES_INT, -1);
		REFRESH_TOKEN = mSharedPreferences.getString(Constants.REFRESH_TOKEN,
				null);
		USER_ID = mSharedPreferences.getString(Constants.USER_ID, null);

	}

	public void processCode(String code) {

		final String url = FeedlyConstants.TOKEN_URL;
		final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("client_id",
				FeedlyConstants.CLIENT_ID));
		params.add(new BasicNameValuePair("client_secret",
				FeedlyConstants.CLIENT_SECRET));
		params.add(new BasicNameValuePair("redirect_uri",
				FeedlyConstants.CALLBACK_URL));
		params.add(new BasicNameValuePair("grant_type",
				FeedlyConstants.GRANT_TYPE));

		DownloadTask task = new DownloadTask(this, url, params, true,
				OAuth.REQUEST_TOKEN);
		task.execute();

	}

	private void processTokenResponse(String result) {

		try {
			// Log.d("auth", result);
			JSONObject response = new JSONObject(result);
			REFRESH_TOKEN = response.optString("refresh_token");
			USER_ID = response.optString("id");
			ACCESS_TOKEN = response.optString("access_token");
			EXPIRES_INT = response.optInt("expires_int");
			TOKEN_TYPE = response.optString("token_type");
			PLAN = response.optString("plan");
			storeParameters();
			FeedManager.getInstance().installGlobalIds(USER_ID);
		} catch (JSONException e) {
			Log.d(TAG, "Error parsing json");
		}

	}

	public void updateAccessToken() {
		String url = FeedlyConstants.TOKEN_URL;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("refresh_token", REFRESH_TOKEN));
		params.add(new BasicNameValuePair("client_id",
				FeedlyConstants.CLIENT_ID));
		params.add(new BasicNameValuePair("client_secret",
				FeedlyConstants.CLIENT_SECRET));
		params.add(new BasicNameValuePair("grant_type", REFRESH_TOKEN));

		DownloadTask task = new DownloadTask(this, url, params, true,
				OAuth.REQUEST_REFRESH_TOKEN);
		task.execute();
	}

	public String getUserId() {
		return USER_ID;
	}

	public String getAccessToken() {
		return ACCESS_TOKEN;
	}

	private void storeParameters() {
		mSharedPreferences.edit().putString(Constants.USER_ID, USER_ID)
				.putString(Constants.ACCESS_TOKEN, ACCESS_TOKEN)
				.putString(Constants.REFRESH_TOKEN, REFRESH_TOKEN)
				.putLong(Constants.EXPIRES_INT, EXPIRES_INT)
				.putString(Constants.PLAN, PLAN)
				.putString(Constants.TOKEN_TYPE, TOKEN_TYPE)
				.putLong(Constants.LAST_REFRESHED, System.currentTimeMillis())
				.apply();

	}

	@Override
	public void onResponseSuccess(String response, String request_id) {
		// TODO Auto-generated method stub
		// Log.d("auth1",response);
		if (request_id == OAuth.REQUEST_TOKEN) {
			processTokenResponse(response);
		} else if (request_id == OAuth.REQUEST_REFRESH_TOKEN) {
			processRefreshToken(response);
		}
	}

	private void processRefreshToken(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			ACCESS_TOKEN = result.optString("access_token");
			REFRESH_TOKEN = result.optString("refresh_token");
			EXPIRES_INT = result.optInt("expires_int");
			storeParameters();
		} catch (JSONException e) {
			Log.d(TAG, "Error parsing refresh token.");
		}
	}

	@Override
	public void onResponseError(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Error: " + error);
	}
}
