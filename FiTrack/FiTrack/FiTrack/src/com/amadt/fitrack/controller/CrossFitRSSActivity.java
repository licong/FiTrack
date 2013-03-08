package com.amadt.fitrack.controller;

import com.amadt.fitrack.R;
import com.amadt.fitrack.model.RSSFeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class CrossFitRSSActivity extends Activity {
	private static final String mobile_xfit_url_ = "http://m.simplepie.org/?feed=http%3A%2F%2Ffeeds.feedburner.com%2Fcrossfit%2FeRTq";
	private RSSFeed feed = null;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) 
	 {
		 super.onCreate(savedInstanceState);
	     this.setContentView(R.layout.crossfit_rss_layout);
	     
		 Button doItbutton = (Button) findViewById(R.id.rss_doit_button);		 
		 doItbutton.setOnClickListener( new View.OnClickListener() {
			public void onClick(View v) {				
				Intent intent = new Intent(CrossFitRSSActivity.this, FindAWodActivity.class);
				startActivity(intent);
				finish();
			}
		});
		 
		 updateDisplay();
	 }
	 
	 public void updateDisplay() 
	 {		 
		 TextView feedTitle = (TextView) findViewById(R.id.rss_feed_title);
		 WebView rssWebView = (WebView) findViewById(R.id.rss_webview);

		 rssWebView.getSettings().setLoadsImagesAutomatically(true);
		 rssWebView.loadUrl(mobile_xfit_url_);
		 
		 if (feed == null) {
			 feedTitle.setText("Crossfit: Forging Elite Fitness");
			 return;
		 }
		 
		 //feedTitle.setText(feed.getTitle());
	 }
}
