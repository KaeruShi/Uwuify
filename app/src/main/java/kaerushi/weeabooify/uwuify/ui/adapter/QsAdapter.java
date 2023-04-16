package kaerushi.weeabooify.uwuify.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
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

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.installer.NotifStyleInstaller;
import kaerushi.weeabooify.uwuify.ui.models.QsModel;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class QsAdapter extends RecyclerView.Adapter<QsAdapter.ViewHolder> {

    Context context;
    ArrayList<QsModel> itemList;
    ArrayList<String> QS_KEY = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    LoadingDialog loadingDialog;
    int selectedItem = -1;

    public QsAdapter(Context context, ArrayList<QsModel> itemList, LoadingDialog loadingDialog) {
        this.context = context;
        this.itemList = itemList;
        this.loadingDialog = loadingDialog;
        
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
        holder.title.setText(itemList.get(position).getName());
        Glide.with(context).load(itemList.get(position).getUrl()).into(holder.preview);
        
        if (Prefs.getBoolean(QS_KEY.get(position))) {
            holder.title.setText(holder.title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
            holder.title.setTextColor(context.getResources().getColor(R.color.colorSuccess));
        } else {
            holder.title.setText(holder.title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
            holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
        }
        refreshButton(holder);

        enableOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewAttachedToWindow(@NonNull QsAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if (Prefs.getBoolean(QS_KEY.get(holder.getBindingAdapterPosition()))) {
            holder.title.setText(holder.title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
            holder.title.setTextColor(context.getResources().getColor(R.color.colorSuccess));
        } else {
            holder.title.setText(holder.title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
            holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
        }

        refreshButton(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    // Function for onClick events
    private void enableOnClickListener(QsAdapter.ViewHolder holder) {
        // Set onClick operation for each item
        holder.container.setOnClickListener(v -> {
            selectedItem = selectedItem == holder.getBindingAdapterPosition() ? -1 : holder.getBindingAdapterPosition();
            refreshLayout(holder);

            if (!Prefs.getBoolean(QS_KEY.get(holder.getBindingAdapterPosition()))) {
                holder.btn_disable.setVisibility(View.GONE);
                if (holder.btn_enable.getVisibility() == View.VISIBLE)
                    holder.btn_enable.setVisibility(View.GONE);
                else holder.btn_enable.setVisibility(View.VISIBLE);
            } else {
                holder.btn_enable.setVisibility(View.GONE);
                if (holder.btn_disable.getVisibility() == View.VISIBLE)
                    holder.btn_disable.setVisibility(View.GONE);
                else holder.btn_disable.setVisibility(View.VISIBLE);
            }
        });

        // Set onClick operation for Enable button
        holder.btn_enable.setOnClickListener(v -> {
            // Show loading dialog
            loadingDialog.show(context.getResources().getString(R.string.loading_dialog_wait));

            @SuppressLint("SetTextI18n") Runnable runnable = () -> {
                NotifStyleInstaller.enableOverlay(holder.getBindingAdapterPosition() + 1);

                ((Activity) context).runOnUiThread(() -> new Handler().postDelayed(() -> {
                    // Hide loading dialog
                    loadingDialog.hide();

                    // Change name to " - applied"
                    holder.title.setText(holder.title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
                    holder.title.setTextColor(context.getResources().getColor(R.color.colorSuccess));

                    // Change button visibility
                    holder.btn_enable.setVisibility(View.GONE);
                    holder.btn_disable.setVisibility(View.VISIBLE);
                    refreshBackground(holder);

                    Toast.makeText(Weeabooify.getAppContext(), context.getResources().getString(R.string.toast_applied), Toast.LENGTH_SHORT).show();
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
                NotifStyleInstaller.disable_pack(holder.getBindingAdapterPosition() + 1);

                ((Activity) context).runOnUiThread(() -> new Handler().postDelayed(() -> {
                    // Hide loading dialog
                    loadingDialog.hide();

                    // Change name back to original
                    holder.title.setText(holder.title.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
                    holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));

                    // Change button visibility
                    holder.btn_disable.setVisibility(View.GONE);
                    holder.btn_enable.setVisibility(View.VISIBLE);
                    refreshBackground(holder);

                    Toast.makeText(Weeabooify.getAppContext(), context.getResources().getString(R.string.toast_disabled), Toast.LENGTH_SHORT).show();
                }, 1000));
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }

    // Function to check for layout changes
    private void refreshLayout(ViewHolder holder) {
        int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisible = linearLayoutManager.findLastVisibleItemPosition();

        for (int i = firstVisible; i <= lastVisible; i++) {
            View view = linearLayoutManager.findViewByPosition(i);

            if (view != null) {
                LinearLayout child = view.findViewById(R.id.qs_child);

                if (!(view == holder.container) && child != null) {
                    child.findViewById(R.id.qs_enable).setVisibility(View.GONE);
                    child.findViewById(R.id.qs_disable).setVisibility(View.GONE);
                }
            }
        }
    }

    // Function to check for applied options
    @SuppressLint("SetTextI18n")
    private void refreshBackground(ViewHolder holder) {
        int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisible = linearLayoutManager.findLastVisibleItemPosition();

        for (int i = firstVisible; i <= lastVisible; i++) {
            View view = linearLayoutManager.findViewByPosition(i);

            if (view != null) {
                LinearLayout child = view.findViewById(R.id.qs_child);

                if (child != null) {
                    if (i == holder.getAbsoluteAdapterPosition() && Prefs.getBoolean(QS_KEY.get(i - (holder.getAbsoluteAdapterPosition() - holder.getBindingAdapterPosition()))))
                        child.setBackground(ContextCompat.getDrawable(context, R.drawable.container_selected));
                    else
                        child.setBackground(ContextCompat.getDrawable(context, R.drawable.container));
                }
            }
        }
    }

    private void refreshButton(ViewHolder holder) {
        if (holder.getBindingAdapterPosition() != selectedItem) {
            holder.btn_enable.setVisibility(View.GONE);
            holder.btn_disable.setVisibility(View.GONE);
        } else {
            if (Prefs.getBoolean(QS_KEY.get(selectedItem))) {
                holder.btn_enable.setVisibility(View.GONE);
                holder.btn_disable.setVisibility(View.VISIBLE);
            } else {
                holder.btn_enable.setVisibility(View.VISIBLE);
                holder.btn_disable.setVisibility(View.GONE);
            }
        }
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        
        TextView title;
        ImageView preview;
        Button btn_enable, btn_disable;
        LinearLayout container;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.qs_child);
            title = itemView.findViewById(R.id.qs_title);
            preview = itemView.findViewById(R.id.qs_preview);
            btn_enable = itemView.findViewById(R.id.qs_enable);
            btn_disable = itemView.findViewById(R.id.qs_disable);
        }
    }
}