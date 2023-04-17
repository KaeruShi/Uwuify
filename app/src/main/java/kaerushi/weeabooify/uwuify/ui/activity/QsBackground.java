package kaerushi.weeabooify.uwuify.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.ui.adapter.QsBackgroundAdapter;
import kaerushi.weeabooify.uwuify.ui.models.QsBackgroundModel;
import kaerushi.weeabooify.uwuify.ui.utils.VBHelpers;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class QsBackground extends AppCompatActivity {
    
    LoadingDialog loadingDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qs_background);

        // Header
        VBHelpers.setHeader(this, findViewById(R.id.collapsing_toolbar), findViewById(R.id.toolbar), R.string.activity_title_qs_background);

        // Loading dialog while enabling or disabling pack
        loadingDialog = new LoadingDialog(this);

        // RecyclerView
        RecyclerView container = findViewById(R.id.qsb_container);
        container.setLayoutManager(new LinearLayoutManager(this));
        container.setAdapter(initQsb());
        container.setHasFixedSize(true);
    }

    private QsBackgroundAdapter initQsb() {
        ArrayList<QsBackgroundModel> qsb_list = new ArrayList<>();
        qsb_list.add(new QsBackgroundModel("Transparent", R.color.transparent));
        qsb_list.add(new QsBackgroundModel("3D", R.drawable.notification_3d));
        qsb_list.add(new QsBackgroundModel("iOS", R.drawable.notification_ios));
        qsb_list.add(new QsBackgroundModel("Inline", R.drawable.notification_inline));
        qsb_list.add(new QsBackgroundModel("Outline", R.drawable.notification_outline));
        qsb_list.add(new QsBackgroundModel("Outline Bottom", R.drawable.notification_outline_bottom));
        qsb_list.add(new QsBackgroundModel("Outline Top", R.drawable.notification_outline_top));
        qsb_list.add(new QsBackgroundModel("Outline Vertical", R.drawable.notification_outline_vertical));
        qsb_list.add(new QsBackgroundModel("Outline Horizontal", R.drawable.notification_outline_horizontal));

        return new QsBackgroundAdapter(this, qsb_list, loadingDialog);
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