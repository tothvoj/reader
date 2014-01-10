package cz.appvision.ebookreader;

import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cz.appvision.ebookreader.image.ImageFetcher;
import cz.appvision.ebookreader.image.ImageWorker.ImageParams;

public class RSSFeedArrayAdapter extends ArrayAdapter<Integer> {

	private int resourceID;
	private List<RSSItem> data;
	private Context context;
	private ImageFetcher imageFetcher;
	
	public RSSFeedArrayAdapter(Context context, int resourceId,
			RSSFeed feed, ImageFetcher imageFetcher) {
		super(context, resourceId);
		this.resourceID = resourceId;
		List<RSSItem> list = feed.getItems();
		data = list;
		this.context = context;
		this.imageFetcher = imageFetcher;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resourceID, parent, false);
		}
		
		RSSItem item = data.get(position);
		
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.rss_feed_item_image);
		
		final ImageParams params = new ImageParams.Builder().setSize(imageView).build();
		imageFetcher.loadImage(item.getThumbnails().get(0).getUrl(), imageView, params );
		
		TextView title = (TextView) convertView.findViewById(R.id.rss_feed_item_title);
		title.setText(item.getTitle());
											
		return convertView;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

}
