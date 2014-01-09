package cz.appvision.ebookreader;

import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;

import android.os.AsyncTask;
import android.util.Log;

public class RetrieveFeedTask extends AsyncTask<String, Void, RSSFeed> {

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
		  List<RSSItem> list = feed.getItems();
		  for (RSSItem i: list) {
		    Log.d("rss", i.getTitle());
		  }
	    }

}
