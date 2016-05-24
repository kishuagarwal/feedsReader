package com.kishu.feedsreader.sources;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kishu.feedsreader.Constants;
import com.kishu.feedsreader.CredentialStorage;
import com.kishu.feedsreader.FeedApplication;
import com.kishu.feedsreader.SearchResultItem;
import com.kishu.feedsreader.StreamIdsResponse;
import com.kishu.feedsreader.UnreadCountResponse;
import com.kishu.feedsreader.data.Category;
import com.kishu.feedsreader.data.Item;
import com.kishu.feedsreader.data.Subscription;
import com.kishu.feedsreader.data.Tag;
import com.kishu.feedsreader.parser.CategoriesParser;
import com.kishu.feedsreader.parser.StreamIdsResponseParser;
import com.kishu.feedsreader.parser.UnreadCountResponseParser;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class FeedlyManager {

	private static FeedlyManager feedlyManager;
	private static OkHttpClient okClient;
	public static final MediaType JSON = MediaType
			.parse("application/json; charset=utf-8");

	public static FeedlyManager getInstance() {

		if (feedlyManager == null) {
			feedlyManager = new FeedlyManager();
		}
		return feedlyManager;

	}

	private FeedlyManager() {
		okClient = new OkHttpClient();
		okClient.setAuthenticator(new Authenticator() {
			
			@Override
			public Request authenticateProxy(Proxy proxy, Response response)
					throws IOException {
				// TODO Auto-generated method stub
				updateAccessToken();
				return response.request().newBuilder().addHeader("Authorization", getAccessToken()).build();
				
			}
			
			@Override
			public Request authenticate(Proxy proxy, Response response) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	public List<Category> getCategories() throws IOException {
		Request request = new Request.Builder()
				.url(FeedlyConstants.CATEGORIES_URL)
				.addHeader("Authorization", getAccessToken()).build();
		Response response = okClient.newCall(request).execute();
		return CategoriesParser.parseCategories(response.body().string());
	}

	public boolean changeCategoryLabel(String id, String label)
			throws IOException {
		JsonObject json = new JsonObject();
		json.addProperty("label", label);
		RequestBody body = RequestBody.create(JSON, json.toString());
		Request request = new Request.Builder()
				.url(FeedlyConstants.CATEGORIES_URL + "/" + id)
				.addHeader("Authorization", getAccessToken()).post(body)
				.build();
		Response response = okClient.newCall(request).execute();
		return response.isSuccessful();
	}

	public boolean deleteCategory(String id) throws IOException {
		Request request = new Request.Builder()
				.url(FeedlyConstants.CATEGORIES_URL + "/" + id).delete()
				.addHeader("Authorization", getAccessToken()).build();
		return okClient.newCall(request).execute().isSuccessful();
	}

	public String getAccessToken() {
		return CredentialStorage.getCredentialStorageInstance().getAccessToken();
	}

	public List<Subscription> getSubcriptions() throws IOException,
			JSONException {
		Request request = new Request.Builder()
				.url(FeedlyConstants.SUBSCRIPTIONS_URL)
				.addHeader("Authorization", getAccessToken()).build();
		Response response = okClient.newCall(request).execute();
		if(response.code() == 401) //expired token
		{
			CredentialStorage.getCredentialStorageInstance().invalidate();
			return getSubcriptions();
		}
			
		// JSONArray array = new JSONArray(response.body().string());
		// return SubscriptionsJsonParser.getSubscriptions(array);
		Gson gson = new Gson();
		Type type = new TypeToken<List<com.kishu.feedsreader.data.Subscription>>() {
		}.getType();
		String body = response.body().string();
		return gson.fromJson(body, type);

	}

	public boolean subscribeToFeed(String id) throws IOException {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		RequestBody body = RequestBody.create(JSON, json.toString());
		Request request = new Request.Builder()
				.url(FeedlyConstants.SUBSCRIPTIONS_URL)
				.addHeader("Authorization", getAccessToken()).post(body)
				.build();
		return okClient.newCall(request).execute().isSuccessful();
	}

	public boolean unsubscribeSubscription(String id) throws IOException {
		Request request = new Request.Builder()
				.url(FeedlyConstants.SUBSCRIPTIONS_URL + "/" + id).delete()
				.addHeader("Authorization", getAccessToken()).build();
		return okClient.newCall(request).execute().isSuccessful();
	}

	public StreamIdsResponse getStreamIds(String id, Integer count,
			String ranked, Boolean unreadOnly, String continuationId)
			throws IOException, JSONException {
		JsonObject json = new JsonObject();
		if (count != null)
			json.addProperty("count", count.intValue());
		if (ranked != null)
			json.addProperty("ranked", ranked);
		if (unreadOnly != null)
			json.addProperty("unreadOnly", unreadOnly.toString());
		if (continuationId != null)
			json.addProperty("continuation", continuationId);
		RequestBody body = RequestBody.create(JSON, json.toString());
		Request request = new Request.Builder()
				.url(FeedlyConstants.STREAMS_URL + id + "/ids")
				.method("GET", body)
				.addHeader("Authorization", getAccessToken()).build();
		Response response = okClient.newCall(request).execute();
		JSONObject o = new JSONObject(response.body().string());
		return StreamIdsResponseParser.getStreamIds(o);

	}

	public com.kishu.feedsreader.data.StreamContentResponse getStreamContent(
			String id, Integer count, String ranked, Boolean unreadOnly,
			String continuationId) throws IOException, JSONException {
		StringBuffer string = new StringBuffer(FeedlyConstants.STREAMS_URL
				+ "contents?streamId=" + id);
		if (count != null)
			string.append("&count=" + count.intValue());
		if (ranked != null)
			string.append("&ranked=" + ranked);
		if (unreadOnly != null)
			string.append("&unreadOnly=" + unreadOnly.toString());
		if (continuationId != null)
			string.append("&continuation=" + continuationId);
		Request request = new Request.Builder().url(string.toString())
				.addHeader("Authorization", getAccessToken()).build();
		Response response = okClient.newCall(request).execute();
		String body = response.body().string();
		Gson gson = new Gson();
		return gson.fromJson(body,
				com.kishu.feedsreader.data.StreamContentResponse.class);

	}

	public List<Tag> getTags() throws IOException, JSONException {
		Request request = new Request.Builder().url(FeedlyConstants.TAGS_URL)
				.addHeader("Authorization", getAccessToken()).build();
		com.squareup.okhttp.Response response = okClient.newCall(request)
				.execute();
		JSONArray o = new JSONArray(response.body().string());
		return com.kishu.feedsreader.parser.TagsResponseParser.parseTags(o);
	}

	public boolean tagsEntries(List<Item> entries, List<Tag> tags)
			throws JSONException, IOException {
		StringBuilder url = new StringBuilder(FeedlyConstants.TAGS_URL + "/");
		for (Tag tag : tags) {
			url.append(tag.getId() + ",");
		}
		url.deleteCharAt(url.length() - 1);
		JSONObject o = new JSONObject();
		JSONArray array = new JSONArray();
		for (Item entry : entries) {
			array.put(entry.getId());

		}
		o.put("entryIds", array);
		Request request = new Request.Builder().url(url.toString())
				.addHeader("Authorization", getAccessToken())
				.put(RequestBody.create(JSON, o.toString())).build();
		return okClient.newCall(request).execute().isSuccessful();
	}

	public UnreadCountResponse getUnreadCounts(Boolean autorefresh,
			Long newerthan, String streamId) throws JSONException, IOException {
		JsonObject body = new JsonObject();
		if (autorefresh != null)
			body.addProperty("autorefresh", String.valueOf(autorefresh));
		if (newerthan != null)
			body.addProperty("newerThan", String.valueOf(newerthan));
		if (streamId != null)
			body.addProperty("streamId", String.valueOf(streamId));
		Request request = new Request.Builder()
				.url(FeedlyConstants.MARKERS_URL + "/counts")
				.addHeader("Authorization", getAccessToken())
				.method("GET", RequestBody.create(JSON, body.toString()))
				.build();
		JSONObject ob = new JSONObject(okClient.newCall(request).execute()
				.body().string());
		return UnreadCountResponseParser.getUnreadCountResponse(ob);
	}

	public SearchResultItem getSearchResults(String query, Integer count,
			String locale) throws IOException {
		StringBuffer url = new StringBuffer(FeedlyConstants.SEARCH_URL);
		url.append("?q=" + query);
		if (count != null)
			url.append("&n=" + count);
		if (locale != null)
			url.append("&locale=" + locale);
		Request request = new Request.Builder().url(url.toString()).build();
		Gson gson = new Gson();
		String responseBody = okClient.newCall(request).execute().body()
				.string();
		return gson.fromJson(responseBody, SearchResultItem.class);
	}
	
	public void updateAccessToken() throws IOException{
		SharedPreferences sharedPreferences = FeedApplication.getInstance().getSharedPreferences();
		Gson gson = new Gson();
		RefreshTokenPojo refreshObject =  new RefreshTokenPojo();
		refreshObject.refresh_token = sharedPreferences.getString(Constants.REFRESH_TOKEN,null);
		refreshObject.client_id = sharedPreferences.getString(Constants.CLIENT_ID, null);
		refreshObject.client_secret = sharedPreferences.getString(Constants.CLIENT_SECRET, null);
		refreshObject.grant_type = "refresh_token";
		String jsonString = gson.toJson(refreshObject,RefreshTokenPojo.class);
		RequestBody body = RequestBody.create(JSON, jsonString); 
		Request request = new Request.Builder().url(FeedlyConstants.TOKEN_URL)
				.post(body)
				.build();
		Response response = okClient.newCall(request).execute();
		RefreshTokenResponse response2 = gson.fromJson(response.body().string(), RefreshTokenResponse.class);
		sharedPreferences.edit().putString(Constants.ACCESS_TOKEN, response2.getAccessToken())
		.putLong(Constants.EXPIRES_INT, response2.getExpiresIn()).apply();
}
}
