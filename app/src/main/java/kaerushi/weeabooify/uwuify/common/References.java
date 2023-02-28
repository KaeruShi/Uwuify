package kaerushi.weeabooify.uwuify.common;

import android.os.Environment;

import kaerushi.weeabooify.uwuify.Weeabooify;

public class References {

    // Storage location
    public static final String MODULE_DIR = "/data/adb/modules/Uwuify";
    public static final String DATA_DIR = Weeabooify.getAppContext().getFilesDir().toString();
    public static final String OVERLAY_DIR = MODULE_DIR + "/system/product/overlay";
    public static final String TOOLS_DIR = MODULE_DIR + "/tools";
    public static final String TEMP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Uwuify";
    public static final String TEMP_OVERLAY_DIR = TEMP_DIR + "/overlays";
    public static final String UNSIGNED_UNALIGNED_DIR = TEMP_DIR + "/overlays/unsigned_unaligned";
    public static final String UNSIGNED_DIR = TEMP_DIR + "/overlays/unsigned";
    public static final String SIGNED_DIR = TEMP_DIR + "/overlays/signed";


    // Preference files
    public static final String SharedPref = Weeabooify.getAppContext().getPackageName();


    // Config
    public static final String FIRST_INSTALL = "firstInstall";
    public static final String UPDATE_DETECTED = "updateDetected";
    public static final String VER_CODE = "versionCode";

    // Others
    public static final int BYTE_ACCESS_RATE = 8192;
}
