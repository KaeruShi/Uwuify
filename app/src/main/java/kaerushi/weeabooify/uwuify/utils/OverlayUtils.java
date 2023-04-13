package kaerushi.weeabooify.uwuify.utils;

import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class OverlayUtils {

    public static List<String> getOverlayList() {
        return Shell.cmd("cmd overlay list |  grep -E '^....UwuifyComponent' | sed -E 's/^....//'").exec().getOut();
    }

    public static List<String> getEnabledOverlayList() {
        return Shell.cmd("cmd overlay list |  grep -E '^.x..UwuifyComponent' | sed -E 's/^.x..//'").exec().getOut();
    }

    public static boolean isOverlayEnabled(List<String> overlays, String pkgName) {
        for (String overlay : overlays) {
            if (overlay.equals(pkgName))
                return true;
        }
        return false;
    }

    public static boolean isOverlayDisabled(List<String> overlays, String pkgName) {
        for (String overlay : overlays) {
            if (overlay.equals(pkgName))
                return false;
        }
        return true;
    }

    static boolean isOverlayInstalled(List<String> enabledOverlays, String pkgName) {
        for (String line : enabledOverlays) {
            if (line.equals(pkgName))
                return true;
        }
        return false;
    }

    public static void enableOverlay(String pkgName) {
        Shell.cmd("cmd overlay enable --user current " + pkgName, "cmd overlay set-priority " + pkgName + " highest").exec();
    }

    public static void disableOverlay(String pkgName) {
        Shell.cmd("cmd overlay disable --user current " + pkgName).exec();
    }

    public static boolean overlayExists() {
        List<String> list = Shell.cmd("[ -f /system/product/overlay/UwuifyComponentHDDH.apk ] && echo \"found\" || echo \"not found\"").exec().getOut();
        return Objects.equals(list.get(0), "found");
    }
}
