package cz.appvision.ebookreader;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import cz.appvision.ebookreader.image.ImageCache;
import cz.appvision.ebookreader.image.ImageCache.ImageCacheParams;
import cz.appvision.ebookreader.image.ImageFetcher;

public class MainActivity extends FragmentActivity implements OnClickListener {

	FrameLayout contentFrameLayout;
	LinearLayout menuToolbar;
	List<ImageView> buttons;
	RelativeLayout root;
	ListView magazineListView;
	private ImageFetcher imageFetcher;
	private ProgressBar progressBar;
	private View oNasLayout;
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageCacheParams cacheParams = new ImageCacheParams(this,
				ImageCache.DISK_CACHE_FILE);
		cacheParams.setMemCacheSizePercent(ImageCache.MEM_CACHE_20_PERCENT);
		ImageCache.getInstance(getSupportFragmentManager(), cacheParams);
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

		LayoutInflater inflater = getLayoutInflater();
		oNasLayout = inflater.inflate(R.layout.o_nas, null);

		onClick(menuOption1);

	}

	@Override
	public void onClick(View v) {

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		v.setSelected(true);
		deselectButtons((ImageView) v);
		switch (v.getId()) {
		case R.id.toolbarMenuOption1:

			CustomApp app = (CustomApp) getApplication();
			WebView webView = app.getWebView();
			contentFrameLayout.removeAllViews();
			contentFrameLayout.addView(webView);

			break;

		case R.id.toolbarMenuOption2:
			contentFrameLayout.removeAllViews();
			BookShelfFragment bookShelfFragment = new BookShelfFragment();

			transaction.replace(R.id.contentFrameLayout, bookShelfFragment);

			transaction.commit();
			break;

		case R.id.toolbarMenuOption3:

			contentFrameLayout.removeAllViews();
			// contentFrameLayout.addView(progressBar);
			break;

		case R.id.toolbarMenuOption4:
			contentFrameLayout.removeAllViews();
			contentFrameLayout.addView(oNasLayout);

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

	private void deselectButtons(ImageView iv) {
		for (ImageView imageView : buttons) {
			if (imageView != iv) {
				imageView.setSelected(false);
			}
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
