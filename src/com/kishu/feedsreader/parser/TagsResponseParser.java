package com.kishu.feedsreader.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.kishu.feedsreader.data.Tag;

public class TagsResponseParser {

	public static List<com.kishu.feedsreader.data.Tag> parseTags(JSONArray o) {
		// TODO Auto-generated method stub
		List<Tag> tags = new ArrayList<Tag>();
		try {
			for (int i = 0; i < o.length(); i++) {
				Tag t = new Tag();
				t.setId(o.getJSONObject(i).getString("id"));
				t.setLabel(o.getJSONObject(i).optString("label", "Saved"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("TagsResponseParser", "Error Parsing tag response json ");
		}

		return tags;
	}

}
