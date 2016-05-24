package com.kishu.feedsreader;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class Utils {

	static String response;

	public static String urlPost(final String url) {
		AsyncTask<Integer, Integer, String> task = new AsyncTask<Integer, Integer, String>() {

			@SuppressWarnings("finally")
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO Auto-generated method stub
				HttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				ResponseHandler<String> handler = new BasicResponseHandler();
				String response = "";
				try {
					response = client.execute(httpGet, handler);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = e.getMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = e.getMessage();
				} finally {
					return response;
				}
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				response = result;
			}
		};
		return response;

	}

}
