package com.showtime.lp.utils;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 作者:liupeng
 * 16/7/25 14:32
 * 注释: 加载WebView
 */
public class WebViewUtils {

    /**
     * 加载webView页面
     *
     * @param url
     * @param webView
     */
    public static void loadWebView(String url, WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultTextEncodingName("UTF-8");
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());

    }

    /**
     * webView加载(可以保存Cookie信息)
     *
     * @param context
     * @param webView
     */
    public static void webView(Context context, WebView webView, String url) {
        WebSettings websettings = webView.getSettings();
        // 设置支持javascript代码
        websettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        websettings.setSupportZoom(true);
        // 设置出现缩放工具
        websettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        websettings.setUseWideViewPort(true);
        //自适应屏幕
        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        websettings.setLoadWithOverviewMode(true);
//        websettings.setAllowFileAccess(true);
//        websettings.setDomStorageEnabled(true);
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
//        String cookieString = "login_token=" + SharedPreUtils.getInstance().getUserToken();
//        cookieManager.setCookie((GlobalSystemUtils.DOMAIN), cookieString);
//        CookieSyncManager.getInstance().sync();
//        Map<String, String> cookie = new HashMap<>();
//        cookie.put("Cookie", cookieString);
//        webView.loadUrl(url, cookie);
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
