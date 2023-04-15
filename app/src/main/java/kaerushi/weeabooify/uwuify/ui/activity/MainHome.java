package kaerushi.weeabooify.uwuify.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.fragment.FragmentAE;
import kaerushi.weeabooify.uwuify.fragment.FragmentSC;
import kaerushi.weeabooify.uwuify.fragment.FragmentUWU;
import kaerushi.weeabooify.uwuify.ui.adapter.MainHomeAdapter;
import kaerushi.weeabooify.uwuify.ui.models.MainHomeModel;

public class MainHome extends AppCompatActivity {


    ViewPager2 variantViewPager;
    ImageView ImgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // Header
        ImgMenu = findViewById(R.id.menu_button);
        ImgMenu.setOnClickListener(view -> showChangelog());
        ImageView icon = findViewById(R.id.iconApp);
        AppBarLayout appBarLayout = findViewById(R.id.appbarlayout);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            float percentage = (float) 1f - Math.abs(verticalOffset / (float)  appBarLayout1.getTotalScrollRange());
            if (Math.abs(verticalOffset) == appBarLayout1.getTotalScrollRange()) {
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
        });

        // ViewPager
        variantViewPager = findViewById(R.id.variantPager);
        FragmentStateAdapter pagerAdapter = new ScreenSlideAdapter(this);

        variantViewPager.setAdapter(pagerAdapter);
        variantViewPager.setClipChildren(false);
        variantViewPager.setClipToPadding(false);
        variantViewPager.setOffscreenPageLimit(3);
        variantViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.10f);
        });

        variantViewPager.setPageTransformer(compositePageTransformer);

        // RecyclerView
        RecyclerView container = findViewById(R.id.main_home_container);
        container.setLayoutManager(new LinearLayoutManager(this));
        container.setAdapter(initMainHome());
        container.setHasFixedSize(true);

    }

    private MainHomeAdapter initMainHome() {
        ArrayList<MainHomeModel> main_list = new ArrayList<>();
        main_list.add(new MainHomeModel("Quick Settings", "Customize quick settings", R.drawable.ic_qs));
        main_list.add(new MainHomeModel("Notification", "Customize notification", R.drawable.ic_notif));
        main_list.add(new MainHomeModel("Extras", "Additional tweaks & misc", R.drawable.ic_settings));
        main_list.add(new MainHomeModel("Info", "Information about this app", R.drawable.ic_info));

        return new MainHomeAdapter(this, main_list);
    }

    private void showChangelog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.bottomSheet);
        bottomSheetDialog.setContentView(R.layout.changelog);
        bottomSheetDialog.show();
    }

    private static class ScreenSlideAdapter extends FragmentStateAdapter {
        public ScreenSlideAdapter(MainHome homePage) {
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