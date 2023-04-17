package kaerushi.weeabooify.uwuify.installer;

import java.io.File;

import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;

public class QsStyleInstaller {

    private static final int TOTAL_QSSTYLE = 6;

    public static void enableOverlay(int n) {
        disable_others(n);
        enable_pack(n);
    }

    protected static void enable_pack(int n) {
        String[] paths = {"/system/product/overlay/UwuifyComponentADDAS" + n + ".apk", "/system/product/overlay/UwuifyComponentQS" + n + ".apk"};

        if (new File(paths[0]).exists()) {
            String overlay = "UwuifyComponentADDAS" + n + ".overlay";

            if (!Prefs.getBoolean(overlay))
                OverlayUtils.enableOverlay(overlay);
        }

        if (new File(paths[1]).exists()) {
            String overlay = "UwuifyComponentQS" + n + ".overlay";

            if (!Prefs.getBoolean(overlay))
                OverlayUtils.enableOverlay(overlay);
        }
    }

    public static void disable_pack(int n) {
        String[] paths = {"/system/product/overlay/UwuifyComponentADDAS" + n + ".apk", "/system/product/overlay/UwuifyComponentQS" + n + ".apk"};

        if (new File(paths[0]).exists()) {
            String overlay = "UwuifyComponentADDAS" + n + ".overlay";

            if (Prefs.getBoolean(overlay))
                OverlayUtils.disableOverlay(overlay);
        }

        if (new File(paths[1]).exists()) {
            String overlay = "UwuifyComponentQS" + n + ".overlay";

            if (Prefs.getBoolean(overlay))
                OverlayUtils.disableOverlay(overlay);
        }
    }

    protected static void disable_others(int n) {
        for (int i = 1; i <= TOTAL_QSSTYLE; i++) {
            if (i != n) {
                String[] paths = {"/system/product/overlay/UwuifyComponentADDAS" + i + ".apk", "/system/product/overlay/UwuifyComponentQS" + i + ".apk"};

                if (new File(paths[0]).exists()) {
                    String overlay = "UwuifyComponentADDAS" + i + ".overlay";

                    if (Prefs.getBoolean(overlay))
                        OverlayUtils.disableOverlay(overlay);
                }

                if (new File(paths[1]).exists()) {
                    String overlay = "UwuifyComponentQS" + i + ".overlay";

                    if (Prefs.getBoolean(overlay))
                        OverlayUtils.disableOverlay(overlay);
                }
            }
        }
    }

}
