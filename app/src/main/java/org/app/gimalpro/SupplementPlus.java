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
    private static int FatLevel,MuscleLevel,goal;
    private String rurl;
    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplementplus);

        FatLevel=MoreSupplement.F_L;
        MuscleLevel=MoreSupplement.M_L;
        btn1=findViewById(R.id.button1);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);
        btn4=findViewById(R.id.button4);

        rurl="https://www.myprotein.co.kr/ranges/myvitamins/products.list";

        if(FatLevel==0 && MuscleLevel==0){goal=0;}
        else if(FatLevel==1 && MuscleLevel==0){goal=0;}
        else if(FatLevel==2 && MuscleLevel==0){goal=1;}
        else if(FatLevel==2 && MuscleLevel==1){goal=1;}
        else if(FatLevel==2 && MuscleLevel==2){goal=1;}

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-none.list";start();}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-none.list";start();}
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-vegan.list";start();}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-vegan.list";start();}
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-vegetarian.list";start();}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-vegetarian.list";start();}
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal==0){rurl="https://www.myprotein.co.kr/goal-selector/male-goals/build-muscle-req/bm-gluten-free.list";start();}
                else{rurl="https://www.myprotein.co.kr/goal-selector/male-goals/lose-weight-req/lw-gluten-free.list";start();}
            }
        });

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
    void start(){
        WV=(WebView) findViewById(R.id.WV);
        WV.getSettings().setJavaScriptEnabled(true);

        WV.loadUrl(rurl);
        WV.setWebChromeClient(new WebChromeClient());
        WV.setWebViewClient(new WebViewClient());
        WV.getSettings().setBuiltInZoomControls(true);
    }

}