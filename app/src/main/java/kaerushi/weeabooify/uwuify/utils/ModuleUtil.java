package kaerushi.weeabooify.uwuify.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kaerushi.weeabooify.uwuify.BuildConfig;
import kaerushi.weeabooify.uwuify.common.References;

import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ModuleUtil {

    public static void handleModule() throws IOException {
        if (moduleExists()) {
            Shell.cmd("rm -rf " + References.MODULE_DIR).exec();
        }
        installModule();
    }

    static void installModule() throws IOException {
        Log.e("ModuleCheck", "Magisk module does not exist, creating!");
        // Clean temporary directory
        Shell.cmd("mkdir -p " + References.MODULE_DIR).exec();
        Shell.cmd("printf 'id=Uwuify\nname=Uwuify\nversion=" +
                BuildConfig.VERSION_NAME + "\nversionCode=" +
                BuildConfig.VERSION_CODE +
                "\nauthor=@KaeruShi\ndescription=Systemless module for Uwuify.\n' > "
                + References.MODULE_DIR + "/module.prop").exec();
        Shell.cmd("mkdir -p " + References.MODULE_DIR + "/common").exec();
        Shell.cmd("printf '#!/system/bin/sh\n" +
                "# Do NOT assume where your module will be located.\n" +
                "# ALWAYS use $MODDIR if you need to know where this script\n" +
                "# and module is placed.\n" +
                "# This will make sure your module will still work\n" +
                "# if Magisk change its mount point in the future\n" +
                "# This script will be executed in late_start service mode\n' > " + References.MODULE_DIR + "/common/post-fs-data.sh").exec();
        Shell.cmd("printf '#!/system/bin/sh\n" +
                "# Do NOT assume where your module will be located.\n" +
                "# ALWAYS use $MODDIR if you need to know where this script\n" +
                "# and module is placed.\n" +
                "# This will make sure your module will still work\n" +
                "# if Magisk change its mount point in the future\n" +
                "MODDIR=${0%%/*}\n" +
                "# This script will be executed in late_start service mode\n' > " + References.MODULE_DIR + "/common/service.sh").exec();
        Shell.cmd("touch " + References.MODULE_DIR + "/common/system.prop").exec();
        Shell.cmd("mkdir -p " + References.MODULE_DIR + "/system").exec();
        Shell.cmd("mkdir -p " + References.MODULE_DIR + "/system/product").exec();
        Shell.cmd("mkdir -p " + References.MODULE_DIR + "/system/product/overlay").exec();
        Shell.cmd("printf '#!/system/bin/sh\n" +
                "# Please do not hardcode /magisk/modname/... ; instead, please use $MODDIR/...\n" +
                "# This will make your scripts compatible even if Magisk change its mount point in the future\n" +
                "MODDIR=${0%%/*}\n" +
                "\n" +
                "# This script will be executed in late_start service mode\n" +
                "# More info in the main Magisk thread\n' > " + References.MODULE_DIR + "/service.sh").exec();
        Log.e("ModuleCheck", "Magisk module successfully created!");
    }

    public static boolean moduleExists() {
        List<String> lines = Shell.cmd("test -d " + References.MODULE_DIR + " && echo '1'").exec().getOut();
        for (String line : lines) {
            if (line.contains("1"))
                return true;
        }
        return false;
    }

    public static void extractTools() {
        String[] supported_abis = Build.SUPPORTED_ABIS;
        boolean isArm64 = false;
        for (String abi : supported_abis) {
            if (abi.contains("arm64")) {
                isArm64 = true;
                break;
            }
        }

        String folderName;
        if (isArm64)
            folderName = "arm64-v8a";
        else
            folderName = "armeabi-v7a";

        try {
            FileUtil.copyAssets("Tools");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Shell.cmd("cp -a " + References.DATA_DIR + "/Tools/" + folderName + "/. " + References.MODULE_DIR + "/tools").exec();
            Shell.cmd("cp " + References.DATA_DIR + "/Tools/zip " + References.MODULE_DIR + "/tools").exec();
            FileUtil.cleanDir("Tools");
            RootUtil.setPermissionsRecursively(755, References.MODULE_DIR + "/tools");
        }
    }

    static void copyOverlays(Context context, String overlayFolder) {

        String[] overlays = new String[0];
        try {
            overlays = context.getAssets().list(overlayFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data_dir = context.getFilesDir().toString();

        // Clean temporary directory
        Shell.cmd("rm -rf " + data_dir).exec();
        File device_file = new File(data_dir + "/" + overlayFolder + "/");
        device_file.mkdirs();

        for (String overlay : overlays) {
            File file = new File(data_dir + "/" + overlayFolder + "/" + overlay);
            if (!file.exists()) {
                try {
                    copyFileTo(context, overlayFolder + "/" + overlay, data_dir + "/" + overlayFolder + "/" + overlay);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Shell.cmd("cp -f " + data_dir + "/" + overlayFolder + "/" + overlay + " " + References.OVERLAY_DIR + '/' + overlay).exec();
        }
        RootUtil.setPermissionsRecursively(644, References.OVERLAY_DIR + '/');
        // Clean temporary directory
        Shell.cmd("rm -rf " + data_dir + "/" + overlayFolder).exec();
    }

    static void copyFileTo(Context c, String source, String destination) throws IOException {

        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(destination);
        myInput = c.getAssets().open(source);

        byte[] buffer = new byte[References.BYTE_ACCESS_RATE];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }

}