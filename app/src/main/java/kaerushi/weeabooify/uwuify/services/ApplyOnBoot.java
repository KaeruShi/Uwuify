package kaerushi.weeabooify.uwuify.services;

import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.utils.FabricatedOverlay;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;

import java.util.List;

public class ApplyOnBoot {

    private static final String INVALID = "null";
    private static List<String> overlays = OverlayUtils.getEnabledOverlayList();
    private static List<String> fabricatedOverlays = FabricatedOverlay.getEnabledOverlayList();

    public static void applyCornerRadius() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (Prefs.loadPrefBool(Weeabooify.getAppContext(), "fabricatedcornerRadius") && (FabricatedOverlay.isOverlayDisabled(fabricatedOverlays, "dialogCornerRadius") || FabricatedOverlay.isOverlayDisabled(fabricatedOverlays, "insetCornerRadius2") || FabricatedOverlay.isOverlayDisabled(fabricatedOverlays, "insetCornerRadius4"))) {
                    if (!Prefs.loadPrefSettings(Weeabooify.getAppContext(), "cornerRadius").equals("null")) {
                        FabricatedOverlay.buildOverlay("android", "dialogCornerRadius", "dimen", "dialog_corner_radius", "0x" + ((Integer.parseInt(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "cornerRadius")) + 8 + 16) * 100));
                        FabricatedOverlay.buildOverlay("android", "insetCornerRadius2", "dimen", "harmful_app_name_padding_right", "0x" + ((Integer.parseInt(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "cornerRadius")) + 6 + 16) * 100));
                        FabricatedOverlay.buildOverlay("android", "insetCornerRadius4", "dimen", "harmful_app_name_padding_left", "0x" + ((Integer.parseInt(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "cornerRadius")) + 4 + 16) * 100));
                    } else {
                        FabricatedOverlay.buildOverlay("android", "dialogCornerRadius", "dimen", "dialog_corner_radius", "0x" + ((16 + 8 + 16) * 100));
                        FabricatedOverlay.buildOverlay("android", "insetCornerRadius2", "dimen", "harmful_app_name_padding_right", "0x" + ((16 + 6 + 16) * 100));
                        FabricatedOverlay.buildOverlay("android", "insetCornerRadius4", "dimen", "harmful_app_name_padding_left", "0x" + ((16 + 4 + 16) * 100));
                    }
                    FabricatedOverlay.enableOverlay("dialogCornerRadius");
                    FabricatedOverlay.enableOverlay("insetCornerRadius2");
                    FabricatedOverlay.enableOverlay("insetCornerRadius4");
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
