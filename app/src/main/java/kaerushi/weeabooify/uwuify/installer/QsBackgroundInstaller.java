package kaerushi.weeabooify.uwuify.installer;

import java.io.File;

import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;

public class QsBackgroundInstaller {

    private static final int TOTAL_QSS = 9;

    public static void enableOverlay(int n) {
        disable_others(n);
        enable_pack(n);
        Prefs.savePrefBool(Weeabooify.getAppContext(), "fabricatedcornerRadius", true);
    }


    protected static void enable_pack(int n) {
        String path = "/system/product/overlay/UwuifyComponentQSS" + n + ".apk";

        if (new File(path).exists()) {
            String overlay = "UwuifyComponentQSS" + n + ".overlay";

            if (!Prefs.getBoolean(overlay))
                OverlayUtils.enableOverlay(overlay);
        }
    }

    public static void disable_pack(int n) {
        String path = "/system/product/overlay/UwuifyComponentQSS" + n + ".apk";

        if (new File(path).exists()) {
            String overlay = "UwuifyComponentQSS" + n + ".overlay";

            if (Prefs.getBoolean(overlay))
                OverlayUtils.disableOverlay(overlay);
        }
    }

    protected static void disable_others(int n) {
        for (int i = 1; i <= TOTAL_QSS; i++) {
            if (i != n) {
                String path = "/system/product/overlay/UwuifyComponentQSS" + i + ".apk";

                if (new File(path).exists()) {
                    String overlay = "UwuifyComponentQSS" + i + ".overlay";

                    if (Prefs.getBoolean(overlay))
                        OverlayUtils.disableOverlay(overlay);
                }
            }
        }
    }

}
