package kaerushi.weeabooify.uwuify.utils;

import static kaerushi.weeabooify.uwuify.utils.apksigner.CryptoUtils.readCertificate;
import static kaerushi.weeabooify.uwuify.utils.apksigner.CryptoUtils.readPrivateKey;

import android.util.Log;

import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Objects;

import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.common.References;
import kaerushi.weeabooify.uwuify.utils.apksigner.JarMap;
import kaerushi.weeabooify.uwuify.utils.apksigner.SignAPK;

public class AOSPCompilerUtil {
    private static final String TAG = "AOSPCompilerUtil";
    private static final String aapt = References.TOOLS_DIR + "/libaapt.so";
    private static final String zipalign = References.TOOLS_DIR + "/libzipalign.so";

    public static boolean buildOverlays() {

        // Create AndroidManifest.xml and build APK using AAPT
        File dir = new File(References.DATA_DIR + "/Overlays/" +
                "AOSP");

        for (File pkg : Objects.requireNonNull(dir.listFiles())) {
            if (pkg.isDirectory()) {
                for (File overlay : Objects.requireNonNull(pkg.listFiles())) {
                    if (overlay.isDirectory()) {
                        String overlay_name = overlay.toString().replace(pkg.toString() + '/', "");
                        if (createManifest(overlay_name, pkg.toString().replace(References.DATA_DIR + "/Overlays/" + "AOSP" + '/', ""), overlay.getAbsolutePath())) {
                            Log.e(TAG, "Failed to create Manifest for " + overlay_name + "! Exiting...");
                            postExecute(true);
                            return false;
                        }
                        if (runAapt(overlay.getAbsolutePath(), overlay_name)) {
                            Log.e(TAG, "Failed to build " + overlay_name + "! Exiting...");
                            postExecute(true);
                            return false;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean alignAPK() {
        // ZipAlign the APK
        File dir = new File(References.UNSIGNED_UNALIGNED_DIR);
        if (dir.listFiles() == null)
            return true;

        for (File overlay : Objects.requireNonNull(dir.listFiles())) {
            if (!overlay.isDirectory()) {
                if (zipAlign(overlay.getAbsolutePath(), overlay.toString().replace(References.UNSIGNED_UNALIGNED_DIR + '/', "").replace("-unaligned", ""))) {
                    Log.e(TAG, "Failed to align " + overlay + "! Exiting...");
                    postExecute(true);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean signAPK() {
        // Sign the APK
        File dir = new File(References.UNSIGNED_DIR);
        if (dir.listFiles() == null)
            return true;

        for (File overlay : Objects.requireNonNull(dir.listFiles())) {
            if (!overlay.isDirectory()) {
                if (apkSigner(overlay.getAbsolutePath(), overlay.toString().replace(References.UNSIGNED_DIR + '/', "").replace("-unsigned", ""))) {
                    Log.e(TAG, "Failed to sign " + overlay + "! Exiting...");
                    postExecute(true);
                    return true;
                }
            }
        }
        return false;
    }

    public static void preExecute() throws IOException {
        // Clean data directory
        Shell.cmd("rm -rf " + References.TEMP_DIR).exec();
        Shell.cmd("rm -rf " + References.DATA_DIR + "/Keystore").exec();
        Shell.cmd("rm -rf " + References.DATA_DIR + "/Overlays").exec();

        // Extract keystore and overlays from assets
        FileUtil.copyAssets("Keystore");
        FileUtil.copyAssets("Overlays/AOSP");
        try {
            CryptoManager.decryptFileRecursively(new File(Weeabooify.getAppContext().getFilesDir() + "/Overlays/AOSP"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create temp directory
        Shell.cmd("rm -rf " + References.TEMP_DIR + "; mkdir -p " + References.TEMP_DIR).exec();
        Shell.cmd("mkdir -p " + References.TEMP_OVERLAY_DIR).exec();
        Shell.cmd("mkdir -p " + References.UNSIGNED_UNALIGNED_DIR).exec();
        Shell.cmd("mkdir -p " + References.UNSIGNED_DIR).exec();
        Shell.cmd("mkdir -p " + References.SIGNED_DIR).exec();
    }

    public static void postExecute(boolean hasErroredOut) {
        // Move all generated overlays to module
        if (!hasErroredOut) {
            Shell.cmd("cp -a " + References.SIGNED_DIR + "/. " + References.OVERLAY_DIR).exec();
            RootUtil.setPermissionsRecursively(644, References.OVERLAY_DIR + '/');
        }

        // Clean temp directory
        Shell.cmd("rm -rf " + References.TEMP_DIR).exec();
        Shell.cmd("rm -rf " + References.DATA_DIR + "/Keystore").exec();
        Shell.cmd("rm -rf " + References.DATA_DIR + "/Overlays").exec();

    }

    private static boolean createManifest(String pkgName, String target, String source) {
        return !Shell.cmd("printf '<?xml version=\"1.0\" encoding=\"utf-8\" ?>\\n<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\" android:versionName=\"v1.0\" package=\"UwuifyComponent" + pkgName + ".overlay\">\\n\\t<overlay android:priority=\"1\" android:targetPackage=\"" + target + "\" />\\n\\t<application android:allowBackup=\"false\" android:hasCode=\"false\" />\\n</manifest>' > " + source + "/AndroidManifest.xml;").exec().isSuccess();
    }

    private static boolean runAapt(String source, String name) {
        return !Shell.cmd(aapt + " p -f -v -M " + source + "/AndroidManifest.xml -I /system/framework/framework-res.apk -I /system/product/priv-app/SystemUI/SystemUI.apk -S " + source + "/res -F " + References.UNSIGNED_UNALIGNED_DIR + '/' + name + "-unsigned-unaligned.apk >/dev/null;").exec().isSuccess();
    }

    private static boolean zipAlign(String source, String name) {
        return !Shell.cmd(zipalign + " 4 " + source + ' ' + References.UNSIGNED_DIR + '/' + name).exec().isSuccess();
    }

    private static boolean apkSigner(String source, String name) {
        File testKey = new File(References.DATA_DIR + "/Keystore/testkey.pk8");
        File certificate = new File(References.DATA_DIR + "/Keystore/testkey.x509.pem");

        if (!testKey.exists() || !certificate.exists()) {
            Log.d("KeyStore", "Loading keystore from assets...");
            try {
                FileUtil.copyAssets("Keystore");
            } catch (Exception e) {
                postExecute(true);
                return true;
            }
        }

        try {
            InputStream keyFile = new FileInputStream(testKey);
            PrivateKey key = readPrivateKey(keyFile);

            InputStream certFile = new FileInputStream(certificate);
            X509Certificate cert = readCertificate(certFile);

            JarMap jar = JarMap.open(new FileInputStream(source), true);
            FileOutputStream out = new FileOutputStream(References.SIGNED_DIR + "/UwuifyComponent" + name);

            SignAPK.sign(cert, key, jar, out);
        } catch (Exception e) {
            Log.e(TAG, "Failed to sign " + name + "! Exiting...\n" + e);
            postExecute(true);
            return true;
        }
        return false;
    }
}
