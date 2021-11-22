package org.app.gimalpro;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class SupplementPlus extends AppCompatActivity {

    private WebView WV;
    private static int FatLevel,MuscleLevel;
    private String rurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplementplus);

        FatLevel=MoreSupplement.F_L;
        MuscleLevel=MoreSupplement.M_L;

        rurl="https://www.myprotein.co.kr/ranges/myvitamins/products.list";
        if(FatLevel==0 && MuscleLevel==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req.list";}
        else if(FatLevel==1 && MuscleLevel==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req.list";}
        else if(FatLevel==2 && MuscleLevel==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req.list";}
        else if(FatLevel==2 && MuscleLevel==1){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req.list";}
        else if(FatLevel==2 && MuscleLevel==2){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req.list";}

        WV=(WebView) findViewById(R.id.WV);
        WV.getSettings().setJavaScriptEnabled(true);

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