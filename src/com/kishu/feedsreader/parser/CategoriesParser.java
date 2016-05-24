package com.kishu.feedsreader.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.kishu.feedsreader.data.Category;

public class CategoriesParser {

	public static final String CATEGORY_ID = "id";
	public static final String CATEGORY_LABEL = "label";

	public static List<com.kishu.feedsreader.data.Category> parseCategories(
			String response) {

		List<Category> categories = new ArrayList<Category>();

		try {
			JSONArray array = new JSONArray(response);
			for (int i = 0; i < array.length(); i++) {
				Category c = new Category();
				c.setId(array.getJSONObject(i).getString(CATEGORY_ID));
				c.setLabel(array.getJSONObject(i).getString(CATEGORY_LABEL));
				categories.add(c);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("CateogiesParser", "Error parsing cateogies json");
		}

		return categories;
	}

}
