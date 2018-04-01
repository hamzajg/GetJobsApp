package com.thephoenixit.getjobsapp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thephoenixit.getjobsapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    mAdView = (com.google.android.gms.ads.AdView) findViewById(R.id.adView);
    adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
    }
com.google.android.gms.ads.AdView mAdView;
    com.google.android.gms.ads.AdRequest adRequest;
}
