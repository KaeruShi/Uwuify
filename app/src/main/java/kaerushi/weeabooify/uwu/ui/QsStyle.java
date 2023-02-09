package kaerushi.weeabooify.uwu.ui;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import kaerushi.weeabooify.uwu.Weeabooify;
import kaerushi.weeabooify.uwu.R;
import kaerushi.weeabooify.uwu.config.PrefConfig;
import kaerushi.weeabooify.uwu.installer.QsStyleInstaller;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.topjohnwu.superuser.Shell;

import java.util.Objects;

public class QsStyle extends AppCompatActivity {

    private static final String STYLE_COLOROS = "WeeabooifyComponentQS1.overlay";
    private static final String STYLE_DESCENDANT = "WeeabooifyComponentQS2.overlay";
    private static final String STYLE_DOTOS = "WeeabooifyComponentQS3.overlay";
    private static final String STYLE_MIUI = "WeeabooifyComponentQS4.overlay";
    private static final String STYLE_OCTAVI = "WeeabooifyComponentQS5.overlay";
    private static final String STYLE_OXYGEN = "WeeabooifyComponentQS6.overlay";

    LinearLayout[] Container;
    LinearLayout ColorOsContainer, DescendantContainer, DotOsContainer, MIUIContainer, OctaviContainer, OxygenContainer;
    ImageView ColorOsImg, DotOsImg;
    TextView ColorOs_title, ColorOs_desc, Descendant_title, Descendant_desc, DotOs_title, DotOs_desc, MIUI_title, MIUI_desc, Octavi_title, Octavi_desc, Oxygen_title, Oxygen_desc;
    Button ColorOs_enable, ColorOs_disable, Descendant_enable, Descendant_disable, DotOs_enable, DotOs_disable, MIUI_enable, MIUI_disable, Octavi_enable, Octavi_disable, Oxygen_enable, Oxygen_disable;
    ShimmerFrameLayout shimmerFrameLayout;
    private ViewGroup container;
    private LinearLayout spinner;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qs_style);

        // Header
        CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle("QS Style");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Progressbar while enabling or disabling pack
        spinner = findViewById(R.id.progressBar_qs_style);

        // Don't show progressbar on opening page
        spinner.setVisibility(View.GONE);

        // QS Style list items
        container = (ViewGroup) findViewById(R.id.qs_style_list);

        // QS Style add item in list
        additem(R.id.qs_style_coloros_container, R.id.qs_style_coloros_image, R.id.qs_style_coloros_title, R.id.qs_style_coloros_desc, R.id.qs_style_coloros_enable, R.id.qs_style_coloros_disable);
        additem(R.id.qs_style_descendant_container, R.id.qs_style_descendant_image, R.id.qs_style_descendant_title, R.id.qs_style_descendant_desc, R.id.qs_style_descendant_enable, R.id.qs_style_descendant_disable);
        additem(R.id.qs_style_dotos_container, R.id.qs_style_dotos_image, R.id.qs_style_dotos_title, R.id.qs_style_dotos_desc, R.id.qs_style_dotos_enable, R.id.qs_style_dotos_disable);
        additem(R.id.qs_style_miui_container, R.id.qs_style_miui_image, R.id.qs_style_miui_title, R.id.qs_style_miui_desc, R.id.qs_style_miui_enable, R.id.qs_style_miui_disable);
        additem(R.id.qs_style_octavi_container, R.id.qs_style_octavi_image, R.id.qs_style_octavi_title, R.id.qs_style_octavi_desc, R.id.qs_style_octavi_enable, R.id.qs_style_octavi_disable);
        additem(R.id.qs_style_oxygen_container, R.id.qs_style_oxygen_image, R.id.qs_style_oxygen_title, R.id.qs_style_oxygen_desc, R.id.qs_style_oxygen_enable, R.id.qs_style_oxygen_disable);

        // ColorOs
        ColorOsContainer = findViewById(R.id.qs_style_coloros_container);
        ColorOsImg = findViewById(R.id.qs_style_coloros_image);
        ColorOs_title = findViewById(R.id.qs_style_coloros_title);
        ColorOs_desc = findViewById(R.id.qs_style_coloros_desc);
        ColorOsImg.setImageResource(R.drawable.sc_banner);
        Picasso.get().load("https://drive.google.com/uc?id=1V_kvJkE05cceBqhWSjgsyz7aQdahe5NQ").into(ColorOsImg);
        ColorOs_desc.setText("Change Default Quick Settings to ColorOS Style");
        ColorOs_title.setText("Style ColorOs");
        ColorOs_enable = findViewById(R.id.qs_style_coloros_enable);
        ColorOs_disable = findViewById(R.id.qs_style_coloros_disable);

        // Descendant
        DescendantContainer = findViewById(R.id.qs_style_descendant_container);
        Descendant_title = findViewById(R.id.qs_style_descendant_title);
        Descendant_desc = findViewById(R.id.qs_style_descendant_desc);
        Descendant_desc.setText("Change Default Quick Settings to DescendantOS Style");
        Descendant_title.setText("Style DescendantOs");
        Descendant_enable = findViewById(R.id.qs_style_descendant_enable);
        Descendant_disable = findViewById(R.id.qs_style_descendant_disable);

        // DotOs
        DotOsContainer = findViewById(R.id.qs_style_dotos_container);
        DotOsImg = findViewById(R.id.qs_style_dotos_image);
        DotOs_title = findViewById(R.id.qs_style_dotos_title);
        DotOs_desc = findViewById(R.id.qs_style_dotos_desc);
        DotOs_desc.setText("Change Default Quick Settings to DotOS Style");
        DotOs_title.setText("Style DotOs");
        DotOs_enable = findViewById(R.id.qs_style_dotos_enable);
        DotOs_disable = findViewById(R.id.qs_style_dotos_disable);

        // MIUI
        MIUIContainer = findViewById(R.id.qs_style_miui_container);
        MIUI_title = findViewById(R.id.qs_style_miui_title);
        MIUI_desc = findViewById(R.id.qs_style_miui_desc);
        MIUI_desc.setText("Change Default Quick Settings to MIUI Style");
        MIUI_title.setText("Style MIUI");
        MIUI_enable = findViewById(R.id.qs_style_miui_enable);
        MIUI_disable = findViewById(R.id.qs_style_miui_disable);

        // Octavi
        OctaviContainer = findViewById(R.id.qs_style_octavi_container);
        Octavi_title = findViewById(R.id.qs_style_octavi_title);
        Octavi_desc = findViewById(R.id.qs_style_octavi_desc);
        Octavi_desc.setText("Change Default Quick Settings to OctaviOS Style");
        Octavi_title.setText("Style Octavi");
        Octavi_enable = findViewById(R.id.qs_style_octavi_enable);
        Octavi_disable = findViewById(R.id.qs_style_octavi_disable);

        // Oxygen
        OxygenContainer = findViewById(R.id.qs_style_oxygen_container);
        Oxygen_title = findViewById(R.id.qs_style_oxygen_title);
        Oxygen_desc = findViewById(R.id.qs_style_oxygen_desc);
        Oxygen_desc.setText("Change Default Quick Settings to OxygenOS Style");
        Oxygen_title.setText("Style Oxygen");
        Oxygen_enable = findViewById(R.id.qs_style_oxygen_enable);
        Oxygen_disable = findViewById(R.id.qs_style_oxygen_disable);

        // List of QS Styles
        Container = new LinearLayout[]{ColorOsContainer, DescendantContainer, DotOsContainer, MIUIContainer, OctaviContainer, OxygenContainer};

        //Enable OnClick
        enableOnClickListener(ColorOsContainer, ColorOs_title, ColorOs_enable, ColorOs_disable, STYLE_COLOROS, 1);
        enableOnClickListener(DescendantContainer, Descendant_title, Descendant_enable, Descendant_disable, STYLE_DESCENDANT, 2);
        enableOnClickListener(DotOsContainer, DotOs_title, DotOs_enable, DotOs_disable, STYLE_DOTOS, 3);
        enableOnClickListener(MIUIContainer, MIUI_title, MIUI_enable, MIUI_disable, STYLE_MIUI, 4);
        enableOnClickListener(OctaviContainer, Octavi_title, Octavi_enable, Octavi_disable, STYLE_OCTAVI, 5);
        enableOnClickListener(OxygenContainer, Oxygen_title, Oxygen_enable, Oxygen_disable, STYLE_OXYGEN, 6);

        refreshBackground();
    }

    private void refreshBackground() {
        checkIfApplied(ColorOs_title, 1);
        checkIfApplied(Descendant_title, 2);
        checkIfApplied(DotOs_title, 3);
        checkIfApplied(MIUI_title, 4);
        checkIfApplied(Octavi_title, 5);
        checkIfApplied(Oxygen_title, 6);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void checkIfApplied(TextView name, int QsStyle) {
        if (PrefConfig.loadPrefBool(Weeabooify.getAppContext(), "WeeabooifyComponentQS" + QsStyle + ".overlay")) {
            name.setTextColor(getResources().getColor(R.color.colorSuccess));
        } else {
            name.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }
    }

    private void additem(int id, int image, int title, int detail, int enableid, int disableid) {
        View list = LayoutInflater.from(this).inflate(R.layout.list_option_qs_style, container, false);

        ImageView img = list.findViewById(R.id.variantImg);
        TextView name = list.findViewById(R.id.qs_style_title);
        TextView desc = list.findViewById(R.id.qs_style_detail);
        Button enable = list.findViewById(R.id.list_button_enable_qs_style);
        Button disable = list.findViewById(R.id.list_button_disable_qs_style);

        // Shimmer
        shimmerFrameLayout = list.findViewById(R.id.preview_shimmer);
        shimmerFrameLayout.startShimmer();

        list.setId(id);
        img.setId(image);
        name.setId(title);
        desc.setId(detail);

        enable.setId(enableid);
        disable.setId(disableid);

        container.addView(list);

    }

    private void enableOnClickListener(LinearLayout layout, TextView name, Button enable, Button disable, String key, int index) {

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout(layout);
                if (!PrefConfig.loadPrefBool(Weeabooify.getAppContext(), key)) {
                    disable.setVisibility(View.GONE);
                    if (enable.getVisibility() == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        enable.setVisibility(View.GONE);
                    } else {
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        enable.setVisibility(View.VISIBLE);
                    }
                } else {
                    enable.setVisibility(View.GONE);
                    if (disable.getVisibility() == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        disable.setVisibility(View.GONE);
                    } else {
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        disable.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // Set onClick operation for Enable button
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout(layout);
                // Show spinner
                spinner.setVisibility(View.VISIBLE);
                // Block touch
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        disable_others(key);
                        QsStyleInstaller.install_pack(index);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                PrefConfig.savePrefBool(Weeabooify.getAppContext(), key, true);
                // Wait 1 second
                spinner.postDelayed(new Runnable() {
                    public void run() {
                        // Hide spinner
                        spinner.setVisibility(View.GONE);
                        // Unblock touch
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        // Change name to selected color
                        name.setTextColor(getResources().getColor(R.color.colorSuccess));
                        // Change button visibility
                        enable.setVisibility(View.GONE);
                        disable.setVisibility(View.VISIBLE);
                        Shell.cmd("killall com.android.systemui").exec();
                        Toast.makeText(getApplicationContext(), "Applied", Toast.LENGTH_LONG).show();
                    }
                }, 1000);
            }
        });

        // Set onClick operation for Disable button
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show spinner
                spinner.setVisibility(View.VISIBLE);
                // Block touch
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        QsStyleInstaller.disable_pack(index);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                PrefConfig.savePrefBool(Weeabooify.getAppContext(), key, false);
                // Wait 1 second
                spinner.postDelayed(new Runnable() {
                    public void run() {
                        // Hide spinner
                        spinner.setVisibility(View.GONE);
                        // Unblock touch
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        // Change name to default color
                        name.setTextColor(getResources().getColor(R.color.textColorPrimary));
                        // Change button visibility
                        disable.setVisibility(View.GONE);
                        enable.setVisibility(View.VISIBLE);
                        Shell.cmd("killall com.android.systemui").exec();
                        Toast.makeText(getApplicationContext(), "Disabled", Toast.LENGTH_LONG).show();
                    }
                }, 1000);
            }
        });
    }

    private void disable_others(String pack) {
        PrefConfig.savePrefBool(Weeabooify.getAppContext(), STYLE_COLOROS, pack.equals(STYLE_COLOROS));
        PrefConfig.savePrefBool(Weeabooify.getAppContext(), STYLE_DESCENDANT, pack.equals(STYLE_DESCENDANT));
        PrefConfig.savePrefBool(Weeabooify.getAppContext(), STYLE_DOTOS, pack.equals(STYLE_DOTOS));
        PrefConfig.savePrefBool(Weeabooify.getAppContext(), STYLE_MIUI, pack.equals(STYLE_MIUI));
        PrefConfig.savePrefBool(Weeabooify.getAppContext(), STYLE_OCTAVI, pack.equals(STYLE_OCTAVI));
        PrefConfig.savePrefBool(Weeabooify.getAppContext(), STYLE_OXYGEN, pack.equals(STYLE_OXYGEN));
    }

    // Function to check for layout changes
    private void refreshLayout(LinearLayout layout) {
        for (LinearLayout linearLayout : Container) {
            if (!(linearLayout == layout)) {
                if (linearLayout == ColorOsContainer) {
                    ColorOs_enable.setVisibility(View.GONE);
                    ColorOs_disable.setVisibility(View.GONE);
                } else if (linearLayout == DescendantContainer) {
                    Descendant_enable.setVisibility(View.GONE);
                    Descendant_disable.setVisibility(View.GONE);
                } else if (linearLayout == DotOsContainer) {
                    DotOs_enable.setVisibility(View.GONE);
                    DotOs_disable.setVisibility(View.GONE);
                } else if (linearLayout == MIUIContainer) {
                    MIUI_enable.setVisibility(View.GONE);
                    MIUI_disable.setVisibility(View.GONE);
                } else if (linearLayout == OctaviContainer) {
                    Octavi_enable.setVisibility(View.GONE);
                    Octavi_disable.setVisibility(View.GONE);
                } else if (linearLayout == OxygenContainer) {
                    Oxygen_enable.setVisibility(View.GONE);
                    Oxygen_disable.setVisibility(View.GONE);
                }
            }
        }
    }

}
