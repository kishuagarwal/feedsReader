package com.kishu.feedsreader;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.kishu.feedsreader.data.FeedManager;
import com.kishu.feedsreader.data.SpecialCategories;
import com.kishu.feedsreader.data.StreamContentResponse;
import com.kishu.feedsreader.sources.FeedlyManager;

public class FeedlySync {

	private Context ctx;
	private static FeedlySync mFeedlySync;
	private static OnSubscriptionUpdateListener subscriptionUpdateListener;
	private static OnEntryUpdatedListener entryUpdatedListener;
	private static Handler mainHandler;
	public static FeedlySync getInstance(){
		if(mFeedlySync == null)
			mFeedlySync = new FeedlySync();
		return mFeedlySync;
	}
	
	public FeedlySync() {
		mainHandler = new Handler(Looper.getMainLooper());
	}

	public void sync() throws IOException, JSONException {
		// TODO Auto-generated method stub
		boolean isNetworkUp = FeedApplication.getInstance().isNetworkUp();
		if (isNetworkUp) {
			syncSubscriptions();
			syncEntries();
			syncEntriesState();
		}
	}

	private void syncEntries() throws IOException, JSONException {
		// TODO Auto-generated method stub
		StreamContentResponse response;
		String continuation = null;
		// do
		// {
		response = FeedlyManager.getInstance().getStreamContent(
				SpecialCategories.getGlobalAllCategoryId(), null, null, null,
				null);
		FeedManager.getInstance().addEntries(response);
		continuation = response.getContinuation();
		// }while(continuation == null);
		
		if(entryUpdatedListener != null)
			mainHandler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				entryUpdatedListener.onEntriesUpdated();
			}
		});
	
	}
	
	
	private void syncEntriesState(){
		
	}

	private void syncSubscriptions() throws IOException, JSONException {
		// TODO Auto-generated method stub
		List<com.kishu.feedsreader.data.Subscription> subscriptions = FeedlyManager
				.getInstance().getSubcriptions();
		FeedManager.getInstance().updateSubscriptionsIfNecessary(
				subscriptions);
		if(subscriptionUpdateListener != null)
			mainHandler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				subscriptionUpdateListener.onSubscriptionUpdate();
			}
		});
		
	}
	
	public void registerOnSubscriptionUpdateListener(OnSubscriptionUpdateListener listener){
		subscriptionUpdateListener = listener;
	}
	
	public void unregisterOnSubscriptionUpdateListener(){
		subscriptionUpdateListener = null;
	}
	
	public void registerOnEntryUpdatedListener(OnEntryUpdatedListener listener){
		entryUpdatedListener = listener;
	}
	
	public void unregisterOnEntryUpdatedListener()
	{
		entryUpdatedListener = null;
	}
	
	public interface OnSubscriptionUpdateListener {
		public void onSubscriptionUpdate();
	}

	public interface OnEntryUpdatedListener {
		public void onEntriesUpdated();
	}
	
}
