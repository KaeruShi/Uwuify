package kaerushi.weeabooify.uwuify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.ui.adapter.NotificationAdapter;
import kaerushi.weeabooify.uwuify.ui.models.NotificationModel;
import kaerushi.weeabooify.uwuify.ui.utils.VBHelpers;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class NotificationStyle extends AppCompatActivity {

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_style);

        // Header
        VBHelpers.setHeader(this, findViewById(R.id.collapsing_toolbar), findViewById(R.id.toolbar), R.string.activity_title_notification);

        // Loading dialog while enabling or disabling pack
        loadingDialog = new LoadingDialog(this);

        // RecyclerView
        RecyclerView container = findViewById(R.id.notif_container);
        container.setLayoutManager(new LinearLayoutManager(this));
        container.setAdapter(initNotification());
        container.setHasFixedSize(true);
    }

    private NotificationAdapter initNotification() {
        ArrayList<NotificationModel> notif_list = new ArrayList<>();
        notif_list.add(new NotificationModel("Transparent", R.drawable.container));

        return new NotificationAdapter(this, notif_list, loadingDialog);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDestroy() {
        loadingDialog.hide();
        super.onDestroy();
    }
}