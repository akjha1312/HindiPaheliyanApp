package com.ajnshs.pehliyan;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class PahliActivity extends AppCompatActivity implements View.OnClickListener {

    DataProvider dataProvider;
    ArrayList<Pahli> data;
    TextView questionView;
    TextView counterView;
    //    TextView answerView;
    Button btnShowAns;
    ImageButton btnPre;
    ImageButton btnNxt;
    ImageButton btnShare;
    int index;
    boolean toggle;
    AdView adView;
    InterstitialAd interstitialAd;

    int adShowCounter = 0;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pehli);
        adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.Interstitial_ad_unit_id));
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(adRequest);
            }
        });

        index = getIntent().getIntExtra("posItem", 0);
        dataProvider = new DataProvider(this, getIntent().getIntExtra("posCatg", 0));
        data = dataProvider.getPehlis();
        Typeface hindiFont = Typeface.createFromAsset(getAssets(), "NotoSans-Regular.ttf");
        questionView = findViewById(R.id.question);
        questionView.setTypeface(hindiFont);
        counterView = findViewById(R.id.counterView);
        counterView.setText(String.valueOf(data.get(index).getQuesNum() + "/" + data.size()));
        btnShowAns = findViewById(R.id.btnShowAns);
        btnShowAns.setTypeface(hindiFont);
        btnShowAns.setOnClickListener(this);
        btnPre = findViewById(R.id.btnPre);
        btnPre.setOnClickListener(this);
        if (index == 0) {
            btnPre.setEnabled(false);
        }
        btnNxt = findViewById(R.id.btnNxt);
        btnNxt.setOnClickListener(this);
        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        reset();
    }

    private void reset() {
        questionView.setText(data.get(index).getQues());
        counterView.setText(String.valueOf(data.get(index).getQuesNum() + "/" + data.size()));
//        answerView.setText(data.get(index).getAns());
//        answerView.setVisibility(View.INVISIBLE);
        btnShowAns.setText("उत्तर देखें");
        toggle = false;

    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.btnShowAns:

                if (!toggle) {
                    questionView.setText("उत्तर:\n" + data.get(index).getAns());
                    btnShowAns.setText("पहेली देखें");
                    toggle = true;
                    if (adShowCounter >= 4) {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        }
                        adShowCounter = 0;
                    } else {
                        adShowCounter++;
                    }
                } else {
                    questionView.setText(data.get(index).getQues());
                    btnShowAns.setText("उत्तर देखें");
                    toggle = false;
                }
                break;
            case R.id.btnPre:
                if (adShowCounter >= 3) {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                    adShowCounter = 0;
                } else {
                    adShowCounter++;
                }
                if (index > 0) {
                    index--;
                    btnNxt.setEnabled(true);
                } else {
                    btnPre.setEnabled(false);
                }
                reset();
                break;
            case R.id.btnNxt:
                if (adShowCounter >= 3) {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                    adShowCounter = 0;
                } else {
                    adShowCounter++;
                }
                if (index < data.size() - 1) {
                    index++;
                    btnPre.setEnabled(true);
                } else {
                    Toast.makeText(this, "You have reached at end of this section.", Toast.LENGTH_SHORT).show();
                    btnNxt.setEnabled(false);
                }
                reset();
                break;
            case R.id.btnShare:
                String appPackageName = getPackageName();
                String shareMsg = "बूझो तो जाने:\n\n" + data.get(index).getQues() + "\n\n" +
                        "For More Fun Paheliyan Download Hindi Paheliya App on PlayStore:\n " + "https://play.google.com/store/apps/details?id=" + appPackageName;
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, shareMsg);
                startActivity(Intent.createChooser(intentShare, "Share On:"));
                break;
        }
    }
}
