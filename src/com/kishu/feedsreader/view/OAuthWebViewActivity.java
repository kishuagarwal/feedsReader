package com.kishu.feedsreader.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kishu.feedsreader.R;
import com.kishu.feedsreader.sources.FeedlyConstants;

public class OAuthWebViewActivity extends Activity {

	public static final int REQUEST_CODE = 1;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		Uri url = Uri.parse(getAuthorizationServerUrl());
		webView = (WebView) findViewById(R.id.webView);
		setJavaScriptEnabled();
		setClient();
		webView.loadUrl(url.toString());

	}

	private void setClient() {
		// TODO Auto-generated method stub
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if (url.contains("localhost")) {
					setResult(RESULT_OK, new Intent().putExtra("code", url));
					finish();
				}

				return super.shouldOverrideUrlLoading(view, url);
			}

		});
	}

	private void setJavaScriptEnabled() {
		// TODO Auto-generated method stub
		webView.getSettings().setJavaScriptEnabled(true);
	}

	private String getAuthorizationServerUrl() {
		StringBuffer u = new StringBuffer(FeedlyConstants.AUTHORIZATION_URL);
		u.append("?response_type=code");
		u.append("&client_id=sandbox");
		u.append("&redirect_uri=http://localhost");
		u.append("&scope=https://cloud.feedly.com/subscriptions");
		return u.toString();

	}

}
