package com.kishu.feedsreader.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kishu.feedsreader.HtmlUtils;
import com.kishu.feedsreader.R;
import com.kishu.feedsreader.data.EntriesDbHelper.EntryCursor;
import com.kishu.feedsreader.data.Item;
import com.squareup.picasso.Picasso;

public class EntryCursorAdapter extends CursorAdapter {

	private EntryCursor cursor;

	public EntryCursorAdapter(Context context, EntryCursor c) {
		super(context, c, 0);
		// TODO Auto-generated constructor stub
		this.cursor = c;

	}

	@Override
	public void bindView(View view, Context ctx, Cursor c) {
		// TODO Auto-generated method stub
		Item e = cursor.getItem();
		String imgUrl = HtmlUtils.getFirstImageUrl(e.getContent().getContent());
		if (imgUrl != null) {
			ImageView imgView = (ImageView) view
					.findViewById(R.id.articleImageView);
			imgView.setVisibility(View.VISIBLE);
			Picasso.with(ctx).load(imgUrl).placeholder(R.drawable.empty_image).fit().centerCrop()
					.into(imgView);
		}
		((TextView) view.findViewById(R.id.title)).setText(e.getTitle());
		((TextView) view.findViewById(R.id.updated)).setText(e
				.getUpdatedString());
	}

	@Override
	public View newView(Context ctx, Cursor c, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(ctx).inflate(R.layout.feeds_item, parent,
				false);
		return v;
	}

}
