package com.greenstar.mecwheel.crb.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Person;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.greenstar.mecwheel.R;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MedIQ  extends AppCompatActivity  {
    WebView wvMedIQ;
    private PermissionRequest mPermissionRequest;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String[] PERM_CAMERA =
            {Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediq);
        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String userId = (shared.getString("userId", ""));
        String url = "https://www.mediq.com.pk/greenstar-login/"+userId;
        wvMedIQ = (WebView) findViewById(R.id.wvMedIQ);
        wvMedIQ.setWebViewClient(new MyBrowser());
        wvMedIQ.getSettings().setJavaScriptEnabled(true);
        wvMedIQ.getSettings().setLoadWithOverviewMode(true);
        wvMedIQ.getSettings().setUseWideViewPort(true);
        WebSettings settings = wvMedIQ.getSettings();
        settings.setDomStorageEnabled(true);

        wvMedIQ.setWebChromeClient(new WebChromeClient() {
            // Grant permissions for cam
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                mPermissionRequest = request;
                final String[] requestedResources = request.getResources();
                for (String r : requestedResources) {
                    if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                        // In this sample, we only accept video capture request.
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MedIQ.this)
                                .setTitle("Allow Permission to camera")
                                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mPermissionRequest.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});

                                    }
                                })
                                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mPermissionRequest.deny();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        break;
                    }
                }
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                super.onPermissionRequestCanceled(request);
                Toast.makeText(MedIQ.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        });


        if(hasCameraPermission()){
            wvMedIQ.loadUrl(url);
        }else{
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs Camera permission for Doctor's video calling.",
                    REQUEST_CAMERA_PERMISSION,
                    PERM_CAMERA);
        }



    }
    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(MedIQ.this, PERM_CAMERA);
    }
    public class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onLoadResource(WebView view, String url) {
            // Check to see if there is a progress dialog
            int i= 9;
        }

        public void onPageFinished(WebView view, String url) {
            // Page is done loading;
            // hide the progress dialog and show the webvie
            String k = "";
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }
    @Override
    public void onBackPressed() {
        if(wvMedIQ!=null){
            if (wvMedIQ.canGoBack()) {
                wvMedIQ.goBack();
            } else {
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }

    }

}
