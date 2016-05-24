package com.kishu.feedsreader.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kishu.feedsreader.UnreadCount;
import com.kishu.feedsreader.UnreadCountResponse;

public class UnreadCountResponseParser {

	public static UnreadCountResponse getUnreadCountResponse(JSONObject object) {
		final UnreadCountResponse response = new UnreadCountResponse();

		try {
			JSONArray array = object.getJSONArray("unreadcounts");
			List<UnreadCount> unreadCounts = new ArrayList<UnreadCount>();
			for (int i = 0; i < array.length(); i++) {
				UnreadCount unreadCount = new UnreadCount();
				JSONObject ob = array.getJSONObject(i);
				unreadCount.setCount(ob.getInt("count"));
				unreadCount.setId(ob.getString("id"));
				unreadCount.setUpdated(ob.getLong("updated"));
				unreadCounts.add(unreadCount);
			}
			response.setUnreadCounts(unreadCounts);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
}
