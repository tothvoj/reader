package cz.appvision.ebookreader;

import android.app.Application;
import android.webkit.WebView;

public class CustomApp extends Application {
	
	private WebView webView;

	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}
		
}
