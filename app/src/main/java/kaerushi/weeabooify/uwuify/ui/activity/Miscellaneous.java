package kaerushi.weeabooify.uwuify.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;
import java.util.Objects;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;
import kaerushi.weeabooify.uwuify.utils.FabricatedOverlay;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;

public class Miscellaneous extends AppCompatActivity {

    LoadingDialog loadingDialog;

    public static void disableEverything() {
        List<String> overlays = OverlayUtils.getEnabledOverlayList();
        List<String> fabricatedOverlays = FabricatedOverlay.getEnabledOverlayList();

        for (String overlay : overlays) {
            OverlayUtils.disableOverlay(overlay);
        }

        for (String fabricatedOverlay : fabricatedOverlays) {
            FabricatedOverlay.disableOverlay(fabricatedOverlay);
        }
        Prefs.clearAllPrefs();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscellaneous);

        // Header
        CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle("Miscellaneous");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Hide VPN
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch hide_vpn = findViewById(R.id.switch_vpn);
        hide_vpn.setChecked(Prefs.getBoolean("UwuifyComponentHDVPN"));

        hide_vpn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Prefs.putBoolean("UwuifyComponentHDVPN", true);
                hide_vpn.setChecked(true);
                OverlayUtils.enableOverlay("UwuifyComponentHDVPN.overlay");
            } else {
                Prefs.putBoolean("UwuifyComponentHDVPN", false);
                hide_vpn.setChecked(false);
                OverlayUtils.disableOverlay("UwuifyComponentHDVPN.overlay");
            }
        });

        // Hide Drag Handle
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch hide_drag_handle = findViewById(R.id.switch_drag_handle);
        hide_drag_handle.setChecked(Prefs.getBoolean("UwuifyComponentHDDH"));

        hide_drag_handle.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Prefs.putBoolean("UwuifyComponentHDDH", true);
                hide_drag_handle.setChecked(true);
                OverlayUtils.enableOverlay("UwuifyComponentHDDH.overlay");
            } else {
                Prefs.putBoolean("UwuifyComponentHDDH", false);
                hide_drag_handle.setChecked(false);
                OverlayUtils.disableOverlay("UwuifyComponentHDDH.overlay");
            }
        });

        // Hide Notif Shadow
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch hide_notif_shadow = findViewById(R.id.switch_notif_shadow);
        hide_notif_shadow.setChecked(Prefs.getBoolean("UwuifyComponentHDNS"));

        hide_notif_shadow.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Prefs.putBoolean("UwuifyComponentHDDH", true);
                hide_notif_shadow.setChecked(true);
                OverlayUtils.enableOverlay("UwuifyComponentHDDH.overlay");
            } else {
                Prefs.putBoolean("UwuifyComponentHDDH", false);
                hide_notif_shadow.setChecked(false);
                OverlayUtils.disableOverlay("UwuifyComponentHDDH.overlay");
            }
        });

        // Hide Draw Notif BG
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch hide_draw_notif = findViewById(R.id.switch_draw_notif);
        hide_draw_notif.setChecked(Prefs.getBoolean("UwuifyComponentHDND"));

        hide_draw_notif.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Prefs.putBoolean("UwuifyComponentHDDH", true);
                hide_draw_notif.setChecked(true);
                OverlayUtils.enableOverlay("UwuifyComponentHDDH.overlay");
            } else {
                Prefs.putBoolean("UwuifyComponentHDDH", false);
                hide_draw_notif.setChecked(false);
                OverlayUtils.disableOverlay("UwuifyComponentHDDH.overlay");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}