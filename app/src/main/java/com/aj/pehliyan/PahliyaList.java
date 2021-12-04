package com.aj.pehliyan;

import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class PahliyaList extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView pmList;
    ArrayList<Pahli> pahelData;
    TextView catgTitle;
    int pos;
    AdView adView;

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pahliya_list);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

        InterstitialAd.load(this,getString(R.string.Interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        interstitialAd = null;
                    }
                });

        pos = getIntent().getIntExtra("menuItemPos", 0);
        DataProvider dataProvider = new DataProvider(this, pos);
        pahelData = dataProvider.getPehlis();
        pmList = findViewById(R.id.listView);
        Typeface hindiFont = Typeface.createFromAsset(getAssets(), "NotoSans-Regular.ttf");
        catgTitle = findViewById(R.id.catgTitle);
        catgTitle.setTypeface(hindiFont);
        catgTitle.setText(Categories.getMenuItem()[pos]);
        pmList.setAdapter(new PaheliAdapter(this, pahelData));
        pmList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(PahliyaList.this, PahliActivity.class);
        intent.putExtra("posCatg", pos);
        intent.putExtra("posItem", position);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd!= null) {
            interstitialAd.show(PahliyaList.this);
        }
        super.onBackPressed();
    }
}
