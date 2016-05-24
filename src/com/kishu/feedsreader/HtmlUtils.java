package com.kishu.feedsreader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlUtils {

	// Change it to use html jsoup library
	public static String getFirstImageUrl(String source) {
		Document doc = Jsoup.parse(source);
		Elements elements = doc.select("img");
		if (elements.size() != 0) {
			return elements.get(0).attr("src");
		}

		return null;
	}

}
