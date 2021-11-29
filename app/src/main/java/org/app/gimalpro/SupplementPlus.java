package org.app.gimalpro;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SupplementPlus extends AppCompatActivity {

    private WebView WV;
    private String rurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplementplus);

        WV=(WebView) findViewById(R.id.WV);
        WV.getSettings().setJavaScriptEnabled(true);

        rurl=fragment4.rurl;
        WV.loadUrl(rurl);
        WV.setWebChromeClient(new WebChromeClient());
        WV.setWebViewClient(new WebViewClient());
        WV.getSettings().setBuiltInZoomControls(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && WV.canGoBack()) {
            WV.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }

}