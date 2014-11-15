package ru.clever_as.android.gurmansoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends ActionBarActivity{
    WebView web;
    SharedPreferences sp;
    View dView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dView = getWindow().getDecorView();
        web = (WebView) findViewById(R.id.web);
        web.setWebViewClient(new HelloWebViewClient());
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        web.getSettings().setJavaScriptEnabled(sp.getBoolean("swJS", false));
        String url = sp.getString("url", "");
        web.loadUrl(url);
        hideUI();
    }
    public void onClick(View v){

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    private class HelloWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume(){
        web.getSettings().setJavaScriptEnabled(sp.getBoolean("swJS", false));
        web.loadUrl(sp.getString("url", ""));

        if(sp.getBoolean("hideUI", false) == true){
            hideUI();
        }
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_reload:
                web.reload();
                break;
            case R.id.action_support:
                break;
            case R.id.action_exit:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void hideUI(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            dView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        else{
            dView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    public void showUI(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            dView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
    public void onViewFocusChanged(boolean hasFocus) {
        hideUI();
    }
}
