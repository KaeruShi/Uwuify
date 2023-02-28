package kaerushi.weeabooify.uwuify.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.topjohnwu.superuser.Shell;

import kaerushi.weeabooify.uwuify.R;

public class AlertDialog extends AppCompatActivity {

    Context context;
    Dialog dialog;

    public AlertDialog(Context context) {
        this.context = context;
    }

    public void show(int AlrtImg, String title, String desc, Boolean success) {
        if (dialog != null)
            dialog.dismiss();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setOnCancelListener(null);
        dialog.setCanceledOnTouchOutside(false);

        ImageView alrtimg = dialog.findViewById(R.id.AlrtImg);
        TextView alrttext = dialog.findViewById(R.id.title);
        TextView alrtdesc = dialog.findViewById(R.id.desc);
        Button alrtBtn = dialog.findViewById(R.id.AlrtBtn);
        alrtimg.setImageResource(AlrtImg);
        alrttext.setText(title);
        alrtdesc.setText(desc);

        alrtBtn.setText(success ? "Reboot" : "Dismiss");

        if (success) {
            alrtdesc.setGravity(Gravity.CENTER);
        }

        alrtBtn.setOnClickListener(view -> {
            if (success) {
                new Handler().postDelayed(restartDevice(), 200);
                dialog.dismiss();
            }
            else {

                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }

    private static Runnable restartDevice() {
        Shell.cmd("su -c 'svc power reboot'").exec();
        return null;
    }

    public void hide() {
        if ((dialog != null) && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && !isFinishing()) {
            dialog.dismiss();
        }
    }
}
