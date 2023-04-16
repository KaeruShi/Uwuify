package kaerushi.weeabooify.uwuify.ui.activity;

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
        notif_list.add(new NotificationModel("Transparent", R.color.transparent));
        notif_list.add(new NotificationModel("Separated", R.drawable.container));
        notif_list.add(new NotificationModel("3D", R.drawable.notification_3d));
        notif_list.add(new NotificationModel("iOS", R.drawable.notification_ios));
        notif_list.add(new NotificationModel("Inline", R.drawable.notification_inline));
        notif_list.add(new NotificationModel("Outline", R.drawable.notification_outline));
        notif_list.add(new NotificationModel("Outline Bottom", R.drawable.notification_outline_bottom));
        notif_list.add(new NotificationModel("Outline Top", R.drawable.notification_outline_top));
        notif_list.add(new NotificationModel("Outline Vertical", R.drawable.notification_outline_vertical));
        notif_list.add(new NotificationModel("Outline Horizontal", R.drawable.notification_outline_horizontal));
        notif_list.add(new NotificationModel("Twoblock", R.drawable.notification_outline_twoblock));

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