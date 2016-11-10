package fingernmath.touchnedu.com.module;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fingernmath.touchnedu.com.R;

/**
 * Created by HONG-QC on 2016-11-10.
 */

public class WebViewMain {
  private WebView mWebView;
  private Activity mActivity;

  public WebViewMain(Context context) {
    mActivity = (Activity)context;
  }

  public void initWebView() {
    mWebView = (WebView)mActivity.findViewById(R.id.webview_main);
    mWebView.getSettings().setBuiltInZoomControls(false);
    mWebView.setHorizontalScrollBarEnabled(false);
    mWebView.setVerticalScrollBarEnabled(false);
    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
      }
    });
  }

  public void initPage() {
    try {
      mWebView.loadUrl("");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}
