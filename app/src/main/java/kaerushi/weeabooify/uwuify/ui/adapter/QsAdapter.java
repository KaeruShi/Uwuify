package kaerushi.weeabooify.uwuify.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.installer.NotifStyleInstaller;
import kaerushi.weeabooify.uwuify.installer.QsStyleInstaller;
import kaerushi.weeabooify.uwuify.ui.models.NotificationModel;
import kaerushi.weeabooify.uwuify.ui.models.QsModel;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;
import kaerushi.weeabooify.uwuify.utils.SystemUtil;

public class QsAdapter extends RecyclerView.Adapter<QsAdapter.ViewHolder> {

    Context context;
    ArrayList<QsModel> itemList;
    ArrayList<String> QS_KEY = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    LoadingDialog loadingDialog;
    private final SparseBooleanArray mExpandedItems;

    public QsAdapter(Context context, ArrayList<QsModel> itemList, LoadingDialog loadingDialog) {
        this.context = context;
        this.itemList = itemList;
        this.loadingDialog = loadingDialog;
        mExpandedItems = new SparseBooleanArray();

        // Preference key
        for (int i = 1; i <= itemList.size(); i++)
            QS_KEY.add("UwuifyComponentQS" + i + ".overlay");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_option_qs_style, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.style_name.setText(itemList.get(position).getName());
        Glide.with(context).load(itemList.get(position).getUrl()).placeholder(R.drawable.kaerushi).into(holder.preview);

        if (Objects.equals(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "AOSP")) {
            holder.btn_enable.setEnabled(false);
            holder.btn_enable.setAlpha(0.5f);
            holder.preview.setColorFilter(context.getColor(R.color.disabled));
            holder.summary.setVisibility(View.VISIBLE);
        }

        if (mExpandedItems.get(position)) {
            holder.expand_view.setVisibility(View.VISIBLE);
        } else {
            holder.expand_view.setVisibility(View.GONE);
        }

        if (Prefs.getBoolean(QS_KEY.get(position))) {
            holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
            holder.style_name.setTextColor(context.getResources().getColor(R.color.colorSuccess));
            holder.btn_enable.setVisibility(View.GONE);
            holder.btn_disable.setVisibility(View.VISIBLE);
        } else {
            holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
            holder.style_name.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
            holder.btn_enable.setVisibility(View.VISIBLE);
            holder.btn_disable.setVisibility(View.GONE);
        }

        if (Objects.equals(Prefs.loadPrefSettings(Weeabooify.getAppContext(), "selectedRomVariant"), "Havoc")) {
            switch (position) {
                case 0:
                case 2:
                case 3:
                    holder.btn_enable.setEnabled(false);
                    holder.btn_enable.setAlpha(0.5f);
                    holder.preview.setColorFilter(context.getColor(R.color.disabled));
                    holder.summary.setVisibility(View.VISIBLE);
                    break;
                default:
                    holder.btn_enable.setEnabled(true);
                    holder.btn_enable.setAlpha(1);
                    holder.preview.setColorFilter(context.getColor(R.color.transparent));
                    holder.summary.setVisibility(View.GONE);
                    break;

            }
        }

        enableOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    // Function for onClick events
    private void enableOnClickListener(ViewHolder holder) {

        // Set onClick operation for Enable button
        holder.btn_enable.setOnClickListener(v -> {
            // Show loading dialog
            loadingDialog.show(context.getResources().getString(R.string.loading_dialog_wait));

            @SuppressLint("SetTextI18n") Runnable runnable = () -> {
                QsStyleInstaller.enableOverlay(holder.getBindingAdapterPosition() + 1);

                ((Activity) context).runOnUiThread(() -> new Handler().postDelayed(() -> {
                    // Hide loading dialog
                    loadingDialog.hide();

                    // Change name to " - applied"
                    holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
                    holder.style_name.setTextColor(context.getResources().getColor(R.color.colorSuccess));

                    // Change button visibility
                    holder.btn_enable.setVisibility(View.GONE);
                    holder.btn_disable.setVisibility(View.VISIBLE);
                    refreshName(holder);

                    Toast.makeText(Weeabooify.getAppContext(), context.getResources().getString(R.string.toast_applied), Toast.LENGTH_SHORT).show();
                    SystemUtil.restartSystemUI();
                }, 1000));
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });

        // Set onClick operation for Disable button
        holder.btn_disable.setOnClickListener(v -> {
            // Show loading dialog
            loadingDialog.show(context.getResources().getString(R.string.loading_dialog_wait));

            Runnable runnable = () -> {
                QsStyleInstaller.disable_pack(holder.getBindingAdapterPosition() + 1);

                ((Activity) context).runOnUiThread(() -> new Handler().postDelayed(() -> {
                    // Hide loading dialog
                    loadingDialog.hide();

                    // Change name back to original
                    holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
                    holder.style_name.setTextColor(context.getResources().getColor(R.color.textColorPrimary));

                    // Change button visibility
                    holder.btn_disable.setVisibility(View.GONE);
                    holder.btn_enable.setVisibility(View.VISIBLE);
                    refreshName(holder);

                    Toast.makeText(Weeabooify.getAppContext(), context.getResources().getString(R.string.toast_disabled), Toast.LENGTH_SHORT).show();
                    SystemUtil.restartSystemUI();
                }, 1000));
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }

    // Function to check for applied options
    // Function to check for applied options
    @SuppressLint("SetTextI18n")
    private void refreshName(ViewHolder holder) {
        int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisible = linearLayoutManager.findLastVisibleItemPosition();

        for (int i = firstVisible; i <= lastVisible; i++) {
            View view = linearLayoutManager.findViewByPosition(i);

            if (view != null) {
                LinearLayout child = view.findViewById(R.id.qs_child);

                if (child != null) {
                    TextView title = child.findViewById(R.id.qs_title);

                    if (i == holder.getAbsoluteAdapterPosition() && Prefs.getBoolean(QS_KEY.get(i - (holder.getAbsoluteAdapterPosition() - holder.getBindingAdapterPosition())))) {
                        title.setText(title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
                        title.setTextColor(context.getResources().getColor(R.color.colorSuccess));
                    } else {
                        title.setText(title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
                        title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    }
                }
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView style_name, summary;
        ImageView preview;
        Button btn_enable, btn_disable;
        LinearLayout container, expand_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.qs_child);
            expand_view = itemView.findViewById(R.id.qs_expand_view);
            style_name = itemView.findViewById(R.id.qs_title);
            summary = itemView.findViewById(R.id.qs_summary);
            preview = itemView.findViewById(R.id.qs_preview);
            btn_enable = itemView.findViewById(R.id.qs_enable);
            btn_disable = itemView.findViewById(R.id.qs_disable);

            itemView.setOnClickListener(view -> {
                boolean isExpanded = mExpandedItems.get(getAdapterPosition());
                // Collapse all other items except for the current item
                for (int i = 0; i < mExpandedItems.size(); i++) {
                    int key = mExpandedItems.keyAt(i);
                    if (key != getAdapterPosition() && mExpandedItems.get(key)) {
                        mExpandedItems.put(key, false);
                        notifyItemChanged(key);
                    }
                }
                // Toggle the state of the current item
                mExpandedItems.put(getAdapterPosition(), !isExpanded);
                notifyItemChanged(getAdapterPosition());
            });
        }

    }
}
