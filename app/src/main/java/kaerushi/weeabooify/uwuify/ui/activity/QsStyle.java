package kaerushi.weeabooify.uwuify.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.ui.adapter.MenuAdapter;
import kaerushi.weeabooify.uwuify.ui.adapter.QsAdapter;
import kaerushi.weeabooify.uwuify.ui.models.MenuModel;
import kaerushi.weeabooify.uwuify.ui.models.QsModel;
import kaerushi.weeabooify.uwuify.ui.utils.VBHelpers;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class QsStyle extends AppCompatActivity {

    LoadingDialog loadingDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qs_style);

        // Header
        VBHelpers.setHeader(this, findViewById(R.id.collapsing_toolbar), findViewById(R.id.toolbar), R.string.activity_title_qs_style);

        // Loading dialog while enabling or disabling pack
        loadingDialog = new LoadingDialog(this);

        // RecyclerView
        RecyclerView container = findViewById(R.id.qs_container);
        container.setLayoutManager(new LinearLayoutManager(this));
        ConcatAdapter adapter = new ConcatAdapter(initActivityItems(), initQS());
        container.setAdapter(adapter);
        container.setHasFixedSize(true);
    }

    private MenuAdapter initActivityItems() {
        ArrayList<MenuModel> brightnessbar_activity_list = new ArrayList<>();

        brightnessbar_activity_list.add(new MenuModel(QsBackground.class, getResources().getString(R.string.activity_title_qsb_variant), getResources().getString(R.string.activity_desc_qsb_variant), R.drawable.ic_qs));

        return new MenuAdapter(this, brightnessbar_activity_list);
    }

    private QsAdapter initQS() {
        ArrayList<QsModel> qs_list = new ArrayList<>();
        qs_list.add(new QsModel("Style ColorOs", "https://drive.google.com/uc?id=1uUWCfQ9D9tO6qhXMhB3L6C8Q39OZJQwD"));
        qs_list.add(new QsModel("Style DescendantOs", "https://drive.google.com/uc?id=1L9pg43imMlet4Vc-2rzbzYrZr4HZvT66"));
        qs_list.add(new QsModel("Style DotOs", "https://drive.google.com/uc?id=1D2kDl4pSShuIzudLM37vCaiIGV6_QmmN"));
        qs_list.add(new QsModel("Style MIUI", "https://drive.google.com/uc?id=1RnA32-Z7ENazHixlE8zEcpqxWLy7r_Ot"));
        qs_list.add(new QsModel("Style OctaviOs", "https://drive.google.com/uc?id=1WSBZtUYmXoOaHw7BzTRN6I68rnEumzVC"));
        qs_list.add(new QsModel("Style OxygenOs", "https://drive.google.com/uc?id=1n9hPNEaHb44HMxEwB-yF9WzSROYyx5y4"));

        return new QsAdapter(this, qs_list, loadingDialog);
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
