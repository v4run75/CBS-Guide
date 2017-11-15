package kronos.cbs.basu.cbsguide;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class InAppBrowser extends Activity {

    private String site_title = "Loading...";
    private String site_url;
    private WebView webView;
    private ImageButton toolbar_back_button;
    private ImageButton toolbar_open_browser_button;
    private TextView toolbar_title;
    private TextView toolbar_site_url;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            site_url = extras.getString("site_url");
        }
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webView);
        toolbar_back_button = (ImageButton) findViewById(R.id.toolbar_back_button);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_site_url = (TextView) findViewById(R.id.toolbar_site_url);
        toolbar_open_browser_button = (ImageButton) findViewById(R.id.toolbar_open_browser_button);
        toolbar_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_open_browser_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl()));
                startActivity(browserIntent);
            }
        });
        toolbar_title.setText(site_title);
        toolbar_site_url.setText(site_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                toolbar_title.setText(webView.getTitle());
                toolbar_site_url.setText(webView.getUrl());
            }
        });
        webView.loadUrl(site_url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}