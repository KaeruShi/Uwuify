package kaerushi.weeabooify.uwuify.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;

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

        alrtBtn.setOnClickListener(view -> {
            if (success)
                Toast.makeText(Weeabooify.getAppContext(), "Reboot!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Weeabooify.getAppContext(), "Dismissed!", Toast.LENGTH_SHORT).show();
        });

        dialog.create();
        dialog.show();
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
