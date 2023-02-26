package kaerushi.weeabooify.uwuify.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.PrefConfig;

public class Info extends AppCompatActivity {

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        // Header
        CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle("About");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView marqueeVersion = findViewById(R.id.txtVersion);
        TextView marqueeVariant = findViewById(R.id.txtVariant);
        marqueeVariant.setSelected(true);
        marqueeVersion.setSelected(true);
        CircleImageView imgVariant = findViewById(R.id.imgVariant);

        if (Objects.equals(PrefConfig.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "RR")) {
            marqueeVariant.setText("Resurrection Remix");
            imgVariant.setImageResource(R.drawable.logo_rr);
        } else if (Objects.equals(PrefConfig.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "Nusan")) {
            marqueeVariant.setText("Nusantara");
            imgVariant.setImageResource(R.drawable.logo_nusa);
        } else if (Objects.equals(PrefConfig.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "LOS")) {
            marqueeVariant.setText("LineageOs");
            imgVariant.setImageResource(R.drawable.logo_nusa);
        } else
            marqueeVariant.setText("Unknown");

        // Credits

        // Iconify
        ViewGroup creditIconify = findViewById(R.id.creditIconify);
        creditIconify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://t.me/iconifyofficial/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        // Hyper
        ViewGroup hyper = findViewById(R.id.hyper);
        hyper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://t.me/hyp3r_sh";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void author(View view) {
        String url = "https://t.me/kaerushi";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void version(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Uwuify", getResources().getString(R.string.app_name) + "\n" + getResources().getString(R.string.version));
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(Weeabooify.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
    }
}