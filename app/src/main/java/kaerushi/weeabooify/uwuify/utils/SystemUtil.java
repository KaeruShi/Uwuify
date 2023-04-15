package kaerushi.weeabooify.uwuify.utils;

import com.topjohnwu.superuser.Shell;

import kaerushi.weeabooify.uwuify.common.References;

public class SystemUtil {

    public static void restartSystemUI() {
        Shell.cmd("killall " + References.SYSTEMUI_PACKAGE).exec();
    }

    public static void mountRW() {
        Shell.cmd("mount -o remount,rw /").exec();
    }

    public static void mountRO() {
        Shell.cmd("mount -o remount,ro /").exec();
    }
}
