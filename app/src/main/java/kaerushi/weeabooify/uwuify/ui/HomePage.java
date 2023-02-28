package kaerushi.weeabooify.uwuify.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.config.PrefConfig;
import kaerushi.weeabooify.uwuify.fragment.FragmentAE;
import kaerushi.weeabooify.uwuify.fragment.FragmentSC;
import kaerushi.weeabooify.uwuify.fragment.FragmentUWU;
import kaerushi.weeabooify.uwuify.services.BackgroundService;
import kaerushi.weeabooify.uwuify.utils.FabricatedOverlay;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class HomePage extends AppCompatActivity {

    public static boolean isServiceRunning = false;
    private final String TAG = "WelcomePage";
    ViewPager2 variantViewPager;
    ImageView ImgMenu;
    LinearLayout home_qsStyle, home_notifStyle, home_progressBar, home_extras, home_info, changelogLL;
    private ViewGroup container;
    private FragmentStateAdapter pagerAdapter;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        PrefConfig.savePrefBool(Weeabooify.getAppContext(), "onHomePage", true);

        // Header
        ImgMenu = findViewById(R.id.menu_button);
        ImgMenu.setOnClickListener(view -> {
            showChangelog();
        });
        ImageView icon = findViewById(R.id.iconApp);
        AppBarLayout appBarLayout = findViewById(R.id.appbarlayout);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percentage = (float) 1f - Math.abs(verticalOffset / (float)  appBarLayout.getTotalScrollRange());
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    icon.animate()
                            .alpha(0f)
                            .setDuration(50);
                    ImgMenu.animate()
                            .setDuration(100)
                            .translationY(35);
                    ImgMenu.animate().scaleX(0.8f).scaleY(0.8F);
                } else if (verticalOffset == 0) {
                    icon.animate()
                            .alpha(1f)
                            .setDuration(100);
                    ImgMenu.animate()
                            .setDuration(100)
                            .translationY(0)
                            .scaleY(1)
                            .scaleX(1);
                } else {
                    icon.animate()
                            .alpha(percentage)
                            .setDuration(100);
                }
            }
        });



        variantViewPager = findViewById(R.id.variantPager);
        pagerAdapter = new ScreenSlideAdapter(this);

        variantViewPager.setAdapter(pagerAdapter);
        variantViewPager.setClipChildren(false);
        variantViewPager.setClipToPadding(false);
        variantViewPager.setOffscreenPageLimit(3);
        variantViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.10f);
            }
        });

        variantViewPager.setPageTransformer(compositePageTransformer);

        // Home page list items
        container = (ViewGroup) findViewById(R.id.home_page_list);
        addItem(R.id.home_qsStyle, "QS Style", "Change Quick Settings layout", R.drawable.ic_qs);
        addItem(R.id.home_notifStyle, "Notification Style", "Change notification style", R.drawable.ic_qs);
        addItem(R.id.home_extras, "Extras", "Additions tweaks and settings", R.drawable.ic_settings);
        addItem(R.id.home_info, "About", "Information about this app", R.drawable.ic_info);

        // Get list of enabled overlays
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                List<String> EnabledOverlays = OverlayUtils.getEnabledOverlayList();
                for (String overlay : EnabledOverlays)
                    PrefConfig.savePrefBool(Weeabooify.getAppContext(), overlay, true);

                List<String> EnabledFabricatedOverlays = FabricatedOverlay.getEnabledOverlayList();
                for (String overlay : EnabledFabricatedOverlays)
                    PrefConfig.savePrefBool(Weeabooify.getAppContext(), "fabricated" + overlay, true);
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                if (!isServiceRunning)
                    startService(new Intent(Weeabooify.getAppContext(), BackgroundService.class));
            }
        };
        Thread thread2 = new Thread(runnable2);
        thread2.start();

        // QS Style item OnCLick
        home_qsStyle = findViewById(R.id.home_qsStyle);
        home_qsStyle.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_a));
        home_qsStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, QsStyle.class);
                startActivity(intent);
            }
        });

        // Notif Style item OnCLick
        home_notifStyle = findViewById(R.id.home_notifStyle);
        home_notifStyle.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_a));
        home_notifStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, NotificationStyle.class);
                startActivity(intent);
            }
        });

        // Extras item onClick
        home_extras = findViewById(R.id.home_extras);
        home_extras.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_b));
        home_extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Extras.class);
                startActivity(intent);
            }
        });

        // About item onClick
        home_info = findViewById(R.id.home_info);
        home_info.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_c));
        home_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Info.class);
                startActivity(intent);
            }
        });
    }

    private void showChangelog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.bottomSheet);
        bottomSheetDialog.setContentView(R.layout.changelog);
        bottomSheetDialog.show();

    }

    // Function to add new item in list
    private void addItem(int id, String title, String desc, int preview) {
        View list_view = LayoutInflater.from(this).inflate(R.layout.list_view, container, false);

        TextView list_title = (TextView) list_view.findViewById(R.id.list_title);
        TextView list_desc = (TextView) list_view.findViewById(R.id.list_desc);
        ImageView list_preview = (ImageView) list_view.findViewById(R.id.list_preview);

        list_view.setId(id);
        list_title.setText(title);
        list_desc.setText(desc);
        list_preview.setImageResource(preview);

        container.addView(list_view);
    }

    private static class ScreenSlideAdapter extends FragmentStateAdapter {
        public ScreenSlideAdapter(HomePage homePage) {
            super(homePage);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FragmentSC();
                case 1:
                    return new FragmentAE();
                case 2:
                    return new FragmentUWU();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}