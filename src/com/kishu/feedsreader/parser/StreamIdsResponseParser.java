package com.kishu.feedsreader.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.kishu.feedsreader.StreamIdsResponse;

public class StreamIdsResponseParser {

	public static StreamIdsResponse getStreamIds(JSONObject o) {
		final StreamIdsResponse response = new StreamIdsResponse();
		try {
			JSONArray array = o.getJSONArray("ids");
			List<String> entriesIds = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				entriesIds.add(array.getJSONObject(i).toString());
			}
			response.setIds(entriesIds);
			response.setContinuationId(o.getString("continuation"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("StreamIdsResponseParser", "Error Parsing Stream Ids Json");
		}
		return response;
	}

}
