package cz.appvision.ebookreader;

import java.util.ArrayList;
import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	FrameLayout contentFrameLayout;
	LinearLayout menuToolbar;
	List<ImageView> buttons;
	RelativeLayout root;
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			new RetrieveFeedTask().execute("http://feeds.bbci.co.uk/news/world/rss.xml");
			  			  
			contentFrameLayout.removeAllViews();
			break;
			
		case R.id.toolbarMenuOption4:
			contentFrameLayout.removeAllViews();
			break;
		}

	}

}
