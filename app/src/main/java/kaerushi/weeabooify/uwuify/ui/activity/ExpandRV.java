package kaerushi.weeabooify.uwuify.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.ui.adapter.ExpandRvAdapter;
import kaerushi.weeabooify.uwuify.ui.models.ExpandRVModel;
import kaerushi.weeabooify.uwuify.ui.models.ExpandRVModel;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class ExpandRV extends AppCompatActivity {

    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_rv);

        // Loading dialog while enabling or disabling pack
        loadingDialog = new LoadingDialog(this);

        // RecyclerView
        RecyclerView container = findViewById(R.id.expand_container);
        container.setLayoutManager(new LinearLayoutManager(this));
        container.setAdapter(initExpandRv());
        container.setHasFixedSize(true);
    }

    private ExpandRvAdapter initExpandRv() {
        ArrayList<ExpandRVModel> list_item = new ArrayList<>();
        list_item.add(new ExpandRVModel("Transparent", R.color.transparent));
        list_item.add(new ExpandRVModel("Separated", R.drawable.container));
        list_item.add(new ExpandRVModel("3D", R.drawable.container));
        list_item.add(new ExpandRVModel("iOS", R.drawable.container));
        list_item.add(new ExpandRVModel("Inline", R.drawable.container));
        list_item.add(new ExpandRVModel("Outline", R.drawable.container));
        list_item.add(new ExpandRVModel("Outline Bottom", R.drawable.container));
        list_item.add(new ExpandRVModel("Outline Top", R.drawable.container));
        list_item.add(new ExpandRVModel("Outline Vertical", R.drawable.container));
        list_item.add(new ExpandRVModel("Outline Horizntal", R.drawable.container));
        list_item.add(new ExpandRVModel("Twoblock", R.drawable.container));

        return new ExpandRvAdapter(this, list_item, loadingDialog);
    }
}