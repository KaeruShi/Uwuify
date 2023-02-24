package kaerushi.weeabooify.uwuify.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.topjohnwu.superuser.Shell;

import java.io.IOException;
import java.util.Objects;

import kaerushi.weeabooify.uwuify.BuildConfig;
import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.common.References;
import kaerushi.weeabooify.uwuify.config.PrefConfig;
import kaerushi.weeabooify.uwuify.utils.ModuleUtil;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;
import kaerushi.weeabooify.uwuify.utils.RootUtil;

public class WelcomePage extends AppCompatActivity {

    private static boolean hasErroredOut = false;
    private final int versionCode = BuildConfig.VERSION_CODE;
    private final String versionName = BuildConfig.VERSION_NAME;
    private LinearLayout spinner;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        // Progressbar while installing module
        spinner = findViewById(R.id.progressBar_installingModule);

        // Continue button
        Button checkRoot = findViewById(R.id.checkRoot);


        // Dialog to show if root not found
        LinearLayout warn = findViewById(R.id.warn);
        TextView warning = findViewById(R.id.warning);

        // Rom variant
        LinearLayout nusa_variant = findViewById(R.id.nusa_variant);
        LinearLayout rr_variant = findViewById(R.id.rr_variant);
        LinearLayout havoc_variant = findViewById(R.id.havoc_variant);
        LinearLayout los_variant = findViewById(R.id.los_variant);

        nusa_variant.setOnClickListener(v -> {
            PrefConfig.savePrefSettings(Weeabooify.getAppContext(), "selectedRomVariant", "Nusan");
            nusa_variant.setBackground(getResources().getDrawable(R.drawable.container_selected));
            rr_variant.setBackground(getResources().getDrawable(R.drawable.container));

            Transition transition = new Fade();
            transition.setDuration(1200);
            transition.addTarget(R.id.checkRoot);
            TransitionManager.beginDelayedTransition(nusa_variant, transition);
            checkRoot.setVisibility(View.VISIBLE);
        });

        rr_variant.setOnClickListener(v -> {
            PrefConfig.savePrefSettings(Weeabooify.getAppContext(), "selectedRomVariant", "RR");
            rr_variant.setBackground(getResources().getDrawable(R.drawable.container_selected));
            nusa_variant.setBackground(getResources().getDrawable(R.drawable.container));

            Transition transition = new Fade();
            transition.setDuration(1200);
            transition.addTarget(R.id.checkRoot);
            TransitionManager.beginDelayedTransition(rr_variant, transition);
            checkRoot.setVisibility(View.VISIBLE);
        });

        havoc_variant.setOnClickListener(view -> {
            Toast.makeText(Weeabooify.getAppContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        los_variant.setOnClickListener(view -> {
            Toast.makeText(Weeabooify.getAppContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        // Check for root onClick
        checkRoot.setOnClickListener(v -> {
            if (Objects.equals(PrefConfig.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "null"))
                Toast.makeText(Weeabooify.getAppContext(), "Select a ROM before proceeding", Toast.LENGTH_SHORT).show();
            else {
                if (RootUtil.isDeviceRooted()) {
                    if (RootUtil.isMagiskInstalled()) {
                        if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED || androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
                            androidx.core.app.ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
                            warn.setVisibility(View.VISIBLE);
                            warning.setText("Grant storage access first!");
                        } else {
                            if ((PrefConfig.loadPrefInt(this, "versionCode") != versionCode) || !ModuleUtil.moduleExists() || !OverlayUtils.overlayExists()) {

                                // Show spinner
                                spinner.setVisibility(View.VISIBLE);

                                // Block touch
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                Runnable runnable = () -> {
                                    try {
                                        hasErroredOut = ModuleUtil.handleModule();
                                    } catch (IOException e) {
                                        Toast.makeText(this, getResources().getString(R.string.toast_error), Toast.LENGTH_LONG).show();
                                        hasErroredOut = true;
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(() -> {
                                        // Hide spinner
                                        spinner.setVisibility(View.GONE);
                                        // Unblock touch
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                        if (hasErroredOut) {
                                            Shell.cmd("rm -rf " + References.MODULE_DIR).exec();
                                            warning.setText(getResources().getString(R.string.installation_failed));
                                            warn.setVisibility(View.VISIBLE);

                                        } else {
                                            if (OverlayUtils.overlayExists()) {
                                                PrefConfig.savePrefInt(this, "versionCode", versionCode);
                                                Intent intent = new Intent(WelcomePage.this, HomePage.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                warn.setVisibility(View.VISIBLE);
                                                warning.setText("Reboot your device first!");
                                            }
                                        }
                                    });
                                };
                                Thread thread = new Thread(runnable);
                                thread.start();
                            } else {
                                Intent intent = new Intent(WelcomePage.this, HomePage.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        warn.setVisibility(View.VISIBLE);
                        warning.setText("Use Magisk to root your device!");
                    }
                } else {
                    warn.setVisibility(View.VISIBLE);
                    warning.setText("Looks like your device is not rooted!");
                }
            }
        });
    }
}