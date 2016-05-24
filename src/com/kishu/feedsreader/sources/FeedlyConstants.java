package com.kishu.feedsreader.sources;

public abstract class FeedlyConstants {

	public static final String BASE_URL = "https://sandbox.feedly.com";
	public static final String USER_ID = "user_id";
	public static final String AUTHORIZATION_URL = BASE_URL + "/v3/auth/auth";
	public static final String TOKEN_URL = BASE_URL + "/v3/auth/token";
	public static final String SCOPE_URL = "https://cloud.feedly.com/subscriptions";
	public static final String CLIENT_ID = "sandbox";
	public static final String CLIENT_SECRET = "ES3R6KCEG46BW9MYD332";
	public static final String CALLBACK_URL = "http://localhost";
	public static final String GRANT_TYPE = "authorization_code";
	public static final String CATEGORIES_URL = BASE_URL + "/v3/categories";
	public static final String SUBSCRIPTIONS_URL = BASE_URL
			+ "/v3/subscriptions";
	public static final String STREAMS_URL = BASE_URL + "/v3/streams/";
	public static final String TAGS_URL = BASE_URL + "/v3/tags";
	public static final String MARKERS_URL = BASE_URL + "/v3/markers";
	public static final String SEARCH_URL = BASE_URL + "/v3/search/feeds";

	private FeedlyConstants() {

	}
}
