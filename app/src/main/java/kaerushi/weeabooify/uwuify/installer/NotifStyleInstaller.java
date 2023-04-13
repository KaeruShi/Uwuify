package kaerushi.weeabooify.uwuify.installer;

import com.topjohnwu.superuser.Shell;

import java.io.File;

import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;

public class NotifStyleInstaller {

    private static final int TOTAL_NOTIFICATIONS = 11;

    public static void enableOverlay(int n) {
        disable_others(n);
        enable_pack(n);
        Prefs.savePrefBool(Weeabooify.getAppContext(), "fabricatedcornerRadius", true);
    }


    protected static void enable_pack(int n) {
        String path = "/system/product/overlay/UwuifyComponentNF" + n + ".apk";

        if (new File(path).exists()) {
            String overlay = "UwuifyComponentNF" + n + ".overlay";

            if (!Prefs.getBoolean(overlay))
                OverlayUtils.enableOverlay(overlay);
        }
    }

    public static void disable_pack(int n) {
        String path = "/system/product/overlay/UwuifyComponentNF" + n + ".apk";

        if (new File(path).exists()) {
            String overlay = "UwuifyComponentNF" + n + ".overlay";

            if (Prefs.getBoolean(overlay))
                OverlayUtils.disableOverlay(overlay);
        }
    }

    protected static void disable_others(int n) {
        for (int i = 1; i <= TOTAL_NOTIFICATIONS; i++) {
            if (i != n) {
                String path = "/system/product/overlay/UwuifyComponentNF" + i + ".apk";

                if (new File(path).exists()) {
                    String overlay = "UwuifyComponentNF" + i + ".overlay";

                    if (Prefs.getBoolean(overlay))
                        OverlayUtils.disableOverlay(overlay);
                }
            }
        }
    }

}
