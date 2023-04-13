package kaerushi.weeabooify.uwuify.installer;

import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;

import com.topjohnwu.superuser.Shell;

import java.io.File;

public class QsStyleInstaller {

    private static final int TOTAL_QSSTYLE = 6;

    public static void install_pack(int n) {
        disable_others(n);
        enable_pack(n);
        Prefs.savePrefBool(Weeabooify.getAppContext(), "fabricatedcornerRadius", true);
    }

    private static void enable_pack(int n) {

        String[] paths = {"/system/product/overlay/UwuifyComponentADDAS" + n + ".apk", "/system/product/overlay/UwuifyComponentQS" + n + ".apk"};

        for (String path : paths) {
            if (new File(path).exists()) {

                String overlay = (path.replaceAll("/system/product/overlay/", "")).replaceAll("apk", "overlay");

                try {
                    Shell.cmd("cmd overlay enable --user current " + overlay).exec();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }


    public static void disable_pack(int n) {

        String[] paths = {"/system/product/overlay/UwuifyComponentADDAS" + n + ".apk", "/system/product/overlay/UwuifyComponentQS" + n + ".apk"};

        for (String path : paths) {
            if (new File(path).exists()) {

                String overlay = (path.replaceAll("/system/product/overlay/", "")).replaceAll("apk", "overlay");

                try {
                    Shell.cmd("cmd overlay disable --user current " + overlay).exec();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    protected static void disable_others(int n) {

        for (int i = 1; i <= TOTAL_QSSTYLE; i++) {
            if (i != n) {
                String[] paths = {"/system/product/overlay/UwuifyComponentADDAS" + i + ".apk", "/system/product/overlay/UwuifyComponentQS" + i + ".apk"};

                if (new File(paths[0]).exists()) {
                    String overlay = "UwuifyComponentADDAS" + i + ".overlay";
                    try {
                        Shell.cmd("cmd overlay disable --user current " + overlay).exec();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }

                if (new File(paths[1]).exists()) {
                    String overlay = "UwuifyComponentQS" + i + ".overlay";
                    try {
                        Shell.cmd("cmd overlay disable --user current " + overlay).exec();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                }
            }
        }
    }

}
