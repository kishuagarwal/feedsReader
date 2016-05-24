package com.kishu.feedsreader.view;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kishu.feedsreader.R;
import com.squareup.picasso.Picasso;

public class LazyImageView extends ImageView {

	private Drawable mPlaceholder, mImage;

	public LazyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public LazyImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public LazyImageView(Context context) {
		super(context, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();

	}

	public void setPlaceholderImage(Drawable image) {
		mPlaceholder = image;
		if (mImage == null)
			setImageDrawable(mPlaceholder);
	}

	public void setPlaceholderImage(int resId) {
		mPlaceholder = getResources().getDrawable(resId);
		if (mImage == null) {
			setImageDrawable(mPlaceholder);
		}
	}

	public void setImageUrl(String url) {
		/*
		 * Ion.with(this).placeholder(R.drawable.empty_image)
		 * .error(R.drawable.unread) .load(url);
		 */
		Picasso.with(getContext()).load(url)
				.placeholder(R.drawable.empty_image).into(this);

	}

	private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String url = arg0[0];
			try {
				URLConnection conn = (new URL(url)).openConnection();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer out = new ByteArrayBuffer(50);
				int current = 0;
				while ((current = bis.read()) != -1) {
					out.append((byte) current);
				}
				byte[] imageData = out.toByteArray();
				return BitmapFactory.decodeByteArray(imageData, 0,
						imageData.length);
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			mImage = new BitmapDrawable(result);
			if (mImage != null) {
				setImageDrawable(mImage);
			}
		}
	}

}
