package com.kishu.feedsreader;

import android.support.v4.util.LruCache;

public class ImageCache<String, Bitmap> extends LruCache<String, Bitmap> {

	public ImageCache(int maxSize) {
		super(maxSize);
		// TODO Auto-generated constructor stub
	}

}
