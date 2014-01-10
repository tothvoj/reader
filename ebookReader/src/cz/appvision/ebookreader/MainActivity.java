package cz.appvision.ebookreader;

import java.util.ArrayList;
import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cz.appvision.ebookreader.image.ImageCache;
import cz.appvision.ebookreader.image.ImageCache.ImageCacheParams;
import cz.appvision.ebookreader.image.ImageFetcher;

public class MainActivity extends FragmentActivity implements OnClickListener {

	FrameLayout contentFrameLayout;
	LinearLayout menuToolbar;
	List<ImageView> buttons;
	RelativeLayout root;
	WebView webView;
	ListView magazineListView;
	private ImageFetcher imageFetcher;
	private ProgressBar progressBar;
	private RSSFeedArrayAdapter rssAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageCacheParams cacheParams = new ImageCacheParams(this, ImageCache.DISK_CACHE_FILE);
		cacheParams.setMemCacheSizePercent(ImageCache.MEM_CACHE_20_PERCENT);
		ImageCache.getInstance(getSupportFragmentManager(), cacheParams );
		imageFetcher = new ImageFetcher(this);
		imageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);

		contentFrameLayout = (FrameLayout) findViewById(R.id.contentFrameLayout);
		menuToolbar = (LinearLayout) findViewById(R.id.menuToolbar);
		buttons = new ArrayList<ImageView>();

		ImageView menuOption1 = (ImageView) findViewById(R.id.toolbarMenuOption1);
		ImageView menuOption2 = (ImageView) findViewById(R.id.toolbarMenuOption2);
		ImageView menuOption3 = (ImageView) findViewById(R.id.toolbarMenuOption3);
		ImageView menuOption4 = (ImageView) findViewById(R.id.toolbarMenuOption4);

		buttons.add(menuOption1);
		buttons.add(menuOption2);
		buttons.add(menuOption3);
		buttons.add(menuOption4);

		menuOption1.setOnClickListener(this);
		menuOption2.setOnClickListener(this);
		menuOption3.setOnClickListener(this);
		menuOption4.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toolbarMenuOption1:

			if (webView == null){
			webView = new WebView(this);

			webView.getSettings().setBuiltInZoomControls(true);
			webView.getSettings().setLoadWithOverviewMode(true);
			webView.getSettings().setUseWideViewPort(true);

			webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));

			webView.loadUrl("http:\\www.sme.sk");
			}

			contentFrameLayout.removeAllViews();
			contentFrameLayout.addView(webView);

			break;

		case R.id.toolbarMenuOption2:
			contentFrameLayout.removeAllViews();
			break;
			
		case R.id.toolbarMenuOption3:
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			progressBar = new ProgressBar(this);
			progressBar.setLayoutParams(params);
			magazineListView = new ListView(this);
			magazineListView.setLayoutParams(params);
//			new RetrieveFeedTask().execute("http://feeds.bbci.co.uk/news/world/rss.xml");
			new RetrieveFeedTask().execute("http://www.ctv.ca/feeds/mRssService.aspx?id=979");  
			contentFrameLayout.removeAllViews();
			contentFrameLayout.addView(progressBar);
			break;
			
		case R.id.toolbarMenuOption4:
			contentFrameLayout.removeAllViews();
			break;
		}

	}
	
	@Override
	protected void onResume() {
		imageFetcher.setExitTasksEarly(false);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		imageFetcher.setExitTasksEarly(true);
		imageFetcher.flushCache();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		imageFetcher.closeCache();
		super.onDestroy();
	}
	
	private class RetrieveFeedTask extends AsyncTask<String, Void, RSSFeed> {

		@Override
		protected RSSFeed doInBackground(String... urls) {
			try {
			String url= urls[0];
			RSSReader reader = new RSSReader();
			RSSFeed feed = reader.load(url);
			reader.close();
			return feed;
			
			} catch (Exception e) {
	            return null;
	        }
		}
		
		  protected void onPostExecute(RSSFeed feed) {
			rssAdapter = new RSSFeedArrayAdapter(MainActivity.this, R.layout.rss_feed_item, feed, imageFetcher);
			magazineListView.setAdapter(rssAdapter);
			contentFrameLayout.removeAllViews();
			contentFrameLayout.addView(magazineListView);
		    }

	}


}
