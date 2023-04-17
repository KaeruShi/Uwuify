package kaerushi.weeabooify.uwuify.ui.adapter;

import static kaerushi.weeabooify.uwuify.ui.utils.VBHelpers.setDrawable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.installer.QsBackgroundInstaller;
import kaerushi.weeabooify.uwuify.ui.models.QsBackgroundModel;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class QsBackgroundAdapter extends RecyclerView.Adapter<QsBackgroundAdapter.ViewHolder> {

    Context context;
    private final ArrayList<QsBackgroundModel> itemList;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> QSB_KEY = new ArrayList<>();
    LoadingDialog loadingDialog;
    private final SparseBooleanArray mExpandedItems;

    public QsBackgroundAdapter(Context context, ArrayList<QsBackgroundModel> itemList, LoadingDialog loadingDialog) {
        this.context = context;
        this.itemList = itemList;
        this.loadingDialog = loadingDialog;
        mExpandedItems = new SparseBooleanArray();

        // Preference key
        for (int i = 1; i <= itemList.size(); i++)
            QSB_KEY.add("UwuifyComponentQSS" + i + ".overlay");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_option_qsb, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setDrawable(holder.container, ContextCompat.getDrawable(context, itemList.get(position).getBackground()));
        QsBackgroundModel qsPanelBgModel = itemList.get(position);
        holder.bind(qsPanelBgModel);
        holder.style_name.setText(itemList.get(position).getTitle());

        if (Prefs.getBoolean(QSB_KEY.get(position))) {
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
                QsBackgroundInstaller.enableOverlay(holder.getBindingAdapterPosition() + 1);

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
                QsBackgroundInstaller.disable_pack(holder.getBindingAdapterPosition() + 1);

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
                }, 1000));
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }

    // Function to check for applied options
    @SuppressLint("SetTextI18n")
    private void refreshName(ViewHolder holder) {
        int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisible = linearLayoutManager.findLastVisibleItemPosition();

        for (int i = firstVisible; i <= lastVisible; i++) {
            View view = linearLayoutManager.findViewByPosition(i);

            if (view != null) {
                LinearLayout child = view.findViewById(R.id.qsb_child);

                if (child != null) {
                    TextView title = child.findViewById(R.id.qsb_title);

                    if (i == holder.getAbsoluteAdapterPosition() && Prefs.getBoolean(QSB_KEY.get(i - (holder.getAbsoluteAdapterPosition() - holder.getBindingAdapterPosition())))) {
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
        TextView style_name;
        Button btn_enable, btn_disable;
        LinearLayout expand_view, container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            style_name = itemView.findViewById(R.id.qsb_title);
            container = itemView.findViewById(R.id.qsb_child);
            expand_view = itemView.findViewById(R.id.qsb_expand_view);
            btn_enable = itemView.findViewById(R.id.qsb_enable);
            btn_disable = itemView.findViewById(R.id.qsb_disable);

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

        public void bind(QsBackgroundModel rvModel) {

            if (mExpandedItems.get(getAdapterPosition())) {
                expand_view.setVisibility(View.VISIBLE);
            } else {
                expand_view.setVisibility(View.GONE);
            }
        }
    }
}
