package kaerushi.weeabooify.uwuify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kaerushi.weeabooify.uwuify.config.PrefConfig;
import kaerushi.weeabooify.uwuify.ui.HomePage;
import kaerushi.weeabooify.uwuify.ui.WelcomePage;
import kaerushi.weeabooify.uwuify.utils.ModuleUtil;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;
import kaerushi.weeabooify.uwuify.utils.RootUtil;
import com.topjohnwu.superuser.Shell;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    static {
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
        if (Shell.getCachedShell() == null)
            Shell.setDefaultBuilder(Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR).setTimeout(20));
    }

    private final int versionCode = BuildConfig.VERSION_CODE;

    private boolean keepShowing = true;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Shell.getShell(shell -> {

                Intent intent;

                if (RootUtil.isDeviceRooted() && RootUtil.isMagiskInstalled() && ModuleUtil.moduleExists() && OverlayUtils.overlayExists() && (versionCode == PrefConfig.loadPrefInt(Weeabooify.getAppContext(), "versionCode"))) {
                    keepShowing = false;
                    intent = new Intent(SplashActivity.this, HomePage.class);
                } else {
                    keepShowing = false;
                    intent = new Intent(SplashActivity.this, WelcomePage.class);
                }
                startActivity(intent);
                finish();
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread thread = new Thread(runnable);
        thread.start();
    }
}