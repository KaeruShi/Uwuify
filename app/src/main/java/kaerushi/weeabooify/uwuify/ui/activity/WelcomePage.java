package kaerushi.weeabooify.uwuify.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.topjohnwu.superuser.Shell;

import java.io.IOException;
import java.util.Objects;

import kaerushi.weeabooify.uwuify.BuildConfig;
import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.common.References;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.ui.view.AlertDialog;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialogAlt;
import kaerushi.weeabooify.uwuify.utils.CompilerUtil;
import kaerushi.weeabooify.uwuify.utils.ModuleUtil;
import kaerushi.weeabooify.uwuify.utils.OverlayUtils;
import kaerushi.weeabooify.uwuify.utils.RootUtil;
import kaerushi.weeabooify.uwuify.utils.AOSPCompilerUtil;

public class WelcomePage extends AppCompatActivity {

    private static boolean hasErroredOut = false;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout warn;
    @SuppressLint("StaticFieldLeak")
    private static TextView warning;
    @SuppressLint("StaticFieldLeak")
    private static Button install_module;
    private final int versionCode = BuildConfig.VERSION_CODE;
    private static startInstallationProcess installModule = null;
    private static startInstallationProcessAosp installModuleAosp = null;
    private LoadingDialogAlt loadingDialog;
    private AlertDialog alertDialog;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);


        // Progressbar while installing module
        loadingDialog = new LoadingDialogAlt(this);
        alertDialog = new AlertDialog(this);

        // Continue button
        install_module = findViewById(R.id.checkRoot);

        // Dialog to show if root not found

        // Rom variant
        LinearLayout nusa_variant = findViewById(R.id.nusa_variant);
        LinearLayout rr_variant = findViewById(R.id.rr_variant);
        LinearLayout havoc_variant = findViewById(R.id.havoc_variant);
        LinearLayout los_variant = findViewById(R.id.los_variant);
        LinearLayout aosp_variant = findViewById(R.id.aosp_variant);

        nusa_variant.setOnClickListener(v -> {
            Prefs.savePrefSettings(Weeabooify.getAppContext(), "selectedRomVariant", "Nusan");
            nusa_variant.setBackground(getResources().getDrawable(R.drawable.container_selected));
            rr_variant.setBackground(getResources().getDrawable(R.drawable.container));
            los_variant.setBackground(getResources().getDrawable(R.drawable.container));
            aosp_variant.setBackground(getResources().getDrawable(R.drawable.container));

            Transition transition = new Fade();
            transition.setDuration(1200);
            transition.addTarget(R.id.checkRoot);
            TransitionManager.beginDelayedTransition(nusa_variant, transition);
            install_module.setVisibility(View.VISIBLE);
        });

        rr_variant.setOnClickListener(v -> {
            Prefs.savePrefSettings(Weeabooify.getAppContext(), "selectedRomVariant", "RR");
            rr_variant.setBackground(getResources().getDrawable(R.drawable.container_selected));
            nusa_variant.setBackground(getResources().getDrawable(R.drawable.container));
            los_variant.setBackground(getResources().getDrawable(R.drawable.container));
            aosp_variant.setBackground(getResources().getDrawable(R.drawable.container));

            Transition transition = new Fade();
            transition.setDuration(1200);
            transition.addTarget(R.id.checkRoot);
            TransitionManager.beginDelayedTransition(rr_variant, transition);
            install_module.setVisibility(View.VISIBLE);
        });

        los_variant.setOnClickListener(view -> {
            Prefs.savePrefSettings(Weeabooify.getAppContext(), "selectedRomVariant", "LOS");
            los_variant.setBackground(getResources().getDrawable(R.drawable.container_selected));
            nusa_variant.setBackground(getResources().getDrawable(R.drawable.container));
            rr_variant.setBackground(getResources().getDrawable(R.drawable.container));
            aosp_variant.setBackground(getResources().getDrawable(R.drawable.container));

            Transition transition = new Fade();
            transition.setDuration(1200);
            transition.addTarget(R.id.checkRoot);
            TransitionManager.beginDelayedTransition(los_variant, transition);
            install_module.setVisibility(View.VISIBLE);
        });

        havoc_variant.setOnClickListener(view -> {
            Toast.makeText(Weeabooify.getAppContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        aosp_variant.setOnClickListener(view -> {
            Prefs.savePrefSettings(Weeabooify.getAppContext(), "selectedRomVariant", "AOSP");
            aosp_variant.setBackground(getResources().getDrawable(R.drawable.container_selected));
            los_variant.setBackground(getResources().getDrawable(R.drawable.container));
            nusa_variant.setBackground(getResources().getDrawable(R.drawable.container));
            rr_variant.setBackground(getResources().getDrawable(R.drawable.container));

            Transition transition = new Fade();
            transition.setDuration(1200);
            transition.addTarget(R.id.checkRoot);
            TransitionManager.beginDelayedTransition(aosp_variant, transition);
            install_module.setVisibility(View.VISIBLE);
        });

        // Check for root onClick
        install_module.setOnClickListener(v -> {
            if (Objects.equals(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "null"))
                Toast.makeText(Weeabooify.getAppContext(), "Select a ROM before proceeding", Toast.LENGTH_SHORT).show();
            else {
                if (RootUtil.isDeviceRooted()) {
                    if (RootUtil.isMagiskInstalled()) {
                        if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED || androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
                            androidx.core.app.ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);

                            Toast.makeText(Weeabooify.getAppContext(), "Select a ROM before proceeding", Toast.LENGTH_SHORT).show();
                        } else {
                            if ((Prefs.loadPrefInt(this, "versionCode") != versionCode) || !ModuleUtil.moduleExists() || !OverlayUtils.overlayExists()) {
                                if (Objects.equals(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "AOSP")) {
                                    installModuleAosp = new startInstallationProcessAosp();
                                    installModuleAosp.execute();
                                } else {
                                    installModule = new startInstallationProcess();
                                    installModule.execute();
                                }
                            } else {
                                Intent intent = new Intent(WelcomePage.this, MainHome.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(Weeabooify.getAppContext(), "Install Magisk first", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Weeabooify.getAppContext(), "Root your device first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (installModule != null) installModule.cancel(true);
        super.onDestroy();
    }

    @SuppressLint("StaticFieldLeak")
    private class startInstallationProcess extends AsyncTask<Void, Integer, Integer> {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show(getResources().getString(R.string.installing), getResources().getString(R.string.init_module_installation));
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            String title = getResources().getString(R.string.step) + ' ' + values[0] + "/10";
            String desc = getResources().getString(R.string.loading_dialog_wait);

            switch (values[0]) {
                case 1:
                    desc = getResources().getString(R.string.module_installation_step1);
                    break;
                case 2:
                    desc = getResources().getString(R.string.module_installation_step2);
                    break;
                case 3:
                    desc = getResources().getString(R.string.module_installation_step3);
                    break;
                case 4:
                    desc = getResources().getString(R.string.module_installation_step4);
                    break;
                case 5:
                    desc = getResources().getString(R.string.module_installation_step5);
                    break;
                case 6:
                    desc = getResources().getString(R.string.module_installation_step6);
                    break;
                case 7:
                    desc = getResources().getString(R.string.module_installation_step7);
                    break;
                case 8:
                    desc = getResources().getString(R.string.module_installation_step8);
                    break;
                case 9:
                    desc = getResources().getString(R.string.module_installation_step9);
                    break;
            }

            loadingDialog.setMessage(title, desc);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int step = 1;

            publishProgress(step++);
            try {
                ModuleUtil.handleModule();
            } catch (IOException e) {
                hasErroredOut = true;
                e.printStackTrace();
            }

            publishProgress(step++);
            ModuleUtil.extractTools();

            // AOSP
            publishProgress(step++);
            try {
                AOSPCompilerUtil.preExecute();
            } catch (IOException e) {
                hasErroredOut = true;
                e.printStackTrace();
            }

            publishProgress(step++);
            hasErroredOut = AOSPCompilerUtil.buildOverlays();

            publishProgress(step++);
            hasErroredOut = AOSPCompilerUtil.alignAPK();

            publishProgress(step++);
            hasErroredOut = AOSPCompilerUtil.signAPK();

            publishProgress(step++);
            AOSPCompilerUtil.postExecute(hasErroredOut);

            // Variant
            publishProgress(step++);
            try {
                CompilerUtil.preExecute();
            } catch (IOException e) {
                hasErroredOut = true;
                e.printStackTrace();
            }
            hasErroredOut = CompilerUtil.buildOverlays();

            publishProgress(step++);
            hasErroredOut = CompilerUtil.alignAPK();

            publishProgress(step);
            hasErroredOut = CompilerUtil.signAPK();

            CompilerUtil.postExecute(hasErroredOut);

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            loadingDialog.hide();

            if (!hasErroredOut) {
                if (OverlayUtils.overlayExists()) {
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(WelcomePage.this, MainHome.class);
                        startActivity(intent);
                        finish();
                    }, 10);
                } else {
                    alertDialog.show(R.drawable.ic_info, "Setup Success", "Reboot your device to apply the overlays", true);
                    install_module.setVisibility(View.GONE);
                }
            } else {
                alertDialog.show(R.drawable.ic_info, "Setup Failed", getString(R.string.reason), false);
                Shell.cmd("rm -rf " + References.MODULE_DIR).exec();
                install_module.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Shell.cmd("rm -rf " + References.DATA_DIR).exec();
            Shell.cmd("rm -rf " + References.TEMP_DIR).exec();
            Shell.cmd("rm -rf " + References.MODULE_DIR).exec();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class startInstallationProcessAosp extends AsyncTask<Void, Integer, Integer> {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show(getResources().getString(R.string.installing), getResources().getString(R.string.init_module_installation));
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            String title = getResources().getString(R.string.step) + ' ' + values[0] + "/7";
            String desc = getResources().getString(R.string.loading_dialog_wait);

            switch (values[0]) {
                case 1:
                    desc = getResources().getString(R.string.module_installation_step1);
                    break;
                case 2:
                    desc = getResources().getString(R.string.module_installation_step2);
                    break;
                case 3:
                    desc = getResources().getString(R.string.module_installation_step3);
                    break;
                case 4:
                    desc = getResources().getString(R.string.module_installation_step4);
                    break;
                case 5:
                    desc = getResources().getString(R.string.module_installation_step5);
                    break;
                case 6:
                    desc = getResources().getString(R.string.module_installation_step7);
                    break;
                case 7:
                    desc = getResources().getString(R.string.module_installation_step9);
                    break;
            }

            loadingDialog.setMessage(title, desc);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int step = 1;

            publishProgress(step++);
            try {
                ModuleUtil.handleModule();
            } catch (IOException e) {
                hasErroredOut = true;
                e.printStackTrace();
            }

            publishProgress(step++);
            ModuleUtil.extractTools();

            // AOSP
            publishProgress(step++);
            try {
                AOSPCompilerUtil.preExecute();
            } catch (IOException e) {
                hasErroredOut = true;
                e.printStackTrace();
            }

            publishProgress(step++);
            hasErroredOut = AOSPCompilerUtil.buildOverlays();

            publishProgress(step++);
            hasErroredOut = AOSPCompilerUtil.alignAPK();

            publishProgress(step++);
            hasErroredOut = AOSPCompilerUtil.signAPK();

            publishProgress(step);
            AOSPCompilerUtil.postExecute(hasErroredOut);

            CompilerUtil.postExecute(hasErroredOut);

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            loadingDialog.hide();

            if (!hasErroredOut) {
                if (OverlayUtils.overlayExists()) {
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(WelcomePage.this, MainHome.class);
                        startActivity(intent);
                        finish();
                    }, 10);
                } else {
                    alertDialog.show(R.drawable.ic_info, "Setup Success", "Reboot your device to apply the overlays", true);
                    install_module.setVisibility(View.GONE);
                }
            } else {
                alertDialog.show(R.drawable.ic_info, "Setup Failed", getString(R.string.reason), false);
                Shell.cmd("rm -rf " + References.MODULE_DIR).exec();
                install_module.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Shell.cmd("rm -rf " + References.DATA_DIR).exec();
            Shell.cmd("rm -rf " + References.TEMP_DIR).exec();
            Shell.cmd("rm -rf " + References.MODULE_DIR).exec();
        }
    }
}