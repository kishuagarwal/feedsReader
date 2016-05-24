package com.kishu.feedsreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask extends AsyncTask<Void, Void, String> {

	private WeakReference<ResponseHandler> handler;
	private String url;
	private ArrayList<NameValuePair> headers;
	private String requestId;
	private boolean isPost;
	private boolean authenticationRequired;

	public DownloadTask(ResponseHandler handler, String url,
			ArrayList<NameValuePair> headers, boolean isPost, String requestId) {
		this.handler = new WeakReference<DownloadTask.ResponseHandler>(handler);
		this.url = url;
		this.headers = headers;
		this.isPost = isPost;
		this.requestId = requestId;
		authenticationRequired = false;

	}

	public void enableAuthentication() {
		authenticationRequired = true;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		InputStream is = getInputStream();
		if (is == null)
			return null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder();
		String res;
		try {
			while ((res = reader.readLine()) != null) {
				response.append(res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(response.toString(), e.getMessage());
			e.printStackTrace();
			return null;
		}

		return response.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if (handler.get() != null) {
			if (result == null)
				handler.get().onResponseError(result);
			handler.get().onResponseSuccess(result, requestId);
		}
	}

	private InputStream getInputStream() {

		try {
			if (headers != null) {
				String urlParameters = "?";
				for (int i = 0; i < headers.size(); i++) {
					urlParameters += headers.get(i).getName()
							+ "="
							+ URLEncoder.encode(headers.get(i).getValue(),
									"Utf-8");
					if (i != headers.size() - 1)
						urlParameters += "&";
				}
				url += urlParameters;
			}

			URL u = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) u.openConnection();

			if (authenticationRequired) {
				String accessToken = FeedApplication.getInstance()
						.getSharedPreferences()
						.getString(Constants.ACCESS_TOKEN, null);
				conn.setRequestProperty("Authorization", accessToken);
			}

			if (isPost && headers != null) {
				// for(NameValuePair nv:headers)
				// {
				// conn.setRequestProperty(nv.getName(), nv.getValue());
				// }

				conn.setRequestMethod("POST");
			}

			conn.connect();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return conn.getInputStream();
			} else
				return null;
		} catch (Exception e) {
			Log.d(url, e.getMessage());
			return null;
		}

	}

	public interface ResponseHandler {

		public void onResponseSuccess(String response, String request);

		public void onResponseError(String error);
	}

}
