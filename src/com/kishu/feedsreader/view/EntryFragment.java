package com.kishu.feedsreader.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kishu.feedsreader.R;
import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.data.Item;
import com.kishu.feedsreader.data.ItemHelper;

public class EntryFragment extends Fragment {

	private String entryId;
	private Item item;
	private String content;
	public static final String ENTRY_ID = "com.kishu.feedsreader.entry_id";

	public static EntryFragment getInstance(String EntryId) {
		EntryFragment fragment = new EntryFragment();
		Bundle bundle = new Bundle();
		bundle.putString(ENTRY_ID, EntryId);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		entryId = getArguments().getString(ENTRY_ID);
		item = ItemHelper.getItem(entryId);
		content = item.getContent().getContent();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.item, container, false);
		processContentAndAddToView(v);
		return v;

	}

	private void processContentAndAddToView(View v) {
		LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.linearLayoutItem);
		TextView heading = new TextView(getActivity());
		heading.setText(Html.fromHtml(getHtml()));
		layout.addView(heading);
		// TextView date = new TextView(this);
		// date.setText(DateUtils.getRelativeTimeSpanString(itemPublished,
		// System.currentTimeMillis(), 0));
		// layout.addView(date);
		int lastPos = 0, nextPos;
		while (true) {
			nextPos = content.indexOf("<img", lastPos);
			if (nextPos == -1) // is there any image left
			{
				TextView temp = new TextView(getActivity());
				temp.setText(Html.fromHtml(content.substring(lastPos)));
				layout.addView(temp);
				break;
			} else {
				TextView temp = new TextView(getActivity());
				temp.setText(Html.fromHtml(content.substring(lastPos, nextPos))); // text
																					// view
																					// till
																					// the
																					// image
				layout.addView(temp);
				LazyImageView image = new LazyImageView(getActivity());
				LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				image.setLayoutParams(params);
				int urlStartIndex = content.indexOf("src", nextPos) + 5;
				int urlEndIndex = content.indexOf("\"", urlStartIndex);
				String imageUrl = content.substring(urlStartIndex, urlEndIndex);
				image.setImageUrl(imageUrl);
				layout.addView(image);
				lastPos = content.indexOf(">", urlEndIndex) + 1;
			}
		}
	}

	private String getHtml() {
		StringBuffer htmlContent = new StringBuffer();
		htmlContent.append("<h2>" + item.getTitle() + "</h2>");
		htmlContent.append("<small>"
				+ DateUtils.getRelativeTimeSpanString(item.getPublished(),
						System.currentTimeMillis(), 0) + "</small></br>");
		return htmlContent.toString();
	}

}
